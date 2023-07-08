package com.craftinginterpreters.sohailrajdev97.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scanner {

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {

        int i = 0, currentLine = 1;

        while (i < source.length()) {

            char c = source.charAt(i);

            switch (c) {
                case '(':
                    tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, currentLine));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, currentLine));
                    break;
                case '{':
                    tokens.add(new Token(TokenType.LEFT_BRACE, "{", null, currentLine));
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RIGHT_BRACE, "}", null, currentLine));
                    break;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ",", null, currentLine));
                    break;
                case '.':
                    tokens.add(new Token(TokenType.DOT, ".", null, currentLine));
                    break;
                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", null, currentLine));
                    break;
                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", null, currentLine));
                    break;
                case ';':
                    tokens.add(new Token(TokenType.SEMICOLON, ";", null, currentLine));
                    break;
                case '*':
                    tokens.add(new Token(TokenType.ASTERIX, "*", null, currentLine));
                    break;

                case '!': {
                    if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.BANG_EQUAL, "!=", null, currentLine));
                        i++;
                    } else {
                        tokens.add(new Token(TokenType.BANG, "!", null, currentLine));
                    }
                    break;
                }
                case '=': {
                    if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, currentLine));
                        i++;
                    } else {
                        tokens.add(new Token(TokenType.EQUAL, "=", null, currentLine));
                    }
                    break;
                }
                case '<': {
                    if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.LESS_EQUAL, "<=", null, currentLine));
                        i++;
                    } else {
                        tokens.add(new Token(TokenType.LESS, "<", null, currentLine));
                    }
                    break;
                }
                case '>': {
                    if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                        tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", null, currentLine));
                        i++;
                    } else {
                        tokens.add(new Token(TokenType.GREATER, ">", null, currentLine));
                    }
                    break;
                }
                case '/': {
                    if (i + 1 < source.length() && source.charAt(i + 1) == '/') {
                        int j = i + 1;
                        while (j < source.length() && source.charAt(j) != '\n') {
                            j++;
                        }
                        i = j - 1;
                    } else {
                        tokens.add(new Token(TokenType.SLASH, "/", null, currentLine));
                    }
                    break;
                }
                case '"': {
                    int j = i + 1;
                    while (j < source.length() && source.charAt(j) != '"') {
                        j++;
                    }
                    if (j == source.length()) {
                        throw new RuntimeException("Unterminated string.");
                    }
                    tokens.add(new Token(TokenType.STRING, source.substring(i + 1, j), source.substring(i + 1, j),
                            currentLine));
                    i = j;
                    break;
                }
                case '\n':
                    currentLine++;
                    break;
                case ' ':
                case '\r':
                case '\t':
                    break;

                default:
                    if (isDigit(c)) {
                        int j = i;
                        while (j < source.length() && (isDigit(source.charAt(j)) || source.charAt(j) == '.')) {
                            j++;
                        }
                        tokens.add(new Token(TokenType.NUMBER, source.substring(i, j),
                                Double.parseDouble(source.substring(i, j)), currentLine));
                        i = j - 1;
                    } else if (isIdentifierChar(c)) {
                        int j = i;
                        while (j < source.length() && isIdentifierChar(source.charAt(j))) {
                            j++;
                        }
                        String lexeme = source.substring(i, j);
                        TokenType type = keywords.get(lexeme);
                        if (type == null) {
                            type = TokenType.IDENTIFIER;
                        }
                        tokens.add(new Token(type, lexeme, type == TokenType.IDENTIFIER ? lexeme : null,
                                currentLine));
                        i = j - 1;
                    } else {
                        throw new RuntimeException("Unexpected character " + c + " at line " + currentLine + ".");
                    }
            }

            i++;
        }

        tokens.add(new Token(TokenType.EOF, "", null, currentLine));
        return tokens;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isIdentifierChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    // The source code being scanned.
    private String source;

    private static HashMap<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("and", TokenType.AND);
        keywords.put("class", TokenType.CLASS);
        keywords.put("else", TokenType.ELSE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("fun", TokenType.FUN);
        keywords.put("for", TokenType.FOR);
        keywords.put("if", TokenType.IF);
        keywords.put("nil", TokenType.NIL);
        keywords.put("or", TokenType.OR);
        keywords.put("print", TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("super", TokenType.SUPER);
        keywords.put("this", TokenType.THIS);
        keywords.put("true", TokenType.TRUE);
        keywords.put("var", TokenType.VAR);
        keywords.put("while", TokenType.WHILE);
    }

    private List<Token> tokens = new ArrayList<>();
}
