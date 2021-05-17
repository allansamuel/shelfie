package com.shelfie.ui.interactivebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Author;
import com.shelfie.model.Character;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.InteractiveBook;
import com.shelfie.model.Quest;
import com.shelfie.ui.fragments.CharacterPreviewFragment;
import com.shelfie.ui.fragments.ProfileAvatarFragment;
import com.shelfie.ui.fragments.QuestPreviewFragment;
import com.shelfie.utils.ImageDecoder;
import com.shelfie.utils.UserSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InteractiveBookActivity extends AppCompatActivity {

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
    private LinearLayout llBookCategories;
    private LinearLayout llBookCharacters;
    private LinearLayout llBookQuests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_book);
        init();
    }

    private void init() {
        receivedBundle = getIntent().getExtras();
        interactiveBook = UserSession.getInteractiveBook(getApplicationContext());

        imgBookCover = findViewById(R.id.img_book_cover);
        tvBookTitle = findViewById(R.id.tv_book_title);
        tvBookSinopsys = findViewById(R.id.tv_book_sinopsys);
        tvBookPublishDate = findViewById(R.id.tv_book_publish_date);
        tvBookAuthors = findViewById(R.id.tv_book_authors);
        tvBookChapters = findViewById(R.id.tv_book_chapters);
        tvBookPrice = findViewById(R.id.tv_book_price);
        llBookCategories = findViewById(R.id.ll_book_categories);
        llBookCharacters = findViewById(R.id.ll_book_characters);
        llBookQuests = findViewById(R.id.ll_book_quests);

        imgBookCover.setImageBitmap(ImageDecoder.decodeBase64(interactiveBook.getBookCover()));
        tvBookTitle.setText(interactiveBook.getTitle());
        tvBookSinopsys.setText(interactiveBook.getSinopsys());
        tvBookPublishDate.setText(formatPublishDate(interactiveBook.getPublishDate()));
        tvBookAuthors.setText(formatAuthors(interactiveBook.getBookAuthors()));

        tvBookChapters.setText(getString(R.string.label_book_chapters_amount, interactiveBook.getChapters().size()));

        tvBookPrice.setText(String.valueOf(interactiveBook.getPrice()));
        mapBookCategories();
        mapBookQuests();
        mapBookCharacters();

    }

    private void mapBookQuests() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(Quest quest : interactiveBook.getQuests()) {
            Fragment profileAvatarFragment = QuestPreviewFragment.newInstance(quest);
            fragmentTransaction.add(R.id.ll_book_quests, profileAvatarFragment, quest.getQuestTitle());
        }
        fragmentTransaction.commit();
    }

    private void mapBookCharacters() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(Character character : interactiveBook.getCharacters()) {
            Fragment profileAvatarFragment = CharacterPreviewFragment.newInstance(character);
            fragmentTransaction.add(R.id.ll_book_characters, profileAvatarFragment, character.getCharacterName());
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
}