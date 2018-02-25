package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.NumberToken;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class DivisionOperatorTokenTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenOnlyOneOperandIsOnStack() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        new DivisionOperatorToken().processOperation(stack);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenDividingByZero() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(0));
        stack.push(new NumberToken(4));
        new DivisionOperatorToken().processOperation(stack);
    }

    @Test
    public void shouldDivideTwoNumbers() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        stack.push(new NumberToken(4));
        new DivisionOperatorToken().processOperation(stack);
        assertEquals(2, ((NumberToken) stack.pop()).getValue(), 0.000000000001);
    }

}