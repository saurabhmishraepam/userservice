package com.bookservice.exception;

/**
 * Exception thrown when the book of a particular id is not found in the system
 *
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
