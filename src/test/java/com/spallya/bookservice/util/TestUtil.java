package com.spallya.bookservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spallya.bookservice.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Spallya Omar
 */
public class TestUtil {

    public static Book getTestBook() {
        return Book.builder()
                .author("SpallyaTest").name("Test Book").price(20.00)
                .description("test book").genre("test")
                .build();
    }

    public static void compareTwoBooksExcludingId(Book bookFromApp, Book testBook) {
        assertThat(bookFromApp.getName()).isEqualTo(testBook.getName());
        assertThat(bookFromApp.getAuthor()).isEqualTo(testBook.getAuthor());
        assertThat(bookFromApp.getPrice()).isEqualTo(testBook.getPrice());
        assertThat(bookFromApp.getGenre()).isEqualTo(testBook.getGenre());
        assertThat(bookFromApp.getDescription()).isEqualTo(testBook.getDescription());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
