package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Integer>{

}
