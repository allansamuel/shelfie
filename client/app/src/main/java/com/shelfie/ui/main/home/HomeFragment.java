package com.shelfie.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.service.CategoryService;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private CategoryService categoryService;
    private NestedScrollView svHomeContainer;
    private RecyclerView rvCategoryBooks;
    private ProgressBar progressCategoryBooksList;
    private ArrayList<Category> categories;
    private CategoryBooksAdapter categoryBooksAdapter;
    int pageNumber = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        svHomeContainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pageNumber++;
                    progressCategoryBooksList.setVisibility(View.VISIBLE);
                    getCategories(pageNumber);
                }
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        categoryService = retrofitConfig.getCategoryService();
        svHomeContainer = getView().findViewById(R.id.sv_home_container);
        rvCategoryBooks = getView().findViewById(R.id.rv_category_books);
        progressCategoryBooksList = getView().findViewById(R.id.progress_category_books_list);
        categories = new ArrayList<>();

        getCategories(pageNumber);
        categoryBooksAdapter = new CategoryBooksAdapter(getActivity(), categories);
        rvCategoryBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategoryBooks.setAdapter(categoryBooksAdapter);
    }

    private void getCategories(int pageNumber) {
        categoryService.getAll(pageNumber).enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    progressCategoryBooksList.setVisibility(View.GONE);
                    categories.addAll(response.body());
                    categoryBooksAdapter = new CategoryBooksAdapter(getActivity(), categories);
                    rvCategoryBooks.setAdapter(categoryBooksAdapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });
    }
}