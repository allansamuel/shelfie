package com.shelfie.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.InteractiveBook;
import com.shelfie.ui.fragments.BookThumbnailFragment;
import com.shelfie.ui.fragments.CategoryBooksFragment;
import com.shelfie.ui.fragments.ChildCoinsFragment;
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.utils.CategoryBooksAdapter;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.service.CategoryService;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private CategoryService categoryService;
    private NestedScrollView svCategories;
    private LinearLayout llCategories;
    private ProgressBar progressCategoryBooksList;
    private FragmentContainerView fragmentHomeChildProfileData;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvCategoryBooks;
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

        rvCategoryBooks.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        pageNumber++;
                        getCategories(pageNumber);
                    }
                }
            }
        });
    }

    private void init() {
        View view = getView();
        retrofitConfig = new RetrofitConfig();
        categoryService = retrofitConfig.getCategoryService();
        svCategories = view.findViewById(R.id.sv_categories);
        llCategories = view.findViewById(R.id.ll_categories);
        progressCategoryBooksList = view.findViewById(R.id.progress_categories);
        fragmentHomeChildProfileData = view.findViewById(R.id.fragment_home_child_data_container);
        rvCategoryBooks = view.findViewById(R.id.rv_category_books);

        setFlHomeChildProfileData();
        categories = new ArrayList<>();
        getCategories(pageNumber);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        categoryBooksAdapter = new CategoryBooksAdapter(categories);
        rvCategoryBooks.setLayoutManager(linearLayoutManager);
        rvCategoryBooks.setAdapter(categoryBooksAdapter);
    }


    private void getCategories(int pageNumber) {
        progressCategoryBooksList.setVisibility(View.VISIBLE);
        categoryService.getAll(pageNumber).enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if(response.isSuccessful()) {
                    progressCategoryBooksList.setVisibility(View.GONE);
                    categories.addAll(response.body());
                    categoryBooksAdapter.notifyDataSetChanged();
                }
                progressCategoryBooksList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getActivity().getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressCategoryBooksList.setVisibility(View.GONE);
            }
        });
    }

    private void setFlHomeChildProfileData() {
        ChildProfile childProfile = UserSession.getChildProfile(getContext());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment childCoinsFragment = new ChildCoinsFragment();
        fragmentTransaction.add(fragmentHomeChildProfileData.getId(), childCoinsFragment, childProfile.getNickname());
        fragmentTransaction.commit();
    }
}