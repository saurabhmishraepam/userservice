package com.bookservice.util;

import com.bookservice.model.Book;
import com.bookservice.dto.ErrorDto;
import com.bookservice.exception.BookNotFoundException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Spallya Omar
 */
public class UtilsTest {

    @Test
    public void getErrorDtoFromException() {
        BookNotFoundException ex = new BookNotFoundException("Book not found");
        ErrorDto errorDto = Utils.getErrorDtoFromException(ex);
        assertThat(errorDto.getMessage()).isEqualTo("Book not found");
        assertThat(errorDto.getException()).isEqualTo(ex.getClass().getSimpleName());
    }

    @Test
    public void forValidBookDataIsValid() {
        assertThat(Utils.isBookDataValid(Utils.convertToBookEntity(TestUtil.getTestBook()))).isEqualTo(true);
    }

    @Test
    public void forInvalidBookDataIsNotValid() {
        assertThat(Utils.isBookDataValid(Book.builder().genre("horror").build())).isEqualTo(false);
    }
}