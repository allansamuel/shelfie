package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@ManyToOne
	@Column(name = "guardian_user_id")
	private GuardianUser guardianUser;
	
	@ManyToOne
	@Column(name = "character_id")
	private Character character;
	
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
