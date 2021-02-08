package com.qa.springExample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.springExample.dto.BooksDTO;
import com.qa.springExample.global.BOOKS;
import com.qa.springExample.persistence.domain.Books;
import com.qa.springExample.persistence.repo.BooksRepo;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class BooksServiceUnitTest {

	@Autowired
	private BookService service;
	@MockBean
	private BooksRepo repo;
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
		// We set an id because norally we auto generate one
		STORY_1.setId(TEST_ID);
		// we then create a book and pass to to our MapToDTO
		BooksDTO expected = this.mapToDTO(STORY_1);

		when(this.repo.save(STORY_1)).thenReturn(STORY_1);
		assertThat(this.service.create(STORY_1)).isEqualTo(expected);
		verify(this.repo, atLeastOnce()).save(STORY_1);
	}

	@Test
	void readTest() throws Exception {
		List<Books> books = new ArrayList<>();
		STORY_1.setId(TEST_ID);
		books.add(STORY_1);

		when(this.repo.findAll()).thenReturn(books);
		assertThat(this.service.read().isEmpty()).isFalse();
		verify(this.repo, atLeastOnce()).findAll();
	}

	@Test
	void readOneTest() throws Exception {
		STORY_1.setId(TEST_ID);
		BooksDTO expected = this.mapToDTO(STORY_1);

		when(this.repo.findById(TEST_ID)).thenReturn(Optional.of(STORY_1));
		assertThat(this.service.read(TEST_ID)).isEqualTo(expected);
		verify(this.repo, atLeastOnce()).findById(TEST_ID);
	}

	@Test
	void updateTest() throws Exception {
		STORY_1.setId(TEST_ID);
		BooksDTO expected = this.mapToDTO(STORY_1);

		when(this.repo.findById(TEST_ID)).thenReturn(Optional.of(STORY_1));
		when(this.repo.save(STORY_1)).thenReturn(STORY_1);
		assertThat(this.service.update(expected, TEST_ID)).isEqualTo(expected);
		verify(this.repo, atLeastOnce()).findById(TEST_ID);
		verify(this.repo, atLeastOnce()).save(STORY_1);

	}

	@Test
	void deleteTest() throws Exception {
		boolean found = false;
		when(this.repo.existsById(TEST_ID)).thenReturn(found);
		assertThat(this.service.delete(TEST_ID)).isNotEqualTo(found);
		verify(this.repo, atLeastOnce()).existsById(TEST_ID);
	}

	@Test
	void findByAuthoTest() throws Exception {
		List<Books> books = new ArrayList<>();
		STORY_1.setId(TEST_ID);
		books.add(STORY_1);

		when(this.repo.findByAuthor(STORY_1.getAuthor())).thenReturn(books);
		assertThat(this.repo.findByAuthor(STORY_1.getAuthor())).asList().isEqualTo(books);
		verify(this.repo, atLeastOnce()).findByAuthor(STORY_1.getAuthor());
	}

	@Test
	void orderByNameTest() throws Exception {
		List<Books> books = new ArrayList<>();
		books.add(STORY_1);
		books.add(STORY_2);
		books.add(STORY_3);
		books.add(STORY_4);
		when(this.repo.orderByName()).thenReturn(books);
		assertThat(this.repo.orderByName().stream().sorted(Comparator.comparing(Books::getName))
				.collect(Collectors.toList())).isEqualTo(books);
		verify(this.repo, atLeastOnce()).orderByName();
	}

}
