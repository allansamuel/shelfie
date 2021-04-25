package com.shelfie.ui.formChildProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.service.ChildProfileService;

import java.util.List;

public class ManageChildProfileActivity extends AppCompatActivity {

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

            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();

        flexboxChildProfiles = findViewById(R.id.flexbox_child_profile);
        cvAddChildProfile = findViewById(R.id.cv_add_child_profile);
    }
}