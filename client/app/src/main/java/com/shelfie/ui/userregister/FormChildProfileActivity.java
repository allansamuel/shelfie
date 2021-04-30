package com.shelfie.ui.userregister;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.shelfie.R;
import com.shelfie.config.ImageDecoder;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.CharacterService;
import com.shelfie.service.ChildProfileService;

import java.util.ArrayList;
import java.util.List;

public class FormChildProfileActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator formValidator;
    private Bundle receivedBundle;
    private GuardianUser guardianUser;

    private RetrofitConfig retrofitConfig;
    private ChildProfileService childProfileService;
    private ChildProfile childProfile;
    private CharacterService characterService;
    private List<Character> characterList;
    private Character currentCharacter;

    private TextInputLayout txtChildProfileNickname;
    private ImageButton ibPreviousCharacter;
    private ImageView imgCharacterPreview;
    private ImageButton ibNextCharacter;
    private Button btnCreateChildProfile;
    private Button btnDeleteChildProfile;
    private ProgressBar progressCircularCharacterLoader;

    @NotEmpty(messageResId = R.string.error_required_field)
    @Length(messageResId = R.string.error_invalid_nickname_length, min = 3, max = 20, trim = true)
    private TextInputEditText etChildProfileNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_child_profile);

        init();

        getCharacterList();

        ibPreviousCharacter.setOnClickListener(view -> getPreviousCharacter());

        ibNextCharacter.setOnClickListener(view -> getNextCharacter());

        btnCreateChildProfile.setOnClickListener(view -> createChildProfile());

        btnDeleteChildProfile.setOnClickListener(view -> deleteChildProfile());
    }

    private void init() {
        receivedBundle = getIntent().getExtras();
        guardianUser = (GuardianUser) receivedBundle.getSerializable(getString(R.string.bundle_guardian_user));
        childProfile = (ChildProfile) receivedBundle.getSerializable(getString(R.string.bundle_child_profile));

        formValidator = new Validator(this);
        formValidator.setValidationListener(this);
        characterList = new ArrayList<>();
        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();
        characterService = retrofitConfig.getCharacterService();

        txtChildProfileNickname = findViewById(R.id.txt_child_profile_nickname);
        etChildProfileNickname = findViewById(R.id.et_child_profile_nickname);
        ibPreviousCharacter = findViewById(R.id.ib_previous_character);
        imgCharacterPreview = findViewById(R.id.img_character_preview);
        ibNextCharacter = findViewById(R.id.ib_next_character);
        btnCreateChildProfile = findViewById(R.id.btn_child_profile_create);
        btnDeleteChildProfile = findViewById(R.id.btn_child_profile_delete);
        progressCircularCharacterLoader = findViewById(R.id.progress_circular_character_loader);

        if(childProfile != null){
            currentCharacter = childProfile.getCharacter();
            etChildProfileNickname.setText(childProfile.getNickname());
            btnDeleteChildProfile.setVisibility(View.VISIBLE);
        } else {
            childProfile = new ChildProfile();
        }
    }

    private void getCharacterList() {
        characterService.getAll().enqueue(new Callback<ArrayList<Character>>() {
            @Override
            public void onResponse(Call<ArrayList<Character>> call, Response<ArrayList<Character>> response) {
                if(response.isSuccessful()) {
                    characterList = response.body();
                    if(!characterList.isEmpty()) {
                        currentCharacter = childProfile.getCharacter() != null ? childProfile.getCharacter() : characterList.get(0);
                        setCharacterImagePreview();
                    }
                    System.out.println(characterList.size());
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "deu ruim meu", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Character>> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setCharacterImagePreview() {
        progressCircularCharacterLoader.setVisibility(View.GONE);
        imgCharacterPreview.setVisibility(View.VISIBLE);
        imgCharacterPreview.setImageBitmap(ImageDecoder.decodeBase64(currentCharacter.getCharacterImage()));
        hideCharacterNavigationButtons();
    }

    private boolean isFirstCharacter(Character character) {
        return characterList.indexOf(currentCharacter) == 0 ? true : false;
    }

    private boolean isLastCharacter(Character character) {
        return characterList.indexOf(currentCharacter) == characterList.size() - 1 ? true : false;
    }

    private void hideCharacterNavigationButtons() {
        ibPreviousCharacter.setVisibility(View.VISIBLE);
        if(isFirstCharacter(currentCharacter)) {
            ibPreviousCharacter.setVisibility(View.INVISIBLE);
        }

        ibNextCharacter.setVisibility(View.VISIBLE);
        if(isLastCharacter(currentCharacter)) {
            ibNextCharacter.setVisibility(View.INVISIBLE);
        }
    }

    private void getPreviousCharacter() {
        if(!isFirstCharacter(currentCharacter)) {
            currentCharacter = characterList.get(characterList.indexOf(currentCharacter) - 1);
            setCharacterImagePreview();
            hideCharacterNavigationButtons();
        }
    }

    private void getNextCharacter() {
        if(!isLastCharacter(currentCharacter)) {
            currentCharacter = characterList.get(characterList.indexOf(currentCharacter) + 1);
            setCharacterImagePreview();
            hideCharacterNavigationButtons();
        }
    }

    private void createChildProfile() {
        childProfile.setNickname(etChildProfileNickname.getText().toString());
        childProfile.setCharacter(currentCharacter);
        childProfile.setGuardianUser(guardianUser);

        childProfileService.create(childProfile).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    Bundle newIntentBundle = new Bundle();
                    newIntentBundle.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
                    intent.putExtras(newIntentBundle);
                    startActivity(intent);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void deleteChildProfile() {
        childProfileService.delete(childProfile.getChildProfileId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
                intent.putExtras(newIntentBundle);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
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