package com.github.maczaj.calc.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ErrorResponse {

    @NonNull
    private String message;
}
