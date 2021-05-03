package com.shelfie.DTO;

import com.shelfie.model.GuardianUser;

public class GuardianUserDTO {
	
	private Integer guardianUserId;
	private String guardianUserEmail;
	private String guardianUserPassword;
	
	public GuardianUserDTO(){
	}
 
	public GuardianUserDTO(GuardianUser guardianUser){
		this.guardianUserId = guardianUser.getGuardianUserId();
		this.guardianUserEmail = guardianUser.getGuardianUserEmail();
		this.guardianUserPassword = guardianUser.getGuardianUserPassword();
	}

	public Integer getGuardianUserId() {
		return guardianUserId;
	}

	public void setGuardianUserId(Integer guardianUserId) {
		this.guardianUserId = guardianUserId;
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
}
