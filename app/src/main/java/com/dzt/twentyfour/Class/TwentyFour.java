package com.dzt.twentyfour.Class;


import java.util.EmptyStackException;
import java.util.Stack;

public class TwentyFour {
    //final char [] operators = {'+', '-', '*', '/'};
    static double expectedResult;
    public static boolean isPossible(int [] numbers, double results) {
        expectedResult = results;

        String permute = "";
        for(int i = 0; i < 4; i++){
            switch (numbers[i])
            {
                case 10:
                    permute+="X";
                    break;
                case 11:
                    permute+="J";
                    break;
                case 12:
                    permute+="Q";
                    break;
                case 13:
                    permute+="K";
                    break;
                default:
                    permute+=numbers[i];
                    break;
            }
        }
        return permutation("", permute);

    }

    public static boolean permutation(String prefix, String s) {
        int n = s.length();
        if(n == 0){
            return evaluate(prefix);
        } else {
            for(int i = 0; i < n; i++) {
                if (permutation(prefix + s.charAt(i), s.substring(0,i) + s.substring(i+1))){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean evaluate(String numbers){
        double result;
        double [] numbersArray = new double[4];
        for (int i = 0; i< numbers.length(); i++){
            switch(numbers.charAt(i)) {
                case 'X':
                    numbersArray[i] = 10;
                    break;
                case 'J':
                    numbersArray[i] = 11;
                    break;
                case 'Q':
                    numbersArray[i] = 12;
                    break;
                case 'K':
                    numbersArray[i] = 13;
                    break;
                default:
                    numbersArray[i] = numbers.charAt(i) - 48;
            }
        }
        for(int a = 0; a < 4; a++){
            for(int b = 0; b < 4; b++){
                for(int c = 0; c < 4; c++){
                    result = evaluate(a, b, c, numbersArray);
                    if(result == expectedResult)
                        return true;
                }
            }
        }
        return false;
    }

    private static double evaluate(int a, int b, int c, double [] numbers)
    {
        double result = numbers[0];
        switch(a){
            case 0:
                result += numbers[1];
                break;
            case 1:
                result -= numbers[1];
                break;
            case 2:
                result *= numbers[1];
                break;
            case 3:
                result /= numbers[1];
                break;
        }

        switch(b){
            case 0:
                result += numbers[2];
                break;
            case 1:
                result -= numbers[2];
                break;
            case 2:
                result *= numbers[2];
                break;
            case 3:
                result /= numbers[2];
                break;
        }

        switch(c){
            case 0:
                result += numbers[3];
                break;
            case 1:
                result -= numbers[3];
                break;
            case 2:
                result *= numbers[3];
                break;
            case 3:
                result /= numbers[3];
                break;
        }
        return result;
    }

    public static String evaluateOperation(String operation, Stack<String> operationStack) {
        String res;

        if(balanceParenthesis(operation)) {
            double result = evaluateParenthesis(operationStack);
            if(result == Double.MIN_VALUE) {
                res = "";
            } else {
                res = result + "";
            }
        } else {
            res = "";
        }
        return res;
    }

    private static double evaluateParenthesis(Stack<String> tokens) {
        Stack<Double> valueStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        if(tokens.empty())
            return 0.0;

        try {
            while (!tokens.empty()) {
                String currentToken = tokens.pop();
                switch (currentToken) {
                    case "(":
                        operatorStack.push(currentToken);
                        break;
                    case ")":
                        while (!operatorStack.peek().equals("(")) {
                            String operator = operatorStack.pop();
                            double first = valueStack.pop();
                            double second = valueStack.pop();
                            double result = applyOperator(operator, first, second);
                            if (result == Double.MIN_VALUE)
                                return Double.MIN_VALUE;
                            valueStack.push(result);
                        }
                        operatorStack.pop();
                        break;
                    case "*":
                    case "/":
                    case "+":
                    case "-":
                        while (!operatorStack.empty() && hasPrecedence(currentToken, operatorStack.peek())) {
                            String operator = operatorStack.pop();
                            double first = valueStack.pop();
                            double second = valueStack.pop();
                            double result = applyOperator(operator, first, second);
                            if (result == Double.MIN_VALUE)
                                return Double.MIN_VALUE;
                            valueStack.push(result);
                        }
                        operatorStack.push(currentToken);
                        break;
                    default:
                        double number = Double.parseDouble(currentToken);
                        valueStack.push(number);
                        break;
                }
            }

            while (!operatorStack.empty()) {
                String operator = operatorStack.pop();
                double first = valueStack.pop();
                double second = valueStack.pop();
                double result = applyOperator(operator, first, second);
                if (result == Double.MIN_VALUE)
                    return Double.MIN_VALUE;
                valueStack.push(result);
            }
        } catch(EmptyStackException e) {
            return Double.MIN_VALUE;
        }
        return valueStack.pop();
    }

    private static boolean hasPrecedence(String first, String second) {
        return !(second.equals("(") || second.equals(")")) && !((first.equals("*") || first.equals("/")) && (second.equals("+") || second.equals("-")));
    }

    private static boolean balanceParenthesis(String operation) {
        int l = operation.length(), balance = 0;
        for(int i = 0; i < l; i++) {
            if(operation.charAt(i) == '(') {
                balance++;
            } else if (operation.charAt(i) == ')') {
                balance--;
            }
            if (balance < 0)
                return false;
        }
        return balance == 0;
    }

    private static double applyOperator(String operator, double first, double second) {
        switch (operator) {
            case "+":
                return second + first;
            case "-":
                return second - first;
            case "*":
                return second * first;
            case "/":
                if (first == 0)
                {
                    return Double.MIN_VALUE;
                }
                else
                    return second / first;
            default:
                return 0;
        }
    }
}
