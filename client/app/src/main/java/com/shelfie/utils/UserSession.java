package com.shelfie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.shelfie.model.GuardianUser;

public final class UserSession {

    public static final int REGISTER_MODE = 0;
    public static final int EDIT_MODE = 1;
    public static final int READ_MODE = 2;

    private static final String PREFS_NAME = "com.shelfie";
    private static final String KEY_GUARDIAN_USER = "KEY_GUARDIAN_USER";
    private static final String KEY_FORM_INTERACTION_MODE = "KEY_FORM_INTERACTION_MODE";

//    private static SharedPreferences settings;
//    private static SharedPreferences.Editor editor;
//    private static Gson gson = new Gson();

    public static void startSession(Context context) {
        startSession(context, null, REGISTER_MODE);
    }

    public static void startSession(Context context, GuardianUser guardianUser, int formInteractionMode) {
//        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
        if(guardianUser != null)
            setGuardianUser(context, guardianUser);
        setFormInteractionMode(context, formInteractionMode);
    }

    public static void setGuardianUser(Context context, GuardianUser guardianUser){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_GUARDIAN_USER, new Gson().toJson(guardianUser));
        editor.commit();
    }

    public static GuardianUser getGuardianUser(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String guardianUserJson = settings.getString(KEY_GUARDIAN_USER, "");
        if(guardianUserJson != null)
            return new Gson().fromJson(guardianUserJson, GuardianUser.class);
        return null;
    }

    public static void deleteGuardianUser(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_GUARDIAN_USER);
        editor.commit();
    }

    public static void setFormInteractionMode(Context context, int formInteractionMode) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_FORM_INTERACTION_MODE, formInteractionMode);
        editor.commit();
    }

    public static int getFormInteractionMode(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(KEY_FORM_INTERACTION_MODE, REGISTER_MODE);
    }

    public static boolean isFormInEditMode(Context context) {
        return getFormInteractionMode(context) == EDIT_MODE;
    }

    public static boolean isFormInRegisterMode(Context context) {
        return getFormInteractionMode(context) == READ_MODE;
    }

    public static boolean isFormInReadMode(Context context) {
        return getFormInteractionMode(context) == REGISTER_MODE;
    }
}
