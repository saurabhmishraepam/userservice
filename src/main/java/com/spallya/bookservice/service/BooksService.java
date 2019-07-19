package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository bookRepository;

    public Book save(Book book) {
        try {
            return this.bookRepository.save(book);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public Book findById(Long bookId) {
        Optional<Book> foundBook = Optional.empty();
        try {
            if (null != bookId) {
                foundBook = this.bookRepository.findById(bookId);
            }
            if (!foundBook.isPresent()) {
                throw new BookNotFoundException("Book with id: " + bookId + " is not found in the system");
            }
            return foundBook.get();
        } catch (BookNotFoundException ex) {
            log.error(ex.getLocalizedMessage());
            throw ex;
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public Book updateById(Long bookId, Book updatedBook) {
        Optional<Book> foundBook = Optional.empty();
        try {
            if (null != bookId) {
                foundBook = this.bookRepository.findById(bookId);
            }
            if (foundBook.isPresent()) {
                updatedBook.setId(foundBook.get().getId());
                this.bookRepository.save(updatedBook);
                return updatedBook;
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }

        return null;
    }

    public List<Book> findAll() {
        try {
            return this.bookRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Collections.emptyList();
    }

    public void deleteById(Long bookId) {
        try {
            this.bookRepository.deleteById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }
}
