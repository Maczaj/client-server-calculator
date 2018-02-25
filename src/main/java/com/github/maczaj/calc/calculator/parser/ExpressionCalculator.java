package com.github.maczaj.calc.calculator.parser;


import com.github.maczaj.calc.calculator.parser.operator.ClosingBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OpeningBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.BinaryOperatorToken;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ExpressionCalculator {

    public double calculate(List<Token> tokens) {
        final Stack<Token> output = new Stack<>();
        final Stack<Token> operatorStack = new Stack<>();

        Collections.reverse(tokens);

        preprocess(tokens, output, operatorStack);

        writeRemainingOperators(output, operatorStack);

        return doCalculate(output);
    }

    private void preprocess(List<Token> tokens, Stack<Token> output, Stack<Token> operatorStack) {
        tokens.forEach(token -> {
            if (token instanceof NumberToken) {
                output.push(token);
            } else if (token instanceof OperatorToken) {
                handleOperatorToken(operatorStack, output, token);
            } else if (token instanceof ClosingBracketOperator) {
                operatorStack.push(token);
            } else if (token instanceof OpeningBracketOperator) {
                moveOperatorsToQueue(output, operatorStack);
            }
        });
    }

    private double doCalculate(Stack<Token> output) {
        while (!output.isEmpty()) {
            Token token = output.pop();
            if (token instanceof OperatorToken) {
                ((OperatorToken) token).processOperation(output);
            } else if (token instanceof NumberToken) {
                return ((NumberToken) token).getValue();
            }
        }
        throw new IllegalStateException("Unexpected state rached");
    }

    private void writeRemainingOperators(Stack<Token> output, Stack<Token> operatorStack) {
        while (!operatorStack.isEmpty()) {
            output.add(operatorStack.pop());
        }
    }

    private void moveOperatorsToQueue(Stack<Token> output, Stack<Token> operatorStack) {
        Token operator;
        do {
            operator = operatorStack.pop();
            if (!(operator instanceof ClosingBracketOperator)) {
                output.push(operator);
            } else {
                return;
            }
        } while (!operatorStack.isEmpty());
    }

    private void handleOperatorToken(Stack<Token> operatorStack, Stack<Token> output, Token token) {
        if (token instanceof BinaryOperatorToken) {
            while (!operatorStack.isEmpty() && !(operatorStack.peek() instanceof ClosingBracketOperator) && isOperatorWithGreaterOrEqualPrecendenceOnTop((BinaryOperatorToken) token, operatorStack)) {
                output.push(operatorStack.pop());
            }
        }
        operatorStack.push(token);
    }

    private boolean isOperatorWithGreaterOrEqualPrecendenceOnTop(BinaryOperatorToken token, Stack<Token> operatorStack) {
        return token.compareTo((OperatorToken) operatorStack.peek()) <= 0;
    }
}
