package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shelfie.model.InteractiveBook;

public interface InteractiveBookRepository extends JpaRepository<InteractiveBook, Integer>{
	
}
