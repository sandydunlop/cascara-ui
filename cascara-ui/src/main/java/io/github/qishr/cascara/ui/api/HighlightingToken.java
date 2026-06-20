package io.github.qishr.cascara.ui.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.qishr.cascara.common.lang.token.Token;

public class HighlightingToken {
    private static Map<HighlightingToken.Kind,String> kindToString = new HashMap<>();
    private static Map<String,HighlightingToken.Kind> stringToKind = new HashMap<>();

    private Kind kind = Kind.OTHER;
    private String lexeme = null;
    private int startIndex = -1;
    private int line = -1;
    private int column = -1;

    public enum Kind {
        // Semantic
        ANNOTATION,
        CONSTANT,
        METHOD,
        NAMESPACE,
        PRIMITIVE_TYPE,
        REFERENCE_TYPE,
        REGEX,
        VARIABLE,
        MODIFIER,

        // Syntatic
        CHARACTER,
        COMMENT,
        IDENTIFIER,
        KEYWORD,
        LITERAL,
        NUMBER,
        OPERATOR,
        OTHER,
        PUNCTUATION,
        STRING;

    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public static Kind getKindForToken(Token token) {
        return switch (token.getType().getCategory()) {
            case KEYWORD      -> Kind.KEYWORD;
            case STRING       -> Kind.STRING;
            case NUMBER       -> Kind.NUMBER;
            case BOOLEAN      -> Kind.NUMBER;
            case NULL         -> Kind.NUMBER;
            case COMMENT      -> Kind.COMMENT;
            case DOC_COMMENT  -> Kind.COMMENT;
            case TYPE_NAME    -> Kind.REFERENCE_TYPE;
            case FUNCTION_NAME-> Kind.METHOD;
            case FIELD_NAME,
                 PARAMETER_NAME,
                 IDENTIFIER   -> Kind.VARIABLE;
            case OPERATOR     -> Kind.OPERATOR;
            case PUNCTUATION,
                 SYMBOL,
                 DELIMITER    -> Kind.PUNCTUATION;
            case TEXT         -> Kind.OTHER;
            case META         -> Kind.OPERATOR;
            case STRUCTURAL   -> Kind.PUNCTUATION;
            default -> Kind.OTHER;
        };
    }

    public static void init() {
        kindToString.put(HighlightingToken.Kind.ANNOTATION, "annotation");
        kindToString.put(HighlightingToken.Kind.CONSTANT, "constant");
        kindToString.put(HighlightingToken.Kind.METHOD, "method");
        kindToString.put(HighlightingToken.Kind.MODIFIER, "modifier");
        kindToString.put(HighlightingToken.Kind.NAMESPACE, "namespace");
        kindToString.put(HighlightingToken.Kind.PRIMITIVE_TYPE, "primitive_type");
        kindToString.put(HighlightingToken.Kind.REFERENCE_TYPE, "reference_type");
        kindToString.put(HighlightingToken.Kind.REGEX, "regex");
        kindToString.put(HighlightingToken.Kind.VARIABLE, "variable");

        kindToString.put(HighlightingToken.Kind.CHARACTER, "character");
        kindToString.put(HighlightingToken.Kind.COMMENT, "comment");
        kindToString.put(HighlightingToken.Kind.IDENTIFIER, "variable");
        kindToString.put(HighlightingToken.Kind.KEYWORD, "keyword");
        kindToString.put(HighlightingToken.Kind.LITERAL, "literal");
        kindToString.put(HighlightingToken.Kind.NUMBER, "number");
        kindToString.put(HighlightingToken.Kind.OPERATOR, "operator");
        kindToString.put(HighlightingToken.Kind.OTHER, "other");
        kindToString.put(HighlightingToken.Kind.PUNCTUATION, "punctuation");
        kindToString.put(HighlightingToken.Kind.STRING, "string");

        for (HighlightingToken.Kind kind : kindToString.keySet()) {
            stringToKind.put(kindToString.get(kind), kind);
        }
    }

    public static Set<HighlightingToken.Kind> getKinds() {
        if (kindToString.isEmpty()) {
            init();
        }
        return kindToString.keySet();
    }

    public static HighlightingToken.Kind getKind(String name) {
        HighlightingToken.Kind  kind = stringToKind.get(name);
        if (kind == null) {
            return HighlightingToken.Kind.OTHER;
        } else {
            return kind;
        }
    }

    public static String getString(HighlightingToken.Kind kind) {
        String string = kindToString.get(kind);
        if (kind == null) {
            return "other";
        } else {
            return string;
        }
    }
}
