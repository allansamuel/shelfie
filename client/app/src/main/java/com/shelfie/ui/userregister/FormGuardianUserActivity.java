package com.shelfie.ui.userregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.shelfie.R;
import com.shelfie.model.GuardianUser;

import java.util.List;
import java.util.Objects;

public class FormGuardianUserActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator formValidator;
    private GuardianUser guardianUser;

    private TextInputLayout txtGuardianUserName;
    private TextInputLayout txtGuardianUserEmail;
    private TextInputLayout txtGuardianUserPassword;
    private TextInputLayout txtGuardianUserPasswordConfirm;
    private Button btnGuardianUserNext;

    @NotEmpty(messageResId = R.string.error_required_field)
    @Length(messageResId = R.string.error_invalid_name_length, min = 3, max = 50, trim = true)
    private TextInputEditText etGuardianUserName;

    @NotEmpty(messageResId = R.string.error_required_field)
    @Email(messageResId = R.string.error_invalid_email)
    private TextInputEditText etGuardianUserEmail;

    @NotEmpty(messageResId = R.string.error_required_field)
    @Password(messageResId = R.string.error_invalid_password, scheme = Password.Scheme.ALPHA_NUMERIC)
    private TextInputEditText etGuardianUserPassword;

    @NotEmpty(messageResId = R.string.error_required_field)
    @ConfirmPassword(messageResId = R.string.error_invalid_password_confirmation)
    private TextInputEditText etGuardianUserPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_guardian_user);

        init();

        btnGuardianUserNext.setOnClickListener(view -> {
            resetErrors();
            formValidator.validate();
        });
    }

    private void init() {
        formValidator = new Validator(this);
        formValidator.setValidationListener(this);
        txtGuardianUserName = findViewById(R.id.txt_guardian_user_name);
        etGuardianUserName = findViewById(R.id.et_guardian_user_name);
        txtGuardianUserEmail = findViewById(R.id.txt_guardian_user_email);
        etGuardianUserEmail = findViewById(R.id.et_guardian_user_email);
        txtGuardianUserPassword = findViewById(R.id.txt_guardian_user_password);
        etGuardianUserPassword = findViewById(R.id.et_guardian_user_password);
        txtGuardianUserPasswordConfirm = findViewById(R.id.txt_guardian_user_password_confirm);
        etGuardianUserPasswordConfirm = findViewById(R.id.et_guardian_user_password_confirm);
        btnGuardianUserNext = findViewById(R.id.btn_guardian_user_next);
    }

    private void startChildProfileFormIntent() {
        formValidator = new Validator(this);
        guardianUser = new GuardianUser();
        guardianUser.setGuardianUserName(Objects.requireNonNull(etGuardianUserName.getText()).toString());
        guardianUser.setGuardianUserEmail(Objects.requireNonNull(etGuardianUserEmail.getText()).toString());
        guardianUser.setGuardianUserPassword(Objects.requireNonNull(etGuardianUserPassword.getText()).toString());

        Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void resetErrors(){
        txtGuardianUserName.setError("");
        txtGuardianUserEmail.setError("");
        txtGuardianUserPassword.setError("");
        txtGuardianUserPasswordConfirm.setError("");
    }

    @Override
    public void onValidationSucceeded() {
        if(guardianUser != null) {
            startChildProfileFormIntent();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError e:errors) {
            View view = e.getView();
            String errorMessage = e.getCollatedErrorMessage(this);

            if(view instanceof TextInputEditText){
                switch (view.getId()){
                    case R.id.et_guardian_user_name:
                        txtGuardianUserName.setError(errorMessage);
                        break;
                    case R.id.et_guardian_user_email:
                        txtGuardianUserEmail.setError(errorMessage);
                        break;
                    case R.id.et_guardian_user_password:
                        txtGuardianUserPassword.setError(errorMessage);
                        break;
                    case R.id.et_guardian_user_password_confirm:
                        txtGuardianUserPasswordConfirm.setError(errorMessage);
                        break;
                }
            }
        }
    }
}