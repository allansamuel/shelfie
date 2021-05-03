package com.shelfie.ui.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.Category;

public class CategorySelectorFragment extends Fragment {

    private static final String ARG_CATEGORY = "CATEGORY_DATA";

    private Category category;

    private CardView cvCategorySelector;
    private ImageView categoryIcon;
    private TextView categoryName;

    public CategorySelectorFragment() {
    }

    public static CategorySelectorFragment newInstance(Category category) {
        CategorySelectorFragment fragment = new CategorySelectorFragment();
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
        init();
        return inflater.inflate(R.layout.fragment_category_selector, container, false);
    }

    private void init() {
        View view = getView();
        assert view != null;
        cvCategorySelector = view.findViewById(R.id.cv_category_selector);
        categoryIcon = view.findViewById(R.id.img_category_icon);
        categoryName = view.findViewById(R.id.tv_category_name);
    }
}