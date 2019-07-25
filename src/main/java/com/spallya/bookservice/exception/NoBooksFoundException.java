package com.spallya.bookservice.exception;

/**
 * @author Spallya Omar
 */
public class NoBooksFoundException extends RuntimeException {

    public NoBooksFoundException() {
        super("There are no books present in database...");
    }

    public NoBooksFoundException(String message) {
        super(message);
    }
}
