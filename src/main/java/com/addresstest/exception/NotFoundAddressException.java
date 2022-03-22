package com.addresstest.exception;

public class NotFoundAddressException extends RuntimeException{
    public  NotFoundAddressException(String message) {
        super(message);
    }
}
