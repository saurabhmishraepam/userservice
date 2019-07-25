package com.spallya.bookservice.controller;

import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BooksService;
import com.spallya.bookservice.util.ControllersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        } else {
            throw new NoBooksFoundException();
        }
    }

    @GetMapping(value = "/{book_id}")
    public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookId) {
        Optional<Book> book = this.bookService.findById(bookId);
        return book.map(ControllersUtil::getOkResponseEntity)
                   .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Optional<Book> savedBook = this.bookService.save(book);
        return savedBook.map(ControllersUtil::getCreatedResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    @PutMapping(value = "/{book_id}")
    public ResponseEntity<Book> updateBook(@PathVariable("book_id") Long bookId, @RequestBody Book book) {
        Optional<Book> updatedBook = this.bookService.updateById(bookId, book);
        return updatedBook.map(ControllersUtil::getOkResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    @DeleteMapping(value = "/{book_id}")
    public HttpStatus deleteBook(@PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            this.bookService.deleteById(bookId);
            return HttpStatus.OK;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
