package model;

import java.util.ArrayList;

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
	@Column(name = "guardian_user_id")
	private GuardianUser guardianUser;
	
	@ManyToOne
	@Column(name = "character_id")
	private Character character;

	@ManyToMany
	@JoinTable(name="child_completed_quests", joinColumns=
	{@JoinColumn(name="child_profile_id")}, inverseJoinColumns= 
	{@JoinColumn(name="quest_id")})
	private ArrayList<Quest> childCompletedQuests;
	
	@ManyToMany
	@JoinTable(name="child_unlocked_books", joinColumns=
	{@JoinColumn(name="child_profile_id")}, inverseJoinColumns= 
	{@JoinColumn(name="interactive_book_id")})
	private ArrayList<InteractiveBook> childUnlockedBooks;
	
	@ManyToMany
	@JoinTable(name="child_saved_books", joinColumns=
	{@JoinColumn(name="child_profile_id")}, inverseJoinColumns= 
	{@JoinColumn(name="interactive_book_id")})
	private ArrayList<InteractiveBook> childSavedBooks;
	
	public ChildProfile() {
		super();
	}

	public ChildProfile(Integer childProfileId, String nickname, GuardianUser guardianUser, Character character) {
		super();
		this.childProfileId = childProfileId;
		this.nickname = nickname;
		this.guardianUser = guardianUser;
		this.character = character;
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
	
}
