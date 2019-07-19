package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import com.spallya.bookservice.util.TestUtil;
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
    public void setUp() {
        booksService = new BooksService(booksRepository);
    }

    @Test
    public void save_shouldSaveBook() {
        Book testBook = TestUtil.getTestBook();
        given(booksRepository.save(testBook)).willReturn(testBook);
        Book savedBook = this.booksService.save(testBook);
        assertThat(savedBook.getId()).isEqualTo(testBook.getId());
        TestUtil.compareTwoBooksExcludingId(savedBook, testBook);
    }

    @Test
    public void findById_returnsBook() {
        Book testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(booksRepository.findById(1L)).willReturn(Optional.of(testBook));
        Book foundBook = this.booksService.findById(1L);
        assertThat(foundBook.getId()).isEqualTo(1L);
        TestUtil.compareTwoBooksExcludingId(foundBook, testBook);

    }

    @Test(expected = BookNotFoundException.class)
    public void findById_whenBookNotFound() {
        given(booksRepository.findById(1L)).willReturn(Optional.empty());
        this.booksService.findById(1L);
    }

    @Test
    public void updateById_shouldUpdateBook() {
        Book testBook = TestUtil.getTestBook();
        given(booksRepository.findById(1L)).willReturn(Optional.of(testBook));
        given(booksRepository.save(testBook)).willReturn(testBook);
        Book updatedBook = this.booksService.updateById(1L, testBook);
        assertThat(updatedBook.getId()).isEqualTo(testBook.getId());
        TestUtil.compareTwoBooksExcludingId(updatedBook, testBook);
    }

    @Test
    public void findAll_shouldReturnAllBooks() {
        Book book = TestUtil.getTestBook();
        List<Book> books = new ArrayList<>();
        books.add(book);
        given(booksRepository.findAll()).willReturn(books);
        List<Book> allBooks = this.booksService.findAll();
        assertThat(allBooks.size()).isEqualTo(books.size());
        assertThat(allBooks.get(0).getId()).isEqualTo(books.get(0).getId());
        TestUtil.compareTwoBooksExcludingId(allBooks.get(0), books.get(0));
    }

}