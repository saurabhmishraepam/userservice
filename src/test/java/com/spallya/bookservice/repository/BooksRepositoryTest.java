package com.spallya.bookservice.repository;

import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findById_returnsBook() {
        Book testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(testBook);
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        Optional<Book> foundBookOptional = booksRepository.findById(savedBook.getId());
        assertThat(foundBookOptional).isNotEmpty();
        Book foundBook = foundBookOptional.get();
        assertThat(foundBook.getId()).isEqualTo(testBook.getId());
        TestUtil.compareTwoBooksExcludingId(foundBook, testBook);
    }

    @Test
    public void updateById_updatesBook() {
        Book testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(testBook);
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        Book updatedBook = booksRepository.save(savedBook);
        assertThat(updatedBook.getId()).isEqualTo(savedBook.getId());
        TestUtil.compareTwoBooksExcludingId(updatedBook, savedBook);
    }

    @Test
    public void deleteById_deletesBook() {
        Book testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(testBook);
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        booksRepository.deleteById(savedBook.getId());
    }

    @Test
    public void save_addsBook() {
        Book testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(testBook);
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        TestUtil.compareTwoBooksExcludingId(savedBook, testBook);
    }
}