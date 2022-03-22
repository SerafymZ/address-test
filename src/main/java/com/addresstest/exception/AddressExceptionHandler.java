package com.addresstest.exception;

import com.addresstest.dto.basedto.ErrorDto;
import com.addresstest.dto.basedto.ResponseDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class AddressExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<String>> handleException(NotFoundAddressException exception) {
        return new ResponseEntity<>(ResponseDto.failedResponseDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<String>> handleException(EmptyResultDataAccessException exception) {
        return new ResponseEntity<>(ResponseDto.failedResponseDto(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
