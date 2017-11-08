package com.mdekhtiarenko;


public class Main {

    public static void main(String [] args){
        ReversePolishNotation rpn = null;
        try {
            rpn = new ReversePolishNotation("-5+6*(8-4)+sin(1)");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(rpn.calculate());
    }


}
