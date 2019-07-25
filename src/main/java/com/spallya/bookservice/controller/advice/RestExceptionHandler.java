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
 * @author Spallya Omar
 */
@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> bookNotFoundHandler(
            BookNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNotFoundResponseEntity();
    }

    @ExceptionHandler(NoBooksFoundException.class)
    protected ResponseEntity<Object> noBooksFoundHandler(
            NoBooksFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNoContentResponseEntity();
    }

    @ExceptionHandler(InvalidBookDataException.class)
    protected ResponseEntity<ErrorDto> invalidUserDataHandler(InvalidBookDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getInternalServerErrorResponseEntity(ex);
    }
}
