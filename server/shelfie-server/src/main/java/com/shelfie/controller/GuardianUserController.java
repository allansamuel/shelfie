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

	private String hashPassword (String plainTextPassword) {
		 return BCrypt.hashpw (plainTextPassword, BCrypt.gensalt());
	}
	
	@PostMapping
	public ResponseEntity<GuardianUser> create(@RequestBody GuardianUser guardianUser) throws Exception{
		
		Optional<GuardianUser> validGuardianUser = guardianUserRepository.findByGuardianUserEmail(guardianUser.getGuardianUserEmail());

		if(validGuardianUser.isEmpty()) {
			
			try {
				guardianUser.setGuardianUserPassword(hashPassword(guardianUser.getGuardianUserPassword()));
				GuardianUser newGuardianUser = guardianUserRepository.save(guardianUser);
				return ResponseEntity.ok().body(newGuardianUser);
				
			} catch (Exception exception) {
				throw exception;
			}
			
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<GuardianUser> update(@PathVariable Integer id,
			@RequestBody GuardianUser guardianUserDetails) throws Exception {
	 GuardianUser guardianUser = guardianUserRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException("not found" + id));
	 
	 guardianUser.setGuardianUserName(guardianUserDetails.getGuardianUserName());
	 guardianUser.setGuardianUserEmail(guardianUserDetails.getGuardianUserEmail());
	 guardianUser.setGuardianUserPassword(hashPassword(guardianUserDetails.getGuardianUserPassword()));
	 guardianUser.setChildProfiles(guardianUserDetails.getChildProfiles());
	 
	 final GuardianUser updatedGuardianUser = guardianUserRepository.save(guardianUser);
	 return ResponseEntity.ok(updatedGuardianUser);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		GuardianUser guardianUser = guardianUserRepository.findById(id).orElseThrow(() -> new NotFoundException ("not found" + id));
		guardianUserRepository.delete(guardianUser);
	}
	
	private boolean checkHash(String plainPassword, String hashedPassword) {
		if(BCrypt.checkpw(plainPassword, hashedPassword) || plainPassword.equals(hashedPassword)) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/login")
	public ResponseEntity<GuardianUser> login(@RequestBody GuardianUser guardianUserBody) throws Exception {
		try {
			GuardianUser guardianUser = guardianUserRepository.findByGuardianUserEmail(guardianUserBody.getGuardianUserEmail())
				 .orElseThrow(() -> new NotFoundException ("Email não cadastrado" + guardianUserBody.getGuardianUserEmail()));
			
			boolean isPasswordValid = checkHash(guardianUserBody.getGuardianUserPassword(), guardianUser.getGuardianUserPassword());
			
			if(guardianUser.getGuardianUserEmail().equals(guardianUserBody.getGuardianUserEmail()) && isPasswordValid) {
				return ResponseEntity.ok().body(guardianUser);
			}else{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (Exception exception) {
			throw exception;
		} 
	}	
}
