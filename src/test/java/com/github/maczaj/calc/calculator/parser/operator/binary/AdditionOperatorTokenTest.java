package com.github.maczaj.calc.calculator.parser.operator.binary;

import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.NumberToken;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class AdditionOperatorTokenTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenOnlyOneOperandIsOnStack() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        new AdditionOperatorToken().processOperation(stack);
    }

    @Test
    public void shouldAddTwoNumbers() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(2));
        stack.push(new NumberToken(3));
        new AdditionOperatorToken().processOperation(stack);
        assertEquals(5, ((NumberToken) stack.pop()).getValue(), 0.000000000001);
    }

}