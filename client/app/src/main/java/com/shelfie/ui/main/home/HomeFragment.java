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
    int pageNumber = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        svCategories.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pageNumber++;
                    getCategories(pageNumber);
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
        setFlHomeChildProfileData();

        getCategories(pageNumber);
    }

    private void mapCategories(List<Category> categoryList) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(Category category : categoryList) {
            Fragment fragment = CategoryBooksFragment.newInstance(category);
            fragmentTransaction.add(llCategories.getId(), fragment);
        }
        fragmentTransaction.commit();
    }

    private void getCategories(int pageNumber) {
        progressCategoryBooksList.setVisibility(View.VISIBLE);
        categoryService.getAll(pageNumber).enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if(response.isSuccessful()) {
                    mapCategories(response.body());
                }
                progressCategoryBooksList.setVisibility(View.GONE);
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