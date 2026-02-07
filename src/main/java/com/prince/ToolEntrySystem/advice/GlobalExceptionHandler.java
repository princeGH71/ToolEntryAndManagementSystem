package com.prince.ToolEntrySystem.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prince.ToolEntrySystem.exceptions.ResourceNotFoundException;
import com.prince.ToolEntrySystem.exceptions.RuntimeConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResponseNotFoundException(Exception e){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(Exception e){
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidEnum(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            String fieldName = invalidFormatEx.getPath().get(0).getFieldName();
            String invalidValue = invalidFormatEx.getValue().toString();
            String allowedValues = invalidFormatEx.getTargetType().getEnumConstants() != null ?
                    String.join(", ",
                            java.util.Arrays.stream(invalidFormatEx.getTargetType().getEnumConstants())
                                    .map(Object::toString).toArray(String[]::new))
                    : "";

            String message = String.format("Invalid value '%s' for field '%s'. Allowed values: [%s]",
                    invalidValue, fieldName, allowedValues);
            ApiError apiError=ApiError.builder().status(HttpStatus.NOT_ACCEPTABLE).message(message).build();
            return buildErrorResponseEntity(apiError);
        }

        // Fallback for other JSON parse errors
        ApiError apiError=ApiError.builder().status(HttpStatus.NOT_ACCEPTABLE).message(ex.getMessage()).build();
        return buildErrorResponseEntity(apiError);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
//        exception.printStackTrace();
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subErrors(errors)
                .build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }

}














