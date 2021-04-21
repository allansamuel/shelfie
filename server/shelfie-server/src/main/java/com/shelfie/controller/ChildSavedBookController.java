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
import com.shelfie.model.ChildSavedBook;
import com.shelfie.repository.ChildSavedBookRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("child_saved_book")
public class ChildSavedBookController {
	
	@Autowired
	private ChildSavedBookRepository childSavedBookRepository;
	
	@GetMapping("{id}")
	public ResponseEntity<ChildSavedBook> getChildSavedBookById(@PathVariable Integer id) throws Exception {
		ChildSavedBook childSavedBook = childSavedBookRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(childSavedBook);
	}
	
	@GetMapping
	public List <ChildSavedBook> getAll(){
	 List <ChildSavedBook> childSavedBooks = childSavedBookRepository.findAll();
	 return childSavedBooks;
	}

	@PostMapping
	public ChildSavedBook createQuest(@RequestBody ChildSavedBook childSavedBook) throws Exception{
		ChildSavedBook newChildSavedBook = childSavedBookRepository.save(childSavedBook);
		return childSavedBookRepository.save(newChildSavedBook); 
	}
}
