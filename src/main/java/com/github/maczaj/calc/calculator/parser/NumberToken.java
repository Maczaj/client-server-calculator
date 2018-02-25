package com.github.maczaj.calc.calculator.parser;

import lombok.Data;
import lombok.NonNull;

@Data
public class NumberToken extends Token {

    @NonNull
    private double value;

}
