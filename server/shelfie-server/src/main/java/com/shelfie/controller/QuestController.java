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
import com.shelfie.model.Quest;
import com.shelfie.repository.QuestRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("quest")
public class QuestController {
	
	@Autowired
	private QuestRepository questRepository;
	
	@GetMapping("{id}")
	public ResponseEntity<Quest> getQuestById(@PathVariable Integer id) throws Exception {
	 Quest quest = questRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(quest);
	}
	
	@GetMapping
	public List <Quest> getAll(){
	 List <Quest> quest = questRepository.findAll();
	 return quest;
	}

	@PostMapping
	public Quest createQuest(@RequestBody Quest quest) throws Exception{
		Quest newQuest = questRepository.save(quest);
		return questRepository.save(newQuest); 
	}
}
