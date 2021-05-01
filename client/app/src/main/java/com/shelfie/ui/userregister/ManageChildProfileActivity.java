package com.shelfie.ui.userregister;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.fragments.ProfileAvatarFragment;
import com.shelfie.utils.ApplicationStateManager;

import java.util.ArrayList;
import java.util.List;

public class ManageChildProfileActivity extends FragmentActivity {

    private ApplicationStateManager applicationStateManager;
    private RetrofitConfig retrofitConfig;
    private GuardianUser guardianUser;
    private List<ChildProfile> childProfiles;
    private GuardianUserService guardianUserService;
    private FragmentTransaction fragmentTransaction;
    private Button btnSaveUserProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();

        btnSaveUserProfiles.setOnClickListener(view -> {
            saveAndAuthenticateUser();
        });
    }

    private void init() {
        applicationStateManager = new ApplicationStateManager();
        guardianUser = applicationStateManager.getCurrentGuardianUser() != null ?
                applicationStateManager.getCurrentGuardianUser() : new GuardianUser();

        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

        Fragment addProfileAvatarFragment = new ProfileAvatarFragment();
        Bundle addProfileAvatarArgs = new Bundle();
        addProfileAvatarArgs.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
        addProfileAvatarFragment.setArguments(addProfileAvatarArgs);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.add_profile_avatar_container, addProfileAvatarFragment, null);
        fragmentTransaction.commit();

        getChildProfiles();

        btnSaveUserProfiles = findViewById(R.id.btn_save_user_profiles);
        if(applicationStateManager.getFormInteractionMode() == ApplicationStateManager.EDIT_MODE && childProfiles.size() > 0) {
            btnSaveUserProfiles.setVisibility(View.VISIBLE);
        }
    }

    private void mapChildProfiles() {
        FragmentTransaction childProfileTransaction = getSupportFragmentManager().beginTransaction();
        for(ChildProfile childProfile : childProfiles) {
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(
                    guardianUser,
                    childProfile,
                    applicationStateManager.getFormInteractionMode());
            childProfileTransaction.add(R.id.flexbox_child_profiles, profileAvatarFragment, childProfile.getNickname());
        }
        childProfileTransaction.commit();
    }

    private void getChildProfiles() {
        childProfiles = new ArrayList<>();
        guardianUserService.getChildProfiles(guardianUser.getGuardianUserId())
            .enqueue(new Callback<ArrayList<ChildProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                if(response.isSuccessful()) {
                    childProfiles.addAll(response.body());
                    mapChildProfiles();
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "caiu aqui", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChildProfile>> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void saveAndAuthenticateUser() {
        Intent newIntent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
        applicationStateManager.setFormInteractionMode(ApplicationStateManager.READ_MODE);
        startActivity(newIntent);
    }
}