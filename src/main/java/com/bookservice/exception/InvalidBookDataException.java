package com.bookservice.exception;

/**
 * Exception thrown when there are required fields missing in Book Model
 *
 * @author Spallya Omar
 */
public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super(message);
    }
}
