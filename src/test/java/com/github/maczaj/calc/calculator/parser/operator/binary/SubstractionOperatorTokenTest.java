package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class SubstractionOperatorTokenTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionForInvalid() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        new SubstractionOperatorToken().processOperation(stack);
    }

    @Test
    public void shouldSubstractTwoNumbers() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(3));
        stack.push(new NumberToken(5));
        new SubstractionOperatorToken().processOperation(stack);
        assertEquals(2, ((NumberToken) stack.pop()).getValue(), 0.000000000001);
    }

}