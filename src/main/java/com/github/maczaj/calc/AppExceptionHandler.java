package com.github.maczaj.calc;

import com.github.maczaj.calc.dto.ErrorResponse;
import com.github.maczaj.calc.exception.InvalidExpressionStructureException;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j
@ControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(InvalidExpressionStructureException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid input data"));
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(RuntimeException ex) {
        log.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Internal Server Error"));
    }

}
