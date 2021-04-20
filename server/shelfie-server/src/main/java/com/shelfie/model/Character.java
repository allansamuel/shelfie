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
	@Column(name = "character_image", length = 100000)
	private Blob characterImage;
	
	@Column(name = "character_description", length = 800)
	private String characterDescription;
	
	
	public Character() {
		super();
	}

	public Character(Integer characterId, String characterName, Blob characterImage, String characterDescription) {
		super();
		this.characterId = characterId;
		this.characterName = characterName;
		this.characterImage = characterImage;
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

	public Blob getCharacterImage() {
		return characterImage;
	}

	public void setCharacterImage(Blob characterImage) {
		this.characterImage = characterImage;
	}

	public String getCharacterDescription() {
		return characterDescription;
	}

	public void setCharacterDescription(String characterDescription) {
		this.characterDescription = characterDescription;
	}

}
