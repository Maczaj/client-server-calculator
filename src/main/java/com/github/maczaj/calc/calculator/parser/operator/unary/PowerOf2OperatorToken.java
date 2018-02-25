package com.github.maczaj.calc.calculator.parser.operator.unary;

import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.operator.OperatorToken;

import java.util.Stack;

public class PowerOf2OperatorToken extends OperatorToken {

    @Override
    public void processOperation(Stack<Token> tokenStack) throws IllegalStateException {
        checkNextToken(tokenStack);

        final double res = Math.pow(((NumberToken) tokenStack.pop()).getValue(), 2);
        tokenStack.push(new NumberToken(res));
    }

    @Override
    protected int getPriority() {
        return 2;
    }
}
