package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.Chapter;
import com.shelfie.repository.ChapterRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("chapter")
public class ChapterController {
	
	@Autowired
	private ChapterRepository chapterRepository;
	
	@GetMapping("{id}")
	public ResponseEntity<Chapter> getChapterById(@PathVariable Integer id) throws Exception {
	 Chapter chapter = chapterRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(chapter);
	}
	
	@GetMapping
	public List <Chapter> getAll(){
	 List <Chapter> chapter = chapterRepository.findAll();
	 return chapter;
	}

	@PostMapping
	public Chapter createChapter(@RequestBody Chapter chapter) throws Exception{
		Chapter newChapter = chapterRepository.save(chapter);
		return chapterRepository.save(newChapter); 
	}
}
