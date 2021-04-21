package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.InteractiveBook;
import com.shelfie.repository.InteractiveBookRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("interactive_book")
public class InteractiveBookController {

	@Autowired
	private InteractiveBookRepository interactiveBookRepository;
	
	@GetMapping
	public List<InteractiveBook> getAll() {
		return interactiveBookRepository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<InteractiveBook> getById(@PathVariable Integer id) throws Exception {
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		return ResponseEntity.ok().body(interactiveBook);		
	}
	
	@PostMapping
	public ResponseEntity<InteractiveBook> create(@RequestBody InteractiveBook interactiveBookBody) throws Exception {
		try {
			InteractiveBook newInteractiveBook = interactiveBookRepository.save(interactiveBookBody);
			return ResponseEntity.ok().body(newInteractiveBook);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<InteractiveBook> edit(@RequestBody InteractiveBook interactiveBookBody, 
			@PathVariable Integer id) throws Exception {
		
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		//interactiveBook.setBookAuthors(interactiveBookBody.getBookAuthors());
		//interactiveBook.setBookCategories(interactiveBookBody.getBookCategories());
		//interactiveBook.setBookCover(interactiveBookBody.getBookCover());
		//interactiveBook.setChapters(interactiveBookBody.getChapters());
		//interactiveBook.setCharacters(interactiveBookBody.getCharacters());
		//interactiveBook.setPrice(interactiveBookBody.getPrice());
		//interactiveBook.setPublishDate(interactiveBookBody.getPublishDate());
		//interactiveBook.setQuests(interactiveBookBody.getQuests());
		//interactiveBook.setSinopsys(interactiveBookBody.getSinopsys());
		//interactiveBook.setTitle(interactiveBookBody.getTitle());
		
		System.out.println(interactiveBookBody);
		
		InteractiveBook updatedInteractiveBook = interactiveBookRepository.save(interactiveBook);
	    return ResponseEntity.ok().body(updatedInteractiveBook);
	}
	
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		interactiveBookRepository.delete(interactiveBook);
	}
	
}
