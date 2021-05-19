package com.shelfie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.ChildCompletedQuest;

public interface ChildCompletedQuestRepository extends JpaRepository<ChildCompletedQuest, Integer>{

	Optional <ChildCompletedQuest> findByChildProfile_ChildProfileIdAndQuest_QuestId(Integer childProfile, Integer quest );
	
}
