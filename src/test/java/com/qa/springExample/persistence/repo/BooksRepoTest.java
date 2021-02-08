package com.qa.springExample.persistence.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.springExample.global.BOOKS;
import com.qa.springExample.persistence.domain.Books;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BooksRepoTest {

	@Autowired
	private BooksRepo repo;

	private final Books STORY_1 = new Books(BOOKS.STORY1.getName(), BOOKS.STORY1.getAuthor(),
			BOOKS.STORY1.getPublisher(), BOOKS.STORY1.getPrice(), BOOKS.STORY1.getPages());
	private final Books STORY_2 = new Books(BOOKS.STORY2.getName(), BOOKS.STORY2.getAuthor(),
			BOOKS.STORY2.getPublisher(), BOOKS.STORY2.getPrice(), BOOKS.STORY2.getPages());
	private final Books STORY_3 = new Books(BOOKS.STORY3.getName(), BOOKS.STORY3.getAuthor(),
			BOOKS.STORY3.getPublisher(), BOOKS.STORY3.getPrice(), BOOKS.STORY3.getPages());
	private final Books STORY_4 = new Books(BOOKS.STORY4.getName(), BOOKS.STORY4.getAuthor(),
			BOOKS.STORY4.getPublisher(), BOOKS.STORY4.getPrice(), BOOKS.STORY4.getPages());

	private final List<Books> DATA_SET = List.of(STORY_1, STORY_2, STORY_3, STORY_4);

	@BeforeAll
	void setUp() {
		this.repo.saveAll(DATA_SET);
	}

	@Test
	void findByAuthorTest() throws Exception {
		assertThat(this.repo.findByAuthor(BOOKS.STORY1.getAuthor()).stream().map(e -> e.getAuthor())
				.collect(Collectors.toList()))
						.isEqualTo(DATA_SET.stream().filter(e -> e.getAuthor().equals(STORY_1.getAuthor()))
								.map(e -> e.getAuthor()).collect(Collectors.toList()));
	}

	@Test
	void orderByNameTest() throws Exception {
		assertThat(this.repo.orderByName().stream().map(e -> e.getName()).collect(Collectors.toList()))
				.isEqualTo(DATA_SET.stream().sorted(Comparator.comparing(Books::getName)).map(e -> e.getName())
						.collect(Collectors.toList()));
	}
}
