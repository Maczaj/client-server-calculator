package com.github.maczaj.calc.calculator.integral;

import com.github.maczaj.calc.dto.IntegralComputationRequest;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.touk.throwing.ThrowingFunction.unchecked;

public class IntegralCalculator {

    public double calculate(IntegralComputationRequest computationRequest) {
        final double lb = computationRequest.getLb();
        final double ub = computationRequest.getUb();
        final ForkJoinPool pool = new ForkJoinPool(computationRequest.getThreads());
        final double chunk = (ub - lb) / computationRequest.getSegments();

        List<Future<Double>> futures = IntStream.range(0, computationRequest.getSegments()).mapToObj(seg -> pool.submit(() -> {
            double offset = lb + seg * chunk;
            return Math.exp(offset + chunk) - Math.exp(offset);
        })).collect(Collectors.toList());


        return futures.parallelStream().map(unchecked(Future::get)).mapToDouble(Double::doubleValue).sum();
    }

}
