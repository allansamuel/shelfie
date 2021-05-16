package com.shelfie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuardianUser implements Serializable {

    private Integer guardianUserId;
    private String guardianUserName;
    private String guardianUserEmail;
    private String guardianUserPassword;
    private List<ChildProfile> childProfiles;

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
        this.childProfiles = childProfiles;
    }

    public void addChildProfile(ChildProfile childProfile) {
        this.childProfiles.add(childProfile);
    }
    @Override
    public String toString() {
        return "GuardianUser{" +
                "guardianUserId=" + guardianUserId +
                ", guardianUserName='" + guardianUserName + '\'' +
                ", guardianUserEmail='" + guardianUserEmail + '\'' +
                ", guardianUserPassword='" + guardianUserPassword + '\'' +
                ", childProfiles=" + childProfiles +
                '}';
    }
}