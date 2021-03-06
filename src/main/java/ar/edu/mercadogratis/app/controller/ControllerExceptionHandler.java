package ar.edu.mercadogratis.app.controller;

import ar.edu.mercadogratis.app.model.ApiError;
import ar.edu.mercadogratis.app.exceptions.ApiException;
import ar.edu.mercadogratis.app.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        return handleErrorResponse(e, HttpStatus.NOT_FOUND.value());
    }

    public ResponseEntity<ApiError> handleErrorResponse(ApiException e, int httpStatus) {
        return handleErrorResponse(buildApiError(e, httpStatus));
    }

    public ResponseEntity<ApiError> handleErrorResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }

    private ApiError buildApiError(ApiException e, int status) {
        return ApiError.builder()
                .cause(e.getCode())
                .message(e.getDescription())
                .status(status)
                .build();
    }
}
