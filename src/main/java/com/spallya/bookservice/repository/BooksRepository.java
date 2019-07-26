package com.spallya.bookservice.repository;

import com.spallya.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class containing Data Access Logic for Book Entity
 *
 * @author Spallya Omar
 */
public interface BooksRepository extends JpaRepository<Book, Long> {

}
