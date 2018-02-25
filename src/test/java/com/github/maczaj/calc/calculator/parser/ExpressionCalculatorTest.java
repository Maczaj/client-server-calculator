package com.github.maczaj.calc.calculator.parser;

import com.github.maczaj.calc.calculator.parser.operator.ClosingBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OpeningBracketOperator;
import org.junit.Test;
import com.github.maczaj.calc.calculator.parser.operator.binary.AdditionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.DivisionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.MultiplicationOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.SubstractionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.PowerOf2OperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.RootOperatorToken;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExpressionCalculatorTest {

    private ExpressionCalculator calculator = new ExpressionCalculator();

    @Test
    public void shouldCalculateForSingleNumber() {
        Token token = new NumberToken(2);
        double res = calculator.calculate(Collections.singletonList(token));
        assertEquals(2, res, 0.0000000001);
    }

    @Test
    public void shouldCalculateSimpleAddition() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new AdditionOperatorToken(), new NumberToken(3));
        double res = calculator.calculate(tokens);
        assertEquals(5, res, 0.000000000000001);
    }

    @Test
    public void shouldCalculateSimpleSubstraction() {
        List<Token> tokens = Arrays.asList(new NumberToken(5), new SubstractionOperatorToken(), new NumberToken(2));
        double res = calculator.calculate(tokens);
        assertEquals(3, res, 0.000000000000001);
    }

    @Test
    public void shouldCalculateSimpleDivision() {
        List<Token> tokens = Arrays.asList(new NumberToken(4), new DivisionOperatorToken(), new NumberToken(2));
        double res = calculator.calculate(tokens);
        assertEquals(2, res, 0.000000000000001);
    }

    @Test
    public void shouldCalculateSimpleMultiplication() {
        List<Token> tokens = Arrays.asList(new NumberToken(4), new MultiplicationOperatorToken(), new NumberToken(2));
        double res = calculator.calculate(tokens);
        assertEquals(8, res, 0.000000000000001);
    }

    @Test
    public void shouldCalculateSimplePower() {
        List<Token> tokens = Arrays.asList(new NumberToken(4), new PowerOf2OperatorToken());
        double res = calculator.calculate(tokens);
        assertEquals(16, res, 0.00000000000001);
    }

    @Test
    public void shouldCalculateSimpleRoot() {
        List<Token> tokens = Arrays.asList(new RootOperatorToken(), new NumberToken(9));
        double res = calculator.calculate(tokens);
        assertEquals(3, res, 0.00000000000001);
    }

    @Test
    public void shouldCalculateExpressionRespectingOperatorPrecedence() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new MultiplicationOperatorToken(), new NumberToken(3), new AdditionOperatorToken(), new NumberToken(4));
        double res = calculator.calculate(tokens);
        assertEquals(10, res, 0.00000000000001);
    }

    @Test
    public void shouldCalculateExpressionRespectingOperatorPrecedence2() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new AdditionOperatorToken(), new NumberToken(3), new MultiplicationOperatorToken(), new NumberToken(4));
        double res = calculator.calculate(tokens);
        assertEquals(14, res, 0.00000000000001);
    }

    @Test
    public void shouldCalculateForBinaryMixedWithRoot() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new AdditionOperatorToken(), new RootOperatorToken(), new NumberToken(9));
        double res = calculator.calculate(tokens);
        assertEquals(5, res, 0.000000001);
    }

    @Test
    public void shouldCalculateForBinaryMixedWithPower() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new AdditionOperatorToken(), new NumberToken(3), new PowerOf2OperatorToken());
        double res = calculator.calculate(tokens);
        assertEquals(11, res, 0.000000001);
    }

    @Test
    public void shouldReturnOneNumberWithinBrackets() {
        List<Token> tokens = Arrays.asList(new OpeningBracketOperator(), new NumberToken(2), new ClosingBracketOperator());
        double res = calculator.calculate(tokens);
        assertEquals(2, res, 0.00000000001);
    }

    @Test
    public void shouldCalculateSimpleExpressionWithBrackets() {
        List<Token> tokens = Arrays.asList(new NumberToken(3), new MultiplicationOperatorToken(), new OpeningBracketOperator(), new NumberToken(1), new SubstractionOperatorToken(), new NumberToken(5), new ClosingBracketOperator());
        double res = calculator.calculate(tokens);
        assertEquals(-12, res, 0.000000000001);
    }

    @Test
    public void shouldCalculateRootForExpressionInsideBrackets() {
        List<Token> tokens = Arrays.asList(new RootOperatorToken(), new OpeningBracketOperator(), new NumberToken(4), new AdditionOperatorToken(), new NumberToken(5), new ClosingBracketOperator());
        double res = calculator.calculate(tokens);
        assertEquals(3, res, 0.000000001);
    }

    @Test
    public void shouldCalculatePowerOfExpressionInsideBrackets() {
        List<Token> tokens = Arrays.asList(new NumberToken(2), new MultiplicationOperatorToken(), new OpeningBracketOperator(), new NumberToken(1), new AdditionOperatorToken(), new NumberToken(2), new ClosingBracketOperator(), new PowerOf2OperatorToken());
        double res = calculator.calculate(tokens);
        assertEquals(18, res, 0.000000000000001);
    }
}