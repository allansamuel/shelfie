package com.shelfie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shelfie.model.InteractiveBook;

@Repository
public interface InteractiveBookRepository extends JpaRepository<InteractiveBook, Integer>{

	Optional <List<InteractiveBook>> findByBookCategories_CategoryId(Integer categoryId);

}
