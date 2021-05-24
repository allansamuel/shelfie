package com.shelfie.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Character implements Serializable {

    private Integer characterId;
    private String characterName;
    private Bitmap characterImage;
    private String characterDescription;
    private InteractiveBook interactiveBook;

    public Character() {
        super();
    }

    public Character(Integer characterId, String characterName, Bitmap characterImage, String characterDescription,
                     InteractiveBook interactiveBook){
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

    public Bitmap getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(Bitmap characterImage) {
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

    @Override
    public String toString() {
        return "Character{" +
                "characterId=" + characterId +
                ", characterName='" + characterName + '\'' +
                ", characterImage='" + characterImage + '\'' +
                ", characterDescription='" + characterDescription + '\'' +
                ", interactiveBook=" + interactiveBook +
                '}';
    }
}
