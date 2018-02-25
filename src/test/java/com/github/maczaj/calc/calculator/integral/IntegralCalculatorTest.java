package com.github.maczaj.calc.calculator.integral;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import com.github.maczaj.calc.dto.IntegralComputationRequest;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RunWith(Parameterized.class)
public class IntegralCalculatorTest {

    private IntegralCalculator calculator = new IntegralCalculator();

    @Parameter
    public int numThreads;

    @Parameter(1)
    public int numSegments;

    @Parameters(name = "for {0} thread(s) within {1} segment(s)")
    public static Collection<Object[]> prepareParameters() {
        List<Object[]> data = new LinkedList<>();
        for (int threadCount = 1; threadCount <= 4; ++threadCount) {
            for (int segCount = 1; segCount <= 10; ++segCount) {
                data.add(new Object[]{threadCount, segCount});
            }
        }
        return data;
    }


    @Test
    public void shouldCalculateBetween0And1() {
        IntegralComputationRequest req = new IntegralComputationRequest(numThreads, numSegments, 0, 1);
        double res = calculator.calculate(req);
        Assert.assertEquals(1.7183, res, 0.0001);
    }

    @Test
    public void shouldCalculateBetween0And5() {
        IntegralComputationRequest req = new IntegralComputationRequest(numThreads, numSegments, 0, 5);
        double res = calculator.calculate(req);
        Assert.assertEquals(147.41315, res, 0.00001);
    }

    @Test
    public void shouldCalculateBetweenMinus5And2() {
        IntegralComputationRequest req = new IntegralComputationRequest(numThreads, numSegments, -5, 2);
        double res = calculator.calculate(req);
        Assert.assertEquals(7.3823, res, 0.0001);
    }

}