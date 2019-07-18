package com.spallya.bookservice.controller;

import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = this.bookService.save(book);
        if (savedBook != null) {
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(savedBook, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{book_id}")
    public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            Book book = this.bookService.findById(bookId);
            if (null != book) {
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{book_id}")
    public HttpStatus deleteBook(@PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            this.bookService.deleteById(bookId);
            return HttpStatus.OK;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = this.bookService.findAll();
        if (null != books && !books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
