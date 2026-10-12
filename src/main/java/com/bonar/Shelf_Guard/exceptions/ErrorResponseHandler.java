package com.bonar.Shelf_Guard.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int statusCode;
}
