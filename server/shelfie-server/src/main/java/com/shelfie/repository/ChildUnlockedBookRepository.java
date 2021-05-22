package com.shelfie.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.ChildUnlockedBook;

public interface ChildUnlockedBookRepository extends JpaRepository<ChildUnlockedBook, Integer> {
	
	Optional<ChildUnlockedBook> findByChildProfile_ChildProfileIdAndInteractiveBook_InteractiveBookId
	(Integer childProfile, Integer interactiveBook);

}