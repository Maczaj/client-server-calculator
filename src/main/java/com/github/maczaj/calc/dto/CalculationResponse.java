package com.github.maczaj.calc.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CalculationResponse {

    @NonNull
    private double result;
}
