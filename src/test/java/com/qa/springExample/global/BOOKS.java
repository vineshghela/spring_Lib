package com.qa.springExample.global;

import lombok.Getter;

@Getter
public enum BOOKS {
	STORY1("The life of vin", "AA", "penguin", 20.99, 3000), STORY2("The story of vin", "BB", "penguin", 20.99, 3000),
	STORY3("A life of vin", "CC", "penguin", 20.99, 3000), STORY4("A story of vin", "DD", "penguin", 20.99, 3000);

	private final String name;
	private final String author;
	private final String publisher;
	private final double price;
	private final int pages;

	BOOKS(String name, String author, String publisher, double price, int pages) {
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.pages = pages;
	}

}
