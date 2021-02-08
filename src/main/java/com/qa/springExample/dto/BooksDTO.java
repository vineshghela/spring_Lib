package com.qa.springExample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BooksDTO {


	private Long id;
	private String name;
	private String author;
	private String publisher;
	private double price;
	private int pages;
	
}
