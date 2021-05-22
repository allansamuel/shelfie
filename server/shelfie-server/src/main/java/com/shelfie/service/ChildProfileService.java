package com.shelfie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.shelfie.model.ChildProfile;
import com.shelfie.repository.ChildProfileRepository;

import javassist.NotFoundException;

@Transactional
@Service
public class ChildProfileService {

	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	public ChildProfile getById(Integer id) throws Exception {
		return childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Not found"));
	}
	
	public ChildProfile create(@RequestBody ChildProfile childProfileBody) throws Exception {
		return childProfileRepository.save(childProfileBody);
	}
	
	public ChildProfile update(ChildProfile childProfileBody, Integer id) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Not found"));
		
		childProfile.setNickname(childProfileBody.getNickname());
		childProfile.setCharacter(childProfileBody.getCharacter());
		
		return childProfileRepository.save(childProfile);
	}
	
	public ChildProfile updateCoins(Integer childProfileId, Integer value) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(childProfileId)
				.orElseThrow(() -> new NotFoundException("Not found"));
		
		Integer updatedCoins = childProfile.getCoins() + value;
		childProfile.setCoins(updatedCoins);
		
		return childProfileRepository.save(childProfile);
	}
	
	public void delete(Integer id) throws Exception {
		ChildProfile childProfile = childProfileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Not found"));
		childProfileRepository.delete(childProfile);
	}
}
