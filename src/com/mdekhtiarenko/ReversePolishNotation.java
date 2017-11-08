package com.mdekhtiarenko;

import java.util.*;

/**
 * Created by mykola.dekhtiarenko on 11/1/17.
 */
public class ReversePolishNotation {

    private static final String NEGATIVE_SIGN = "ns";
    private String operators = "+-*/^";
    private String brackets = "()";
    private List<String> functions = Arrays.asList("sin", "cos", "sqrt");

    private List<String> reversePolishNotation = new ArrayList<>();
    private Deque<String> operationStack = new ArrayDeque<>();

    public ReversePolishNotation(String expr) throws Exception {
        parse(expr);
    }

    public List<String> getReversePolishNotation() {
        return reversePolishNotation;
    }


    private boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < (operators+brackets).length(); i++) {
            if (token.charAt(0) == (operators+brackets).charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String token) {
        if(token.equals("")) return false;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperand(String token){
        return token != null && token.matches("[+-]?\\d*\\.?\\d+");
    }

    private boolean isFunction(String token) {
//        System.out.println(token+" is function: "+functions.contains(token));
        if (functions.contains(token)) return true;
        return false;
    }

    private int precedence(String token){
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        if (token.equals("^") || isFunction(token)) return 4;
        return 5;
    }

    private void parse(String expr) throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(expr, (operators+brackets), true);
        String previous = "";
        String current = "";

        while (tokenizer.hasMoreTokens()){
            current = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(current))
                throw new Exception("Wrong expression!");

            if(current.equals(" ")) continue;

            if(isOperand(current)){
                if(previous.equals(NEGATIVE_SIGN))
                    reversePolishNotation.add("-"+current);
                else
                    reversePolishNotation.add(current);
            }
            else if(current.equals("(")){
                operationStack.add(current);
            }
            else if(current.equals(")")){
                String currentOperator = operationStack.pollLast();
                while (!operationStack.isEmpty()&&!currentOperator.equals("(")) {
                    reversePolishNotation.add(currentOperator);
                    currentOperator = operationStack.pollLast();
                }
            }
            else{
                if((previous.equals("")||isDelimiter(previous))&&current.equals("-")) {
                    previous = NEGATIVE_SIGN;
                    continue;
                }
                addOperatorToStack(current);
            }
            previous = current;
        }

        while (!operationStack.isEmpty())
            reversePolishNotation.add(operationStack.pollLast());
    }

    private void addOperatorToStack(String operator){
        if(operationStack.isEmpty()){
//            System.out.println("Macив порожній: "+operationStack.toString()+", тому '"+operator+"' додаю в масив.");
            operationStack.add(operator);
        }
        else{
            if(!(precedence(operator)>precedence(operationStack.getLast()))){
//                System.out.println("Macив: "+operationStack.toString()+"; Останній елемент '"+operationStack.getLast()+"' >= '"+operator+"';");
                reversePolishNotation.add(operationStack.pollLast());
                addOperatorToStack(operator);
            }
            else{
//                System.out.println("Macив: "+operationStack.toString()+"; Останній елемент '"+operationStack.getLast()+"' < '"+operator+"' тому '"+operator+"' додаю в масив.");
                operationStack.add(operator);
            }
        }
    }

    public double calculate() {
        double returnValue = 0;
        Stack<String> stack = new Stack<>();
        for(String t : reversePolishNotation){
            if(isOperand(t)){
                stack.push(t);
            }
            else{
                if(isFunction(t)){
                    int index = functions.indexOf(t);
                    double a = Double.valueOf(stack.pop());
                    switch (index){
                        case 0:
                            stack.push(String.valueOf(Math.sin(Math.toRadians(a))));
                            break;
                        case 1:
                            stack.push(String.valueOf(Math.cos(Math.toRadians(a))));
                            break;
                        case 2:
                            stack.push(String.valueOf(Math.sqrt(a)));
                            break;
                    }
                }
                else{
                    int index = operators.indexOf(t);
                    double a = Double.valueOf(stack.pop());
                    double b = Double.valueOf(stack.pop());
                    switch(index){
                        case 0:
                            stack.push(String.valueOf(a+b));
                            break;
                        case 1:
                            stack.push(String.valueOf(b-a));
                            break;
                        case 2:
                            stack.push(String.valueOf(a*b));
                            break;
                        case 3:
                            stack.push(String.valueOf(b/a));
                            break;
                        case 4:
                            stack.push(String.valueOf((int)b^(int)a));
                            break;
                    }
                }

            }
        }
        returnValue = Double.valueOf(stack.pop());

        return returnValue;

    }

    @Override
    public String toString() {
        return reversePolishNotation.toString();
    }
}
