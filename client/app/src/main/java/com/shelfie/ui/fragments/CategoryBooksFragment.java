package com.shelfie.ui.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.model.InteractiveBook;

import java.util.ArrayList;
import java.util.List;

public class CategoryBooksFragment extends Fragment {

    private static final String ARG_CATEGORY = "CATEGORY_DATA";

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;

    private Category category;
    private TextView tvCategoryName;
    private HorizontalScrollView svCategoryBooks;
    private LinearLayout llCategoryBooks;
    private ProgressBar progressBookThumbnails;
    private int pageNumber = 1;

    public CategoryBooksFragment() {
    }

    public static CategoryBooksFragment newInstance(Category category) {
        CategoryBooksFragment fragment = new CategoryBooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        svCategoryBooks.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if(i == (llCategoryBooks.getMeasuredWidth() - view.getMeasuredWidth())) {
                    pageNumber++;
                    getCategoryBooks(pageNumber);
                }
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();

        View view = getView();
        tvCategoryName = view.findViewById(R.id.tv_category_name);
        progressBookThumbnails = view.findViewById(R.id.progress_book_thumbnails);
        svCategoryBooks = view.findViewById(R.id.sv_category_books);
        llCategoryBooks = view.findViewById(R.id.ll_category_books);

        tvCategoryName.setText(category.getCategoryName());

        getCategoryBooks(pageNumber);
    }

    private void mapCategoryBooks(List<InteractiveBook> interactiveBooks) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(InteractiveBook interactiveBook : interactiveBooks) {
            Fragment fragment = BookThumbnailFragment.newInstance(interactiveBook);
            fragmentTransaction.add(llCategoryBooks.getId(), fragment);
        }
        fragmentTransaction.commit();
    }

    private void getCategoryBooks(int pageNumber) {
        interactiveBookService.getByCategories(category.getCategoryId(), pageNumber)
                .enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful()) {
                    mapCategoryBooks(response.body());
                }
                progressBookThumbnails.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getActivity().getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressBookThumbnails.setVisibility(View.GONE);
            }
        });
    }
}