 package com.shelfie.model;

import java.util.ArrayList;
//import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "guardian_users")
public class GuardianUser {
 
	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "guardian_user_id")
	private Integer guardianUserId;

	@NotNull
	@Column (name = "guardian_user_name", length = 255)
	private String guardianUserName;

	@NotNull
	@Column (name = "guardian_user_email", length = 320)
	private String guardianUserEmail;

	@NotNull
	@Column (name = "guardian_user_password", length = 255)
	private String guardianUserPassword;
	
	@OneToMany(
			mappedBy = "guardianUser",
	        cascade = CascadeType.REMOVE,
	        fetch = FetchType.LAZY
	        )
	private List<ChildProfile> childProfiles = new ArrayList<>();

	public GuardianUser() {
		super();
	}

	public GuardianUser(Integer guardianUserId, String guardianUserName, String guardianUserEmail,
			String guardianUserPassword, List<ChildProfile> childProfiles) {
		super();
		this.guardianUserId = guardianUserId;
		this.guardianUserName = guardianUserName;
		this.guardianUserEmail = guardianUserEmail;
		this.guardianUserPassword = guardianUserPassword;
		this.childProfiles = childProfiles;
	}

	public Integer getGuardianUserId() {
		return guardianUserId;
	}

	public void setGuardianUserId(Integer guardianUserId) {
		this.guardianUserId = guardianUserId;
	}

	public String getGuardianUserName() {
		return guardianUserName;
	}

	public void setGuardianUserName(String guardianUserName) {
		this.guardianUserName = guardianUserName;
	}

	public String getGuardianUserEmail() {
		return guardianUserEmail;
	}

	public void setGuardianUserEmail(String guardianUserEmail) {
		this.guardianUserEmail = guardianUserEmail;
	}

	public String getGuardianUserPassword() {
		return guardianUserPassword;
	}

	public void setGuardianUserPassword(String guardianUserPassword) {
		this.guardianUserPassword = guardianUserPassword;
	}

	public List<ChildProfile> getChildProfiles() {
		return childProfiles;
	}

	public void setChildProfiles(List<ChildProfile> childProfiles) {
		this.childProfiles.addAll(childProfiles);
	}

}
