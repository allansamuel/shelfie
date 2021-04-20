package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shelfie.model.ChildProfile;
import com.shelfie.repository.ChildProfileRepository;

import javassist.NotFoundException;

@Controller
@RequestMapping("child_profiles")
public class ChildProfileController {

	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	@GetMapping
	@ResponseBody
	public List<ChildProfile> getAll() {
		return childProfileRepository.findAll();
	}
	
	@GetMapping("{id}")
	@ResponseBody
	public ChildProfile getById(@PathVariable(value = "id") Integer childProfileId) throws Exception {
		return childProfileRepository.findById(childProfileId)
				.orElseThrow(() -> new Exception());
	}
	
	/*
	@GetMapping("getByGuardian/{guardianUserId}")
	@ResponseBody
	public List<ChildProfile> getByGuardianUserId(@RequestParam Integer guardianUserId){
		return repo.findByGuardianUser(guardianUserId);
	}
	*/
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<ChildProfile> create(@RequestBody ChildProfile childProfileBody) throws Exception {
		try {
			ChildProfile newChildProfile = childProfileRepository.save(childProfileBody);
			return ResponseEntity.ok(newChildProfile);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}")
	@ResponseBody
	public ResponseEntity<ChildProfile> edit(@RequestBody ChildProfile childProfileBody, 
			@PathVariable(value = "id") Integer childProfileId) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(childProfileId)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		childProfile.setNickname(childProfileBody.getNickname());
		childProfile.setCharacter(childProfileBody.getCharacter());
		
		ChildProfile updatedChildProfile = childProfileRepository.save(childProfile);
	    return ResponseEntity.ok(updatedChildProfile);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable(value = "id") Integer childProfileId) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(childProfileId)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		childProfileRepository.delete(childProfile);
	}
}
