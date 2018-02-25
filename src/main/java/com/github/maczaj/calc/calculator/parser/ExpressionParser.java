package com.github.maczaj.calc.calculator.parser;

import com.github.maczaj.calc.calculator.parser.operator.ClosingBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OpeningBracketOperator;
import com.github.maczaj.calc.calculator.parser.operator.OperatorTokenFactory;
import com.github.maczaj.calc.calculator.parser.operator.UnsupportedSymbolException;

import java.util.*;

public class ExpressionParser {

    private static final Set<Character> OPENING_BRACKETS = new HashSet<>(Arrays.asList('(', '{', '['));
    private static final Set<Character> ENCLOSING_BRACKETS = new HashSet<>(Arrays.asList(')', '}', ']'));

    private OperatorTokenFactory operatorTokenFactory = new OperatorTokenFactory();

    public List<Token> tokenize(final String expression) {
        final List<Token> result = new LinkedList<>();
        final StringBuilder queue = replaceCommasWithDots(expression);
        final int len = queue.length();

        int currentIdx = 0;

        while (currentIdx < len) {
            char c = queue.charAt(currentIdx);
            if (Character.isDigit(c)) {
                final int numberEndIdx = getNumberEndIndex(queue, currentIdx);
                String number = queue.substring(currentIdx, numberEndIdx);
                result.add(new NumberToken(Double.valueOf(number)));
                currentIdx = numberEndIdx;
            } else if (operatorTokenFactory.isOperatorToken(c)) {
                result.add(operatorTokenFactory.forOperator(c));
                ++currentIdx;
            } else if (isOpeningBracket(c)) {
                result.add(new OpeningBracketOperator());
                ++currentIdx;
            } else if (isEnclosingBracket(c)) {
                result.add(new ClosingBracketOperator());
                ++currentIdx;
            } else {
                throw new UnsupportedSymbolException(c);
            }
        }

        return result;
    }

    private boolean isOpeningBracket(char c) {
        return OPENING_BRACKETS.contains(c);
    }

    private boolean isEnclosingBracket(char c) {
        return ENCLOSING_BRACKETS.contains(c);
    }

    private StringBuilder replaceCommasWithDots(String expression) {
        return new StringBuilder(expression.replaceAll(",", "."));
    }

    private int getNumberEndIndex(StringBuilder queue, int currentIdx) {
        while (currentIdx < queue.length() && (Character.isDigit(queue.charAt(currentIdx)) || queue.charAt(currentIdx) == '.')) {
            ++currentIdx;
        }
        return currentIdx;
    }
}
