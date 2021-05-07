package com.shelfie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shelfie.model.GuardianUser;

public final class UserSession {

    public static final int REGISTER_MODE = 0;
    public static final int EDIT_MODE = 1;
    public static final int READ_MODE = 2;

    private static final String PREFS_NAME = "com.shelfie";
    private static final String KEY_GUARDIAN_USER = "KEY_GUARDIAN_USER";
    private static final String KEY_FORM_INTERACTION_MODE = "KEY_FORM_INTERACTION_MODE";

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    private static Gson gson = new Gson();

    public static void startSession(Context ctx) {
        startSession(ctx, null, REGISTER_MODE);
    }

    public static void startSession(Context ctx, GuardianUser guardianUser, int formInteractionMode) {
        if(settings == null) {
            settings = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE );
        }
        editor = settings.edit();
        if(guardianUser != null)
            setGuardianUser(guardianUser);
        setFormInteractionMode(formInteractionMode);
    }

    public static void setGuardianUser(GuardianUser guardianUser){
        editor.putString(KEY_GUARDIAN_USER, gson.toJson(guardianUser));
        editor.commit();
    }

    public static GuardianUser getGuardianUser(){
        String guardianUserJson = settings.getString(KEY_GUARDIAN_USER, "");
        if(guardianUserJson != null)
            return gson.fromJson(guardianUserJson, GuardianUser.class);
        return null;
    }

    public void deleteGuardianUser(){
        editor.remove(KEY_GUARDIAN_USER);
        editor.commit();
    }

    public static void setFormInteractionMode(int formInteractionMode) {
        editor.putInt(KEY_FORM_INTERACTION_MODE, formInteractionMode);
        editor.commit();
    }

    public static int getFormInteractionMode() {
        return settings.getInt(KEY_FORM_INTERACTION_MODE, REGISTER_MODE);
    }

    public static boolean isFormInEditMode() {
        return getFormInteractionMode() == EDIT_MODE;
    }

    public static boolean isFormInRegisterMode() {
        return getFormInteractionMode() == READ_MODE;
    }

    public static boolean isFormInReadMode() {
        return getFormInteractionMode() == REGISTER_MODE;
    }
}
