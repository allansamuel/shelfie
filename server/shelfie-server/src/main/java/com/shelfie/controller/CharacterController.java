package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shelfie.model.Character;
import com.shelfie.repository.CharacterRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("character")
public class CharacterController {
	
	@Autowired
	private CharacterRepository characterRepository;

	@GetMapping("{id}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Integer id) throws Exception {
		Character character = characterRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("Not found"));

		 return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(character.getCharacterImage());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Character> getById(@PathVariable Integer id) throws Exception {
	 Character character = characterRepository.findById(id)
			 .orElseThrow(() -> new NotFoundException ("Not found"));

	 return ResponseEntity.ok(character);
	}
	
	@GetMapping
	public ResponseEntity<List<Character>> getAll(){
		try {
			List <Character> character = characterRepository.findAll();
			return ResponseEntity.ok(character);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PostMapping
	public ResponseEntity<Character> create(@RequestBody Character characterBody) throws Exception{
		try {
			Character newCharacter = characterRepository.save(characterBody); 
			return ResponseEntity.ok(newCharacter);
		} catch (Exception exception) {
			throw exception;
		} 
	}

	@PutMapping("{id}/upload_image")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file, 
			@PathVariable Integer id) throws Exception {
		Character updatedCharacter = characterRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("Not found"));
		
		updatedCharacter.setCharacterImage(file.getBytes());
		characterRepository.save(updatedCharacter);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Character> update(@RequestBody() Character characterBody, 
			@PathVariable Integer id) throws Exception {
		Character character = characterRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("Not found"));
		
		character.setCharacterName(characterBody.getCharacterName());
		character.setCharacterDescription(characterBody.getCharacterDescription());

		Character updatedCharacter = characterRepository.save(character);
		
		return ResponseEntity.ok().body(updatedCharacter);
	}
	
}
