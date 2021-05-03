package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.GuardianUser;
import com.shelfie.model.ChildProfile;
import com.shelfie.repository.GuardianUserRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("guardian_user")
public class GuardianUserController {

	@Autowired
	private GuardianUserRepository guardianUserRepository;
	
	
	@GetMapping("{id}")
	public ResponseEntity<GuardianUser> getById(@PathVariable Integer id) throws Exception {
	 GuardianUser guardianUser = guardianUserRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(guardianUser);
	}
	
	@GetMapping("{id}/child_profiles")
	public ResponseEntity<List<ChildProfile>> getChildProfiles(@PathVariable Integer id) throws Exception {
		 GuardianUser guardianUser = guardianUserRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		 return ResponseEntity.ok().body(guardianUser.getChildProfiles());
	}
	
	@PostMapping
	public ResponseEntity<GuardianUser> create(@RequestBody GuardianUser guardianUser) throws Exception{
		try {
			GuardianUser newGuardianUser = guardianUserRepository.save(guardianUser);
			 return ResponseEntity.ok().body(newGuardianUser);
		} catch (Exception exception) {
			throw exception;
		} 
	}
	
	@PutMapping("{id}")
	public ResponseEntity<GuardianUser> update(@PathVariable Integer id,
			@RequestBody GuardianUser guardianUserDetails) throws Exception {
	 GuardianUser guardianUser = guardianUserRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException("not found" + id));
	 
	 guardianUser.setGuardianUserName(guardianUserDetails.getGuardianUserName());
	 guardianUser.setGuardianUserEmail(guardianUserDetails.getGuardianUserEmail());
	 guardianUser.setGuardianUserPassword(guardianUserDetails.getGuardianUserPassword());
	 guardianUser.setChildProfiles(guardianUserDetails.getChildProfiles());
	 
	 final GuardianUser updatedGuardianUser = guardianUserRepository.save(guardianUser);
	 return ResponseEntity.ok(updatedGuardianUser);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		
		GuardianUser guardianUser = guardianUserRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		
		guardianUserRepository.delete(guardianUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<GuardianUser> login(@RequestBody GuardianUser guardianUserBody) throws Exception {
		
		try {
			GuardianUser guardianUser = guardianUserRepository.findByGuardianUserEmail(guardianUserBody.getGuardianUserEmail())
				 .orElseThrow(() -> new NotFoundException ("Email não cadastrado" + guardianUserBody.getGuardianUserEmail()));
			
			if(guardianUser.getGuardianUserEmail().equals(guardianUserBody.getGuardianUserEmail()) 
					&& guardianUser.getGuardianUserPassword().equals(guardianUserBody.getGuardianUserPassword())) {
				return ResponseEntity.ok().body(guardianUser);
			}else{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			
		} catch (Exception exception) {
			throw exception;
		} 
	}
}
