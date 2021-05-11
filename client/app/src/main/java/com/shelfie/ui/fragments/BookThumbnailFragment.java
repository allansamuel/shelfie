package com.shelfie.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shelfie.R;
import com.shelfie.ui.interactivebook.InteractiveBookActivity;
import com.shelfie.utils.ImageDecoder;
import com.shelfie.model.InteractiveBook;
import com.shelfie.ui.main.MainActivity;
import com.shelfie.utils.UserSession;

import java.io.ByteArrayOutputStream;

public class BookThumbnailFragment extends Fragment {

    private static final String ARG_INTERACTIVE_BOOK = "INTERACTIVE_BOOK_DATA";

    private CardView cvBookThumbnail;
    private InteractiveBook interactiveBook;

    public BookThumbnailFragment() {
    }

    public static BookThumbnailFragment newInstance(InteractiveBook interactiveBook) {
        BookThumbnailFragment fragment = new BookThumbnailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_INTERACTIVE_BOOK, interactiveBook);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            interactiveBook = (InteractiveBook) getArguments().getSerializable(ARG_INTERACTIVE_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_thumbnail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        cvBookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSession.setInteractiveBook(getContext(), interactiveBook);
                Intent interactiveBookIntent = new Intent(getContext(), InteractiveBookActivity.class);
                startActivity(interactiveBookIntent);
            }});
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    private void init() {
        View view = getView();
        cvBookThumbnail = view.findViewById(R.id.cv_book_thumbnail);
        ImageView imgBookThumbnail = view.findViewById(R.id.img_book_thumbnail);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        imgBookThumbnail.setImageBitmap(ImageDecoder.decodeBase64(interactiveBook.getBookCover()));
    }
}