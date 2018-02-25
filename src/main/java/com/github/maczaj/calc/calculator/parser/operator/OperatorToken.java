package com.github.maczaj.calc.calculator.parser.operator;

import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.exception.InvalidExpressionStructureException;

import java.util.Stack;

public abstract class OperatorToken extends Token implements Comparable<OperatorToken> {

    /**
     * Processes action specific for operator. It is assumed that operator will work directly on passed stack, including putting back result.
     * <p>
     * This method checks whether next token is also {@link OperatorToken}, thus implementors should start by calling this method.
     *
     * @param tokenStack stack representing expression to evaluate
     * @throws IllegalStateException thrown when operator cannot successfully execute, for example: binary operator requires 2 operands to be present on the stack
     */
    public void processOperation(Stack<Token> tokenStack) throws InvalidExpressionStructureException {
        if (!tokenStack.isEmpty() && tokenStack.peek() instanceof OperatorToken) {
            ((OperatorToken) tokenStack.pop()).processOperation(tokenStack);
        }
    }

    /**
     * Returns this operator's priority. It's essential to maintain correct order of performing arithmetic operations. Used internally by {@link OperatorToken#compareTo(OperatorToken)} method.
     *
     * @return operator's priority. Generally, the bigger, the higher priority
     */
    protected abstract int getPriority();


    @Override
    public int compareTo(OperatorToken o) {
        return this.getPriority() - o.getPriority();
    }

    protected void checkNextToken(Stack<Token> tokenStack) {
        if (!tokenStack.isEmpty() && isNextTokenOperation(tokenStack.peek())) {
            ((OperatorToken) tokenStack.pop()).processOperation(tokenStack);
        }
        if (nextTokenInvalid(tokenStack)) {
            throw new InvalidExpressionStructureException();
        }
    }

    private boolean isNumberToken(Token token) {
        return token instanceof NumberToken;
    }

    private boolean isNextTokenOperation(Token peek) {
        return peek instanceof OperatorToken;
    }

    private boolean nextTokenInvalid(Stack<Token> tokenStack) {
        if (tokenStack.isEmpty()) {
            return true;
        }
        Token token = tokenStack.peek();
        return token == null || !isNumberToken(token);
    }

    protected double valueOf(Token token) {
        return ((NumberToken) token).getValue();
    }
}
