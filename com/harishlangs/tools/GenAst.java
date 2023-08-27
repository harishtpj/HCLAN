package com.harishlangs.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenAst {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: genast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
            "Assign   : Token name, Expr value",
            "Binary   : Expr left, Token operator, Expr right",
            "Call     : Expr callee, Token paren, List<Expr> arguments",
            "Get      : Expr object, Token name",
            "Grouping : Expr expression",
            "Literal  : Object value",
            "Logical  : Expr left, Token operator, Expr right",
            "Set      : Expr object, Token name, Expr value",
            "Super    : Token keyword, Token method",
            "Self     : Token keyword",
            "Unary    : Token operator, Expr right",
            "Variable : Token name"
        ));

        defineAst(outputDir, "Stmt", Arrays.asList(
            "Block      : List<Stmt> statements",
            "Break      : ",
            "Class      : Token name, Expr.Variable superclass, List<Stmt.Function> methods, List<Stmt.Function> classMethods",
            "Expression : Expr expression",
            "Function   : Token name, List<Token> params," +
                  " List<Stmt> body",
            "If         : Expr condition, Stmt thenBranch," +
                  " Stmt elseBranch",
            "Print      : Expr expression",
            "Return     : Token keyword, Expr value",
            "Let        : Token name, Expr initializer",
            "Loop       : Stmt body",
            "Import     : Token keyword, Boolean isStd, Expr module"
    ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.harishlangs.hclan;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.printf("abstract class %s {\n", baseName);

        defineVisitor(writer, baseName, types);

        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim(); 
            defineType(writer, baseName, className, fields);
        }

        // The base accept() method.
        writer.println();
        writer.println("  abstract <R> R accept(Visitor<R> visitor);");

        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("  interface Visitor<R> {");

        for (String type : types) {
             String typeName = type.split(":")[0].trim();
             writer.printf("    R visit%s%s(%s %s);\n", typeName, baseName, typeName, baseName.toLowerCase());
        }

        writer.println("  }");
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.printf("  static class %s extends %s {\n", className, baseName);
        writer.printf("    %s (%s) {\n", className, fieldList);

        // Store parameters in fields.
        String[] fields;
        if (fieldList.isEmpty()) {
            fields = new String[0];
        } else {
            fields = fieldList.split(", ");
        }
        
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.printf("      this.%s = %s;\n", name, name);
        }

        writer.println("    }");

        // Visitor pattern.
        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.printf("      return visitor.visit%s%s(this);\n", className, baseName);
        writer.println("    }");

        // Fields.
        writer.println();
        for (String field : fields) {
            writer.printf("    final %s;\n", field);
        }

        writer.println("  }");
    }
}