package com.spallya.bookservice.repository;

import com.spallya.bookservice.dto.BookDTO;
import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.Optional;

import static com.spallya.bookservice.util.Utils.convertToBookEntity;
import static com.spallya.bookservice.util.Utils.mapBookEntityToDTO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Spallya Omar
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByIdReturnsBook() {
        BookDTO testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(convertToBookEntity(testBook));
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        Optional<Book> foundBookOptional = booksRepository.findById(savedBook.getId());
        assertThat(foundBookOptional).isNotEmpty();
        if (foundBookOptional.isPresent()) {
            Book foundBook = foundBookOptional.get();
            assertThat(foundBook.getId()).isEqualTo(savedBook.getId());
            TestUtil.compareTwoBooksExcludingId(Objects.requireNonNull(mapBookEntityToDTO(foundBook)), testBook);
        }
    }

    @Test
    public void updateByIdUpdatesBook() {
        BookDTO testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(convertToBookEntity(testBook));
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        Book updatedBook = booksRepository.save(savedBook);
        assertThat(updatedBook.getId()).isEqualTo(savedBook.getId());
        TestUtil.compareTwoBooksExcludingId(mapBookEntityToDTO(updatedBook), mapBookEntityToDTO(savedBook));
    }

    @Test
    public void deleteByIdDeletesBook() {
        BookDTO testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(convertToBookEntity(testBook));
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        booksRepository.deleteById(savedBook.getId());
    }

    @Test
    public void saveAddsBook() {
        BookDTO testBook = TestUtil.getTestBook();
        Book savedBook = entityManager.persistFlushFind(convertToBookEntity(testBook));
        assertThat(savedBook.getId()).isNotNull().isNotNegative();
        TestUtil.compareTwoBooksExcludingId(mapBookEntityToDTO(savedBook), testBook);
    }
}