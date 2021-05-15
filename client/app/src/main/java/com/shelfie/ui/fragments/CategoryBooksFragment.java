package com.shelfie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.utils.BookAdapter;
import com.shelfie.utils.CategoryBooksAdapter;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.CategoryService;

import java.util.ArrayList;

public class CategoryBooksFragment extends Fragment {

    private static final String ARG_CATEGORY = "CATEGORY_DATA";

    private BookAdapter bookAdapter;

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;
    private ArrayList<InteractiveBook> interactiveBooks;

    private Category category;
    private TextView tvCategoryName;
    private LinearLayout llCategoryBooks;
    private RecyclerView rvBookThumbnails;
    private ProgressBar progressBookThumbnails;
    private LinearLayoutManager linearLayoutManager;
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

        rvBookThumbnails.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {
                    //check for scroll down
                     int visibleItemCount = linearLayoutManager.getChildCount();
                     int totalItemCount = linearLayoutManager.getItemCount();
                     int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                     if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                         pageNumber++;
                         getCategoryBooks(pageNumber);
                     }
                }

            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        interactiveBooks = new ArrayList<>();

        View view = getView();
        tvCategoryName = view.findViewById(R.id.tv_category_name);
        rvBookThumbnails = view.findViewById(R.id.rv_book_thumbnails);
        progressBookThumbnails = view.findViewById(R.id.progress_book_thumbnails);

        tvCategoryName.setText(category.getCategoryName());

        getCategoryBooks(pageNumber);
        bookAdapter = new BookAdapter(interactiveBooks);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvBookThumbnails.setLayoutManager(linearLayoutManager);
        rvBookThumbnails.setAdapter(bookAdapter);
    }

    private void getCategoryBooks(int pageNumber) {
        interactiveBookService.getByCategories(category.getCategoryId(), pageNumber)
                .enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful()) {
                    progressBookThumbnails.setVisibility(View.GONE);
                    interactiveBooks.addAll(response.body());
                    rvBookThumbnails.setAdapter(bookAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {

            }
        });
    }
}