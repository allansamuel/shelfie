package com.shelfie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shelfie.model.ChildProfile;
import com.shelfie.repository.ChildProfileRepository;

import javassist.NotFoundException;

@Transactional
@Service
public class ChildProfileService {

	
	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	
	public ChildProfile updateCoins(Integer childProfileId, Integer value) throws Exception {
		
		ChildProfile childProfile = childProfileRepository.findById(childProfileId)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		Integer updatedCoins = childProfile.getCoins() + value;
		childProfile.setCoins(updatedCoins);
		
		return childProfileRepository.save(childProfile);
	}
}
