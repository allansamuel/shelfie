package com.shelfie.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shelfie.R;
import com.shelfie.ui.userregister.FormGuardianUserActivity;
import com.shelfie.ui.userregister.ManageChildProfileActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FormGuardianUserActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        btnRegister = findViewById(R.id.btn_register);
    }
}