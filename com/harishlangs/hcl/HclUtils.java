package com.harishlangs.hcl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HclUtils {
    public static boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }

    public static boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
    
        return a.equals(b);
    }

    public static String stringify(Object object) {
        if (object == null) return "null";
    
        if (object instanceof Double) {
          String text = object.toString();
          if (text.endsWith(".0")) {
            text = text.substring(0, text.length() - 2);
          }
          return text;
        }
    
        return object.toString();
    }

    public static Object convertNative(Object object) {
        if (object instanceof Double) {
          String text = object.toString();
          if (text.endsWith(".0")) {
            text = text.substring(0, text.length() - 2);
            return Integer.parseInt(text);
          }
        }
        return object;
    }

    public static void checkNumberOperand(Token operator, Object operand) {
      if (operand instanceof Double) return;
      throw new RuntimeError(operator, "Operand must be a number.");
    }

    public static void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    public static void importIt(Token kw, String path) {
      try {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        Hcl.run(new String(bytes, Charset.defaultCharset()));
      } catch (IOException ex) {
        throw new RuntimeError(kw, "File not found: " + ex.getMessage());
      }
    }
}
