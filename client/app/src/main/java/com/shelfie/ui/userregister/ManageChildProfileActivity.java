package com.shelfie.ui.userregister;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.snackbar.Snackbar;
import com.shelfie.R;
import com.shelfie.ui.fragments.EmptyStateDialogFragment;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.fragments.BottomSheetLayout;
import com.shelfie.ui.fragments.ProfileAvatarFragment;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;

public class ManageChildProfileActivity extends FragmentActivity {

    private RetrofitConfig retrofitConfig;
    private GuardianUser guardianUser;
    private GuardianUserService guardianUserService;
    private ArrayList<ChildProfile> childProfiles;

    private ConstraintLayout clAddChildProfileContainer;
    private FlexboxLayout flexboxChildProfiles;
    private CardView cvAddChildProfile;
    private Button btnSaveUserProfiles;
    private Button btnUserSettings;
    private ProgressBar progressChildProfiles;
    private TextView tvTitleManageChildProfiles;
    private TextView tvSubtitleManageChildProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();

        cvAddChildProfile.setOnClickListener(view -> {
            addChildProfile();
        });

        btnSaveUserProfiles.setOnClickListener(view -> {
                saveAndAuthenticateUser();

        });

        btnUserSettings.setOnClickListener(view -> {
            BottomSheetLayout bottomSheetLayout = new BottomSheetLayout();
            bottomSheetLayout.show(getSupportFragmentManager(), getString(R.string.settings));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateData();
    }

    private void init() {
        guardianUser = UserSession.getGuardianUser(getApplicationContext());
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

        clAddChildProfileContainer = findViewById(R.id.cl_add_child_profile_container);
        flexboxChildProfiles = findViewById(R.id.flexbox_child_profiles);
        cvAddChildProfile = findViewById(R.id.cv_add_child_profile);
        btnSaveUserProfiles = findViewById(R.id.btn_save_user_profiles);
        btnUserSettings = findViewById(R.id.btn_user_settings);
        progressChildProfiles = findViewById(R.id.progress_child_profiles);
        tvTitleManageChildProfiles = findViewById(R.id.tv_title_manage_child_profiles);
        tvSubtitleManageChildProfiles = findViewById(R.id.tv_subtitle_manage_child_profile);

        if(UserSession.isFormInReadMode(getApplicationContext())) {
            tvTitleManageChildProfiles.setText(getString(R.string.title_manage_child_profile_read));
            tvSubtitleManageChildProfiles.setText(getString(R.string.subtitle_manage_child_profile_read));
        }
    }

    private void populateData() {
        getChildProfiles();
    }

    private void addChildProfile() {
        UserSession.setFormInteractionMode(getApplicationContext(), UserSession.REGISTER_MODE);
        Intent createChildProfileIntent = new Intent(getApplicationContext(), FormChildProfileActivity.class);
        startActivity(createChildProfileIntent);
    }

    private void mapChildProfiles(ArrayList<ChildProfile> childProfiles) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for(ChildProfile childProfile : childProfiles) {
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(childProfile);
            fragmentTransaction.add(flexboxChildProfiles.getId(), profileAvatarFragment, childProfile.getNickname());
        }
        fragmentTransaction.commit();

        if(UserSession.isFormInReadMode(getApplicationContext())) {
            btnUserSettings.setVisibility(View.VISIBLE);
            btnSaveUserProfiles.setVisibility(View.INVISIBLE);
        } else {
            if(childProfiles.size() < 6) {
                btnSaveUserProfiles.setVisibility(View.VISIBLE);
                clAddChildProfileContainer.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getChildProfiles() {
        progressChildProfiles.setVisibility(View.VISIBLE);
        guardianUserService.getChildProfiles(guardianUser.getGuardianUserId())
            .enqueue(new Callback<ArrayList<ChildProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                if(response.isSuccessful()) {
                    if(response.body().isEmpty()){
                        clAddChildProfileContainer.setVisibility(View.VISIBLE);
                        btnSaveUserProfiles.setVisibility(View.INVISIBLE);
                    }else{
                        mapChildProfiles(response.body());
                    }
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "caiu aqui", Snackbar.LENGTH_LONG).show();
                }
                progressChildProfiles.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<ChildProfile>> call, Throwable t) {
                EmptyStateDialogFragment emptyStateDialogFragment = new EmptyStateDialogFragment();
                emptyStateDialogFragment.show(getSupportFragmentManager(), "EmptyStateDialogFragment");
                progressChildProfiles.setVisibility(View.GONE);
            }
        });

    }

    private void saveAndAuthenticateUser() {
        UserSession.setFormInteractionMode(getApplicationContext(), UserSession.READ_MODE);
        Intent newIntent = new Intent(getApplicationContext(), ManageChildProfileActivity.class);
        startActivity(newIntent);
    }
}