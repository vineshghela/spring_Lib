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

import com.qa.springExample.dto.LibraryDTO;
import com.qa.springExample.global.LIBRARY;
import com.qa.springExample.persistence.domain.Library;
import com.qa.springExample.rest.LibraryController;
import com.qa.springExample.service.LibraryService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class LibraryControllerUnitTest {

	@Autowired
	private LibraryController controller;
	@MockBean
	private LibraryService service;
	@Autowired
	private ModelMapper mapper;

	private LibraryDTO mapToDTO(Library library) {
		return this.mapper.map(library, LibraryDTO.class);
	}

	private final Long TEST_ID = 1L;

	private final Library LIBRARY_1 = new Library(TEST_ID, LIBRARY.LL.getName());
	private final Library LIBRARY_2 = new Library(TEST_ID, LIBRARY.KL.getName());
	private final Library LIBRARY_3 = new Library(TEST_ID, LIBRARY.AL.getName());

	@Test
	void createTest() throws Exception {
		LibraryDTO expected = this.mapToDTO(LIBRARY_1);
		when(this.service.create(LIBRARY_1)).thenReturn(expected);
		assertThat(new ResponseEntity<LibraryDTO>(expected, HttpStatus.CREATED))
				.isEqualTo(this.controller.create(LIBRARY_1));
		verify(this.service, atLeastOnce()).create(LIBRARY_1);
	}

	@Test
	void ReadTest() throws Exception {
		List<Library> library = new ArrayList<>();
		library.add(LIBRARY_1);
		when(this.service.read()).thenReturn(library.stream().map(this::mapToDTO).collect(Collectors.toList()));
		assertThat(this.controller.read().getBody().isEmpty()).isFalse();
		verify(this.service, atLeastOnce()).read();

	}

	@Test
	void ReadOneTest() throws Exception {
		LibraryDTO expected = this.mapToDTO(LIBRARY_1);
		when(this.service.read(TEST_ID)).thenReturn(expected);
		assertThat(new ResponseEntity<LibraryDTO>(expected, HttpStatus.OK)).isEqualTo(this.controller.read(TEST_ID));
		verify(this.service, atLeastOnce()).read(TEST_ID);
	}

	@Test
	void UpdateTest() throws Exception {
		LibraryDTO expected = this.mapToDTO(LIBRARY_1);
		when(this.service.update(expected, TEST_ID)).thenReturn(expected);
		assertThat(new ResponseEntity<LibraryDTO>(expected, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.update(TEST_ID, expected));
		verify(this.service, atLeastOnce()).update(expected, TEST_ID);
	}

	@Test
	void DeleteTest() throws Exception {
		this.controller.delete(TEST_ID);
		verify(this.service, atLeastOnce()).delete(TEST_ID);
	}
}
