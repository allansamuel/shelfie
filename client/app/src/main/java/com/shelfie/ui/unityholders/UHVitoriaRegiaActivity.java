package com.shelfie.ui.unityholders;

import android.os.Bundle;
import com.shelfie.utils.UserSession;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class UHVitoriaRegiaActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UnityPlayer.UnitySendMessage("FixedButton", "setCurrentChildProfile", getChildProfileId());
        setContentView(mUnityPlayer);
        UnityPlayer.UnitySendMessage("FixedButton", "setCurrentChildProfile", getChildProfileId());
        mUnityPlayer.requestFocus();
        UnityPlayer.UnitySendMessage("FixedButton", "setCurrentChildProfile", getChildProfileId());
        mUnityPlayer.windowFocusChanged(true);
    }

    public String getChildProfileId(){
        return UserSession.getChildProfile(getApplicationContext()).getChildProfileId().toString();
    }
}