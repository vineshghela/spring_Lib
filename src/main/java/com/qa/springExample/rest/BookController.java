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

import com.qa.springExample.dto.BooksDTO;
import com.qa.springExample.persistence.domain.Books;
import com.qa.springExample.service.BookService;

@CrossOrigin
@RestController
@RequestMapping("/Book")
public class BookController {

	private BookService service;

	@Autowired
	public BookController(BookService service) {
		super();
		this.service = service;
	}

	@GetMapping("hello")
	public String hello() {
		return "Hello";
	}

	@GetMapping("hello/{name}")
	public String helloFriend(@PathVariable String name) {
		return "Hello " + name;
	}

	@GetMapping("/addNumber/number1/{number1}/number2/{number2}")
	public int letsAdd(@PathVariable("number1") int number1, @PathVariable("number2") int number2) {
		return number1 + number2;
	}

	@PostMapping("/create")
	public ResponseEntity<BooksDTO> create(@RequestBody Books books) {
		BooksDTO created = this.service.create(books);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/read")
	public ResponseEntity<List<BooksDTO>> read() {
		return ResponseEntity.ok(this.service.read());
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<BooksDTO> read(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BooksDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)// will give 204
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// not found
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<BooksDTO> update(@PathVariable Long id, @RequestBody BooksDTO bookDto) {
		return new ResponseEntity<>(this.service.update(bookDto, id), HttpStatus.ACCEPTED);
	}

	@GetMapping("/readBy/name/{author}")
	public ResponseEntity<List<BooksDTO>> findByAuthor(@PathVariable String author) {
		return ResponseEntity.ok(this.service.findByAuthor(author));
	}

	@GetMapping("/read/names")
	public ResponseEntity<List<BooksDTO>> orderByName() {
		return ResponseEntity.ok(this.service.orderByName());
	}

}
