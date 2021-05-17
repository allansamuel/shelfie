package com.shelfie.ui.unityholders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.shelfie.R;
import com.unity3d.player.UnityPlayerActivity;

public class UHVitoriaRegiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_h_vitoria_regia);

        Intent start = new Intent(UHVitoriaRegiaActivity.this, UnityPlayerActivity.class);
        startActivity(start);
    }
}