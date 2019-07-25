package com.spallya.bookservice.util;

import com.spallya.bookservice.dto.ErrorDto;
import com.spallya.bookservice.model.Book;
import org.apache.commons.lang3.StringUtils;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    static ErrorDto getErrorDtoFromException(Exception ex) {
        return ErrorDto.builder()
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    public static boolean isUserDataValid(Book book) {
        boolean isValid = false;
        if (StringUtils.isNoneBlank(book.getName(), book.getAuthor(), book.getPublishedYear())
                && book.getPrice() != null && book.getPrice() > 0) {
            isValid = true;
        }
        return isValid;
    }
}