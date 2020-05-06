package com.bookservice.service;

import com.bookservice.dto.BookDTO;
import com.bookservice.exception.BookNotFoundException;
import com.bookservice.exception.NoBooksFoundException;
import com.bookservice.repository.BooksRepository;
import com.bookservice.util.TestUtil;
import com.bookservice.util.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
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
        BookDTO testBook = TestUtil.getTestBook();
        given(booksRepository.save(Utils.convertToBookEntity(testBook))).willReturn(Utils.convertToBookEntity(testBook));
        Optional<BookDTO> savedBookOptional = this.booksService.save(testBook);
        assertThat(savedBookOptional).isNotEmpty();
        if (savedBookOptional.isPresent()) {
            BookDTO savedBook = savedBookOptional.get();
            assertThat(savedBook.getId()).isEqualTo(testBook.getId());
            TestUtil.compareTwoBooksExcludingId(savedBook, testBook);
        }
    }

    @Test
    public void findByIdReturnsBook() {
        BookDTO testBook = TestUtil.getTestBook();
        testBook.setId(1L);
        given(booksRepository.findById(1L)).willReturn(Optional.of(Utils.convertToBookEntity(testBook)));
        Optional<BookDTO> foundBookOptional = this.booksService.findById(1L);
        assertThat(foundBookOptional).isNotEmpty();
        if (foundBookOptional.isPresent()) {
            BookDTO foundBook = foundBookOptional.get();
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
        BookDTO testBook = TestUtil.getTestBook();
        given(booksRepository.findById(1L)).willReturn(Optional.of(Utils.convertToBookEntity(testBook)));
        given(booksRepository.save(Utils.convertToBookEntity(testBook))).willReturn(Utils.convertToBookEntity(testBook));
        Optional<BookDTO> updatedBookOptional = this.booksService.updateById(1L, testBook);
        assertThat(updatedBookOptional).isNotEmpty();
        if (updatedBookOptional.isPresent()) {
            BookDTO updatedBook = updatedBookOptional.get();
            assertThat(updatedBook.getId()).isEqualTo(testBook.getId());
            TestUtil.compareTwoBooksExcludingId(updatedBook, testBook);
        }
    }

    @Test
    public void findAllShouldReturnAllBooks() {
        BookDTO book = TestUtil.getTestBook();
        List<BookDTO> books = new ArrayList<>();
        books.add(book);
        given(booksRepository.findAll()).willReturn(books.stream().map(Utils::convertToBookEntity).collect(toList()));
        List<BookDTO> allBooks = this.booksService.findAll();
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