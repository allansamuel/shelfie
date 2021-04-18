package com.shelfie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "child_profiles")
public class ChildProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_profile_id")
	private Integer childProfileId;
	
	@Column(name = "nickname", length = 255)
	private String  nickname;
	
	@Column(name = "coins")
	private Integer coins;
	
	@ManyToOne
	@JoinColumn(name = "guardian_user_id")
	private GuardianUser guardianUser;
	
	@ManyToOne
	@JoinColumn(name = "character_id")
	private Character character;

	@ManyToMany
	@JoinTable(name = "child_completed_quests", 
	joinColumns = {@JoinColumn(name = "child_profile_id")}, 
	inverseJoinColumns = {@JoinColumn(name = "quest_id")})
	private List<Quest> childCompletedQuests;
	
	@ManyToMany
	@JoinTable(name = "child_unlocked_books", 
	joinColumns = {@JoinColumn(name = "child_profile_id")}, 
	inverseJoinColumns = {@JoinColumn(name = "interactive_book_id")})
	private List<InteractiveBook> childUnlockedBooks;
	
	@ManyToMany
	@JoinTable(name = "child_saved_books", 
	joinColumns = {@JoinColumn(name = "child_profile_id")}, 
	inverseJoinColumns = {@JoinColumn(name = "interactive_book_id")})
	private List<InteractiveBook> childSavedBooks;
	
	public ChildProfile() {
		super();
	}

	public ChildProfile(Integer childProfileId, String nickname, Integer coins, GuardianUser guardianUser,
			Character character, List<Quest> childCompletedQuests, List<InteractiveBook> childUnlockedBooks,
			List<InteractiveBook> childSavedBooks) {
		super();
		this.childProfileId = childProfileId;
		this.nickname = nickname;
		this.coins = coins;
		this.guardianUser = guardianUser;
		this.character = character;
		this.childCompletedQuests = childCompletedQuests;
		this.childUnlockedBooks = childUnlockedBooks;
		this.childSavedBooks = childSavedBooks;
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

	public List<Quest> getChildCompletedQuests() {
		return childCompletedQuests;
	}

	public void setChildCompletedQuests(List<Quest> childCompletedQuests) {
		this.childCompletedQuests = childCompletedQuests;
	}

	public List<InteractiveBook> getChildUnlockedBooks() {
		return childUnlockedBooks;
	}

	public void setChildUnlockedBooks(List<InteractiveBook> childUnlockedBooks) {
		this.childUnlockedBooks = childUnlockedBooks;
	}

	public List<InteractiveBook> getChildSavedBooks() {
		return childSavedBooks;
	}

	public void setChildSavedBooks(List<InteractiveBook> childSavedBooks) {
		this.childSavedBooks = childSavedBooks;
	}
	
}
