package com.shelfie.ui.formChildProfile;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.customViews.ProfileAvatarFragment;

import java.util.ArrayList;
import java.util.List;

public class ChildProfileListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CardView cvAddChildProfile;
    private GuardianUser guardianUser;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ProfileAvatarFragment profileAvatarFragment;
    private GuardianUserService guardianUserService;


    public ChildProfileListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cvAddChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getActivity().getApplicationContext(), FormChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable("GUARDIAN_USER_DATA", guardianUser);
                newIntent.putExtras(newIntentBundle);
                startActivity(newIntent);
            }
        });

        return inflater.inflate(R.layout.fragment_child_profile_list, container, false);
    }

    private void init() {
        cvAddChildProfile = getActivity().findViewById(R.id.cv_add_child_profile);
    }

    private void mapChildProfileAvatars(List<ChildProfile> childProfileList) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(ChildProfile childProfile : childProfileList) {
            profileAvatarFragment = new ProfileAvatarFragment();
            fragmentTransaction.add(profileAvatarFragment, childProfile.getNickname());
        }
        fragmentTransaction.commit();
    }

    private void setChildProfiles() {
        guardianUserService.getChildProfiles().enqueue(new Callback<ArrayList<ChildProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                if(response.isSuccessful()) {
                    mapChildProfileAvatars(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChildProfile>> call, Throwable t) {
            }
        });
    }
}