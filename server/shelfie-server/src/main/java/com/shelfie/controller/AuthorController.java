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

import com.shelfie.model.Author;
import com.shelfie.repository.AuthorRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("author")
public class AuthorController {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@GetMapping("{id}")
	public ResponseEntity<Author> getById(@PathVariable Integer id) throws Exception {
	 Author author = authorRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(author);
	}
	
	@GetMapping
	public ResponseEntity<List<Author>> getAll(){
		try {
			List <Author> author = authorRepository.findAll();
			return ResponseEntity.ok().body(author); 
		} catch (Exception exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<Author> create(@RequestBody Author author) throws Exception{
		try {
		Author newAuthor = authorRepository.save(author);
		return ResponseEntity.ok().body(newAuthor); 
		} catch (Exception exception) {
			throw exception;
		}
	}
}
