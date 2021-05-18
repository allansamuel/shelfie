package com.shelfie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.model.InteractiveBook;
import com.shelfie.repository.ChildProfileRepository;
import com.shelfie.repository.ChildUnlockedBookRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("child_unlocked_book")
public class ChildUnlockedBookController {
	
	@Autowired
	private ChildUnlockedBookRepository childUnlockedBookRepository;
	
	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	@PostMapping("child_profile/{id}/unlock_book")
	public ResponseEntity<ChildProfile> create( @PathVariable Integer id, @RequestBody InteractiveBook interactiveBookBody) 
			throws Exception {
		
		try {
			ChildProfile childProfile = childProfileRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("not found"));
			
			ChildUnlockedBook childUnlockedBook = new ChildUnlockedBook();
			
			childUnlockedBook.setChildProfile(childProfile);
			childUnlockedBook.setInteractiveBook(interactiveBookBody);
			
			childUnlockedBook = childUnlockedBookRepository.save(childUnlockedBook);
			
			return ResponseEntity.ok().body(childProfile);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
}
