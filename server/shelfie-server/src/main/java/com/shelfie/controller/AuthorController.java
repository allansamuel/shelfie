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
	public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) throws Exception {
	 Author author = authorRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(author);
	}
	
	@GetMapping
	public List <Author> getAll(){
	 List <Author> author = authorRepository.findAll();
	 return author;
	}

	@PostMapping
	public Author createAuthor(@RequestBody Author author) throws Exception{
		Author newAuthor = authorRepository.save(author);
		return authorRepository.save(newAuthor); 
	}
}
