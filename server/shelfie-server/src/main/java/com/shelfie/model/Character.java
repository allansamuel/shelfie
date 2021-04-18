package com.shelfie.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "characters")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "character_id")
	private Integer characterId;
	
	@Column(name = "character_name", length = 255)
	private String characterName;
	
	@Lob
	@Column(name = "image", length = 100000)
	private Blob image;
	
	@Column(name = "character_description", length = 800)
	private String characterDescription;
	
	public Character() {
		super();
	}

	public Character(Integer characterId, String characterName, Blob image, String characterDescription) {
		super();
		this.characterId = characterId;
		this.characterName = characterName;
		this.image = image;
		this.characterDescription = characterDescription;
	}

	public Integer getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getCharacterDescription() {
		return characterDescription;
	}

	public void setCharacterDescription(String characterDescription) {
		this.characterDescription = characterDescription;
	}
	
}
