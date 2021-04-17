package model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "guardian_users")
public class GuardianUser {

	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "guardia_user_id")
	private Integer guardianUserId;

	@Column (name = "name", length = 255)
	private String name;

	@Column (name = "email", length = 320)
	private String email;

	@Column (name = "password", length = 255)
	private String password;
	
	@OneToMany
	private ArrayList<ChildUser> childProfiles;

	public GuardianUser(Integer guardianUserId, String name, String email, String password,
			ArrayList<ChildUser> childProfiles) {
		super();
		this.guardianUserId = guardianUserId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.childProfiles = childProfiles;
	}

	public GuardianUser() {
		super();
	}

	public Integer getGuardianUserId() {
		return guardianUserId;
	}

	public void setGuardianUserId(Integer guardianUserId) {
		this.guardianUserId = guardianUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<ChildUser> getChildProfiles() {
		return childProfiles;
	}

	public void setChildProfiles(ArrayList<ChildUser> childProfiles) {
		this.childProfiles = childProfiles;
	}
	
}
