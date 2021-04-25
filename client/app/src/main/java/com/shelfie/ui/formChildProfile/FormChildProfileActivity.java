package com.shelfie.ui.formChildProfile;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.service.CharacterService;
import com.shelfie.service.ChildProfileService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FormChildProfileActivity extends AppCompatActivity {

    private RetrofitConfig retrofitConfig;
    private ChildProfileService childProfileService;
    private ChildProfile childProfile;
    private CharacterService characterService;
    private List<Character> characterList;
    private ListIterator characterListIterator;
    private Character currentCharacter;
    private Bitmap characterImagePreview;

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

        ibPreviousCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPreviousCharacter();
            }
        });

        ibNextCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextCharacter();
            }
        });

        btnCreateChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createChildProfile();
            }
        });
    }

    private void init() {
        characterList = new ArrayList<>();
        characterListIterator = characterList.listIterator();
        retrofitConfig = new RetrofitConfig();
        childProfileService = retrofitConfig.getChildProfileService();
        characterService = retrofitConfig.getCharacterService();

        txtChildProfileNickname = findViewById(R.id.txt_child_profile_nickname);
        etChildProfileNickname = findViewById(R.id.et_child_profile_nickname);
        ibPreviousCharacter = findViewById(R.id.ib_previous_character);
        imgCharacterPreview = findViewById(R.id.img_character_preview);
        ibNextCharacter = findViewById(R.id.ib_next_character);
        btnCreateChildProfile = findViewById(R.id.btn_child_profile_create);

        setCharacterList();
    }

    private void setCharacterList() {
        characterService.getAll().enqueue(new Callback<ArrayList<Character>>() {
            @Override
            public void onResponse(Call<ArrayList<Character>> call, Response<ArrayList<Character>> response) {
                if(response.isSuccessful()) {
                    characterList = response.body();
                    if(!characterList.isEmpty()) {
                        currentCharacter = characterList.get(0);
                        setCharacterImagePreview();
                    }
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
        characterImagePreview = BitmapFactory.decodeByteArray(
                currentCharacter.getCharacterImage(),
                0,
                currentCharacter.getCharacterImage().length);
        imgCharacterPreview.setImageBitmap(characterImagePreview);
    }

    private void getPreviousCharacter() {
        if(characterListIterator.hasPrevious()) {
            currentCharacter = (Character) characterListIterator.previous();
            setCharacterImagePreview();
        }
    }

    private void getNextCharacter() {
        if(characterListIterator.hasNext()) {
            currentCharacter = (Character) characterListIterator.next();
            setCharacterImagePreview();
        }
    }

    private void createChildProfile() {
        childProfile = new ChildProfile();
        childProfile.setNickname(etChildProfileNickname.getText().toString());
        childProfile.setCharacter(currentCharacter);

        childProfileService.create(childProfile).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("NEW_CHILD_PROFILE_DATA", response.body());
                    intent.putExtras(bundle);
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
}