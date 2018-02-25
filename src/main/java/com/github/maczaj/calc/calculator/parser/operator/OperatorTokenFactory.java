package com.github.maczaj.calc.calculator.parser.operator;

import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.calculator.parser.operator.binary.MultiplicationOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.RootOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.AdditionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.DivisionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.binary.SubstractionOperatorToken;
import com.github.maczaj.calc.calculator.parser.operator.unary.PowerOf2OperatorToken;

import java.util.HashMap;
import java.util.Map;

public class OperatorTokenFactory {

    private static final Map<Character, Class> TOKEN_TO_IMPLEMENTOR_MAP = new HashMap<>();

    static {
        TOKEN_TO_IMPLEMENTOR_MAP.put('+', AdditionOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('-', SubstractionOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('×', MultiplicationOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('÷', DivisionOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('÷', DivisionOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('√', RootOperatorToken.class);
        TOKEN_TO_IMPLEMENTOR_MAP.put('²', PowerOf2OperatorToken.class);
    }

    public boolean isOperatorToken(char symbol) {
        return TOKEN_TO_IMPLEMENTOR_MAP.keySet().contains(symbol);
    }

    public Token forOperator(char c) {
        try {
            return (OperatorToken) TOKEN_TO_IMPLEMENTOR_MAP.get(c).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Exception during instation of operator entity", e);
        }
    }
}
