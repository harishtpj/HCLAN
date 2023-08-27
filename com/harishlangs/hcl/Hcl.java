package com.harishlangs.hcl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

public class Hcl {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    public static String homePath;
    
    public static void main(String[] args) throws IOException {
        String path = Hcl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File jarFile = new File(path);
        homePath = jarFile.getParentFile().getAbsolutePath();

        if (args.length > 1) {
            System.out.println("Usage: hcl [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }

    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) System.exit(65);
        if (hadRuntimeError) System.exit(70);
    }

    @SuppressWarnings("unchecked")
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        System.out.println(Meta.replStr);

        while (true) { 
            hadError = false;
            System.out.print("HCL .>> ");

            String line = reader.readLine();
            if (line == null) break;

            Scanner scanner = new Scanner(line);
            List<Token> tokens = scanner.scanTokens();

            Parser parser = new Parser(tokens);
            Object syntax = parser.parseRepl();

            if (hadError) continue;

            if (syntax instanceof List) {
                interpreter.interpret((List<Stmt>)syntax);
            } else if (syntax instanceof Expr) {
                String result = interpreter.interpret((Expr)syntax);
                if (result != null) {
                    System.out.printf(":= %s\n", result);
                }
            }
        }
    }

    public static void run(String src) {
        Scanner scanner = new Scanner(src);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        if (hadError) return;

        interpreter.interpret(statements);
    }

    static void error(int line, String msg) {
        report(line, "", msg);
    }
    
    private static void report(int line, String where, String msg) {
        System.err.printf("[Line %d]: Error %s: %s\n", line, where, msg);
        hadError = true;
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
          report(token.line, " at end", message);
        } else {
          report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error) {
        System.err.printf("[line %d](RuntimeError): %s\n", error.token.line, error.getMessage());
        hadRuntimeError = true;
    }
}