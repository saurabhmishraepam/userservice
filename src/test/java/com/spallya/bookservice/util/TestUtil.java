package com.spallya.bookservice.util;

import com.spallya.bookservice.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    public static Book getTestBook() {
        return Book.builder()
                .author("Test").name("Test Book").price(20.00)
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
}
