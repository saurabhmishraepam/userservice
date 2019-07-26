package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.exception.InvalidBookDataException;
import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import com.spallya.bookservice.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Spallya Omar
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository bookRepository;

    public Optional<Book> save(Book book) {
        if (!Utils.isBookDataValid(book)) {
            throw new InvalidBookDataException("Invalid Book data. Book Name, Author Name, Published Year and Price can not be empty");
        }
        try {
            return Optional.of(this.bookRepository.save(book));
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public Optional<Book> findById(Long bookId) {
        Optional<Book> foundBook = Optional.empty();
        try {
            foundBook = this.bookRepository.findById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.ofNullable(foundBook.orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + " is not found in the system")));
    }

    public Optional<Book> updateById(Long bookId, Book updatedBook) {
        if (!Utils.isBookDataValid(updatedBook)) {
            throw new InvalidBookDataException("Invalid Book data. Book Name, Author Name, Published Year and Price can not be empty");
        }
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

    public List<Book> findAll() {
        List<Book> allBooks = Collections.emptyList();
        try {
            allBooks = this.bookRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.ofNullable(allBooks).orElseThrow(NoBooksFoundException::new);
    }

    public void deleteById(Long bookId) {
        try {
            this.bookRepository.deleteById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }
}
