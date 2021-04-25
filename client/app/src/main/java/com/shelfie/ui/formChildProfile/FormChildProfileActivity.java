package com.shelfie.ui.formChildProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.service.ChildProfileService;

public class FormChildProfileActivity extends AppCompatActivity {

    private RetrofitConfig retrofitConfig;
    private ChildProfileService childProfileService;
    private ChildProfile childProfile;

    private TextInputLayout txtChildProfileNickname;
    private TextInputEditText etChildProfileNickname;
    private ImageButton ibPreviousCharacter;
    private ImageView imgCharacterPreview;
    private ImageButton ibNextCharacter;
    private Button btnCreateChildProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_child_profile);

        init();

        btnCreateChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();

        txtChildProfileNickname = findViewById(R.id.txt_child_profile_nickname);
        etChildProfileNickname = findViewById(R.id.et_child_profile_nickname);
        ibPreviousCharacter = findViewById(R.id.ib_previous_character);
        imgCharacterPreview = findViewById(R.id.img_character_preview);
        ibNextCharacter = findViewById(R.id.ib_next_character);
        btnCreateChildProfile = findViewById(R.id.btn_child_profile_create);
    }
}