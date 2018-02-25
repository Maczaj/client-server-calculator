package com.github.maczaj.calc.calculator.parser.operator.unary;

import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Assert;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.NumberToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.AdditionOperatorToken;

import java.util.Stack;

public class RootOperatorTokenTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionNumberIsNotNext() {
        Stack<Token> stack = new Stack<>();
        stack.push(new AdditionOperatorToken());
        new RootOperatorToken().processOperation(stack);
    }

    @Test
    public void shouldCalculateForValidState() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(9));
        new RootOperatorToken().processOperation(stack);
        Assert.assertEquals(3, ((NumberToken) stack.pop()).getValue(), 0.0000000001);
    }

}