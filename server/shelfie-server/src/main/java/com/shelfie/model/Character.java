package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name = "characters")
public class Character {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "character_id")
	private Integer characterId;
	
	@NotNull
	@Column(name = "character_name", length = 255)
	private String characterName;
	
	@Lob
	@Column(name = "character_image", length = 100000)
	private byte[] characterImage;
	
	@Column(name = "character_description", length = 800)
	private String characterDescription;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;
	
	public Character() {
		super();
	}

	public Character(Integer characterId, String characterName, byte[] characterImage, String characterDescription,
			InteractiveBook interactiveBook) {
		super();
		this.characterId = characterId;
		this.characterName = characterName;
		this.characterImage = characterImage;
		this.characterDescription = characterDescription;
		this.interactiveBook = interactiveBook;
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

	public byte[] getCharacterImage() {
		return characterImage;
	}

	public void setCharacterImage(byte[] characterImage) {
		this.characterImage = characterImage;
	}

	public String getCharacterDescription() {
		return characterDescription;
	}

	public void setCharacterDescription(String characterDescription) {
		this.characterDescription = characterDescription;
	}

	public InteractiveBook getInteractiveBook() {
		return interactiveBook;
	}

	public void setInteractiveBook(InteractiveBook interactiveBook) {
		this.interactiveBook = interactiveBook;
	}

}
