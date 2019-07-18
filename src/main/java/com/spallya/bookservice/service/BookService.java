package com.spallya.bookservice.service;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(Book book) {
        if (null != book) {
            this.bookRepository.save(book);
        }
        return book;
    }

    public Book findById(Long bookId) {
        Optional<Book> foundBook = Optional.empty();
        if (null != bookId) {
            foundBook = this.bookRepository.findById(bookId);
        }
        if (!foundBook.isPresent()) {
            throw new BookNotFoundException("Book with id: " + bookId + " is not found in the system");
        }
        return foundBook.get();
    }

    public Book updateById(Long bookId, Book updatedBook) {
        Optional<Book> foundBook = Optional.empty();
        if (null != bookId) {
            foundBook = this.bookRepository.findById(bookId);
        }
        if (foundBook.isPresent()) {
            updatedBook.setId(foundBook.get().getId());
            this.bookRepository.save(updatedBook);
            return updatedBook;
        }
        return null;
    }

    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public void deleteById(Long bookId) {
        this.bookRepository.deleteById(bookId);
    }
}
