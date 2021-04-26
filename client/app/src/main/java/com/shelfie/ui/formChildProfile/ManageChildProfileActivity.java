package com.shelfie.ui.formChildProfile;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.snackbar.Snackbar;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.customViews.ProfileAvatarFragment;

import java.util.ArrayList;
import java.util.List;

public class ManageChildProfileActivity extends FragmentActivity {

    private Bundle prevBundle;
    private GuardianUser guardianUser;

    private RetrofitConfig retrofitConfig;
    private GuardianUserService guardianUserService;

    private FlexboxLayout flexboxChildProfiles;
    private FragmentTransaction fragmentTransaction;
    private Fragment addProfileAvatarFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);
        init();
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

        prevBundle = getIntent().getExtras();
        guardianUser = (GuardianUser) prevBundle.getSerializable("GUARDIAN_USER_DATA");

        flexboxChildProfiles = findViewById(R.id.flexbox_child_profiles);
        addProfileAvatarFragment = new ProfileAvatarFragment();
        Bundle addProfileAvatarArgs = new Bundle();
        addProfileAvatarArgs.putSerializable("GUARDIAN_USER", guardianUser);
        addProfileAvatarFragment.setArguments(addProfileAvatarArgs);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flexbox_child_profiles, addProfileAvatarFragment, null);
        fragmentTransaction.commit();

        setChildProfiles();
    }

    private void mapChildProfileAvatars(List<ChildProfile> childProfileList) {
        FragmentTransaction childProfileTransaction = getSupportFragmentManager().beginTransaction();
        for(int index = 0; index < childProfileList.size(); index++) {
            System.out.println(guardianUser);
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(
                    guardianUser,
                    childProfileList.get(index),
                    true);
            childProfileTransaction.add(R.id.flexbox_child_profiles, profileAvatarFragment, String.valueOf(index));
        }
        childProfileTransaction.commit();
    }

    private void setChildProfiles() {
        if(guardianUser != null) {
            guardianUserService.getChildProfiles(guardianUser.getGuardianUserId())
                .enqueue(new Callback<ArrayList<ChildProfile>>() {
                @Override
                public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                    if(response.isSuccessful()) {
                        mapChildProfileAvatars(response.body());
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ChildProfile>> call, Throwable t) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

}