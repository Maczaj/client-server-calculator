package com.github.maczaj.calc.calculator.parser.operator.unary;

import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.operator.OperatorToken;

import java.util.Stack;

public class RootOperatorToken extends OperatorToken {

    @Override
    public void processOperation(Stack<Token> tokenStack) throws IllegalStateException {
        checkNextToken(tokenStack);
        NumberToken token = (NumberToken) tokenStack.pop();

        final double computed = Math.sqrt(token.getValue());
        tokenStack.push(new NumberToken(computed));
    }

    @Override
    protected int getPriority() {
        return 2;
    }
}
