package com.github.maczaj.calc.calculator.parser.operator;

public class UnsupportedSymbolException extends IllegalArgumentException {

    public UnsupportedSymbolException(char symbol) {
        super(String.valueOf(symbol));
    }
}
