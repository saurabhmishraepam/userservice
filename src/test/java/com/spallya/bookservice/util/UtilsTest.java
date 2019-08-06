package com.spallya.bookservice.util;

import com.spallya.bookservice.dto.ErrorDto;
import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
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