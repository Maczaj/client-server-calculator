package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.exception.InvalidExpressionStructureException;

import java.util.Stack;

public class DivisionOperatorToken extends BinaryOperatorToken {

    @Override
    public void processOperation(Stack<Token> tokenStack) throws IllegalStateException {
        super.processOperation(tokenStack);
        checkNextToken(tokenStack);
        Token first = tokenStack.pop();

        checkNextToken(tokenStack);
        Token second = tokenStack.pop();
        double secondVal = valueOf(second);

        if (secondVal == 0.0) {
            throw new InvalidExpressionStructureException();
        }

        double result = valueOf(first) / secondVal;

        tokenStack.push(new NumberToken(result));
    }

    @Override
    protected int getPriority() {
        return 1;
    }
}
