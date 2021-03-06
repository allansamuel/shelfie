package com.shelfie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import com.shelfie.service.GuardianUserService;

@RestController
@RequestMapping("guardian_user")
public class GuardianUserController {

	@Autowired
	private GuardianUserService guardianUserService = new GuardianUserService();
	
	private boolean isHashValid(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword) || plainPassword.equals(hashedPassword) ? true : false;
	}
	
	@GetMapping("{guardianUserId}")
	public ResponseEntity<GuardianUser> getById(@PathVariable Integer guardianUserId) throws Exception {
		try {
			return ResponseEntity.ok().body(guardianUserService.getById(guardianUserId));
		} catch(Exception exception) {
			throw exception;
		}
	}
	
	@GetMapping("{guardianUserId}/child_profiles")
	public ResponseEntity<List<ChildProfile>> getChildProfiles(@PathVariable Integer guardianUserId) throws Exception {
		 try {
			 return ResponseEntity.ok().body(guardianUserService.getChildProfiles(guardianUserId));
		 } catch(Exception exception) {
			 throw exception;
		 }
	}
	
	@PostMapping
	public ResponseEntity<GuardianUser> create(@RequestBody GuardianUser guardianUser) throws Exception{
		Optional<GuardianUser> existingGuardianUser = guardianUserService.getByEmail(guardianUser.getGuardianUserEmail());
		if(existingGuardianUser.isEmpty()) {
			try {
				return ResponseEntity.ok().body(guardianUserService.create(guardianUser));
			} catch (Exception exception) {
				throw exception;
			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}
	
	@PutMapping("{guardianUserId}")
	public ResponseEntity<GuardianUser> update(
			@PathVariable Integer guardianUserId,
			@RequestBody GuardianUser guardianUserBody) throws Exception {
		try {
			return ResponseEntity.ok().body(guardianUserService.update(guardianUserId, guardianUserBody));
		} catch(Exception exception) {
			throw exception;
		}
	}
	
	@DeleteMapping("{guardianUserId}")
	public void delete(@PathVariable Integer guardianUserId) throws Exception {
		try {
			guardianUserService.delete(guardianUserId);
		} catch(Exception exception) {
			throw exception;
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<GuardianUser> login(@RequestBody GuardianUser guardianUserBody) throws Exception {
		try {
			Optional<GuardianUser> existingGuardianUser = guardianUserService.getByEmail(guardianUserBody.getGuardianUserEmail());
			
			if(!existingGuardianUser.isEmpty()) {
				if(existingGuardianUser.get().getGuardianUserEmail().equals(guardianUserBody.getGuardianUserEmail()) 
						&& isHashValid(guardianUserBody.getGuardianUserPassword(), existingGuardianUser.get().getGuardianUserPassword())) {
					return ResponseEntity.ok().body(existingGuardianUser.get());
				}else{
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
				}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			
		} catch (Exception exception) {
			throw exception;
		} 
	}	
}
