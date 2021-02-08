package com.qa.springExample.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.springExample.persistence.domain.Books;

@Repository
public interface BooksRepo extends JpaRepository<Books,Long>  {

	@Query(value = "SELECT * FROM BOOKS WHERE AUTHOR = ?1", nativeQuery = true)
    List<Books> findByAuthor(String author);
	
	@Query(value = "SELECT * FROM BOOKS ORDER BY name", nativeQuery = true)
    List<Books> orderByName();


}
