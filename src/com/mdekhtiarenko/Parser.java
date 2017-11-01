package com.mdekhtiarenko;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by mykola.dekhtiarenko on 11/1/17.
 */
public class Parser {

    private String operators = "+-*/";
    private String brackets = "()";
    private List<String> functions;

    public Parser() {
        functions = new ArrayList<>();
        functions.add("sin");
        functions.add("cos");
        functions.add("sqrt");
    }

    private boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < (operators+brackets).length(); i++) {
            if (token.charAt(0) == (operators+brackets).charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean isFunction(String token) {
        if (functions.contains(token)) return true;
        return false;
    }

    public int precedence(String token){
        if (token.equals("(") || token.equals("=")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

//    public List<String> convertToRPN(String expr) {
//        List<String> reverseNotation = new ArrayList<>();
//        StringTokenizer tokenizer = new StringTokenizer(expr, (operators+brackets), true);
//
//    }
}
