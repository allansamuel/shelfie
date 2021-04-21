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

import com.shelfie.model.ChildProfile;
import com.shelfie.repository.ChildProfileRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("child_profile")
public class ChildProfileController {

	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	@GetMapping
	public List<ChildProfile> getAll() {
		return childProfileRepository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ChildProfile> getById(@PathVariable Integer id) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		return ResponseEntity.ok().body(childProfile);		
	}
	
	@PostMapping
	public ResponseEntity<ChildProfile> create(@RequestBody ChildProfile childProfileBody) throws Exception {
		try {
			ChildProfile newChildProfile = childProfileRepository.save(childProfileBody);
			return ResponseEntity.ok().body(newChildProfile);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ChildProfile> update(@RequestBody ChildProfile childProfileBody, 
			@PathVariable Integer id) throws Exception {
		
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		childProfile.setNickname(childProfileBody.getNickname());
		childProfile.setCharacter(childProfileBody.getCharacter());
		
		ChildProfile updatedChildProfile = childProfileRepository.save(childProfile);
	    return ResponseEntity.ok().body(updatedChildProfile);
	}
	
	@PutMapping("{id}/update_coins/{value}")
	public ResponseEntity<Integer> updateCoins(@PathVariable Integer id,
			@PathVariable Integer value) throws Exception {
		
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		Integer updatedCoins = childProfile.getCoins() + value;
		childProfile.setCoins(updatedCoins);
		
		childProfileRepository.save(childProfile);
	    return ResponseEntity.ok().body(updatedCoins);
	}
	
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		childProfileRepository.delete(childProfile);
	}
	
}
