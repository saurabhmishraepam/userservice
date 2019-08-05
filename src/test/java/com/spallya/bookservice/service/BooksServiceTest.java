package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.exception.InvalidBookDataException;
import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import com.spallya.bookservice.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Spallya Omar
 */
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
    public void saveShouldSaveBook() {
        Book testBook = TestUtil.getTestBook();
        given(booksRepository.save(testBook)).willReturn(testBook);
        Optional<Book> savedBookOptional = this.booksService.save(testBook);
        assertThat(savedBookOptional).isNotEmpty();
        if (savedBookOptional.isPresent()) {
            Book savedBook = savedBookOptional.get();
            assertThat(savedBook.getId()).isEqualTo(testBook.getId());
            TestUtil.compareTwoBooksExcludingId(savedBook, testBook);
        }
    }

    @Test
    public void findByIdReturnsBook() {
        Book testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(booksRepository.findById(1L)).willReturn(Optional.of(testBook));
        Optional<Book> foundBookOptional = this.booksService.findById(1L);
        assertThat(foundBookOptional).isNotEmpty();
        if (foundBookOptional.isPresent()) {
            Book foundBook = foundBookOptional.get();
            assertThat(foundBook.getId()).isEqualTo(1L);
            TestUtil.compareTwoBooksExcludingId(foundBook, testBook);
        }

    }

    @Test(expected = BookNotFoundException.class)
    public void findByIdWhenBookNotFound() {
        given(booksRepository.findById(1L)).willReturn(Optional.empty());
        this.booksService.findById(1L);
    }

    @Test
    public void updateByIdShouldUpdateBook() {
        Book testBook = TestUtil.getTestBook();
        given(booksRepository.findById(1L)).willReturn(Optional.of(testBook));
        given(booksRepository.save(testBook)).willReturn(testBook);
        Optional<Book> updatedBookOptional = this.booksService.updateById(1L, testBook);
        assertThat(updatedBookOptional).isNotEmpty();
        if (updatedBookOptional.isPresent()) {
            Book updatedBook = updatedBookOptional.get();
            assertThat(updatedBook.getId()).isEqualTo(testBook.getId());
            TestUtil.compareTwoBooksExcludingId(updatedBook, testBook);
        }
    }

    @Test
    public void findAllShouldReturnAllBooks() {
        Book book = TestUtil.getTestBook();
        List<Book> books = new ArrayList<>();
        books.add(book);
        given(booksRepository.findAll()).willReturn(books);
        List<Book> allBooks = this.booksService.findAll();
        assertThat(allBooks.size()).isEqualTo(books.size());
        assertThat(allBooks.get(0).getId()).isEqualTo(books.get(0).getId());
        TestUtil.compareTwoBooksExcludingId(allBooks.get(0), books.get(0));
    }

    @Test(expected = NoBooksFoundException.class)
    public void findByIdWhenNoBooksFound() {
        given(booksRepository.findAll()).willReturn(null);
        this.booksService.findAll();
    }

}