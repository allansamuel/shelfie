package model;

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
	
	@Column(name = "name", length = 255)
	private String name;
	
	@Lob
	@Column(name = "image", length = 100000)
	private Blob image;
	
	@Column(name = "description", length = 800)
	private String description;
	
	public Character() {
		super();
	}

	public Character(Integer characterId, String name, Blob image, String description) {
		super();
		this.characterId = characterId;
		this.name = name;
		this.image = image;
		this.description = description;
	}

	public Integer getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
