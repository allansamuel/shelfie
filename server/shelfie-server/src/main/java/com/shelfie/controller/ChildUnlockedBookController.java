package com.shelfie.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.ChildUnlockedBookService;

@RestController
@RequestMapping("child_unlocked_books")
public class ChildUnlockedBookController {
	
	@Autowired
	private ChildUnlockedBookService childUnlockedBookService ;
	
	@PostMapping("child_profile/{id}/unlock_book")
	public ResponseEntity<ChildProfile> unlock( @PathVariable Integer id, @RequestBody InteractiveBook interactiveBookBody) 
			throws Exception {
		
		try {
			return ResponseEntity.ok(childUnlockedBookService.unlock(id, interactiveBookBody));
			
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@GetMapping("child_profile/{ChildProfileId}/interactive_book/{interactiveBookId}")
	public ResponseEntity<Optional<ChildUnlockedBook>> isUnlocked(@PathVariable Integer ChildProfileId, Integer interactiveBookId)
			throws Exception {

		try {
			return ResponseEntity.ok(childUnlockedBookService.getByChildAndBook(ChildProfileId, interactiveBookId));
		}catch (Exception exception) {
			throw exception;
		}
		
	}
}