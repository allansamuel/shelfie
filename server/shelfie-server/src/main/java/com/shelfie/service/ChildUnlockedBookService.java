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
import com.shelfie.repository.InteractiveBookRepository;

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
	
	@Autowired
	private InteractiveBookRepository interactiveBookRepository;
	
	public Boolean isBookUnlocked(Integer childProfileId, Integer interactiveBookId) throws Exception {
		 Optional<ChildUnlockedBook> childUnlockedBook = childUnlockedBookRepository
				.findByChildProfile_ChildProfileIdAndInteractiveBook_InteractiveBookId(childProfileId, interactiveBookId);
		 return childUnlockedBook.isEmpty() ? false : true;
		
	}
	
	public ChildProfile unlock(Integer childProfileId, Integer interactiveBookId) throws Exception{
	
		if(!isBookUnlocked(childProfileId, interactiveBookId)) {
		
			ChildProfile childProfile = childProfileRepository.findById(childProfileId)
					.orElseThrow(() -> new NotFoundException("not found"));
			
			InteractiveBook interactiveBook = interactiveBookRepository.findById(interactiveBookId)
					.orElseThrow(() -> new NotFoundException("not found"));
			
			if(childProfile.getCoins() >= interactiveBook.getPrice()) {
			
				ChildUnlockedBook childUnlockedBook = new ChildUnlockedBook();
				childUnlockedBook.setChildProfile(childProfile);
				childUnlockedBook.setInteractiveBook(interactiveBook);
				childUnlockedBook = childUnlockedBookRepository.save(childUnlockedBook);
				childProfileService.updateCoins(childProfileId, -interactiveBook.getPrice());

				return childProfile;
				
			}else {
				throw new NotAcceptableStatusException("Not enough coins");
			}
		}else {
			throw new NotAcceptableStatusException("Book already unlocked");
		}
	}
}
