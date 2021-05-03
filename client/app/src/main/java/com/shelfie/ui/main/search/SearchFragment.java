package com.shelfie.ui.main.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private CategoryService categoryService;
    private List<Category> categories;

    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
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
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        svSearchResults = view.findViewById(R.id.sv_search_results);
    }
}