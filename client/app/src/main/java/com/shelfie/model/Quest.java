package com.shelfie.model;

import java.io.Serializable;

public class Quest implements Serializable {

    private Integer questId;
    private String questTitle;
    private String questDescription;
    private Integer coinsReward;
    private InteractiveBook interactiveBook;

    public Quest() {
    }

    public Quest(Integer questId, String questTitle, String questDescription, Integer coinsReward, InteractiveBook interactiveBook) {
        this.questId = questId;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.coinsReward = coinsReward;
        this.interactiveBook = interactiveBook;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public void setQuestDescription(String questDescription) {
        this.questDescription = questDescription;
    }

    public Integer getCoinsReward() {
        return coinsReward;
    }

    public void setCoinsReward(Integer coinsReward) {
        this.coinsReward = coinsReward;
    }

    public InteractiveBook getInteractiveBook() {
        return interactiveBook;
    }

    public void setInteractiveBook(InteractiveBook interactiveBook) {
        this.interactiveBook = interactiveBook;
    }
}
