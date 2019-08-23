package com.spallya.bookservice.service;

import com.spallya.bookservice.dto.BookDTO;
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

import static java.util.stream.Collectors.toList;

/**
 * Class containing business logic for the operations on Book Entity
 *
 * @author Spallya Omar
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    /**
     * Business logic for saving a new book, before saving the book
     * data is validated
     *
     * @param bookDto {@link Book} model
     * @return Optional of Added Book {@link Book}
     * @throws InvalidBookDataException is thrown if book data is not valid
     */
    public Optional<BookDTO> save(BookDTO bookDto) {
        try {
            return Optional.ofNullable(
                    Utils.mapBookEntityToDTO(
                            this.booksRepository.save(Utils.convertToBookEntity(bookDto))));
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
    public Optional<BookDTO> findById(Long bookId) {
        Optional<Book> foundBook = Optional.empty();
        try {
            foundBook = this.booksRepository.findById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        if (!foundBook.isPresent()) {
            throw new BookNotFoundException("Book with id: "
                    + bookId + " is not found in the system");
        }
        return Optional.ofNullable(Utils.mapBookEntityToDTO(foundBook.get()));
    }

    /**
     * Business logic for updating an existing book, before updation the
     * book data is validated
     *
     * @param bookId and updatedBook {@link Book} model
     * @return Optional of Updated Book {@link Book}
     * @throws InvalidBookDataException is thrown if book data is not valid
     */
    public Optional<BookDTO> updateById(Long bookId, BookDTO updatedBookDTO) {
        Optional<Book> foundBook = Optional.empty();
        try {
            if (null != bookId) {
                foundBook = this.booksRepository.findById(bookId);
            }
            if (foundBook.isPresent()) {
                Book updatedBook = Utils.convertToBookEntity(updatedBookDTO);
                updatedBook.setId(foundBook.get().getId());
                this.booksRepository.save(updatedBook);
                return Optional.ofNullable(Utils.mapBookEntityToDTO(updatedBook));
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return Optional.empty();
    }

    /**
     * Business logic for getting all the books present in the system
     *
     * @return List of Books {@link Book}
     * @throws NoBooksFoundException is thrown if no books are present
     */
    public List<BookDTO> findAll() {
        List<Book> allBooks = Collections.emptyList();
        try {
            allBooks = this.booksRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        if (null == allBooks || allBooks.isEmpty()) {
            throw new NoBooksFoundException("No books found in the database");
        }
        return allBooks.stream().map(Utils::mapBookEntityToDTO).collect(toList());
    }

    /**
     * Business logic for deleting an existing book
     *
     * @param bookId
     * @throws BookNotFoundException is thrown if book is not found
     */
    public void deleteById(Long bookId) {
        try {
            this.booksRepository.deleteById(bookId);
        } catch (EmptyResultDataAccessException ex) {
            log.error(ex.getLocalizedMessage());
            throw new BookNotFoundException("Book with id: "
                    + bookId + " is not found in the system");
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }

    public List<BookDTO> findByIdIn(List<Long> bookIds) {
        List<Book> allBooks = Collections.emptyList();
        try {
            allBooks = this.booksRepository.findByIdIn(bookIds);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        if (null == allBooks || allBooks.isEmpty()) {
            throw new NoBooksFoundException("No books found in the database");
        }
        return allBooks.stream().map(Utils::mapBookEntityToDTO).collect(toList());
    }
}
