package com.shelfie.ui.userregister;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.shelfie.R;
import com.shelfie.ui.login.LoginActivity;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.fragments.CustomDialogFragment;
import com.shelfie.utils.UserSession;

import java.util.List;
import java.util.Objects;

public class FormGuardianUserActivity extends AppCompatActivity implements Validator.ValidationListener {

    private RetrofitConfig retrofitConfig;
    private GuardianUserService guardianUserService;
    private GuardianUser guardianUser;

    private Validator formValidator;
    private TextInputLayout txtGuardianUserName;
    private TextInputLayout txtGuardianUserEmail;
    private TextInputLayout txtGuardianUserPassword;
    private TextInputLayout txtGuardianUserPasswordConfirm;
    private Button btnGuardianUserNext;
    private Button btnGuardianUserDelete;
    private ProgressBar progressGuardianUserSave;

    @Length(messageResId = R.string.error_invalid_name_length, min = 3, max = 50, trim = true)
    private TextInputEditText etGuardianUserName;

    @Email(messageResId = R.string.error_invalid_email)
    private TextInputEditText etGuardianUserEmail;

    @Password(messageResId = R.string.error_invalid_password, scheme = Password.Scheme.ALPHA_NUMERIC)
    private TextInputEditText etGuardianUserPassword;

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

        btnGuardianUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserGuardian();
            }
        });


    }

    private void init() {
        guardianUser = new GuardianUser();
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

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
        btnGuardianUserDelete = findViewById(R.id.btn_guardian_user_delete);
        progressGuardianUserSave = findViewById(R.id.progress_guardian_user_save);

        if(UserSession.isFormInEditMode(getApplicationContext())){
            guardianUser = UserSession.getGuardianUser(getApplicationContext());
            etGuardianUserName.setText(guardianUser.getGuardianUserName());
            etGuardianUserEmail.setText(guardianUser.getGuardianUserEmail());
            btnGuardianUserDelete.setVisibility(View.VISIBLE);
        }

    }

    private void createGuardianUser() {
        guardianUserService.create(guardianUser)
                .enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {
                if(response.isSuccessful()) {
                    guardianUser = response.body();
                    UserSession.setGuardianUser(getApplicationContext(), guardianUser);
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    startActivity(intent);
                } else {

                }
                progressGuardianUserSave.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressGuardianUserSave.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updateGuardianUser() {
        guardianUserService.update(guardianUser.getGuardianUserId(), guardianUser)
                .enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {
                if(response.isSuccessful()) {
                    guardianUser = response.body();
                    UserSession.setGuardianUser(getApplicationContext(), guardianUser);
                    UserSession.setFormInteractionMode(getApplicationContext(), UserSession.READ_MODE);
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    startActivity(intent);
                } else {

                }
                progressGuardianUserSave.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressGuardianUserSave.setVisibility(View.INVISIBLE);
            }
        });
    }


    private void deleteUserGuardian(){
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.buildDialog(
                getString(R.string.dialog_confirm_delete_user),
                CustomDialogFragment.CONFIRMATION_DIALOG,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        guardianUserService.delete(guardianUser.getGuardianUserId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                            }
                        });
                    }
                });
        customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
    }






    private void resetErrors(){
        txtGuardianUserName.setError("");
        txtGuardianUserEmail.setError("");
        txtGuardianUserPassword.setError("");
        txtGuardianUserPasswordConfirm.setError("");
    }

    @Override
    public void onValidationSucceeded() {
        progressGuardianUserSave.setVisibility(View.VISIBLE);
        guardianUser.setGuardianUserName(Objects.requireNonNull(etGuardianUserName.getText()).toString());
        guardianUser.setGuardianUserEmail(Objects.requireNonNull(etGuardianUserEmail.getText()).toString());
        guardianUser.setGuardianUserPassword(Objects.requireNonNull(etGuardianUserPassword.getText()).toString());

        if (UserSession.isFormInEditMode(getApplicationContext())) {
            updateGuardianUser();
        } else {
            createGuardianUser();
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