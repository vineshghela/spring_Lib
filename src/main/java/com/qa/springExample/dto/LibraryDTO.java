package com.qa.springExample.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LibraryDTO {

	
	private Long id;
	private String name;
	
	// add this during relationships
	private List<BooksDTO> books;
	
}
