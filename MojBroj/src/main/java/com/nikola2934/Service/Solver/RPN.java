package com.nikola2934.Service.Solver;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

class RPN {

    //Racunanje postfix izraza
    static int racunaj(ArrayList<String> expr) throws
            ArithmeticException,
            EmptyStackException {
        Stack<Double> stack = new Stack<>();

        for (String element : expr) {
            switch (element) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    double delilac = stack.pop();
                    double deljenik = stack.pop();
                    if (deljenik % delilac == 0) {
                        stack.push(deljenik / delilac);
                    } else {
                        return 0;
                    }
                    break;
                default:
                    stack.push(Double.parseDouble(element));
                    break;
            }
        }
        double out = stack.pop();
        return (int) out;
    }


    private static boolean isWeakOperator(String c) {
        return c.equals("+") || c.equals("-");
    }

    private static boolean isStrongOperator(String c) {
        return c.equals("*") || c.equals("/");
    }


    //Konverzija iz postfix-a u infix izraz
    static String postToInfix(ArrayList<String> postfix) {
        Stack<String> s = new Stack<>();
        for (int i = 0; i < postfix.size(); i++) {
            String c = postfix.get(i);

            if (isWeakOperator(c)) {
                String b = s.pop();
                String a = s.pop();
                s.push("(" + a + c + b + ")");
            } else if (isStrongOperator(c)) {
                String b = s.pop();
                String a = s.pop();
                s.push(a + c + b);
            } else {
                s.push("" + c);
            }
        }

        String out = "";
        while (!s.empty()) {
            out += s.pop();
        }
        return out.substring(1, out.length() - 1);
    }

}
