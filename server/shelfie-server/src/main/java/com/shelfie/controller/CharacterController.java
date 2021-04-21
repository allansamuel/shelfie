package com.shelfie.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public Character createCharacter(@RequestBody Character characterBody) throws Exception{
		try {
			
			Character newCharacter = characterRepository.save(characterBody); 
			return newCharacter;
		} catch (Exception exception) {
			throw exception;
		} 
	}
	
	@PutMapping("{id}/upload_image")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file, 
			@PathVariable Integer id) throws Exception {
		Character updatedCharacter = characterRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		
		updatedCharacter.setCharacterImage(file.getBytes());
		characterRepository.save(updatedCharacter);
	}
	
	public static byte[] compressBytes(byte[] data) throws IOException {
	    Deflater deflater = new Deflater();
	    deflater.setInput(data);
	    deflater.finish();
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	    byte[] buffer = new byte[1024];
	    while (!deflater.finished()) {
	        int count = deflater.deflate(buffer);
	        outputStream.write(buffer, 0, count);
	    }
	    try {
	        outputStream.close();
	    } catch (IOException exception) {
			throw exception;
	    }
	    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
	    return outputStream.toByteArray();
	}
	
}
