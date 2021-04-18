package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.Quest;

public interface QuestRepository extends JpaRepository<Quest, Integer> {

}
