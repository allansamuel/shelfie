package com.shelfie.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.Category;
import com.shelfie.repository.CategoryRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("category")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("{id}")
	public ResponseEntity<Category> getById(@PathVariable Integer id) throws Exception {
		Category category = categoryRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(category);
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> getAll(){
		try {
			List<Category> categories = categoryRepository.findAll();
			return ResponseEntity.ok().body(categories );
		} catch (Exception exception) {
			throw exception;
		} 
	}
	
	@PostMapping
	public ResponseEntity<Category> create(@RequestBody Category  category ) throws Exception{
		try {
			Category newCategory  = categoryRepository.save(category );
		return ResponseEntity.ok().body(newCategory ); 
		} catch (Exception exception) {
			throw exception;
		} 
	}
}
