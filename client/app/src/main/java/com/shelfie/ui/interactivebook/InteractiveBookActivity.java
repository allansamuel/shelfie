package com.shelfie.ui.interactivebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Author;
import com.shelfie.model.Category;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.InteractiveBook;
import com.shelfie.model.Quest;
import com.shelfie.service.ChildProfileService;
import com.shelfie.service.ChildUnlockedBookService;
import com.shelfie.ui.fragments.CharacterPreviewFragment;
import com.shelfie.ui.fragments.CustomDialogFragment;
import com.shelfie.ui.fragments.QuestPreviewFragment;
import com.shelfie.unityholders.UHVitoriaRegia;
import com.shelfie.utils.ImageDownloader;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ProgressBar progressInteractiveBookDetails;
    private ProgressBar progressInteractiveBookUnlock;

    private List<Character> bookCharacters;
    private List<Quest> bookQuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_book);

        init();

        btnBookUnlock.setOnClickListener(view -> unlockInteractiveBook());

        btnBookRead.setOnClickListener(view -> UHVitoriaRegia.launchGame(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
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
        progressInteractiveBookDetails = findViewById(R.id.progress_interactive_book_details);
        progressInteractiveBookUnlock = findViewById(R.id.progress_interactive_book_unlock);

        ImageDownloader imageDownloader = new ImageDownloader(imgBookCover);
        imageDownloader.execute(getString(R.string.url_book_get_image, interactiveBook.getInteractiveBookId()));
        tvBookTitle.setText(interactiveBook.getTitle());
        tvBookSinopsys.setText(interactiveBook.getSinopsys());
        tvBookCategories.setText(mapBookCategories(interactiveBook.getBookCategories()));
        tvBookPublishDate.setText(formatPublishDate(interactiveBook.getPublishDate()));
        tvBookAuthors.setText(mapBookAuthors(interactiveBook.getBookAuthors()));
        tvBookChapters.setText(getString(R.string.label_book_unlock));
        tvBookPrice.setText(String.valueOf(interactiveBook.getPrice()));
        tvChildCurrentCoinsAmount.setText(getString(R.string.label_book_child_current_coins_amount, childProfile.getCoins()));

        bookQuests = new ArrayList<>();
        bookQuests.addAll(interactiveBook.getQuests());

        bookCharacters = new ArrayList<>();
        bookCharacters.addAll(interactiveBook.getCharacters());
        mapBookQuests();
        mapBookCharacters();

        checkIfBookIsUnlocked();
    }

    private void mapBookQuests() {
        llBookQuests.removeAllViews();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(Quest quest : bookQuests) {
            Fragment profileAvatarFragment = QuestPreviewFragment.newInstance(quest);
            fragmentTransaction.add(llBookQuests.getId(), profileAvatarFragment, quest.getQuestTitle());
        }
        fragmentTransaction.commit();
    }

    private void mapBookCharacters() {
        llBookCharacters.removeAllViews();
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
        progressInteractiveBookUnlock.setVisibility(View.VISIBLE);
        childUnlockedBookService.unlock(
                childProfile.getChildProfileId(),
                interactiveBook.getInteractiveBookId()).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    llBookUnlock.setVisibility(View.GONE);
                    btnBookRead.setVisibility(View.VISIBLE);
                    UserSession.setChildProfile(getApplicationContext(), response.body());
                } else {
                    CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                    customDialogFragment.buildDialog(getString(R.string.dialog_book_unlock_failed));
                    customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                }
                progressInteractiveBookUnlock.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressInteractiveBookUnlock.setVisibility(View.INVISIBLE);
            }
        });
        checkIfBookIsUnlocked();
    }

    private void checkIfBookIsUnlocked() {
        childUnlockedBookService.isUnlocked(
                childProfile.getChildProfileId(),
                interactiveBook.getInteractiveBookId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    if (response.body()) {
                        llBookUnlock.setVisibility(View.GONE);
                        btnBookRead.setVisibility(View.VISIBLE);
                    } else {
                        llBookUnlock.setVisibility(View.VISIBLE);
                        btnBookRead.setVisibility(View.INVISIBLE);
                    }
                }
                progressInteractiveBookDetails.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                customDialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
                progressInteractiveBookDetails.setVisibility(View.INVISIBLE);
            }
        });
    }
}