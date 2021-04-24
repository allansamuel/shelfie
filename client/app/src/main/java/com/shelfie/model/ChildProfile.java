package com.shelfie.model;

import java.util.List;

public class ChildProfile {

    private Integer childProfileId;
    private String  nickname;
    private Integer coins;
    private GuardianUser guardianUser;
    private Character character;
//    private List<Quest> childCompletedQuests;
//    private List<InteractiveBook> childUnlockedBooks;
//    private List<ChildSavedBook> childSavedBooks;

    public ChildProfile() {
        super();
    }

    public ChildProfile(Integer childProfileId, String nickname, Integer coins, GuardianUser guardianUser,
                        Character character
//                        List<Quest> childCompletedQuests,
//                        List<InteractiveBook> childUnlockedBooks,
//                        List<ChildSavedBook> childSavedBooks
    ) {
        super();
        this.childProfileId = childProfileId;
        this.nickname = nickname;
        this.coins = coins;
        this.guardianUser = guardianUser;
        this.character = character;
//        this.childCompletedQuests = childCompletedQuests;
//        this.childUnlockedBooks = childUnlockedBooks;
//        this.childSavedBooks = childSavedBooks;
    }

    public Integer getChildProfileId() {
        return childProfileId;
    }

    public void setChildProfileId(Integer childProfileId) {
        this.childProfileId = childProfileId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public GuardianUser getGuardianUser() {
        return guardianUser;
    }

    public void setGuardianUser(GuardianUser guardianUser) {
        this.guardianUser = guardianUser;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

//    public List<Quest> getChildCompletedQuests() {
//        return childCompletedQuests;
//    }
//
//    public void setChildCompletedQuests(List<Quest> childCompletedQuests) {
//        this.childCompletedQuests = childCompletedQuests;
//    }
//
//    public List<InteractiveBook> getChildUnlockedBooks() {
//        return childUnlockedBooks;
//    }
//
//    public void setChildUnlockedBooks(List<InteractiveBook> childUnlockedBooks) {
//        this.childUnlockedBooks = childUnlockedBooks;
//    }
//
//    public List<ChildSavedBook> getChildSavedBooks() {
//        return childSavedBooks;
//    }
//
//    public void setChildSavedBooks(List<ChildSavedBook> childSavedBooks) {
//        this.childSavedBooks = childSavedBooks;
//    }

}