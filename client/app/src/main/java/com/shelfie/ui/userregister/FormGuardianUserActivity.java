package com.shelfie.ui.userregister;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
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
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;

import java.util.List;

public class FormGuardianUserActivity extends AppCompatActivity implements Validator.ValidationListener {

    private RetrofitConfig retrofitConfig;
    private GuardianUserService guardianUserService;
    private GuardianUser guardianUser;

    private TextInputLayout txtGuardianUserName;
    private TextInputLayout txtGuardianUserEmail;
    private TextInputLayout txtGuardianUserPassword;
    private TextInputLayout txtGuardianUserPasswordConfirm;
    private Button btnGuardianUserNext;

    private static final int NAME_FIELD_LENGTH_MIN = 3;
    private static final int NAME_FIELD_LENGTH_MAX = 50;
    private final String NAME_FIELD_LENGTH_MESSAGE = getString(R.string.error_invalid_length, NAME_FIELD_LENGTH_MIN, NAME_FIELD_LENGTH_MAX);
    @NotEmpty(messageResId = R.string.error_required_field)
    @Length(message = NAME_FIELD_LENGTH_MESSAGE, min = NAME_FIELD_LENGTH_MIN, max = NAME_FIELD_LENGTH_MAX)
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

        btnGuardianUserNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGuardianUser();
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

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

    private void createGuardianUser() {
        guardianUser = new GuardianUser();
        guardianUser.setGuardianUserName(etGuardianUserName.getText().toString());
        guardianUser.setGuardianUserEmail(etGuardianUserEmail.getText().toString());
        guardianUser.setGuardianUserPassword(etGuardianUserPassword.getText().toString());

        guardianUserService.create(guardianUser).enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("GUARDIAN_USER_DATA", response.body());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao deu colega", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}