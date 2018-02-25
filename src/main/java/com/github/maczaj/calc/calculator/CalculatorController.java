package com.github.maczaj.calc.calculator;

import com.github.maczaj.calc.calculator.parser.ExpressionCalculator;
import com.github.maczaj.calc.calculator.parser.Token;
import com.github.maczaj.calc.dto.CalculationRequest;
import com.github.maczaj.calc.dto.IntegralComputationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.maczaj.calc.calculator.integral.IntegralCalculator;
import com.github.maczaj.calc.calculator.parser.ExpressionParser;
import com.github.maczaj.calc.dto.CalculationResponse;

import java.util.List;

@Controller
@RequestMapping("/api/calculation")
public class CalculatorController {

    private final ExpressionParser expressionParser = new ExpressionParser();
    private final ExpressionCalculator expressionCalculator = new ExpressionCalculator();
    private final IntegralCalculator integralCalculator = new IntegralCalculator();

    @PostMapping("/expression")
    public ResponseEntity<CalculationResponse> calculateExpressionResult(@RequestBody CalculationRequest request) {
        List<Token> tokenized = expressionParser.tokenize(request.getExpression());
        final double result = expressionCalculator.calculate(tokenized);
        return ResponseEntity.ok(new CalculationResponse(result));
    }

    @PostMapping("/integral")
    public ResponseEntity<CalculationResponse> calculateIntegral(@RequestBody IntegralComputationRequest request) {
        final double result = integralCalculator.calculate(request);
        return ResponseEntity.ok(new CalculationResponse(result));
    }

}
