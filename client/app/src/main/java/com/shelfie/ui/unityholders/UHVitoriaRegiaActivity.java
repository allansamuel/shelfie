package com.shelfie.ui.unityholders;

import android.content.Intent;
import android.os.Bundle;

import com.shelfie.R;
import com.shelfie.utils.UserSession;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class UHVitoriaRegiaActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_h_vitoria_regia);

        Intent launchIntent = new Intent(this, UnityPlayerActivity.class);
        launchIntent.putExtra("CHILD_PROFILE_ID", getChildProfileId());
        startActivity(launchIntent);
    }

    public String getChildProfileId(){
        return UserSession.getChildProfile(getApplicationContext()).getChildProfileId().toString();
    }
}