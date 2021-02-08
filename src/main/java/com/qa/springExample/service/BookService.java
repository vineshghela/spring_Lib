package com.qa.springExample.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springExample.dto.BooksDTO;
import com.qa.springExample.exceptions.BookNotFoundException;
import com.qa.springExample.persistence.domain.Books;
import com.qa.springExample.persistence.repo.BooksRepo;
import com.qa.springExample.util.SpringBeanUtil;

@Service
public class BookService {

	private BooksRepo repo;

	private ModelMapper mapper;

	private BooksDTO mapToDto(Books books) {
		return this.mapper.map(books, BooksDTO.class);
	}

	@Autowired
	public BookService(BooksRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// create
	public BooksDTO create(Books books) {
		return this.mapToDto(this.repo.save(books));
	}

	// read
	public List<BooksDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

//	public List<BooksDTO> read(){
//		List<Books> listOFClass =this.repo.findAll();
//		List<BooksDTO> listOFClassDTO = listOFClass.stream()
//				.map(this::mapToDto)
//				.collect(Collectors.toList());
//		return listOFClassDTO;
//	} 

	// read one
	public BooksDTO read(Long id) {
		return this.mapToDto(this.repo.findById(id).orElseThrow(BookNotFoundException::new));
	}

	// update
	public BooksDTO update(BooksDTO bookDto, Long id) {
		Books toUpdate = this.repo.findById(id).orElseThrow(BookNotFoundException::new);
		toUpdate.setName(bookDto.getName());
		SpringBeanUtil.mergeNotNull(bookDto, toUpdate);
		return this.mapToDto(this.repo.save(toUpdate));
	}

	// delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	// findbyAuth
	public List<BooksDTO> findByAuthor(String author) {
		return this.repo.findByAuthor(author).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// orderByName
	public List<BooksDTO> orderByName() {
		return this.repo.orderByName().stream().map(this::mapToDto).collect(Collectors.toList());
	}

}
