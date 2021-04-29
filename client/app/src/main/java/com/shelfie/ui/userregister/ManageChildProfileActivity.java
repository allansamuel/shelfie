package com.shelfie.ui.userregister;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.flexbox.FlexboxLayout;
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

    private GuardianUser guardianUser;
    private GuardianUserService guardianUserService;
    private Button btnSaveUserProfiles;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();

        btnSaveUserProfiles.setOnClickListener(view -> {
            createGuardianUser();
        });
    }

    private void init() {
        RetrofitConfig retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

        Bundle previousBundle = getIntent().getExtras();
        guardianUser = (GuardianUser) previousBundle.getSerializable(getString(R.string.bundle_guardian_user));

        btnSaveUserProfiles = findViewById(R.id.btn_save_user_profiles);
        Fragment addProfileAvatarFragment = new ProfileAvatarFragment();
        Bundle addProfileAvatarArgs = new Bundle();
        addProfileAvatarArgs.putSerializable("GUARDIAN_USER", guardianUser);
        addProfileAvatarFragment.setArguments(addProfileAvatarArgs);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.add_profile_avatar_container, addProfileAvatarFragment, null);
        fragmentTransaction.commit();

        getChildProfiles();
    }

    private void mapChildProfiles(List<ChildProfile> childProfileList) {
        FragmentTransaction childProfileTransaction = getSupportFragmentManager().beginTransaction();
        for(int index = 0; index < childProfileList.size(); index++) {
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(
                    guardianUser,
                    childProfileList.get(index),
                    true);
            childProfileTransaction.add(R.id.flexbox_child_profiles, profileAvatarFragment, String.valueOf(index));
        }
        childProfileTransaction.commit();
    }

    private void getChildProfiles() {
        guardianUserService.getChildProfiles(guardianUser.getGuardianUserId())
            .enqueue(new Callback<ArrayList<ChildProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                if(response.isSuccessful()) {
                    mapChildProfiles(response.body());
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

    private void createGuardianUser() {
        guardianUserService.create(guardianUser).enqueue(new Callback<GuardianUser>() {
            @Override
            public void onResponse(Call<GuardianUser> call, Response<GuardianUser> response) {
                if(response.isSuccessful()) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "BOA!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GuardianUser> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "nao rolou", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}