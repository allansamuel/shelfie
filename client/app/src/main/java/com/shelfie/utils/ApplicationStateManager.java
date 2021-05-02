package com.shelfie.utils;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;

public class ApplicationStateManager {

    private static ApplicationStateManager instance;
    private static GuardianUser currentGuardianUser;
    private static ChildProfile currentChildProfile;
    private static int formInteractionMode = 1;

    public static final int READ_MODE = 0;
    public static final int REGISTER_MODE = 1;
    public static final int EDIT_MODE = 2;

    private ApplicationStateManager() {}

    public static ApplicationStateManager getInstance() {
        if(instance != null) {
            instance = new ApplicationStateManager();
        }
        return instance;
    }

    public static GuardianUser getCurrentGuardianUser() {
        return currentGuardianUser;
    }

    public static void setCurrentGuardianUser(GuardianUser currentGuardianUser) {
        ApplicationStateManager.currentGuardianUser = currentGuardianUser;
    }

    public static ChildProfile getCurrentChildProfile() {
        return currentChildProfile;
    }

    public static void setCurrentChildProfile(ChildProfile currentChildProfile) {
        ApplicationStateManager.currentChildProfile = currentChildProfile;
    }

    public static int getFormInteractionMode() {
        return formInteractionMode;
    }

    public static void setFormInteractionMode(int formInteractionMode) {
        ApplicationStateManager.formInteractionMode = formInteractionMode;
    }
}
