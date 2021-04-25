package com.shelfie.ui.formChildProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.ChildProfileService;

import java.util.List;

public class ManageChildProfileActivity extends AppCompatActivity {

    private Bundle prevBundle;
    private GuardianUser guardianUser;

    private RetrofitConfig retrofitConfig;
    private ChildProfileService childProfileService;
    private List<ChildProfile> childProfiles;

    private FlexboxLayout flexboxChildProfiles;
    private CardView cvAddChildProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();

        cvAddChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getApplicationContext(), FormChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable("GUARDIAN_USER_DATA", guardianUser);
                newIntent.putExtras(newIntentBundle);
                startActivity(newIntent);
            }
        });
    }

    private void init() {
        prevBundle = getIntent().getExtras();
        guardianUser = (GuardianUser) prevBundle.getSerializable("NEW_GUARDIAN_USER_DATA");

        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();

        flexboxChildProfiles = findViewById(R.id.flexbox_child_profiles);
        cvAddChildProfile = findViewById(R.id.cv_add_child_profile);
    }
}