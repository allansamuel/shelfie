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
	public ResponseEntity<GuardianUser> getGuardianUserById(@PathVariable(value = "id") Integer guardianUserId)
	 throws Exception {
		
	 GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
			 .orElseThrow(() -> new NotFoundException ("not found" + guardianUserId));
	 
	 return ResponseEntity.ok().body(guardianUser);
	}
	
	@GetMapping("{id}/childProfiles")
	public ResponseEntity<List<ChildProfile>> getChildProfiles(@PathVariable(value = "id") Integer guardianUserId) throws Exception {
		 GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
				 .orElseThrow(() -> new NotFoundException ("not found" + guardianUserId));
		 return ResponseEntity.ok().body(guardianUser.getChildProfiles());
	}
	
	@PostMapping
	public GuardianUser createGuardianUser(@RequestBody GuardianUser guardianUser) throws Exception{
		GuardianUser newGuardianUser = guardianUserRepository.save(guardianUser);
		return guardianUserRepository.save(newGuardianUser); 
	}
	
	@PutMapping("{id}")
	public ResponseEntity<GuardianUser> updateGuardianUser(@PathVariable(value = "id") Integer guardianUserId,
			@RequestBody GuardianUser guardianUserDetails) throws Exception {
	 GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
			 .orElseThrow(() -> new NotFoundException("not found" + guardianUserId));
	 guardianUser.setGuardianUserName(guardianUserDetails.getGuardianUserName());
	 guardianUser.setGuardianUserEmail(guardianUserDetails.getGuardianUserEmail());
	 guardianUser.setGuardianUserPassword(guardianUserDetails.getGuardianUserPassword());
	 guardianUser.setChildProfiles(guardianUserDetails.getChildProfiles());
	 
	 final GuardianUser updatedGuardianUser = guardianUserRepository.save(guardianUser);
	 return ResponseEntity.ok(updatedGuardianUser);
	}
	
	@DeleteMapping("{id}")
	public void deleteGuardianUser(@PathVariable(value = "id") Integer guardianUserId) throws Exception {
		GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
				 .orElseThrow(() -> new NotFoundException ("not found" + guardianUserId));
		guardianUserRepository.delete(guardianUser);
	}
}
