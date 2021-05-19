package com.shelfie.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.NotAcceptableStatusException;

import com.shelfie.model.ChildCompletedQuest;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.Quest;
import com.shelfie.repository.ChildCompletedQuestRepository;
import com.shelfie.repository.ChildProfileRepository;
import com.shelfie.repository.QuestRepository;

import javassist.NotFoundException;

@Transactional
@Service
public class ChildCompletedQuestService {

	@Autowired
	ChildCompletedQuestRepository childCompletedQuestRepository;

	@Autowired
	private ChildProfileRepository childProfileRepository;
	
	@Autowired
	private QuestRepository questRepository;
	
	public Boolean isQuestCompleted(Integer childProfileId, Integer questId) throws Exception {
		Optional<ChildCompletedQuest> childCompletedQuest = childCompletedQuestRepository
					.findByChildProfile_ChildProfileIdAndQuest_QuestId(childProfileId, questId);
		return childCompletedQuest.isEmpty() ? true : false;
	}
	
	public ChildProfile complete(Integer childProfileId, Integer questId) throws Exception {
		if(!isQuestCompleted(childProfileId, questId)) {
			ChildProfile childProfile = childProfileRepository.findById(childProfileId)
					.orElseThrow(() -> new NotFoundException("not found"));
			
			Quest quest = questRepository.findById(questId)
					.orElseThrow(() -> new NotFoundException("not found"));
			
				ChildCompletedQuest chChildCompletedQuest = new ChildCompletedQuest();
				chChildCompletedQuest.setChildProfile(childProfile);
				chChildCompletedQuest.setQuest(quest);
				chChildCompletedQuest = childCompletedQuestRepository.save(chChildCompletedQuest);
				
				return childProfile;
		} else {
			throw new NotAcceptableStatusException("Quest already completed");
		}
	}
}
