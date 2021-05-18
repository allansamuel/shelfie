package com.shelfie.ui.main.search;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shelfie.R;
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.ui.fragments.BookThumbnailFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;

    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
    private TextView tvSearchEmptyState;
    private NestedScrollView svSearchBookList;
    private FlexboxLayout flexboxSearchEmptyState;
    private FlexboxLayout flexboxSearchBookList;
    private ProgressBar progressSearchBook;
    private ProgressBar progressSearchNextPage;
    private Handler inputTypingHandler;
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
                inputTypingHandler.removeCallbacksAndMessages(null);
                inputTypingHandler.postDelayed(userStoppedTyping, 2000);
            }

            Runnable userStoppedTyping = new Runnable() {
                @Override
                public void run() {
                    flexboxSearchBookList.removeAllViews();
                    pageNumber = 1;
                    String searchedTitle = etSearchBook.getText().toString();
                    if(searchedTitle.trim() != "" && searchedTitle.length() >= 3) {
                        getInteractiveBooksResult(searchedTitle, pageNumber);
                    }
                }
            };
        });

        svSearchBookList.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                pageNumber++;
                progressSearchNextPage.setVisibility(View.VISIBLE);
                getInteractiveBooksResult(etSearchBook.getText().toString(), pageNumber);
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        inputTypingHandler = new Handler();

        View view = getView();
        assert view != null;
        txtSearchBook = view.findViewById(R.id.txt_search_book);
        etSearchBook = view.findViewById(R.id.et_search_book);
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        flexboxSearchBookList = view.findViewById(R.id.flexbox_search_book_list);
        flexboxSearchEmptyState = view.findViewById(R.id.flexbox_search_empty_state);
        progressSearchBook = view.findViewById(R.id.progress_search_book);
        progressSearchNextPage = view.findViewById(R.id.progress_search_next_page);
        svSearchBookList = view.findViewById(R.id.sv_search_book_list);
    }

    private void mapSearchResult(List<InteractiveBook> interactiveBooks) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(InteractiveBook interactiveBook : interactiveBooks) {
            Fragment fragment = BookThumbnailFragment.newInstance(interactiveBook);
            fragmentTransaction.add(flexboxSearchBookList.getId(), fragment);
        }
        fragmentTransaction.commit();
    }

    private void getInteractiveBooksResult(String searchedTitle, int pageNumber) {
        flexboxSearchEmptyState.setVisibility(View.GONE);
        progressSearchBook.setVisibility(View.VISIBLE);

        interactiveBookService.getByTitleOrCategory(searchedTitle.trim(), pageNumber)
                .enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful()) {
                    mapSearchResult(response.body());
                    svSearchBookList.setVisibility(View.VISIBLE);
                } else {
                    flexboxSearchEmptyState.setVisibility(View.VISIBLE);
                    svSearchBookList.setVisibility(View.GONE);

                    tvSearchEmptyState.setText(getString(R.string.empty_state_book_search_not_found));
                    tvSearchEmptyState.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, 0, R.drawable.cherry_list_is_empty);
                }
                progressSearchNextPage.setVisibility(View.INVISIBLE);
                progressSearchBook.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getActivity().getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressSearchNextPage.setVisibility(View.INVISIBLE);
                progressSearchBook.setVisibility(View.INVISIBLE);
            }
        });
    }
}