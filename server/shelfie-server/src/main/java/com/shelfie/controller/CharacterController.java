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

import com.shelfie.model.Character;
import com.shelfie.repository.CharacterRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("character")
public class CharacterController {
	
	@Autowired
	private CharacterRepository characterRepository;

	@GetMapping("{id}")
	public ResponseEntity<Character> getCharacterById(@PathVariable Integer id) throws Exception {
	 Character character = characterRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("not found" + id));
	 return ResponseEntity.ok().body(character);
	}
	
	@GetMapping
	public List <Character> getAll(){
	 List <Character> character = characterRepository.findAll();
	 return character;
	}
	
	@PostMapping
	public Character createCharacter(@RequestBody Character character) throws Exception{
		try {
		Character newCharacter = characterRepository.save(character);
		return characterRepository.save(newCharacter); 
		} catch (Exception exception) {
			throw exception;
		} 
	}
	
}
