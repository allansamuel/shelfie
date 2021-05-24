package com.shelfie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.model.InteractiveBook;

public final class UserSession {

    public static final int REGISTER_MODE = 0;
    public static final int EDIT_MODE = 1;
    public static final int READ_MODE = 2;

    private static final String PREFS_NAME = "com.shelfie";
    private static final String KEY_GUARDIAN_USER = "KEY_GUARDIAN_USER";
    private static final String KEY_CHILD_PROFILE = "KEY_CHILD_PROFILE";
    private static final String KEY_FORM_INTERACTION_MODE = "KEY_FORM_INTERACTION_MODE";
    private static final String KEY_INTERACTIVE_BOOK = "KEY_INTERACTIVE_BOOK";

    public static void startSession(Context context) {
        startSession(context, null, REGISTER_MODE);
    }

    public static void startSession(Context context, GuardianUser guardianUser, int formInteractionMode) {
        if(guardianUser != null)
            setGuardianUser(context, guardianUser);
        setFormInteractionMode(context, formInteractionMode);
    }

    public static void clearSession(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().commit();
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
        return getFormInteractionMode(context) == REGISTER_MODE;
    }

    public static boolean isFormInReadMode(Context context) {
        return getFormInteractionMode(context) == READ_MODE;
    }

    public static void setInteractiveBook(Context context, InteractiveBook interactiveBook){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_INTERACTIVE_BOOK, new Gson().toJson(interactiveBook));
        editor.commit();
    }

    public static InteractiveBook getInteractiveBook(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String interactiveBookJson = settings.getString(KEY_INTERACTIVE_BOOK, "");
        if(interactiveBookJson != null)
            return new Gson().fromJson(interactiveBookJson, InteractiveBook.class);
        return null;
    }

    public static void setChildProfile(Context context, ChildProfile childProfile){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CHILD_PROFILE, new Gson().toJson(childProfile));
        editor.commit();
    }

    public static ChildProfile getChildProfile(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String childProfileJson = settings.getString(KEY_CHILD_PROFILE, "");
        if(childProfileJson != null)
            return new Gson().fromJson(childProfileJson, ChildProfile.class);
        return null;
    }

    public static void deleteChildProfile(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(KEY_CHILD_PROFILE);
        editor.commit();
    }
}
