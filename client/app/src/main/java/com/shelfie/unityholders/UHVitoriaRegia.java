package com.shelfie.unityholders;

import android.app.Activity;
import android.content.Intent;
import com.shelfie.utils.UserSession;
import com.unity3d.player.UnityPlayerActivity;

public class UHVitoriaRegia {

    public static void launchGame(Activity activity) {
//        Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage("com.unity3d.player");
        Intent launchIntent = new Intent(activity, UnityPlayerActivity.class);
        launchIntent.putExtra(
                "CHILD_PROFILE_ID",
                UserSession.getChildProfile(activity.getApplicationContext()).getChildProfileId().toString()
        );
        activity.startActivity(launchIntent);
    }
}