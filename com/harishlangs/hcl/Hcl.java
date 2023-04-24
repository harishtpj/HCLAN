package com.harishlangs.hcl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.util.PythonInterpreter;


public class Hcl {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: hcl [script]");
            System.exit(64); 
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        ClassLoader classLoader = Hcl.class.getClassLoader();
        URL classLocation = classLoader.getResource("com/harishlangs/hcl/Hcl.class");
        String classPath = classLocation.getPath();
        String packagePath = classPath.replace("Hcl.class", "");
        String pyFilePath = packagePath + "preprocessor/proc.py";

        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            pyInterp.execfile(pyFilePath);
            PyObject preprocess = pyInterp.get("preprocess");
            PyObject preprocessPrgm = preprocess.__call__(new PyString(path));
            PyTuple res = (PyTuple) preprocessPrgm.__tojava__(PyTuple.class);

            PyList imports = (PyList) res.pyget(0).__tojava__(PyList.class);
            String proc_src = res.pyget(1).asString();

            
            for (int i = 0; i < imports.__len__(); i++) {
                String imported = imports.__getitem__(i).asString();
                run(imported);

                if (hadError) System.exit(65);
                if (hadRuntimeError) System.exit(70);
            }
            
            run(proc_src);

            if (hadError) System.exit(65);
            if (hadRuntimeError) System.exit(70);
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true) { 
          System.out.print("hcl .>> ");
          String line = reader.readLine();
          if (line == null) break;
          run(line);
          hadError = false;
        }
    }

    private static void run(String src) {
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
        System.err.printf("%s\n[line %d]\n", error.getMessage(), error.token.line);
        hadRuntimeError = true;
    }
}