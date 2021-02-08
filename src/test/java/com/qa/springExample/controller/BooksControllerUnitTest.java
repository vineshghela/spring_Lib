package com.qa.springExample.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.springExample.dto.BooksDTO;
import com.qa.springExample.global.BOOKS;
import com.qa.springExample.persistence.domain.Books;
import com.qa.springExample.rest.BookController;
import com.qa.springExample.service.BookService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class BooksControllerUnitTest {

	@Autowired
	private BookController controller;

	@MockBean
	private BookService service;

	@Autowired
	private ModelMapper mapper;

	private BooksDTO mapToDTO(Books books) {
		return this.mapper.map(books, BooksDTO.class);
	}

	private final Long TEST_ID = 1L;

	private final Books STORY_1 = new Books(TEST_ID, BOOKS.STORY1.getName(), BOOKS.STORY1.getAuthor(),
			BOOKS.STORY1.getPublisher(), BOOKS.STORY1.getPrice(), BOOKS.STORY1.getPages());
	private final Books STORY_2 = new Books(TEST_ID, BOOKS.STORY2.getName(), BOOKS.STORY2.getAuthor(),
			BOOKS.STORY2.getPublisher(), BOOKS.STORY2.getPrice(), BOOKS.STORY2.getPages());
	private final Books STORY_3 = new Books(TEST_ID, BOOKS.STORY3.getName(), BOOKS.STORY3.getAuthor(),
			BOOKS.STORY3.getPublisher(), BOOKS.STORY3.getPrice(), BOOKS.STORY3.getPages());
	private final Books STORY_4 = new Books(TEST_ID, BOOKS.STORY4.getName(), BOOKS.STORY4.getAuthor(),
			BOOKS.STORY4.getPublisher(), BOOKS.STORY4.getPrice(), BOOKS.STORY4.getPages());

	@Test
	void createTest() throws Exception {
		BooksDTO expected = this.mapToDTO(STORY_1);
		when(this.service.create(STORY_1)).thenReturn(expected);
		assertThat(new ResponseEntity<BooksDTO>(expected, HttpStatus.CREATED))
				.isEqualTo(this.controller.create(STORY_1));
		verify(this.service, atLeastOnce()).create(STORY_1);
	}

	@Test
	void readAllTest() throws Exception {
		List<Books> books = new ArrayList<>();
		books.add(STORY_1);
		when(this.service.read()).thenReturn(books.stream().map(this::mapToDTO).collect(Collectors.toList()));
		assertThat(this.controller.read().getBody().isEmpty()).isFalse();
		verify(this.service, atLeastOnce()).read();
	}

	@Test
	void readOneTest() throws Exception {
		BooksDTO expected = this.mapToDTO(STORY_1);
		when(this.service.read(TEST_ID)).thenReturn(expected);
		assertThat(new ResponseEntity<BooksDTO>(expected, HttpStatus.OK)).isEqualTo(this.controller.read(TEST_ID));
		verify(this.service, atLeastOnce()).read(TEST_ID);
	}

	@Test
	void updateTest() throws Exception {
		BooksDTO expected = this.mapToDTO(STORY_1);
		when(this.service.update(expected, TEST_ID)).thenReturn(expected);
		assertThat(new ResponseEntity<BooksDTO>(expected, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_ID, expected));
		verify(this.service, atLeastOnce()).update(expected, TEST_ID);
	}

	@Test
	void deleteTest() throws Exception {
		this.controller.delete(TEST_ID);
		verify(this.service, atLeastOnce()).delete(TEST_ID);
	}

	@Test
	void findByNameTest() {
		List<BooksDTO> books = new ArrayList<>();
		books.add(this.mapToDTO(STORY_1));
		books.add(this.mapToDTO(STORY_2));
		books.add(this.mapToDTO(STORY_3));
		books.add(this.mapToDTO(STORY_4));

		when(this.service.findByAuthor(STORY_1.getAuthor())).thenReturn(books);
		assertThat(this.controller.findByAuthor(STORY_1.getAuthor()))
				.isEqualTo(new ResponseEntity<List<BooksDTO>>(books, HttpStatus.OK));
		verify(this.service, atLeastOnce()).findByAuthor(STORY_1.getAuthor());
	}

	@Test
	void orderByTest() {
		List<BooksDTO> books = new ArrayList<>();
		books.add(this.mapToDTO(STORY_1));
		books.add(this.mapToDTO(STORY_2));
		books.add(this.mapToDTO(STORY_3));
		books.add(this.mapToDTO(STORY_4));
		when(this.service.orderByName()).thenReturn(books);
		assertThat(this.controller.orderByName()).isEqualTo(new ResponseEntity<List<BooksDTO>>(books, HttpStatus.OK));
		verify(this.service, atLeastOnce()).orderByName();
	}
}
