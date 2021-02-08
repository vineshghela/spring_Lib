package com.qa.springExample.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.qa.springExample.dto.LibraryDTO;
import com.qa.springExample.persistence.domain.Library;
import com.qa.springExample.service.LibraryService;



@CrossOrigin
@RestController
@RequestMapping("/Library")
public class LibraryController {

	private LibraryService service;
	
	
	@Autowired
	public LibraryController(LibraryService service) {
		super();
		this.service = service;
	}
	@GetMapping("hello")
	public String hello() {
		return "Hello";
	}
	@GetMapping("hello/{name}")
	public String helloFriend(@PathVariable String name) {
		return "Hello "+ name;
	}
	@GetMapping("/addNumber/number1/{number1}/number2/{number2}")
	public int letsAdd(@PathVariable("number1") int number1,@PathVariable("number2")int number2) {
		return number1+number2;
	}
	
	@PostMapping("/create")
	public ResponseEntity<LibraryDTO> create(@RequestBody Library library){
		LibraryDTO created = this.service.create(library);
		return new ResponseEntity<>(created,HttpStatus.CREATED);
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<LibraryDTO>> read(){
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/read/{id}")
	  public ResponseEntity<LibraryDTO> read(@PathVariable Long id) {
	        return ResponseEntity.ok(this.service.read(id));
	    }

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<LibraryDTO> delete(@PathVariable Long id){
		return this.service.delete(id)? new ResponseEntity<>(HttpStatus.NO_CONTENT)// will give 204
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// not found
	}
	
	@PutMapping("/update/{id}")
	 public ResponseEntity<LibraryDTO>update(@PathVariable Long id, @RequestBody LibraryDTO libraryDTO) {
		return new ResponseEntity<>(this.service.update(libraryDTO, id), HttpStatus.ACCEPTED);}

	
}
