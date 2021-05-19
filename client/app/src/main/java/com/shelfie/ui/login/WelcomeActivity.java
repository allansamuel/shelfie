package com.shelfie.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.shelfie.R;
import com.shelfie.ui.userregister.ManageChildProfileActivity;
import com.shelfie.utils.UserSession;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView imgWelcomeShelfieLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
        // UserSession.clearSession(getApplicationContext());
        verifyUserSession();
    }

    private void init() {
        imgWelcomeShelfieLogo = findViewById(R.id.img_welcome_shelfie_logo);
        ObjectAnimator pulseAnimation = ObjectAnimator.ofPropertyValuesHolder(
                imgWelcomeShelfieLogo,
                PropertyValuesHolder.ofFloat("scaleX", 1.08f),
                PropertyValuesHolder.ofFloat("scaleY", 1.08f));
        pulseAnimation.setDuration(500);

        pulseAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        pulseAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        pulseAnimation.start();
    }

    private void verifyUserSession() {
        UserSession.setFormInteractionMode(getApplicationContext(), UserSession.READ_MODE);
        UserSession.deleteChildProfile(getApplicationContext());
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if(UserSession.getGuardianUser(getApplicationContext()) != null) {
                            startActivity(new Intent(getApplicationContext(), ManageChildProfileActivity.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        finish();
                    }
                },
                1500
        );
    }
}