package com.shelfie.model;

public class ChildCompletedQuest {

    private Integer childCompletedQuestId;
    private ChildProfile childProfile;
    private Quest quest;

    public ChildCompletedQuest() {
    }

    public ChildCompletedQuest(Integer childCompletedQuestId, ChildProfile childProfile, Quest quest) {
        this.childCompletedQuestId = childCompletedQuestId;
        this.childProfile = childProfile;
        this.quest = quest;
    }

    public Integer getChildCompletedQuestId() {
        return childCompletedQuestId;
    }

    public void setChildCompletedQuestId(Integer childCompletedQuestId) {
        this.childCompletedQuestId = childCompletedQuestId;
    }

    public ChildProfile getChildProfile() {
        return childProfile;
    }

    public void setChildProfile(ChildProfile childProfile) {
        this.childProfile = childProfile;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}
