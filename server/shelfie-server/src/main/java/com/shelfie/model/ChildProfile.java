package com.shelfie.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "child_profiles")
public class ChildProfile {

	public static final int DEFAULT_START_COINS = 500;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_profile_id")
	private Integer childProfileId;
	
	@NotNull
	@Column(name = "nickname", length = 255)
	private String  nickname;
	
	@Column(name = "coins")
	private Integer coins = DEFAULT_START_COINS;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name = "guardian_user_id")
	private GuardianUser guardianUser;

	@ManyToOne
	@JoinColumn(name = "character_id")
	private Character character;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "child_completed_quests", 
			joinColumns = {@JoinColumn(name = "child_profile_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "quest_id")}
			)
	private List<Quest> childCompletedQuests;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "child_unlocked_books", 
			joinColumns = {@JoinColumn(name = "child_profile_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "interactive_book_id")}
			)
	private List<InteractiveBook> childUnlockedBooks;
	
	@OneToMany(
			mappedBy = "childProfile",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	        )
	private List<ChildSavedBook> childSavedBooks;
	
	public ChildProfile() {
		super();
	}

	public ChildProfile(Integer childProfileId, String nickname, Integer coins, GuardianUser guardianUser,
			Character character, List<Quest> childCompletedQuests, List<InteractiveBook> childUnlockedBooks,
			List<ChildSavedBook> childSavedBooks) {
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

	@JsonIgnore
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

	public List<ChildSavedBook> getChildSavedBooks() {
		return childSavedBooks;
	}

	public void setChildSavedBooks(List<ChildSavedBook> childSavedBooks) {
		this.childSavedBooks = childSavedBooks;
	}

	@Override
	public String toString() {
		return "ChildProfile [childProfileId=" + childProfileId + ", nickname=" + nickname + ", coins=" + coins
				+ ", guardianUser=" + guardianUser + ", character=" + character + ", childCompletedQuests="
				+ childCompletedQuests + ", childUnlockedBooks=" + childUnlockedBooks + ", childSavedBooks="
				+ childSavedBooks + "]";
	}
	
}
