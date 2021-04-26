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
    private CardView cvAddChildProfile;
    private FragmentTransaction fragmentTransaction;
    private ProfileAvatarFragment[] fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_child_profile);

        init();

        cvAddChildProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getApplicationContext(), FormChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable("GUARDIAN_USER_DATA", guardianUser);
                newIntent.putExtras(newIntentBundle);
                startActivity(newIntent);
            }
        });
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUserService = retrofitConfig.getGuardianUserService();

        prevBundle = getIntent().getExtras();
        guardianUser = (GuardianUser) prevBundle.getSerializable("NEW_GUARDIAN_USER_DATA");

        flexboxChildProfiles = findViewById(R.id.flexbox_child_profiles);
        cvAddChildProfile = findViewById(R.id.cv_add_child_profile);

        setChildProfiles();
    }

    private void mapChildProfileAvatars(List<ChildProfile> childProfileList) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragments = new ProfileAvatarFragment[childProfileList.size()];
        for(int index = 0; index < fragments.length; index++) {
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setId(index);
            flexboxChildProfiles.addView(frameLayout);
            fragmentTransaction.add(R.id.child_avatar_container, fragments[index]);
        }
        fragmentTransaction.commit();
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