package com.bonar.Shelf_Guard.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Data
@Builder
@RestControllerAdvice
public class ApiResponse <T>{
    private HttpStatusCode statusCode;
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .statusCode(status)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return of(HttpStatus.OK, message, data);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return of(HttpStatus.CREATED, message, data);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return of(status, message, null);
    }

}
