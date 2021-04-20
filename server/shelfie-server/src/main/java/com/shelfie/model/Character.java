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
	@Column(name = "id")
	private Integer id;
	
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

	public Character(Integer id, String characterName, Blob characterImage, String characterDescription) {
		super();
		this.id = id;
		this.characterName = characterName;
		this.characterImage = characterImage;
		this.characterDescription = characterDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
