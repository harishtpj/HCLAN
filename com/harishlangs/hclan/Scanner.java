package com.harishlangs.hclan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {
    private final String src;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",      TokenType.AND);
        keywords.put("class",    TokenType.CLASS);
        keywords.put("else",     TokenType.ELSE);
        keywords.put("false",    TokenType.FALSE);
        keywords.put("for",      TokenType.FOR);
        keywords.put("fun",      TokenType.FUN);
        keywords.put("if",       TokenType.IF);
        keywords.put("null",     TokenType.NULL);
        keywords.put("or",       TokenType.OR);
        keywords.put("print",    TokenType.PRINT);
        keywords.put("println",  TokenType.PRINTLN);
        keywords.put("return",   TokenType.RETURN);
        keywords.put("super",    TokenType.SUPER);
        keywords.put("self",     TokenType.SELF);
        keywords.put("true",     TokenType.TRUE);
        keywords.put("let",      TokenType.LET);
        keywords.put("while",    TokenType.WHILE);
        keywords.put("break",    TokenType.BREAK);
        keywords.put("continue", TokenType.CONTINUE);
        keywords.put("import",   TokenType.IMPORT);
        keywords.put("loop",     TokenType.LOOP);
        keywords.put("std",      TokenType.STD);
    }

    Scanner(String src) {
        this.src = src;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LEFT_PAREN); break;
            case ')': addToken(TokenType.RIGHT_PAREN); break;
            case '{': addToken(TokenType.LEFT_BRACE); break;
            case '}': addToken(TokenType.RIGHT_BRACE); break;
            case ',': addToken(TokenType.COMMA); break;
            case '.': addToken(TokenType.DOT); break;
            case '-': addToken(TokenType.MINUS); break;
            case '+': addToken(TokenType.PLUS); break;
            case ';': addToken(TokenType.SEMICOLON); break;
            case '*': addToken(TokenType.STAR); break;
            case '^': addToken(TokenType.CARET); break;
            case '=': addToken(TokenType.EQUAL); break;
            case '/': addToken(TokenType.SLASH); break;

            case '!':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case ':':
                addToken(match('=') ? TokenType.COLON_EQUAL : TokenType.COLON);
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
                break;
            
            // Comments
            case '#':
                if (match('[')) {
                    multilineComment();
                } else {
                    while (peek() != '\n' && !isAtEnd()) advance();
                }
                break;
            
            // Ignore whitespace.
            case ' ':
            case '\r':
            case '\t':
                break;
            
            case '\n':
                line++;
                break;
            
            case '"': string(); break;

            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Hclan.error(line, "Unexpected character.");
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();
    
        String text = src.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.IDENTIFIER;
        addToken(type);
    }

    private void number() {
        while (isDigit(peek())) advance();
    
        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
          advance(); // Consumes .
    
          while (isDigit(peek())) advance();
        }
    
        addToken(TokenType.NUMBER,
            Double.parseDouble(src.substring(start, current)));
    }

    private void string() {
        while (peek() != '"' && !isAtEnd()) {
          if (peek() == '\n') line++;
          advance();
        }
    
        if (isAtEnd()) {
          Hclan.error(line, "Unterminated string.");
          return;
        }
    
        advance(); // The closing ".
    
        // Trim the surrounding quotes.
        String value = src.substring(start + 1, current - 1);
        addToken(TokenType.STRING, value);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (src.charAt(current) != expected) return false;
    
        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return src.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= src.length()) return '\0';
        return src.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
                c == '_';
    }
    
    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= src.length();
    }

    private char advance() {
        return src.charAt(current++);
    }
    
    private void addToken(TokenType type) {
        addToken(type, null);
    }
    
    private void addToken(TokenType type, Object literal) {
        String text = src.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private void multilineComment() {
        while (peek() != ']' && !isAtEnd()) {
            if (peek() == '\n') line++;
            if (match('[')) multilineComment();
            advance();
        }
        advance(); // The closing ].
    }
}
