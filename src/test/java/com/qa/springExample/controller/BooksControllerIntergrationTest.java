package com.qa.springExample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.springExample.dto.BooksDTO;
import com.qa.springExample.global.BOOKS;
import com.qa.springExample.global.LIBRARY;
import com.qa.springExample.persistence.domain.Books;
import com.qa.springExample.persistence.domain.Library;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@Sql(scripts = { "classpath:schema.sql", "classpath:data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class BooksControllerIntergrationTest {

	private static final MediaType JSON_FORMAT = MediaType.APPLICATION_JSON;

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper jsonifier;
	@Autowired
	private ModelMapper mapper;

	private BooksDTO mapToDTO(Books books) {
		return this.mapper.map(books, BooksDTO.class);
	}

	private final String URI = "/Book";

	private final Long TEST_ID = 1L;

	private final Books STORY_1 = new Books(TEST_ID, BOOKS.STORY1.getName(), BOOKS.STORY1.getAuthor(),
			BOOKS.STORY1.getPublisher(), BOOKS.STORY1.getPrice(), BOOKS.STORY1.getPages());
	private final Books STORY_2 = new Books(TEST_ID, BOOKS.STORY2.getName(), BOOKS.STORY2.getAuthor(),
			BOOKS.STORY2.getPublisher(), BOOKS.STORY2.getPrice(), BOOKS.STORY2.getPages());
	private final Books STORY_3 = new Books(TEST_ID, BOOKS.STORY3.getName(), BOOKS.STORY3.getAuthor(),
			BOOKS.STORY3.getPublisher(), BOOKS.STORY3.getPrice(), BOOKS.STORY3.getPages());
	private final Books STORY_4 = new Books(TEST_ID, BOOKS.STORY4.getName(), BOOKS.STORY4.getAuthor(),
			BOOKS.STORY4.getPublisher(), BOOKS.STORY4.getPrice(), BOOKS.STORY4.getPages());

	private final Library TEST_LIBRARY = new Library(TEST_ID, LIBRARY.LL.getName());

	@Test
	void testCreate() throws Exception {
		STORY_1.setId(TEST_ID + 1);
		STORY_1.setLibrary(TEST_LIBRARY);
		BooksDTO expected = this.mapToDTO(STORY_1);

		this.mvc.perform(post(URI + "/create").accept(JSON_FORMAT).contentType(JSON_FORMAT)
				.content(this.jsonifier.writeValueAsString(STORY_1))).andExpect(status().isCreated())
				.andExpect(content().json(this.jsonifier.writeValueAsString(expected)));
	}

	@Test
	void testRead() throws Exception {
		List<BooksDTO> books = new ArrayList<>();
		books.add(this.mapToDTO(STORY_1));
		books.add(this.mapToDTO(STORY_2));
		books.add(this.mapToDTO(STORY_3));
		books.add(this.mapToDTO(STORY_4));

		this.mvc.perform(get(URI + "/read").accept(JSON_FORMAT)).andExpect(status().isOk())
				.andExpect(content().json(this.jsonifier.writeValueAsString(books)));

	}

	@Test
	void testReadOne() throws Exception {
		BooksDTO expected = this.mapToDTO(STORY_1);

		this.mvc.perform(get(URI + "/read/" + TEST_ID).accept(JSON_FORMAT)).andExpect(status().isOk())
				.andExpect(content().json(this.jsonifier.writeValueAsString(expected)));
	}

	@Test
	void testUpdate() throws Exception {
		BooksDTO expected = this.mapToDTO(STORY_1);
		this.mvc.perform(put(URI + "/update/" + TEST_ID).accept(JSON_FORMAT).contentType(JSON_FORMAT)
				.content(this.jsonifier.writeValueAsString(STORY_1))).andExpect(status().isAccepted())
				.andExpect(content().json(this.jsonifier.writeValueAsString(expected)));
	}

	@Test
	void testDelete() throws Exception {
		this.mvc.perform(delete(URI + "/delete/" + TEST_ID)).andExpect(status().isNoContent());
	}

	@Test
	void testFindByAuthor() throws Exception {
		List<BooksDTO> books = new ArrayList<>();
		books.add(this.mapToDTO(STORY_1));
		books.add(this.mapToDTO(STORY_2));
		books.add(this.mapToDTO(STORY_3));
		books.add(this.mapToDTO(STORY_4));

		this.mvc.perform(get(URI + "/readBy/name/" + STORY_1.getAuthor()).accept(JSON_FORMAT))
				.andExpect(content().json(this.jsonifier.writeValueAsString(books.stream()
						.filter(e -> e.getAuthor().equals(STORY_1.getAuthor())).collect(Collectors.toList()))));
	}

	@Test
	void testOrderBy() throws Exception {
		List<BooksDTO> books = new ArrayList<>();
		books.add(this.mapToDTO(STORY_1));
		books.add(this.mapToDTO(STORY_2));
		books.add(this.mapToDTO(STORY_3));
		books.add(this.mapToDTO(STORY_4));

		this.mvc.perform(get(URI + "/read/names").accept(JSON_FORMAT)).andExpect(status().isOk())
				.andExpect(content().json(this.jsonifier.writeValueAsString(books)));
	}

}
