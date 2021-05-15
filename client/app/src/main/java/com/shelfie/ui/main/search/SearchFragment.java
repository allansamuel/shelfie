package com.shelfie.ui.main.search;

import android.os.Bundle;
import android.os.Handler;
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
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.utils.BookAdapter;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.ui.fragments.BookThumbnailFragment;

import java.util.ArrayList;
import java.util.List;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class SearchFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;
    private ArrayList<InteractiveBook> interactiveBooks;

    private BookAdapter bookAdapter;
    private TextInputLayout txtSearchBook;
    private TextInputEditText etSearchBook;
    private TextView tvSearchEmptyState;
    private FlexboxLayout flexboxSearchEmptyState;
    private RecyclerView rvSearchResults;
    private FlexboxLayoutManager flexboxLayoutManager;
    private ProgressBar progressSearchBook;
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
                    String searchedTitle = etSearchBook.getText().toString();
                    interactiveBooks.clear();
                    flexboxSearchEmptyState.setVisibility(View.VISIBLE);

                    if(searchedTitle.trim() != "" && searchedTitle.length() >= 3) {
                        getInteractiveBooksResult(searchedTitle, pageNumber);
                    }
                }
            };
        });

        rvSearchResults.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    int visibleItemCount = flexboxLayoutManager.getChildCount();
                    int totalItemCount = flexboxLayoutManager.getItemCount();
                    int pastVisibleItems = flexboxLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        pageNumber++;
                        getInteractiveBooksResult(etSearchBook.getText().toString(), pageNumber);
                    }

                }
            }
        });

        bookAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        interactiveBooks = new ArrayList<>();
        inputTypingHandler = new Handler();

        View view = getView();
        assert view != null;
        txtSearchBook = view.findViewById(R.id.txt_search_book);
        etSearchBook = view.findViewById(R.id.et_search_book);
        tvSearchEmptyState = view.findViewById(R.id.tv_search_empty_state);
        flexboxSearchEmptyState = view.findViewById(R.id.flexbox_search_empty_state);
        rvSearchResults = view.findViewById(R.id.rv_search_results);
        progressSearchBook = view.findViewById(R.id.progress_search_book);

        bookAdapter = new BookAdapter(interactiveBooks);
        flexboxLayoutManager = new FlexboxLayoutManager(getActivity().getApplicationContext());
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
        rvSearchResults.setAdapter(bookAdapter);
        rvSearchResults.setLayoutManager(flexboxLayoutManager);
    }

    private void getInteractiveBooksResult(String searchedTitle, int pageNumber) {
        progressSearchBook.setVisibility(View.VISIBLE);
        interactiveBookService.getByTitle(searchedTitle.trim(), pageNumber)
                .enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful() && !response.body().isEmpty()) {
                    interactiveBooks.addAll(response.body());
                    bookAdapter.notifyDataSetChanged();

                    flexboxSearchEmptyState.setVisibility(View.GONE);
                    rvSearchResults.setVisibility(View.VISIBLE);
                } else {
                    flexboxSearchEmptyState.setVisibility(View.VISIBLE);
                    rvSearchResults.setVisibility(View.GONE);
                    tvSearchEmptyState.setText(getString(R.string.empty_state_book_search_not_found));
                    tvSearchEmptyState.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, 0, R.drawable.cherry_list_is_empty);
                }
                progressSearchBook.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getActivity().getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressSearchBook.setVisibility(View.INVISIBLE);
            }
        });

    }
}