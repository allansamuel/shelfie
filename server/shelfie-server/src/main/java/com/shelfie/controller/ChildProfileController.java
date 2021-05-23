package com.shelfie.controller;

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

import com.shelfie.model.ChildProfile;
import com.shelfie.service.ChildProfileService;


@RestController
@RequestMapping("child_profile")
public class ChildProfileController {
	
	@Autowired
	private ChildProfileService childProfileService;
	
	@GetMapping("{id}")
	public ResponseEntity<ChildProfile> getById(@PathVariable Integer id) throws Exception {
		try {
			return ResponseEntity.ok().body(childProfileService.getById(id));
		} catch (Exception exception) {
			throw exception;
		}		
	}
	
	@PostMapping
	public ResponseEntity<ChildProfile> create(@RequestBody ChildProfile childProfileBody) throws Exception {
		try {
			System.out.println(childProfileBody.toString());
			return ResponseEntity.ok().body(childProfileService.create(childProfileBody));
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ChildProfile> update(
			@RequestBody ChildProfile childProfileBody, 
			@PathVariable Integer id) throws Exception {
		try {
			return ResponseEntity.ok().body(childProfileService.update(childProfileBody, id));
		} catch(Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}/update_coins/{value}")
	public ResponseEntity<ChildProfile> updateCoins(
			@PathVariable Integer id,
			@PathVariable Integer value) throws Exception {
		try {
			return ResponseEntity.ok().body(childProfileService.updateCoins(id, value));
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		try {
			childProfileService.delete(id);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
}
