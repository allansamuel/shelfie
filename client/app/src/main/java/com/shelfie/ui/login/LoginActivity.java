package com.shelfie.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.shelfie.R;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.dialogs.CustomDialogFragment;
import com.shelfie.ui.user.FormGuardianUserActivity;
import com.shelfie.ui.user.childprofile.ManageChildProfileActivity;
import com.shelfie.utils.UserSession;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private RetrofitConfig retrofitConfig;
    private GuardianUserService guardianUserService;
    private GuardianUser guardianUser;

    private Validator formValidator;
    private TextInputLayout txtLoginGuardianUserEmail;
    private TextInputLayout txtLoginGuardianUserPassword;
    private ProgressBar progressGuardianUserLogin;
    private Button btnLogin;
    private Button btnRegister;

    @Email(messageResId = R.string.error_invalid_email)
    private TextInputEditText etLoginGuardianUserEmail;

    @Password(messageResId = R.string.error_invalid_password, scheme = Password.Scheme.ALPHA_NUMERIC)
    private TextInputEditText etLoginGuardianUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnLogin.setOnClickListener(view -> {
            resetErrors();
            formValidator.validate();
        });

        btnRegister.setOnClickListener(view -> {
            UserSession.startSession(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), FormGuardianUserActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();
        guardianUser = new GuardianUser();

        formValidator = new Validator(this);
        formValidator.setValidationListener(this);
        txtLoginGuardianUserEmail = findViewById(R.id.txt_login_guardian_user_email);
        txtLoginGuardianUserPassword = findViewById(R.id.txt_login_guardian_user_password);
        etLoginGuardianUserEmail = findViewById(R.id.et_login_guardian_user_email);
        etLoginGuardianUserPassword = findViewById(R.id.et_login_guardian_user_password);
        progressGuardianUserLogin = findViewById(R.id.progress_guardian_user_login);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void resetErrors(){
        txtLoginGuardianUserEmail.setError("");
        txtLoginGuardianUserPassword.setError("");
    }

    private void login() {
        guardianUserService.login(guardianUser).enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {
                if(response.isSuccessful()) {
                    guardianUser = response.body();
                    UserSession.startSession(getApplicationContext());
                    UserSession.setGuardianUser(getApplicationContext(), guardianUser);
                    if(!guardianUser.getChildProfiles().isEmpty()) {
                        UserSession.setFormInteractionMode(getApplicationContext(), UserSession.READ_MODE);
                    }
                    finish();
                    Intent manageChildProfilesActivity = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    startActivity(manageChildProfilesActivity);
                } else {
                    CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                    customDialogFragment.buildDialog(getString(R.string.dialog_login_failed));
                    customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                }
                progressGuardianUserLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressGuardianUserLogin.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        progressGuardianUserLogin.setVisibility(View.VISIBLE);
        guardianUser.setGuardianUserEmail(etLoginGuardianUserEmail.getText().toString());
        guardianUser.setGuardianUserPassword(etLoginGuardianUserPassword.getText().toString());
        login();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError e:errors) {
            View view = e.getView();
            String errorMessage = e.getCollatedErrorMessage(this);

            if(view instanceof TextInputEditText){
                switch (view.getId()){
                    case R.id.et_login_guardian_user_email:
                        txtLoginGuardianUserEmail.setError(errorMessage);
                        break;
                    case R.id.et_login_guardian_user_password:
                        txtLoginGuardianUserPassword.setError(errorMessage);
                        break;
                }
            }
        }
    }
}