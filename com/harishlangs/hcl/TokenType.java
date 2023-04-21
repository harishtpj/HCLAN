package com.harishlangs.hcl;

enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
    COLON,
  
    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, COLON_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,
    LESS_MINUS,
  
    // Literals.
    IDENTIFIER, STRING, NUMBER,
  
    // Keywords.
    AND, BREAK, CONTINUE, CLASS, ELSE, FALSE, FUN, 
    FOR, IF, IMPORT, LET, LOOP, NULL, OR, PRINT, PRINTLN, 
    RETURN, SELF, STD, SUPER, TRUE, WHILE, 
  
    EOF
  }
