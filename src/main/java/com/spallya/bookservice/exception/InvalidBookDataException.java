package com.spallya.bookservice.exception;

/**
 * @author Spallya Omar
 */
public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super(message);
    }
}
