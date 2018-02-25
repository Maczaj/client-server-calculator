package com.github.maczaj.calc.calculator.parser.operator.unary;

import com.github.maczaj.calc.calculator.parser.Token;
import org.junit.Assert;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.NumberToken;

import java.util.Stack;

public class PowerOf2OperatorTokenTest {

    @Test
    public void shouldCalculateSimplePower() {
        Stack<Token> stack = new Stack<>();
        stack.push(new NumberToken(3));
        new PowerOf2OperatorToken().processOperation(stack);
        Assert.assertEquals(9, ((NumberToken) stack.pop()).getValue(), 0.0000000000000001);
    }

}