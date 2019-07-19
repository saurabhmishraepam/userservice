package com.spallya.bookservice.controller;

import com.spallya.bookservice.exception.BookNotFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spallya Omar
 */
@Slf4j
@RestController
@RequestMapping(value = "/books")
public class BooksController {

    @Autowired
    private BooksService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = this.bookService.findAll();
        if (null != books && !books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{book_id}")
    public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookId) {
        Book book = this.bookService.findById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = this.bookService.save(book);
        if (savedBook != null) {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/{book_id}")
    public ResponseEntity<Book> updateBook(@PathVariable("book_id") Long bookId, @RequestBody Book book) {
        Book updatedBook = this.bookService.updateById(bookId, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{book_id}")
    public HttpStatus deleteBook(@PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            this.bookService.deleteById(bookId);
            return HttpStatus.OK;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void bookNotFoundHandler(BookNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
    }

}
