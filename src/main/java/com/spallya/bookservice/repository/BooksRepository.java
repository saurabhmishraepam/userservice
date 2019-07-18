package com.spallya.bookservice.repository;

import com.spallya.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {

}
