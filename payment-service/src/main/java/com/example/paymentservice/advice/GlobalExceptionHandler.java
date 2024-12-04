package com.example.paymentservice.advice;



import com.example.paymentservice.exception.GeneralException;
import com.example.paymentservice.helper.MetricsHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        MetricsHelper.captureValidationErrorMetric();
        BindingResult bindingResult = ex.getBindingResult();
        Map<String,String> validationErrors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        log.debug("VALIDATION EXCEPTION : {}", validationErrors);
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleInventoryExceptions(GeneralException ex) {
        MetricsHelper.captureGeneralExceptionMetric();
        Map<String,String> validationErrors = new HashMap<>();
        validationErrors.put("message", ex.get().getDescription());
        validationErrors.put("code", ex.get().getCode());
        log.debug("GENERAL EXCEPTION : {} - {}", ex.get().getCode(), ex.get().getDescription());
        return new ResponseEntity<>(validationErrors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
