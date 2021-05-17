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
import com.shelfie.model.InteractiveBook;
import com.shelfie.model.Quest;
import com.shelfie.ui.fragments.CharacterPreviewFragment;
import com.shelfie.ui.fragments.QuestPreviewFragment;
import com.shelfie.ui.unityholders.UHVitoriaRegiaActivity;
import com.shelfie.utils.ImageDecoder;
import com.shelfie.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InteractiveBookActivity extends AppCompatActivity {

    private ChildProfile childProfile;
    private SimpleDateFormat dateFormatter;
    private Bundle receivedBundle;
    private InteractiveBook interactiveBook;

    private ImageView imgBookCover;
    private TextView tvBookTitle;
    private TextView tvBookSinopsys;
    private TextView tvBookPublishDate;
    private TextView tvBookAuthors;
    private TextView tvBookChapters;
    private TextView tvBookPrice;
    private TextView tvChildCurrentCoinsAmount;
    private LinearLayout llBookCategories;
    private LinearLayout llBookCharacters;
    private LinearLayout llBookQuests;
    private LinearLayout llBookUnlock;
    private Button btnBookUnlock;
    private Button btnBookRead;

    private boolean isBookUnlocked = false;

    private List<Category> bookCategories;
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

        btnBookRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openBook = new Intent(InteractiveBookActivity.this, UHVitoriaRegiaActivity.class);
                startActivity(openBook);
            }
        });
    }

    private void init() {
        childProfile = UserSession.getChildProfile(getApplicationContext());
        receivedBundle = getIntent().getExtras();
        interactiveBook = UserSession.getInteractiveBook(getApplicationContext());

        imgBookCover = findViewById(R.id.img_book_cover);
        tvBookTitle = findViewById(R.id.tv_book_title);
        tvBookSinopsys = findViewById(R.id.tv_book_sinopsys);
        tvBookPublishDate = findViewById(R.id.tv_book_publish_date);
        tvBookAuthors = findViewById(R.id.tv_book_authors);
        tvBookChapters = findViewById(R.id.tv_book_chapters);
        tvBookPrice = findViewById(R.id.tv_book_price);
        tvChildCurrentCoinsAmount = findViewById(R.id.tv_child_current_coins_amount);
        llBookCategories = findViewById(R.id.ll_book_categories);
        llBookCharacters = findViewById(R.id.ll_book_characters);
        llBookQuests = findViewById(R.id.ll_book_quests);
        llBookUnlock = findViewById(R.id.ll_book_unlock);
        btnBookUnlock = findViewById(R.id.btn_book_unlock);
        btnBookRead = findViewById(R.id.btn_book_read);

        imgBookCover.setImageBitmap(ImageDecoder.decodeBase64(interactiveBook.getBookCover()));
        tvBookTitle.setText(interactiveBook.getTitle());
        tvBookSinopsys.setText(interactiveBook.getSinopsys());
        tvBookPublishDate.setText(formatPublishDate(interactiveBook.getPublishDate()));
        tvBookAuthors.setText(formatAuthors(interactiveBook.getBookAuthors()));
        tvBookChapters.setText(getString(R.string.label_book_chapters_amount, interactiveBook.getChapters().size()));
        tvBookPrice.setText(String.valueOf(interactiveBook.getPrice()));
        tvChildCurrentCoinsAmount.setText(getString(R.string.label_book_child_current_coins_amount, childProfile.getCoins()));
        if(childProfile.getCoins() >= interactiveBook.getPrice()) {
            btnBookUnlock.setEnabled(true);
        }

        bookCategories = new ArrayList<>();
        bookCategories.addAll(interactiveBook.getBookCategories());

        bookQuests = new ArrayList<>();
        bookQuests.addAll(interactiveBook.getQuests());

        bookCharacters = new ArrayList<>();
        bookCharacters.addAll(interactiveBook.getCharacters());
        mapBookCategories();
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

    private void mapBookCategories() {
    }

    private String formatPublishDate(Date date) {
        dateFormatter = new SimpleDateFormat(getString(R.string.date_formatter));
        return dateFormatter.format(date);
    }

    private String formatAuthors(List<Author> authors) {
        String formattedAuthors = "";
        for(Author author : authors) {
            formattedAuthors = formattedAuthors.concat(author.getAuthorName());
            if(authors.indexOf(author) != authors.size() - 1) {
                formattedAuthors = formattedAuthors.concat(",\n");
            }
        }
        return formattedAuthors;
    }

    private void unlockInteractiveBook() {
        //chamar serviço para desbloquear
        isBookUnlocked = true;
        checkIfBookIsUnlocked();
    }

    private void checkIfBookIsUnlocked() {
        //chamar serviço para verificar se o livro atual está desbloqueado pela criança logada
        if(isBookUnlocked) {
            llBookUnlock.setVisibility(View.GONE);
            btnBookRead.setVisibility(View.VISIBLE);
        }
    }
}