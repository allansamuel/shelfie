package com.shelfie.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shelfie.R;
import com.shelfie.config.ImageDecoder;
import com.shelfie.model.InteractiveBook;
import com.shelfie.ui.main.MainActivity;

public class BookThumbnailFragment extends Fragment {

    private static final String ARG_INTERACTIVE_BOOK = "INTERACTIVE_BOOK_DATA";

    private CardView cvBookThumbnail;
    private ImageView imgBookThumbnail;
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
        init();

        cvBookThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interactiveBookIntent = new Intent(requireActivity().getApplicationContext(), MainActivity.class);
                interactiveBookIntent.putExtra(ARG_INTERACTIVE_BOOK, interactiveBook);
                startActivity(interactiveBookIntent);
            }
        });

        return inflater.inflate(R.layout.fragment_book_thumbnail, container, false);
    }

    private void init() {
        View view = getView();
        assert view != null;
        cvBookThumbnail = view.findViewById(R.id.cv_book_thumbnail);
        imgBookThumbnail = view.findViewById(R.id.img_book_thumbnail);
        Bitmap bookCover = ImageDecoder.decodeBase64(interactiveBook.getBookCover());
        bookCover = Bitmap.createBitmap(bookCover, 0, 0, 1000, 1000);
        imgBookThumbnail.setImageBitmap(bookCover);
    }
}