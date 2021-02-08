package com.qa.springExample.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.springExample.dto.LibraryDTO;
import com.qa.springExample.exceptions.BookNotFoundException;
import com.qa.springExample.exceptions.LibraryNotFoundException;
import com.qa.springExample.persistence.domain.Library;
import com.qa.springExample.persistence.repo.LibraryRepo;
import com.qa.springExample.util.SpringBeanUtil;

@Service
public class LibraryService {

	private LibraryRepo repo;
	
	private ModelMapper mapper;
	
	
	private LibraryDTO mapToDto(Library library) {
		return this.mapper.map(library, LibraryDTO.class);
	}
	@Autowired
	public LibraryService(LibraryRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	//create 
	public LibraryDTO create(Library library) {
		return this.mapToDto(this.repo.save(library));
	}
	
	//read
	public List<LibraryDTO> read() {
		return this.repo.findAll()
				.stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}
	
//	public List<LibraryDTO> read(){
//		List<Library> listOFClass =this.repo.findAll();
//		List<LibraryDTO> listOFClassDTO = listOFClass.stream()
//				.map(this::mapToDto)
//				.collect(Collectors.toList());
//		return listOFClassDTO;
//	} 
//	
	//read one
	public LibraryDTO read(Long id) {
		return this.mapToDto(this.repo.findById(id).orElseThrow(BookNotFoundException::new));
	}
	
	//update
	public LibraryDTO update(LibraryDTO libraryDTO , Long id) {
		Library toUpdate = this.repo.findById(id).orElseThrow(LibraryNotFoundException::new);
		toUpdate.setName(libraryDTO.getName());
		SpringBeanUtil.mergeNotNull(libraryDTO, toUpdate);
		return this.mapToDto(this.repo.save(toUpdate));
		 }

	
	//delete
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
	
	
}
