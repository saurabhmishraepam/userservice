package com.spallya.bookservice;

import com.spallya.bookservice.model.Book;
import com.spallya.bookservice.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author Spallya Omar
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@SqlGroup({
			@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:before.sql"),
			@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:after.sql") })
	public void getBook_returnsBook() {
		ResponseEntity<Book> response = restTemplate.getForEntity("/books/1000", Book.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@SqlGroup({
			@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:after.sql") })
	public void addBook_addsBook() {
		ResponseEntity<Book> response = restTemplate.postForEntity("/books", TestUtil.getTestBook(), Book.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@SqlGroup({
			@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:before.sql"),
			@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:after.sql") })
	public void updateBook_updatesBook() {
		restTemplate.put("/books/1000", TestUtil.getTestBook());
	}

	@Test
	@SqlGroup({
			@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:before.sql")})
	public void deleteBook_deletesBook() {
		restTemplate.delete("/books/1000");
	}

}
