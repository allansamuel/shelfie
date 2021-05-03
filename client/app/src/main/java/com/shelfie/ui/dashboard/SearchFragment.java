package com.shelfie.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.model.ChildProfile;
import com.shelfie.service.CategoryService;
import com.shelfie.ui.fragments.CategorySelectorFragment;
import com.shelfie.ui.fragments.ProfileAvatarFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private CategoryService categoryService;
    private List<Category> categories;

    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
    private LinearLayout llCategorySelectorContainer;
    private TextView tvSearchEmptyState;
    private ScrollView svSearchResults;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        init();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        categoryService = retrofitConfig.getCategoryService();
        categories = new ArrayList<>();

        View view = getView();
        assert view != null;
        txtSearchBook = view.findViewById(R.id.txt_search_book);
        etSearchBook = view.findViewById(R.id.et_search_book);
        llCategorySelectorContainer = view.findViewById(R.id.ll_category_selector_container);
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        svSearchResults = view.findViewById(R.id.sv_search_results);

        getCategories();
    }

    private void getCategories() {
        categoryService.getAll().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if(response.isSuccessful()) {
                    categories.addAll(response.body());
                    mapCategorySelectors();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {

            }
        });
    }

    private void mapCategorySelectors() {
        FragmentTransaction childProfileTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(Category category : categories) {
            Fragment categorySelectorFragment = CategorySelectorFragment.newInstance(category);
            childProfileTransaction.add(R.id.flexbox_child_profiles, categorySelectorFragment, category.getCategoryName());
        }
        childProfileTransaction.commit();
    }
}