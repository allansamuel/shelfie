package com.shelfie.ui.main.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.Category;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.CategoryService;
import com.shelfie.service.InteractiveBookService;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;
    private List<InteractiveBook> categories;

    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
    private TextView tvSearchEmptyState;
    private ScrollView svSearchResults;

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
                getInteractiveBooksResult((String) charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        categories = new ArrayList<>();

        View view = getView();
        assert view != null;
        txtSearchBook = view.findViewById(R.id.txt_search_book);
        etSearchBook = view.findViewById(R.id.et_search_book);
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        svSearchResults = view.findViewById(R.id.sv_search_results);
    }

    private void getInteractiveBooksResult(String searchedTitle) {
        interactiveBookService.getAll(1).enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {

            }
        });
    }
}