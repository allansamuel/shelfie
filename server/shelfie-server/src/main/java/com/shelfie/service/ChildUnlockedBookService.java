package com.shelfie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.model.InteractiveBook;
import com.shelfie.repository.ChildProfileRepository;
import com.shelfie.repository.ChildUnlockedBookRepository;

import javassist.NotFoundException;

@Transactional
@Service
public class ChildUnlockedBookService {

	@Autowired
	private ChildUnlockedBookRepository childUnlockedBookRepository;
	
	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	@Autowired
	private ChildProfileService childProfileService;
	
	public Optional<ChildUnlockedBook> getByChildAndBook(Integer childProfileId, Integer interactiveBookId) throws Exception {
		
		 return childUnlockedBookRepository
				.findByChildProfile_ChildProfileIdAndInteractiveBook_InteractiveBookId(childProfileId, interactiveBookId);
		
	}
	
	public ChildProfile unlock(Integer childProfileId, InteractiveBook interactiveBookBody) throws Exception{
		
		System.out.println(getByChildAndBook(childProfileId, interactiveBookBody.getInteractiveBookId()));
		
		if(getByChildAndBook(childProfileId, interactiveBookBody.getInteractiveBookId()).isEmpty() ) {
		
			ChildProfile childProfile = childProfileRepository.findById(childProfileId)
					.orElseThrow(() -> new NotFoundException("not found"));
			
			if(childProfile.getCoins() >= interactiveBookBody.getPrice()) {
			
				
				ChildUnlockedBook childUnlockedBook = new ChildUnlockedBook();
				
				childUnlockedBook.setChildProfile(childProfile);
				childUnlockedBook.setInteractiveBook(interactiveBookBody);
				
				childUnlockedBook = childUnlockedBookRepository.save(childUnlockedBook);
				
				childProfileService.updateCoins(childProfileId, -interactiveBookBody.getPrice());
				
				return childProfile;
				
			}else {
				throw new NotAcceptableStatusException("not enough coins");
			}
		}else {
			throw new NotAcceptableStatusException("already");
		}
	}
}
