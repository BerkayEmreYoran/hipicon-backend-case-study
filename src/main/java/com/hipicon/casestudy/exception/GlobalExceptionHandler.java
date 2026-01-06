package com.hipicon.casestudy.exception;

import com.hipicon.casestudy.base.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Response<String>> handleApp(AppException ex) {
        Response<String> response = new Response<>();
        response.setData(null);
        response.setError(ex.getMessage());
        response.setMetaData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        Response<String> response = new Response<>();
        response.setData(null);
        response.setError(message);
        response.setMetaData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleAll(Exception ex) {
        ex.printStackTrace();
        Response<String> response = new Response<>();
        response.setData(null);
        response.setError("Bir hata oluştu");
        response.setMetaData(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
