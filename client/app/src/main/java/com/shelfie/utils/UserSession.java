package com.shelfie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shelfie.model.GuardianUser;

public final class UserSession {

    private static final String PREFS_NAME = "br.ifsul.shelfie";

    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;
    private static Gson gson = new Gson();

    public static void startSession(Context ctx) {
        startSession(ctx, null);
    }

    public static void startSession(Context ctx, GuardianUser guardianUser) {
        if(settings == null) {
            settings = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE );
        }
        editor = settings.edit();
        if(guardianUser != null)
            setGuardianUser(guardianUser);
    }

    public static void setGuardianUser(GuardianUser guardianUser){
        if(guardianUser != null) {
            editor.putString( PREFS_NAME, gson.toJson(guardianUser) );
            editor.commit();
        }
    }

    public static GuardianUser getGuardianUser(){
        return gson.fromJson(settings.getString(PREFS_NAME, ""), GuardianUser.class);
    }

    public void deleteGuardianUser(){
        editor.remove( PREFS_NAME );
        editor.commit();
    }
}
