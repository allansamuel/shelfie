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
import org.springframework.web.bind.annotation.ResponseBody;
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
	@ResponseBody
	public ResponseEntity<GuardianUser> getGuardianUserById(@PathVariable Integer id) throws Exception {
	 GuardianUser guardianUser = guardianUserRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 
	 return ResponseEntity.ok().body(guardianUser);
	}
	
	@GetMapping("{id}/childProfiles")
	@ResponseBody
	public ResponseEntity<List<ChildProfile>> getChildProfiles(@PathVariable Integer id) throws Exception {
		 GuardianUser guardianUser = guardianUserRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		 
		 return ResponseEntity.ok().body(guardianUser.getChildProfiles());
	}
	
	@PostMapping
	@ResponseBody
	public GuardianUser createGuardianUser(@RequestBody GuardianUser guardianUser) throws Exception{
		GuardianUser newGuardianUser = guardianUserRepository.save(guardianUser);
		
		return guardianUserRepository.save(newGuardianUser); 
	}
	
	@PutMapping("{id}")
	@ResponseBody
	public ResponseEntity<GuardianUser> updateGuardianUser(@PathVariable Integer id,
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
	@ResponseBody
	public void deleteGuardianUser(@PathVariable Integer id) throws Exception {
		GuardianUser guardianUser = guardianUserRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		guardianUserRepository.delete(guardianUser);
	}
}
