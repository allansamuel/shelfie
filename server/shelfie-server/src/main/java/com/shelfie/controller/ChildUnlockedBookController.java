package com.shelfie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.service.ChildUnlockedBookService;

@RestController
@RequestMapping("child_unlocked_books")
public class ChildUnlockedBookController {
	
	@Autowired
	private ChildUnlockedBookService childUnlockedBookService ;
	
	@PostMapping("child_profile/{id}/unlock_book/{interactiveBookId}")
	public ResponseEntity<ChildProfile> unlock( @PathVariable Integer id, @PathVariable Integer interactiveBookId) 
			throws Exception {
		
		try {
			return ResponseEntity.ok(childUnlockedBookService.unlock(id, interactiveBookId));
			
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@GetMapping("child_profile/{childProfileId}/interactive_book/{interactiveBookId}")
	public ResponseEntity<ChildUnlockedBook> getByChildAndBook(@PathVariable Integer childProfileId,@PathVariable Integer interactiveBookId)
			throws Exception {

		try {
			
			ChildUnlockedBook childUnlockedBook = childUnlockedBookService.getByChildAndBook(childProfileId, interactiveBookId);
		
			if(childUnlockedBook == null) {
				return ResponseEntity.notFound().build();
			}else {
			return ResponseEntity.ok(childUnlockedBookService.getByChildAndBook(childProfileId, interactiveBookId));
			}
			
		}catch (Exception exception) {
			throw exception;
		}
		
	}
}