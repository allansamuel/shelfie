package com.shelfie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shelfie.model.InteractiveBook;

@Repository
public interface InteractiveBookRepository extends JpaRepository<InteractiveBook, Integer>{

	Page<InteractiveBook> findAllByOrderByPublishDateDescTitleAsc(Pageable pageble);

	Page<InteractiveBook> findByTitleIgnoreCaseContainingOrBookCategories_CategoryNameIgnoreCaseContainingOrderByPublishDateDesc(String title, String categoryName, Pageable pageble);
	
}
