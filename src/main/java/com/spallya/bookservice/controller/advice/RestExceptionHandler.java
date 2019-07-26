package com.spallya.bookservice.controller.advice;

import com.spallya.bookservice.dto.ErrorDto;
import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.exception.InvalidBookDataException;
import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.util.ControllersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Class Containing Handlers for the Exceptions
 *
 * @author Spallya Omar
 */
@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handling Book Not Found Exception. This exception is thrown when the book
     * of a particular id is not found in the system
     *
     * @return ResponseEntity with not value and status as 404
     */
    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> bookNotFoundHandler(
            BookNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNotFoundResponseEntity();
    }

    /**
     * Handling No Books Found Exception. This exception is thrown when there are
     * no books found in the system
     *
     * @return ResponseEntity with not value and status as 204
     */
    @ExceptionHandler(NoBooksFoundException.class)
    protected ResponseEntity<Object> noBooksFoundHandler(
            NoBooksFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNoContentResponseEntity();
    }

    /**
     * Handling Invalid Book Data Exception. This exception is thrown when there are
     * required fields missing in Book Model
     *
     * @return ResponseEntity with Error DTO and status as 500
     */
    @ExceptionHandler(InvalidBookDataException.class)
    protected ResponseEntity<ErrorDto> invalidBookDataHandler(InvalidBookDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getInternalServerErrorResponseEntity(ex);
    }
}
