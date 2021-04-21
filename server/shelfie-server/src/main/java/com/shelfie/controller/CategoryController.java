package com.shelfie.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) throws Exception {
		Category category = categoryRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(category);
	}
	
	@GetMapping
	public List <Category> getAll(){
	 List <Category> category = categoryRepository.findAll();
	 return category;
	}
	
	@PostMapping
	public Category createCategory (@RequestBody Category  category ) throws Exception{
		try {
			Category newCategory  = categoryRepository.save(category );
		return categoryRepository.save(newCategory ); 
		} catch (Exception exception) {
			throw exception;
		} 
	}
}
