package com.shelfie.ui.main.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.utils.BookAdapter;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.ui.fragments.BookThumbnailFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;
    private ArrayList<InteractiveBook> interactiveBooks;

    private BookAdapter bookAdapter;
    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
    private TextView tvSearchEmptyState;
    private NestedScrollView svSearchResults;
    private FlexboxLayout flexboxSearchEmptyState;
    private FlexboxLayout flexboxSearchResults;
    private RecyclerView rvSearchResults;
    private FlexboxLayoutManager flexboxLayoutManager;
    private ProgressBar progressSearchBook;
    private int pageNumber = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        etSearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getInteractiveBooksResult(editable.toString(), pageNumber);
            }
        });

        svSearchResults.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pageNumber++;
                    getInteractiveBooksResult(tvSearchEmptyState.getText().toString(), pageNumber);
                }
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        interactiveBooks = new ArrayList<>();

        View view = getView();
        assert view != null;
        txtSearchBook = view.findViewById(R.id.txt_search_book);
        etSearchBook = view.findViewById(R.id.et_search_book);
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        svSearchResults = view.findViewById(R.id.sv_search_results);
        flexboxSearchEmptyState = view.findViewById(R.id.flexbox_search_empty_state);
        flexboxSearchResults = view.findViewById(R.id.flexbox_search_results);
        rvSearchResults = view.findViewById(R.id.rv_search_results);
        progressSearchBook = view.findViewById(R.id.progress_search_book);

        bookAdapter = new BookAdapter(interactiveBooks);
        flexboxLayoutManager = new FlexboxLayoutManager(getActivity().getApplicationContext());
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        rvSearchResults.setAdapter(bookAdapter);
        rvSearchResults.setLayoutManager(flexboxLayoutManager);
    }

    private void getInteractiveBooksResult(String searchedTitle, int pageNumber) {
        flexboxSearchEmptyState.setVisibility(View.GONE);
        progressSearchBook.setVisibility(View.VISIBLE);
        interactiveBooks.clear();
        interactiveBookService.getByTitle(searchedTitle, pageNumber).enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                interactiveBooks.clear();
                if(response.isSuccessful()) {
                    interactiveBooks.addAll(response.body());
                    rvSearchResults.setLayoutManager(flexboxLayoutManager);
                    rvSearchResults.setAdapter(bookAdapter);
                    svSearchResults.setVisibility(View.VISIBLE);
                }
                progressSearchBook.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {
                progressSearchBook.setVisibility(View.INVISIBLE);
            }
        });
    }
}