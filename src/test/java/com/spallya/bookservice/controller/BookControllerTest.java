package com.spallya.bookservice.controller;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getBook_returnsBook() throws Exception {
        given(bookService.findById(Long.valueOf(1))).willReturn(Book.builder()
                .author("Test").name("Test Book").price(20.00).genre("test").build());
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Test Book"))
                .andExpect(jsonPath("author").value("Test"))
                .andExpect(jsonPath("genre").value("test"))
                .andExpect(jsonPath("price").value(20.00));
    }

    @Test
    public void getBook_notFound() throws Exception {
        given(bookService.findById(Long.valueOf(1))).willThrow(new BookNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(status().isNotFound());
    }
}