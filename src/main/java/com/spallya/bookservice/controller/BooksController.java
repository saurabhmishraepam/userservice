package com.spallya.bookservice.controller;

import com.spallya.bookservice.exception.NoBooksFoundException;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.service.BooksService;
import com.spallya.bookservice.util.ControllersUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Class Containing all apis related to book operation
 *
 * @author Spallya Omar
 */
@Slf4j
@RestController
@RequestMapping(value = "/books")
@Api(description = "Operations pertaining to Books")
public class BooksController {

    @Autowired
    private BooksService bookService;

    /**
     * Get all available books in the system
     *
     * @return List of Books {@link Book}
     */
    @GetMapping
    @ApiOperation(value = "View list of available books", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No books present in the database")
    }
    )
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = this.bookService.findAll();
        if (null != books && !books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            throw new NoBooksFoundException();
        }
    }

    /**
     * Get an book from book id
     *
     * @param bookId
     * @return Found Book {@link Book}
     */
    @GetMapping(value = "/{book_id}")
    @ApiOperation(value = "Search an book with an ID", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 404, message = "No book present in the database with the given ID")
    }
    )
    public ResponseEntity<Book> getBook(@PathVariable("book_id") Long bookId) {
        Optional<Book> book = this.bookService.findById(bookId);
        return book.map(ControllersUtil::getOkResponseEntity)
                   .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    /**
     * Add a new book
     *
     * @param book {@link Book} model
     * @return Added Book {@link Book}
     */
    @PostMapping
    @ApiOperation(value = "Add a new book", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Optional<Book> savedBook = this.bookService.save(book);
        return savedBook.map(ControllersUtil::getCreatedResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    /**
     * Update an book using its book id
     *
     * @param bookId and updated book {@link Book} model
     * @return Updated Book {@link Book}
     */
    @PutMapping(value = "/{book_id}")
    @ApiOperation(value = "Update an book with an ID", response = Book.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    public ResponseEntity<Book> updateBook(@PathVariable("book_id") Long bookId, @RequestBody Book book) {
        Optional<Book> updatedBook = this.bookService.updateById(bookId, book);
        return updatedBook.map(ControllersUtil::getOkResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    /**
     * Delete an book using its book id
     *
     * @param bookId
     * @return HttpStatus 200 on Successful Delete
     */
    @DeleteMapping(value = "/{book_id}")
    @ApiOperation(value = "Delete an book with an ID", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    public HttpStatus deleteBook(@PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            this.bookService.deleteById(bookId);
            return HttpStatus.OK;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
