package com.shelfie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shelfie.model.InteractiveBook;

@Repository
public interface InteractiveBookRepository extends JpaRepository<InteractiveBook, Integer>{

	Page<InteractiveBook> findByBookCategories_CategoryId(Integer categoryId, Pageable pageble);

	Page<InteractiveBook> findByTitleIgnoreCaseContaining(String title, Pageable pageble);
	
}
