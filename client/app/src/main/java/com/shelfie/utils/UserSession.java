package com.shelfie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shelfie.model.GuardianUser;

public final class UserSession {

    public static final int READ_MODE = 0;
    public static final int REGISTER_MODE = 1;
    public static final int EDIT_MODE = 2;

    private static final String PREF_GUARDIAN_USER = "PREF_GUARDIAN_USER";
    private static final String PREF_FORM_INTERACTION_MODE = "PREF_FORM_INTERACTION_MODE";

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    private static Gson gson = new Gson();

    public static void startSession(Context ctx) {
        startSession(ctx, null, REGISTER_MODE);
    }

    public static void startSession(Context ctx, GuardianUser guardianUser, int formInteractionMode) {
        if(settings == null) {
            settings = ctx.getSharedPreferences(PREF_GUARDIAN_USER, Context.MODE_PRIVATE );
        }
        editor = settings.edit();
        if(guardianUser != null)
            setGuardianUser(guardianUser);
        setFormInteracionMode(formInteractionMode);
    }

    public static void setGuardianUser(GuardianUser guardianUser){
        if(guardianUser != null) {
            editor.putString( PREF_GUARDIAN_USER, gson.toJson(guardianUser) );
            editor.commit();
        }
    }

    public static GuardianUser getGuardianUser(){
        return gson.fromJson(settings.getString(PREF_GUARDIAN_USER, ""), GuardianUser.class);
    }

    public void deleteGuardianUser(){
        editor.remove( PREF_GUARDIAN_USER );
        editor.commit();
    }

    public static void setFormInteracionMode(int formInteractionMode) {
        editor.putInt(PREF_FORM_INTERACTION_MODE, formInteractionMode);
    }

    public static int getFormInteractionMode() {
        return settings.getInt(PREF_FORM_INTERACTION_MODE, 0);
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
