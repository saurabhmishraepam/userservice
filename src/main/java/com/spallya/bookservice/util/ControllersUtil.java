package com.spallya.bookservice.util;

import com.spallya.bookservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Spallya Omar
 */
public class ControllersUtil {

    private ControllersUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> ResponseEntity<T> getInternalServerErrorResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ErrorDto> getInternalServerErrorResponseEntity(Exception ex) {
        return new ResponseEntity<>(Utils.getErrorDtoFromException(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<T> getOkResponseEntity(T returnObject) {
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> getCreatedResponseEntity(T returnObject) {
        return new ResponseEntity<>(returnObject, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> getNotFoundResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> getNoContentResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
