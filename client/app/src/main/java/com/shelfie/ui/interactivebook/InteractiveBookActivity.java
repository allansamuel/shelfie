package com.shelfie.ui.interactivebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Author;
import com.shelfie.model.Category;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.model.InteractiveBook;
import com.shelfie.model.Quest;
import com.shelfie.service.ChildProfileService;
import com.shelfie.service.ChildUnlockedBookService;
import com.shelfie.ui.fragments.CharacterPreviewFragment;
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.ui.fragments.QuestPreviewFragment;
import com.shelfie.ui.unityholders.UHVitoriaRegiaActivity;
import com.shelfie.utils.ImageDecoder;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InteractiveBookActivity extends AppCompatActivity {

    private ChildProfile childProfile;
    private SimpleDateFormat dateFormatter;
    private InteractiveBook interactiveBook;

    private RetrofitConfig retrofitConfig;
    private ChildUnlockedBookService childUnlockedBookService;
    private ChildProfileService childProfileService;

    private ImageView imgBookCover;
    private TextView tvBookTitle;
    private TextView tvBookSinopsys;
    private TextView tvBookPublishDate;
    private TextView tvBookAuthors;
    private TextView tvBookChapters;
    private TextView tvBookPrice;
    private TextView tvChildCurrentCoinsAmount;
    private TextView tvBookCategories;
    private LinearLayout llBookCharacters;
    private LinearLayout llBookQuests;
    private LinearLayout llBookUnlock;
    private Button btnBookUnlock;
    private Button btnBookRead;

    private boolean isBookUnlocked = false;
    private List<Character> bookCharacters;
    private List<Quest> bookQuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_book);
        init();

        btnBookUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unlockInteractiveBook();
            }
        });

        btnBookRead.setOnClickListener(view -> {
            Intent openBook = new Intent(InteractiveBookActivity.this, UHVitoriaRegiaActivity.class);
            startActivity(openBook);
        });
    }

    private void init() {
        childProfile = UserSession.getChildProfile(getApplicationContext());
        interactiveBook = UserSession.getInteractiveBook(getApplicationContext());

        retrofitConfig = new RetrofitConfig();
        childUnlockedBookService = retrofitConfig.getChildUnlockedBookService();
        childProfileService = retrofitConfig.getChildProfileService();

        imgBookCover = findViewById(R.id.img_book_cover);
        tvBookTitle = findViewById(R.id.tv_book_title);
        tvBookSinopsys = findViewById(R.id.tv_book_sinopsys);
        tvBookPublishDate = findViewById(R.id.tv_book_publish_date);
        tvBookAuthors = findViewById(R.id.tv_book_authors);
        tvBookChapters = findViewById(R.id.tv_book_chapters);
        tvBookPrice = findViewById(R.id.tv_book_price);
        tvChildCurrentCoinsAmount = findViewById(R.id.tv_child_current_coins_amount);
        tvBookCategories = findViewById(R.id.tv_book_categories);
        llBookCharacters = findViewById(R.id.ll_book_characters);
        llBookQuests = findViewById(R.id.ll_book_quests);
        llBookUnlock = findViewById(R.id.ll_book_unlock);
        btnBookUnlock = findViewById(R.id.btn_book_unlock);
        btnBookRead = findViewById(R.id.btn_book_read);

        imgBookCover.setImageBitmap(ImageDecoder.decodeBase64(interactiveBook.getBookCover()));
        tvBookTitle.setText(interactiveBook.getTitle());
        tvBookSinopsys.setText(interactiveBook.getSinopsys());
        tvBookCategories.setText(mapBookCategories(interactiveBook.getBookCategories()));
        tvBookPublishDate.setText(formatPublishDate(interactiveBook.getPublishDate()));
        tvBookAuthors.setText(mapBookAuthors(interactiveBook.getBookAuthors()));
        tvBookChapters.setText(getString(R.string.label_book_chapters_amount, interactiveBook.getChapters().size()));
        tvBookPrice.setText(String.valueOf(interactiveBook.getPrice()));
        tvChildCurrentCoinsAmount.setText(getString(R.string.label_book_child_current_coins_amount, childProfile.getCoins()));

        if(childProfile.getCoins() >= interactiveBook.getPrice()) {
            btnBookUnlock.setVisibility(View.VISIBLE);
        }

        bookQuests = new ArrayList<>();
        bookQuests.addAll(interactiveBook.getQuests());

        bookCharacters = new ArrayList<>();
        bookCharacters.addAll(interactiveBook.getCharacters());
        mapBookQuests();
        mapBookCharacters();

        checkIfBookIsUnlocked();

    }

    private void mapBookQuests() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(Quest quest : bookQuests) {
            Fragment profileAvatarFragment = QuestPreviewFragment.newInstance(quest);
            fragmentTransaction.add(llBookQuests.getId(), profileAvatarFragment, quest.getQuestTitle());
        }
        fragmentTransaction.commit();
    }

    private void mapBookCharacters() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(Character character : bookCharacters) {
            Fragment profileAvatarFragment = CharacterPreviewFragment.newInstance(character);
            fragmentTransaction.add(llBookCharacters.getId(), profileAvatarFragment, character.getCharacterName());
        }
        fragmentTransaction.commit();
    }

    private String mapBookCategories(List<Category> categories) {
        String formattedCategories = "";
        for(Category category : categories) {
            formattedCategories = formattedCategories.concat(category.getCategoryName());
            if(categories.indexOf(category) != categories.size() - 1) {
                formattedCategories = formattedCategories.concat(", ");
            }
        }
        return formattedCategories;
    }

    private String mapBookAuthors(List<Author> authors) {
        String formattedAuthors = "";
        for(Author author : authors) {
            formattedAuthors = formattedAuthors.concat(author.getAuthorName());
            if(authors.indexOf(author) != authors.size() - 1) {
                formattedAuthors = formattedAuthors.concat(",\n");
            }
        }
        return formattedAuthors;
    }

    private String formatPublishDate(Date date) {
        dateFormatter = new SimpleDateFormat(getString(R.string.date_formatter));
        return dateFormatter.format(date);
    }

    private void unlockInteractiveBook() {
        childUnlockedBookService.unlock(childProfile.getChildProfileId(), interactiveBook.getInteractiveBookId()).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    btnBookUnlock.setVisibility(View.INVISIBLE);
                    btnBookRead.setVisibility(View.VISIBLE);
                    UserSession.setChildProfile(getApplicationContext(), response.body());
                }
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getSupportFragmentManager(), "EmptyStateDialogFragment");
            }
        });
        checkIfBookIsUnlocked();
    }

    private void checkIfBookIsUnlocked() {

        childUnlockedBookService.getByChildAndBook(childProfile.getChildProfileId(), interactiveBook.getInteractiveBookId()).enqueue(new Callback<ChildUnlockedBook>() {
            @Override
            public void onResponse(Call<ChildUnlockedBook> call, Response<ChildUnlockedBook> response) {

                if(response.isSuccessful()){
                        isBookUnlocked = true;
                        llBookUnlock.setVisibility(View.GONE);
                        btnBookRead.setVisibility(View.VISIBLE);
                        btnBookUnlock.setVisibility(View.INVISIBLE);
                }else{
                    btnBookRead.setVisibility(View.INVISIBLE);
                    btnBookUnlock.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ChildUnlockedBook> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getSupportFragmentManager(), "EmptyStateDialogFragment");            }
        });
    }
}