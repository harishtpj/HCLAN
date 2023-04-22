package com.harishlangs.hcl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.harishlangs.hcl.Stmt.Expression;

class Parser {
    private static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    List<Stmt> parse() {
      List<Stmt> statements = new ArrayList<>();
      while (!isAtEnd()) {
        statements.add(declaration());
      }
  
      return statements; 
    }

    private Stmt statement() {
      if (match(TokenType.PRINT)) return printStatement();
      if (match(TokenType.PRINTLN)) return printLnStatement();
      if (match(TokenType.LEFT_BRACE)) return new Stmt.Block(block());
  
      return expressionStatement();
    }

    private Stmt printStatement() {
      Expr value = expression();
      consume(TokenType.SEMICOLON, "Expect ';' after value.");
      return new Stmt.Print(value);
    }

    private Stmt printLnStatement() {
      Expr value = expression();
      consume(TokenType.SEMICOLON, "Expect ';' after value.");
      Stmt print = new Stmt.Print(value);
      Stmt newline = new Stmt.Print(new Expr.Literal("\n"));
      return new Stmt.Block(Arrays.asList(print, newline));
    }

    private Stmt varDeclaration() {
      Token name = consume(TokenType.IDENTIFIER, "Expect variable name.");
  
      Expr initializer = null;
      if (match(TokenType.COLON_EQUAL)) {
        initializer = expression();
      }
  
      consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");
      return new Stmt.Let(name, initializer);
    }

    private Stmt expressionStatement() {
      Expr expr = expression();
      consume(TokenType.SEMICOLON, "Expect ';' after expression.");
      return new Stmt.Expression(expr);
    }

    private List<Stmt> block() {
      List<Stmt> statements = new ArrayList<>();
  
      while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
        statements.add(declaration());
      }
  
      consume(TokenType.RIGHT_BRACE, "Expect '}' after block.");
      return statements;
    }

    private Expr assignment() {
      Expr expr = equality();
  
      if (match(TokenType.COLON_EQUAL)) {
        Token equals = previous();
        Expr value = assignment();
  
        if (expr instanceof Expr.Variable) {
          Token name = ((Expr.Variable)expr).name;
          return new Expr.Assign(name, value);
        }
  
        error(equals, "Invalid assignment target."); 
      }
  
      return expr;
    }

    private Expr expression() {
      return assignment();
    }

    private Stmt declaration() {
      try {
        if (match(TokenType.LET)) return varDeclaration();
  
        return statement();
      } catch (ParseError error) {
        synchronize();
        return null;
      }
    }

    private Expr equality() {
        Expr expr = comparison();
        while (match(TokenType.BANG_EQUAL, TokenType.EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
      
        return expr;
    }

    private Expr comparison() {
        Expr expr = term();
    
        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
          Token operator = previous();
          Expr right = term();
          expr = new Expr.Binary(expr, operator, right);
        }
    
        return expr;
    }

    private Expr term() {
        Expr expr = factor();
    
        while (match(TokenType.MINUS, TokenType.PLUS)) {
          Token operator = previous();
          Expr right = factor();
          expr = new Expr.Binary(expr, operator, right);
        }
    
        return expr;
    }

    private Expr factor() {
        Expr expr = unary();
    
        while (match(TokenType.SLASH, TokenType.STAR)) {
          Token operator = previous();
          Expr right = unary();
          expr = new Expr.Binary(expr, operator, right);
        }
    
        return expr;
    }

    private Expr unary() {
        if (match(TokenType.BANG, TokenType.MINUS)) {
          Token operator = previous();
          Expr right = unary();
          return new Expr.Unary(operator, right);
        }
    
        return primary();
    }

    private Expr primary() {
        if (match(TokenType.FALSE)) return new Expr.Literal(false);
        if (match(TokenType.TRUE)) return new Expr.Literal(true);
        if (match(TokenType.NULL)) return new Expr.Literal(null);
    
        if (match(TokenType.NUMBER, TokenType.STRING)) {
          return new Expr.Literal(previous().literal);
        }

        if (match(TokenType.IDENTIFIER)) {
          return new Expr.Variable(previous());
        }
    
        if (match(TokenType.LEFT_PAREN)) {
          Expr expr = expression();
          consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.");
          return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
          if (check(type)) {
            advance();
            return true;
          }
        }
    
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
    
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }
    
    private Token peek() {
        return tokens.get(current);
    }
    
    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String message) {
        Hcl.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        advance();
    
        while (!isAtEnd()) {
          if (previous().type == TokenType.SEMICOLON) return;
    
          switch (peek().type) {
            case CLASS:
            case FUN:
            case LET:
            case FOR:
            case IF:
            case LOOP:
            case WHILE:
            case PRINT:
            case PRINTLN:
            case IMPORT:
            case RETURN:
              return;
          }
    
          advance();
        }
      }
}   
