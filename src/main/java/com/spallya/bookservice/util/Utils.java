package com.spallya.bookservice.util;

import com.spallya.bookservice.dto.ErrorDto;
import com.spallya.bookservice.model.Book;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for all classes
 *
 * @author Spallya Omar
 */
public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Helper method for creating the {@link ErrorDto} from {@link Exception}
     *
     * @param ex Exception
     * @return {@link ErrorDto}
     */
    static ErrorDto getErrorDtoFromException(Exception ex) {
        return ErrorDto.builder()
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    /**
     * Helper method for validating the Book data
     *
     * @param book {@link Book}
     * @return {@link ErrorDto}
     */
    public static boolean isBookDataValid(Book book) {
        boolean isValid = false;
        if (StringUtils.isNoneBlank(book.getName(), book.getAuthor(), book.getPublishedYear())
                && book.getPrice() != null && book.getPrice() > 0) {
            isValid = true;
        }
        return isValid;
    }
}