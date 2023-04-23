package com.harishlangs.hcl;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.harishlangs.hcl.std.*;

public class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {
    private static class BreakException extends RuntimeException {}
    public Scanner stdin = new Scanner(System.in);

    final Environment globals = new Environment();
    private Environment environment = globals;
    private final Map<Expr, Integer> locals = new HashMap<>();


    Interpreter() {
      NativeModule HCLstd = new NativeModule();
      globals.importMod(HCLstd.getObjects());
    }

    void interpret(List<Stmt> statements) {
      try {
        for (Stmt statement : statements) {
          execute(statement);
        }
      } catch (RuntimeError error) {
        Hcl.runtimeError(error);
      }
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
      Object left = evaluate(expr.left);
    
      if (expr.operator.type == TokenType.OR) {
        if (isTruthy(left)) return left;
      } else {
        if (!isTruthy(left)) return left;
      }
    
      return evaluate(expr.right);
    }

    @Override
    public Object visitSetExpr(Expr.Set expr) {
      Object object = evaluate(expr.object);

      if (!(object instanceof HclInstance)) { 
        throw new RuntimeError(expr.name,"Only instances have fields.");
      }

      Object value = evaluate(expr.value);
      ((HclInstance)object).set(expr.name, value);
      return value;
    }

    @Override
    public Object visitSelfExpr(Expr.Self expr) {
      return lookUpVariable(expr.keyword, expr);
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double)right;
        }  

        // Unreachable.
        return null;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
      return lookUpVariable(expr.name, expr);
    }

    private Object lookUpVariable(Token name, Expr expr) {
      Integer distance = locals.get(expr);
      if (distance != null) {
        return environment.getAt(distance, name.lexeme);
      } else {
        return globals.get(name);
      }
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
    
        return a.equals(b);
    }

    private String stringify(Object object) {
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

    public Object convertNative(Object object) {
      if (object instanceof Double) {
        String text = object.toString();
        if (text.endsWith(".0")) {
          text = text.substring(0, text.length() - 2);
          return Integer.parseInt(text);
        }
      }
      return object;
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    private void execute(Stmt stmt) {
      stmt.accept(this);
    }
    
    void resolve(Expr expr, int depth) {
      locals.put(expr, depth);
    }

    void executeBlock(List<Stmt> statements, Environment environment) {
      Environment previous = this.environment;
      try {
        this.environment = environment;

        for (Stmt statement : statements) {
          execute(statement);
        }
      } finally {
        this.environment = previous;
      }
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
      executeBlock(stmt.statements, new Environment(environment));
      return null;
    }

    @Override
    public Void visitClassStmt(Stmt.Class stmt) {
      environment.define(stmt.name.lexeme, null);

      Map<String, HclFunction> classMethods = new HashMap<>();
      for (Stmt.Function method : stmt.classMethods) {
        HclFunction function = new HclFunction(method, environment, false);
        classMethods.put(method.name.lexeme, function);
      }
    
      HclClass metaclass = new HclClass(null, stmt.name.lexeme + " metaclass", classMethods);


      Map<String, HclFunction> methods = new HashMap<>();
      for (Stmt.Function method : stmt.methods) {
        HclFunction function = new HclFunction(method, environment, method.name.lexeme.equals("_init"));
        methods.put(method.name.lexeme, function);
      }

      HclClass klass = new HclClass(metaclass, stmt.name.lexeme, methods);
      environment.assign(stmt.name, klass);
      return null;
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
      evaluate(stmt.expression);
      return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {
      HclFunction function = new HclFunction(stmt, environment, false);
      environment.define(stmt.name.lexeme, function);
      return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
      if (isTruthy(evaluate(stmt.condition))) {
        execute(stmt.thenBranch);
      } else if (stmt.elseBranch != null) {
        execute(stmt.elseBranch);
      }
      return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
      Object value = evaluate(stmt.expression);
      System.out.print(stringify(value));
      return null;
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
      Object value = null;
      if (stmt.value != null) value = evaluate(stmt.value);
    
      throw new Return(value);
    }

    @Override
    public Void visitLetStmt(Stmt.Let stmt) {
      Object value = null;
      if (stmt.initializer != null) {
        value = evaluate(stmt.initializer);
      }

      environment.define(stmt.name.lexeme, value);
      return null;
    }

    @Override
    public Void visitLoopStmt(Stmt.Loop stmt) {
      try {
        while(true) {
          execute(stmt.body);
        }
      } catch (BreakException ex) {
        // Breaks Loop
      }
      return null;
    }

    @Override
    public Void visitBreakStmt(Stmt.Break stmt) {
      throw new BreakException();
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
      Object value = evaluate(expr.value);
      
      Integer distance = locals.get(expr);
      if (distance != null) {
        environment.assignAt(distance, expr.name, value);
      } else {
        globals.assign(expr.name, value);
      }

      return value;
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
      Object left = evaluate(expr.left);
      Object right = evaluate(expr.right); 

      switch (expr.operator.type) {
        case GREATER:
            checkNumberOperands(expr.operator, left, right);
            return (double)left > (double)right;
        case GREATER_EQUAL:
            checkNumberOperands(expr.operator, left, right);
            return (double)left >= (double)right;
        case LESS:
            checkNumberOperands(expr.operator, left, right);
            return (double)left < (double)right;
        case LESS_EQUAL:
            checkNumberOperands(expr.operator, left, right);
            return (double)left <= (double)right;
        case MINUS:
            checkNumberOperands(expr.operator, left, right);
            return (double)left - (double)right;
        case PLUS:

          if (left instanceof Double && right instanceof Double) {
            return (double)left + (double)right;
          } 
  
          if (left instanceof String && right instanceof String) {
            return (String)left + (String)right;
          }

          if (left instanceof String && right instanceof Double) {
            return (String)left + stringify(right);
          }

          if (left instanceof Double && right instanceof String) {
            return stringify(left) + (String)right;
          }

          throw new RuntimeError(expr.operator,"Operands must be two numbers or two strings.");

        case SLASH:
            checkNumberOperands(expr.operator, left, right);
            return (double)left / (double)right;
        case STAR:
            if (left instanceof Double && right instanceof Double) {
              return (double)left * (double)right;
            }

            if (left instanceof String && right instanceof Double) {
              return ((String)left).repeat((int)Math.floor((double)right));
            }

            if (left instanceof Double && right instanceof String) {
              return ((String)right).repeat((int)Math.floor((double)left));
            }

            throw new RuntimeError(expr.operator,"Operands must be two numbers or any one can be strings.");
        
        case CARET:
          checkNumberOperands(expr.operator, left, right);
          return Math.pow((double)left, (double)right);

        case BANG_EQUAL: return !isEqual(left, right);
        case EQUAL: return isEqual(left, right);
      }

      // Unreachable.
      return null;
    }

    @Override
    public Object visitCallExpr(Expr.Call expr) {
      Object callee = evaluate(expr.callee);

      List<Object> arguments = new ArrayList<>();
      for (Expr argument : expr.arguments) { 
        arguments.add(evaluate(argument));
      }

      if (!(callee instanceof HclCallable)) {
        throw new RuntimeError(expr.paren,"Can only call functions and classes.");
      }

      HclCallable function = (HclCallable)callee;
      if ((arguments.size() != function.arity()) && !function.isVaArg()) {
        throw new RuntimeError(expr.paren, "Expected " +
            function.arity() + " arguments but got " +
            arguments.size() + ".");
      }

      return function.call(this, arguments);
    }

    @Override
    public Object visitGetExpr(Expr.Get expr) {
      Object object = evaluate(expr.object);
      if (object instanceof HclInstance) {
        return ((HclInstance) object).get(expr.name);
      }

      throw new RuntimeError(expr.name,
          "Only instances have properties.");
    }

}
