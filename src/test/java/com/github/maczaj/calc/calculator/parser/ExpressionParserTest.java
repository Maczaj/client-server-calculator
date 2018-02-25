package com.github.maczaj.calc.calculator.parser;

import com.github.maczaj.calc.calculator.parser.operator.ClosingBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OpeningBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.UnsupportedSymbolException;
import com.github.maczaj.calc.calculator.parser.operator.binary.MultiplicationOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.PowerOf2OperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.RootOperatorToken;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import com.github.maczaj.calc.calculator.parser.operator.binary.AdditionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.DivisionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.SubstractionOperatorToken;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ExpressionParserTest {

    @Rule
    public Timeout timeout = Timeout.seconds(5);

    private ExpressionParser parser = new ExpressionParser();

    @Test
    public void shouldReturnSingleTokenForOneInteger() {
        List<Token> result = parser.tokenize("123");
        assertEquals(1, result.size());
        assertEquals(123, getNthTokenAsNumber(result, 0), 0.005);
    }

    @Test
    public void shouldReturnSingleTokenForOneDouble() {
        List<Token> result = parser.tokenize("123.75");
        assertEquals(1, result.size());
        assertEquals(123.75, getNthTokenAsNumber(result, 0), 0.005);
    }

    private double getNthTokenAsNumber(List<Token> result, int index) {
        return (((NumberToken) result.get(index))).getValue();
    }

    @Test
    public void shouldCorrectlyParseTwoNumbersAndPlus() {
        List<Token> result = parser.tokenize("2+5");
        assertEquals(3, result.size());
        assertEquals(2, getNthTokenAsNumber(result, 0), 0.005);
        assertTrue(result.get(1) instanceof AdditionOperatorToken);
        assertEquals(5, getNthTokenAsNumber(result, 2), 0.005);
    }

    @Test
    public void shouldCorrectlyParseTwoNumbersAndSubstraction() {
        List<Token> result = parser.tokenize("2-5");
        assertEquals(3, result.size());
        assertEquals(2, getNthTokenAsNumber(result, 0), 0.005);
        assertTrue(result.get(1) instanceof SubstractionOperatorToken);
        assertEquals(5, getNthTokenAsNumber(result, 2), 0.005);
    }

    @Test
    public void shouldCorrectlyParseTwoNumbersAndMultiplication() {
        List<Token> result = parser.tokenize("2×5");
        assertEquals(3, result.size());
        assertEquals(2, getNthTokenAsNumber(result, 0), 0.005);
        assertTrue(result.get(1) instanceof MultiplicationOperatorToken);
        assertEquals(5, getNthTokenAsNumber(result, 2), 0.005);
    }

    @Test
    public void shouldCorrectlyParseTwoNumbersAndDivision() {
        List<Token> result = parser.tokenize("2÷5");
        assertEquals(3, result.size());
        assertEquals(2, getNthTokenAsNumber(result, 0), 0.005);
        assertTrue(result.get(1) instanceof DivisionOperatorToken);
        assertEquals(5, getNthTokenAsNumber(result, 2), 0.005);
    }

    @Test
    public void shouldCorrectlyParseRootAndNumber() {
        List<Token> res = parser.tokenize("√6");
        assertEquals(2, res.size());
        assertTrue(res.get(0) instanceof RootOperatorToken);
        assertEquals(6, getNthTokenAsNumber(res, 1), 0.000000005);
    }

    @Test
    public void shouldCorrectlyParseNumberAndPower() {
        List<Token> res = parser.tokenize("3²");
        assertEquals(2, res.size());
        assertEquals(3, getNthTokenAsNumber(res, 0), 0.000005);
        assertTrue(res.get(1) instanceof PowerOf2OperatorToken);
    }

    @Test
    public void shouldCorrectlyParseExpressionWithFourOperators() {
        List<Token> res = parser.tokenize("2,5+3,12-7,52345×6÷17");
        assertEquals(9, res.size());
    }

    @Test
    public void shouldCorrectlyParseSimpleExpressionContainingBrackets() {
        List<Token> res = parser.tokenize("2+(5-3)");
        assertEquals(7, res.size());
        assertTrue(res.get(2) instanceof OpeningBracketOperator);
        assertTrue(res.get(6) instanceof ClosingBracketOperator);
    }

    @Test(expected = UnsupportedSymbolException.class)
    public void shouldThrowExceptionWhenEncounteredUnknownSymbol() {
        parser.tokenize("2 + 5 ;;'");
        fail();
    }

}