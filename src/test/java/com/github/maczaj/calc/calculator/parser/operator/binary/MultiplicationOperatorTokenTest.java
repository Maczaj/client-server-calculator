package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.NumberToken;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class MultiplicationOperatorTokenTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenOnlyOneOperandIsOnStack() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        new MultiplicationOperatorToken().processOperation(stack);
    }

    @Test
    public void shouldAddTwoNumbers() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        stack.push(new NumberToken(3));
        new MultiplicationOperatorToken().processOperation(stack);
        assertEquals(6, ((NumberToken) stack.pop()).getValue(), 0.000000000001);
    }

}