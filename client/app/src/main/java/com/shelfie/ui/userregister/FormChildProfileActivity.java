package com.shelfie.ui.userregister;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.shelfie.R;
import com.shelfie.utils.ImageDownloader;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.CharacterService;
import com.shelfie.service.ChildProfileService;
import com.shelfie.ui.fragments.CustomDialogFragment;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class FormChildProfileActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator formValidator;
    private RetrofitConfig retrofitConfig;
    private GuardianUser guardianUser;
    private ChildProfileService childProfileService;
    private ChildProfile childProfile;
    private CharacterService characterService;
    private List<Character> characterList;
    private Character currentCharacter;
    private Handler changeCharacterHandler;

    private TextInputLayout txtChildProfileNickname;
    private ImageButton ibPreviousCharacter;
    private ImageView imgCharacterPreview;
    private ImageButton ibNextCharacter;
    private Button btnSaveChildProfile;
    private Button btnDeleteChildProfile;
    private ProgressBar progressCircularCharacterLoader;
    private ProgressBar progressChildProfileSave;

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

        btnSaveChildProfile.setOnClickListener(view -> formValidator.validate());

        btnDeleteChildProfile.setOnClickListener(view -> deleteChildProfile());
    }

    private void init() {
        guardianUser = UserSession.getGuardianUser(getApplicationContext()) != null ?
                UserSession.getGuardianUser(getApplicationContext()) : new GuardianUser();
        childProfile = new ChildProfile();

        formValidator = new Validator(this);
        formValidator.setValidationListener(this);
        characterList = new ArrayList<>();
        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();
        characterService = retrofitConfig.getCharacterService();
        changeCharacterHandler = new Handler();

        txtChildProfileNickname = findViewById(R.id.txt_child_profile_nickname);
        etChildProfileNickname = findViewById(R.id.et_child_profile_nickname);
        ibPreviousCharacter = findViewById(R.id.ib_previous_character);
        imgCharacterPreview = findViewById(R.id.img_character_preview);
        ibNextCharacter = findViewById(R.id.ib_next_character);
        btnSaveChildProfile = findViewById(R.id.btn_child_profile_create);
        btnDeleteChildProfile = findViewById(R.id.btn_child_profile_delete);
        progressCircularCharacterLoader = findViewById(R.id.progress_circular_character_loader);
        progressChildProfileSave = findViewById(R.id.progress_child_profile_save);

        if(UserSession.isFormInEditMode(getApplicationContext())){
            childProfile = UserSession.getChildProfile(getApplicationContext());
            currentCharacter = childProfile.getCharacter();
            etChildProfileNickname.setText(childProfile.getNickname());
            btnDeleteChildProfile.setVisibility(View.VISIBLE);
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
                    progressCircularCharacterLoader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Character>> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressCircularCharacterLoader.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setCharacterImagePreview() {
        ImageDownloader imageDownloader = new ImageDownloader(imgCharacterPreview);
        imageDownloader.execute(getString(R.string.url_character_get_image, currentCharacter.getCharacterId()));
        imgCharacterPreview.setVisibility(View.VISIBLE);
        toggleCharacterNavigationButtons();
    }

    private boolean isFirstCharacter() {
        return characterList.indexOf(currentCharacter) == 0 ? true : false;
    }

    private boolean isLastCharacter() {
        return characterList.indexOf(currentCharacter) == characterList.size() - 1 ? true : false;
    }

    private void toggleCharacterNavigationButtons() {
        ibPreviousCharacter.setVisibility(View.VISIBLE);
        if(isFirstCharacter()) {
            ibPreviousCharacter.setVisibility(View.INVISIBLE);
        }

        ibNextCharacter.setVisibility(View.VISIBLE);
        if(isLastCharacter()) {
            ibNextCharacter.setVisibility(View.INVISIBLE);
        }
    }

    Runnable changeCharacterPreview = new Runnable() {
        @Override
        public void run() {
            setCharacterImagePreview();
        }
    };

    private void getPreviousCharacter() {
        if(!isFirstCharacter()) {
            changeCharacterHandler.removeCallbacksAndMessages(null);
            changeCharacterHandler.postDelayed(changeCharacterPreview, 2000);
            currentCharacter = characterList.get(characterList.indexOf(currentCharacter) - 1);
            toggleCharacterNavigationButtons();
        }
    }

    private void getNextCharacter() {
        if(!isLastCharacter()) {
            changeCharacterHandler.removeCallbacksAndMessages(null);
            changeCharacterHandler.postDelayed(changeCharacterPreview, 2000);
            currentCharacter = characterList.get(characterList.indexOf(currentCharacter) + 1);
            toggleCharacterNavigationButtons();
        }
    }

    private void createChildProfile() {
        childProfileService.create(childProfile).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                }
                progressChildProfileSave.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressChildProfileSave.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updateChildProfile() {
        childProfileService.update(childProfile.getChildProfileId(), childProfile).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                }
                progressChildProfileSave.setVisibility(View.INVISIBLE);
                finish();
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressChildProfileSave.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void deleteChildProfile() {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.buildDialog(
                getString(R.string.dialog_confirm_delete_profile),
                CustomDialogFragment.CONFIRMATION_DIALOG,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressChildProfileSave.setVisibility(View.VISIBLE);
                        childProfileService.delete(childProfile.getChildProfileId()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                                startActivity(intent);
                                progressChildProfileSave.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                                progressChildProfileSave.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
        customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
    }

    @Override
    public void onValidationSucceeded() {
        progressChildProfileSave.setVisibility(View.VISIBLE);
        childProfile.setGuardianUser(guardianUser);
        childProfile.setNickname(etChildProfileNickname.getText().toString());
        childProfile.setCharacter(currentCharacter);

        if (UserSession.isFormInEditMode(getApplicationContext())) {
            updateChildProfile();
        } else {
            createChildProfile();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError e:errors) {
            View view = e.getView();
            String errorMessage = e.getCollatedErrorMessage(this);

            if(view.getId() == R.id.et_child_profile_nickname){
                txtChildProfileNickname.setError(errorMessage);
            }
        }
    }
}