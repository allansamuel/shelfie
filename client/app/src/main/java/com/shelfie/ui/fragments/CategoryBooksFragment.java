package com.shelfie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class CategoryBooksFragment extends Fragment {

    private static final String ARG_CATEGORY = "CATEGORY_DATA";

    private RetrofitConfig retrofitConfig;
    private CategoryService categoryService;
    private List<InteractiveBook> interactiveBooks;

    private NestedScrollView svCategoryBooksList;
    private Category category;
    private TextView tvCategoryName;
    private LinearLayout llCategoryBooks;
    private ProgressBar progressCategoryBooks;
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
        svCategoryBooksList.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollX == v.getChildAt(0).getMeasuredWidth() - v.getMeasuredWidth()) {
                    pageNumber++;
                    getCategoryBooks(pageNumber);
                }
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        categoryService = retrofitConfig.getCategoryService();
        interactiveBooks = new ArrayList<>();

        View view = getView();
        svCategoryBooksList = view.findViewById(R.id.sv_category_books_list);
        tvCategoryName = view.findViewById(R.id.tv_category_name);
        progressCategoryBooks = view.findViewById(R.id.progress_category_books);

        tvCategoryName.setText(category.getCategoryName());
    }

    private void getCategoryBooks(int pageNumber) {
        categoryService.getInteractiveBooks(pageNumber).enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful()) {
                  interactiveBooks.addAll(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {

            }
        });
    }
}