package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.Token;

import java.util.Stack;

public class SubstractionOperatorToken extends BinaryOperatorToken {

    @Override
    public void processOperation(Stack<Token> tokenStack) throws IllegalStateException {
        super.processOperation(tokenStack);
        checkNextToken(tokenStack);
        Token first = tokenStack.pop();

        checkNextToken(tokenStack);
        Token second = tokenStack.pop();

        double value = valueOf(first) - valueOf(second);

        tokenStack.push(new NumberToken(value));
    }

    @Override
    protected int getPriority() {
        return 0;
    }
}
