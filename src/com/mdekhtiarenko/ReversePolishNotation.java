package com.mdekhtiarenko;

import java.util.*;

/**
 * Created by mykola.dekhtiarenko on 11/1/17.
 */
public class ReversePolishNotation {

    private static final String NEGATIVE_SIGN = "nv";
    private String operators = "+-*/^";
    private String brackets = "()";
    private List<String> functions;

    private List<String> reverseNotation = new ArrayList<>();
    private Deque<String> operationStack = new ArrayDeque<>();

    public ReversePolishNotation() {
        functions = new ArrayList<>();
        functions.add("sin");
        functions.add("cos");
        functions.add("sqrt");
    }

//    private boolean isDelimiter(String token) {
//        if (token.length() != 1) return false;
//        for (int i = 0; i < (operators+brackets).length(); i++) {
//            if (token.charAt(0) == (operators+brackets).charAt(i)) return true;
//        }
//        return false;
//    }

    private boolean isOperator(String token) {
        if(token.equals("")) return false;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperand(String token){
        return token != null && token.matches("[n]?\\d*\\.?\\d+");
    }

    private boolean isFunction(String token) {
        if (functions.contains(token)) return true;
        return false;
    }

    public int precedence(String token){
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public List<String> parse(String expr) {

        StringTokenizer tokenizer = new StringTokenizer(expr, (operators+brackets), true);
        while (tokenizer.hasMoreTokens()){
            String previous = "";
            String current = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(current))
                throw new RuntimeException("Wrong expression!");

            if(current.equals(" ")) continue;

            if(isOperand(current)){
                System.out.println(previous+" "+current);
                if(previous.equals(NEGATIVE_SIGN))
                    reverseNotation.add("-"+current);
                else
                    reverseNotation.add(current);
            }
            else if(isFunction(current)){
                operationStack.add(current);
            }
            else if(current.equals("(")){
                operationStack.add(current);
            }
            else if(current.equals(")")){
                String currentOperator = operationStack.pollLast();
                while (!operationStack.isEmpty()&&!currentOperator.equals("(")) {
                    reverseNotation.add(currentOperator);
                    currentOperator = operationStack.pollLast();
                }
            }
            else{
                if(isOperator(previous)&&current.equals("-")) {
                    previous = NEGATIVE_SIGN;
                    continue;
                }
                addOperatorToStack(current);
            }
            previous = current;
        }
        reverseNotation.addAll(operationStack);
        return reverseNotation;
    }


    private void addOperatorToStack(String operator){
        if(operationStack.isEmpty()){
            operationStack.add(operator);
        }
        else{
            if(precedence(operationStack.getLast())>precedence(operator)){
                reverseNotation.add(operationStack.pollLast());
                addOperatorToStack(operator);
            }
            else{
                operationStack.add(operator);
            }
        }
    }
}
