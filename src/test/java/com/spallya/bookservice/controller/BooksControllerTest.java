package com.spallya.bookservice.controller;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.exception.InvalidBookDataException;
import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BooksService;
import com.spallya.bookservice.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.spallya.bookservice.util.TestUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Spallya Omar
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService bookService;

    @Test
    public void getBookReturnsBook() throws Exception {
        Book testBook = TestUtil.getTestBook();
        given(bookService.findById(1L)).willReturn(Optional.of(testBook));
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(testBook.getName()))
                .andExpect(jsonPath("author").value(testBook.getAuthor()))
                .andExpect(jsonPath("genre").value(testBook.getGenre()))
                .andExpect(jsonPath("price").value(testBook.getPrice()));
    }

    @Test
    public void getBookNotFound() throws Exception {
        given(bookService.findById(1L)).willThrow(new BookNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBooksReturnsAllBooks() throws Exception {
        Book testBook = TestUtil.getTestBook();
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        given(bookService.findAll()).willReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(testBook.getName()))
                .andExpect(jsonPath("$.[0].author").value(testBook.getAuthor()))
                .andExpect(jsonPath("$.[0].genre").value(testBook.getGenre()))
                .andExpect(jsonPath("$.[0].price").value(testBook.getPrice()));
    }

    @Test
    public void getAllBooksNoContent() throws Exception {
        given(bookService.findAll()).willThrow(new NoBooksFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void addBookAddsBook() throws Exception {
        Book testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(bookService.save(any(Book.class))).willReturn(Optional.of(testBook));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .content(asJsonString(testBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").value(testBook.getName()))
                .andExpect(jsonPath("author").value(testBook.getAuthor()))
                .andExpect(jsonPath("genre").value(testBook.getGenre()))
                .andExpect(jsonPath("price").value(testBook.getPrice()));
    }

    @Test
    public void addBookInvalidBookData() throws Exception {
        Book testBook = TestUtil.getTestBook();
        given(bookService.save(any(Book.class))).willThrow(InvalidBookDataException.class);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/books")
                .content(asJsonString(testBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("exception").value(InvalidBookDataException.class.getSimpleName()));
    }

    @Test
    public void updateBookUpdatesBook() throws Exception {
        Book testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(bookService.updateById(anyLong(), any(Book.class))).willReturn(Optional.of(testBook));
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/1")
                .content(asJsonString(testBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value(testBook.getName()))
                .andExpect(jsonPath("author").value(testBook.getAuthor()))
                .andExpect(jsonPath("genre").value(testBook.getGenre()))
                .andExpect(jsonPath("price").value(testBook.getPrice()));
    }

    @Test
    public void updateBookInvalidBookData() throws Exception {
        Book testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(bookService.updateById(anyLong(), any(Book.class))).willThrow(InvalidBookDataException.class);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/1")
                .content(asJsonString(testBook))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("exception").value(InvalidBookDataException.class.getSimpleName()));
    }
}