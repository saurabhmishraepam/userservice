package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.exception.InvalidBookDataException;
import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import com.spallya.bookservice.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class containing business logic for the operations on Book Entity
 *
 * @author Spallya Omar
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository bookRepository;

    /**
     * Business logic for saving a new book, before saving the book
     * data is validated
     *
     * @param book {@link Book} model
     * @return Optional of Added Book {@link Book}
     * @throws InvalidBookDataException is thrown if book data is not valid
     */
    public Optional<Book> save(Book book) {
        try {
            return Optional.of(this.bookRepository.save(book));
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.empty();
    }

    /**
     * Business logic for finding an book using its ID
     *
     * @param bookId
     * @return Optional of Found Book {@link Book}
     * @throws BookNotFoundException is thrown if book is not found
     */
    public Optional<Book> findById(Long bookId) {
        Optional<Book> foundBook = Optional.empty();
        try {
            foundBook = this.bookRepository.findById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.ofNullable(foundBook.orElseThrow(() -> new BookNotFoundException("Book with id: "
                + bookId + " is not found in the system")));
    }

    /**
     * Business logic for updating an existing book, before updation the
     * book data is validated
     *
     * @param bookId and updatedBook {@link Book} model
     * @return Optional of Updated Book {@link Book}
     * @throws InvalidBookDataException is thrown if book data is not valid
     */
    public Optional<Book> updateById(Long bookId, Book updatedBook) {
        Optional<Book> foundBook = Optional.empty();
        try {
            if (null != bookId) {
                foundBook = this.bookRepository.findById(bookId);
            }
            if (foundBook.isPresent()) {
                updatedBook.setId(foundBook.get().getId());
                this.bookRepository.save(updatedBook);
                return Optional.of(updatedBook);
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return foundBook;
    }

    /**
     * Business logic for getting all the books present in the system
     *
     * @return List of Books {@link Book}
     * @throws NoBooksFoundException is thrown if no books are present
     */
    public List<Book> findAll() {
        List<Book> allBooks = Collections.emptyList();
        try {
            allBooks = this.bookRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.ofNullable(allBooks).orElseThrow(NoBooksFoundException::new);
    }

    /**
     * Business logic for deleting an existing book
     *
     * @param bookId
     * @throws BookNotFoundException is thrown if book is not found
     */
    public void deleteById(Long bookId) {
        try {
            this.bookRepository.deleteById(bookId);
        } catch (EmptyResultDataAccessException ex) {
            log.error(ex.getLocalizedMessage());
            throw new BookNotFoundException("Book with id: "
                    + bookId + " is not found in the system");
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }
}
