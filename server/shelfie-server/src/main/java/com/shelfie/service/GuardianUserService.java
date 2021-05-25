package com.shelfie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.repository.GuardianUserRepository;

import javassist.NotFoundException;

public class GuardianUserService {

	@Autowired
	private GuardianUserRepository guardianUserRepository;

	private String hashPassword(String plainTextPassword) {
		 return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
	
	public GuardianUser getById(Integer guardianUserId) throws Exception {
	 return guardianUserRepository.findById(guardianUserId)
			 .orElseThrow(() -> new NotFoundException("Not found"));
	}
	
	public GuardianUser getByEmail(String guardianUserEmail) throws Exception {
	 return guardianUserRepository.findByGuardianUserEmail(guardianUserEmail)
			 .orElseThrow(() -> new NotFoundException("Not found"));
	}
	
	public List<ChildProfile> getChildProfiles(Integer guardianUserId) throws Exception {
		 GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
				 .orElseThrow(() -> new NotFoundException("Not found"));
		 return guardianUser.getChildProfiles();
	}
	
	public GuardianUser create(GuardianUser guardianUser) throws Exception {
		guardianUser.setGuardianUserPassword(hashPassword(guardianUser.getGuardianUserPassword()));
		return guardianUserRepository.save(guardianUser);
	}
	
	public GuardianUser update(Integer guardianUserId, GuardianUser guardianUserBody) throws Exception {
		GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
			 .orElseThrow(() -> new NotFoundException("Not found"));
	 
		guardianUser.setGuardianUserName(guardianUserBody.getGuardianUserName());
		guardianUser.setGuardianUserEmail(guardianUserBody.getGuardianUserEmail());
		guardianUser.setGuardianUserPassword(hashPassword(guardianUserBody.getGuardianUserPassword()));
		guardianUser.setChildProfiles(guardianUserBody.getChildProfiles());
		
		return guardianUserRepository.save(guardianUser);
	}
	
	public void delete(Integer guardianUserId) throws Exception {
		GuardianUser guardianUser = guardianUserRepository.findById(guardianUserId)
				.orElseThrow(() -> new NotFoundException("Not found"));
		guardianUserRepository.delete(guardianUser);
	}
}
