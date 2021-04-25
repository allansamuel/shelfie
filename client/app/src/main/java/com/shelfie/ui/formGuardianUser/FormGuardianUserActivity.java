package com.shelfie.ui.formGuardianUser;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;

public class FormGuardianUserActivity extends AppCompatActivity {

    private RetrofitConfig retrofitConfig;
    private GuardianUserService guardianUserService;
    private GuardianUser guardianUser;

    private TextInputLayout txtGuardianUserName;
    private TextInputEditText etGuardianUserName;
    private TextInputLayout txtGuardianUserEmail;
    private TextInputEditText etGuardianUserEmail;
    private TextInputLayout txtGuardianUserPassword;
    private TextInputEditText etGuardianUserPassword;
    private TextInputLayout txtGuardianUserPasswordConfirm;
    private TextInputEditText etGuardianUserPasswordConfirm;
    private Button buttonGuardianUserNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_guardian_user);

        init();

        buttonGuardianUserNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getProductService();

        txtGuardianUserName = findViewById(R.id.txt_guardian_user_name);
        etGuardianUserName = findViewById(R.id.et_guardian_user_name);
        txtGuardianUserEmail = findViewById(R.id.txt_guardian_user_email);
        etGuardianUserEmail = findViewById(R.id.et_guardian_user_email);
        txtGuardianUserPassword = findViewById(R.id.txt_guardian_user_password);
        etGuardianUserPassword = findViewById(R.id.et_guardian_user_password);
        txtGuardianUserPasswordConfirm = findViewById(R.id.txt_guardian_user_password_confirm);
        etGuardianUserPasswordConfirm = findViewById(R.id.et_guardian_user_password_confirm);
        buttonGuardianUserNext = findViewById(R.id.button_guardian_user_next);
    }

    private void createGuardianUser() {
        guardianUser.setGuardianUserName(etGuardianUserName.getText().toString());
        guardianUser.setGuardianUserEmail(etGuardianUserEmail.getText().toString());
        guardianUser.setGuardianUserPassword(etGuardianUserPassword.getText().toString());

        guardianUserService.create(guardianUser).enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {

            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {

            }
        });
    }
}