package com.github.maczaj.calc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegralComputationRequest {

    @Min(value = 1)
    private int threads;
    @Min(value = 1)
    private int segments;
    private double lb;
    private double ub;
}
