package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BooksServiceTest {

    private BooksService booksService;

    @Mock
    private BooksRepository booksRepository;

    @Before
    public void setUp() throws Exception {
        booksService = new BooksService(booksRepository);
    }

    @Test
    public void save_shouldSaveBook() throws Exception {
        Book book = Book.builder().id(1L)
                .author("Test").name("Test Book").price(20.00).genre("test").build();
        given(booksRepository.save(book)).willReturn(book);
        Book savedBook = this.booksService.save(book);
        assertThat(savedBook.getId()).isEqualTo(book.getId());
        assertThat(savedBook.getName()).isEqualTo(book.getName());
        assertThat(savedBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(savedBook.getPrice()).isEqualTo(book.getPrice());
        assertThat(savedBook.getGenre()).isEqualTo(book.getGenre());
    }

    @Test
    public void findById_returnsBook() throws Exception {
        given(booksRepository.findById(1L)).willReturn(Optional.of(Book.builder().id(1L)
                .author("Test").name("Test Book").price(20.00).genre("test").build()));
        Book foundBook = this.booksService.findById(1L);
        assertThat(foundBook.getId()).isEqualTo(1L);
        assertThat(foundBook.getName()).isEqualTo("Test Book");
        assertThat(foundBook.getAuthor()).isEqualTo("Test");
        assertThat(foundBook.getPrice()).isEqualTo(20.00);
        assertThat(foundBook.getGenre()).isEqualTo("test");

    }

    @Test(expected = BookNotFoundException.class)
    public void findById_whenBookNotFound() throws Exception {
        given(booksRepository.findById(1L)).willReturn(Optional.empty());
        this.booksService.findById(1L);
    }

    @Test
    public void updateById_shouldUpdateBook() throws Exception {
        Book book = Book.builder().id(1L)
                .author("Test").name("Test Book").price(20.00).genre("test").build();
        given(booksRepository.findById(1L)).willReturn(Optional.of(book));
        given(booksRepository.save(book)).willReturn(book);
        Book updatedBook = this.booksService.updateById(1L, book);
        assertThat(updatedBook.getId()).isEqualTo(book.getId());
        assertThat(updatedBook.getName()).isEqualTo(book.getName());
        assertThat(updatedBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(updatedBook.getPrice()).isEqualTo(book.getPrice());
        assertThat(updatedBook.getGenre()).isEqualTo(book.getGenre());
    }

    @Test
    public void findAll_shouldReturnAllBooks() throws Exception {
        Book book = Book.builder().id(1L)
                .author("Test").name("Test Book").price(20.00).genre("test").build();
        List<Book> books = new ArrayList<>();
        books.add(book);
        given(booksRepository.findAll()).willReturn(books);
        List<Book> allBooks = this.booksService.findAll();
        assertThat(allBooks.size()).isEqualTo(books.size());
        assertThat(allBooks.get(0).getId()).isEqualTo(books.get(0).getId());
        assertThat(allBooks.get(0).getName()).isEqualTo(books.get(0).getName());
        assertThat(allBooks.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(allBooks.get(0).getPrice()).isEqualTo(books.get(0).getPrice());
        assertThat(allBooks.get(0).getGenre()).isEqualTo(books.get(0).getGenre());
    }

}