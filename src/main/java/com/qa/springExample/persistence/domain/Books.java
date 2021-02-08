package com.qa.springExample.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Books {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 2,max = 55)
	private String name;
	
	
	@NotNull
	@Size(min = 2,max = 55)
	private String author;
	
	@NotNull
	@Size(min = 2,max = 55)
	private String publisher;
	
	@NotNull
	@Min(0)
	@Max(5000)
	private double price;
	
	@NotNull
	private int pages;
	
	@ManyToOne
	private Library library;

	public Books(Long id, String name,String author, String publisher,double price,int pages) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.pages = pages;
	}

	public Books(String name,  String author,String publisher, double price,int pages) {
		super();
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.pages = pages;
	}

	
	
}
