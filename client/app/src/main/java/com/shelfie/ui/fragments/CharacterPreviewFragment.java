package com.shelfie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Character;
import com.shelfie.utils.ImageDownloader;

public class CharacterPreviewFragment extends Fragment {

    private static final String ARG_CHARACTER = "CHARACTER_DATA";

    private Character character;
    private ImageView imgBookCharacterPreview;
    private TextView tvBookCharacterName;
    private TextView tvBookCharacterDescription;

    public CharacterPreviewFragment() {
    }

    public static CharacterPreviewFragment newInstance(Character character) {
        CharacterPreviewFragment fragment = new CharacterPreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHARACTER, character);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            character = (Character) getArguments().getSerializable(ARG_CHARACTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        View view = getView();
        imgBookCharacterPreview = view.findViewById(R.id.img_book_character_preview);
        tvBookCharacterName = view.findViewById(R.id.tv_book_character_name);
        tvBookCharacterDescription = view.findViewById(R.id.tv_book_character_description);

        ImageDownloader imageDownloader = new ImageDownloader(imgBookCharacterPreview);
        imageDownloader.execute(getString(R.string.url_character_get_image, character.getCharacterId()));
        tvBookCharacterName.setText(character.getCharacterName());
        tvBookCharacterDescription.setText(character.getCharacterDescription());
    }
}