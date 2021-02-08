package com.qa.springExample.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springExample.persistence.domain.Library;



@Repository
public interface LibraryRepo extends JpaRepository<Library,Long> {

}
