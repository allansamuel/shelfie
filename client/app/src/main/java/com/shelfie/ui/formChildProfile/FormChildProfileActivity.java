package com.shelfie.ui.formChildProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.google.android.flexbox.FlexboxLayout;
import com.shelfie.R;

public class FormChildProfileActivity extends AppCompatActivity {

    private FlexboxLayout flexboxChildProfiles;
    private CardView cvAddChildProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_child_profile);

        init();
    }

    private void init() {
        flexboxChildProfiles = findViewById(R.id.flexbox_child_profiles);
        cvAddChildProfile = findViewById(R.id.cv_add_child_profile);
    }
}