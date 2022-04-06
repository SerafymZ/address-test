package com.addresstest.exception;

import com.addresstest.dto.basedto.ErrorDto;
import com.addresstest.dto.basedto.ResponseDto;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AddressExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Object>> handleException(NotFoundAddressException exception) {
        var error = new ErrorDto();
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(ResponseDto.failedResponseDto(List.of(error)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<String>> handleException(NullPointerException exception) {
        var error = new ErrorDto();
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(ResponseDto.failedResponseDto(List.of(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<String>> handleException(DataAccessException exception) {
        var error = new ErrorDto();
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(ResponseDto.failedResponseDto(List.of(error)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Object>> handleException(MethodArgumentNotValidException exception) {
        List<ErrorDto> errors = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            var error = new ErrorDto();
            error.setMessage("Field '" + fieldError.getField() + "' : " + fieldError.getDefaultMessage());
            errors.add(error);
        }
        return new ResponseEntity<>(ResponseDto.failedResponseDto(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Object>> handleException(ConstraintViolationException exception) {
        var error = new ErrorDto();
        error.setMessage(exception.getMessage());
        return new ResponseEntity<>(ResponseDto.failedResponseDto(List.of(error)), HttpStatus.BAD_REQUEST);
    }
}
