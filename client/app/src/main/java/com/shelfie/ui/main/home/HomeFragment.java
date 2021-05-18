package com.shelfie.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.flexbox.FlexboxLayout;
import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.InteractiveBookService;
import com.shelfie.ui.fragments.BookThumbnailFragment;
import com.shelfie.ui.fragments.ChildCoinsFragment;
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private InteractiveBookService interactiveBookService;
    private NestedScrollView svInteractiveBooks;
    private FlexboxLayout flexboxInteractiveBooks;
    private ProgressBar progressInteractiveBooks;
    private FragmentContainerView fragmentHomeChildProfileData;
    int pageNumber = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        svInteractiveBooks.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pageNumber++;
                    getInteractiveBooks(pageNumber);
                }
            }
        });
    }

    private void init() {
        View view = getView();
        retrofitConfig = new RetrofitConfig();
        interactiveBookService = retrofitConfig.getInteractiveBookService();
        svInteractiveBooks = view.findViewById(R.id.sv_interactive_books);
        flexboxInteractiveBooks = view.findViewById(R.id.flexbox_interactive_books);
        progressInteractiveBooks = view.findViewById(R.id.progress_interactive_books);
        fragmentHomeChildProfileData = view.findViewById(R.id.fragment_home_child_data_container);
        setFlHomeChildProfileData();
        getInteractiveBooks(pageNumber);
    }

    private void mapInteractiveBooks(ArrayList<InteractiveBook> interactiveBooks) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(InteractiveBook interactiveBook : interactiveBooks) {
            Fragment fragment = BookThumbnailFragment.newInstance(interactiveBook);
            fragmentTransaction.add(flexboxInteractiveBooks.getId(), fragment);
        }
        fragmentTransaction.commit();
    }

    private void getInteractiveBooks(int pageNumber) {
        progressInteractiveBooks.setVisibility(View.VISIBLE);
        interactiveBookService.getAll(pageNumber).enqueue(new Callback<ArrayList<InteractiveBook>>() {
            @Override
            public void onResponse(Call<ArrayList<InteractiveBook>> call, Response<ArrayList<InteractiveBook>> response) {
                if(response.isSuccessful()) {
                    mapInteractiveBooks(response.body());
                }
                progressInteractiveBooks.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<InteractiveBook>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getActivity().getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressInteractiveBooks.setVisibility(View.GONE);
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