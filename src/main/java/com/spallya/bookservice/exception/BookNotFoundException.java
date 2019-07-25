package com.spallya.bookservice.exception;

/**
 * @author Spallya Omar
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book Not Found...");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
