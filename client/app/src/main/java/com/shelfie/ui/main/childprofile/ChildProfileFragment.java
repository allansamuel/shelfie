package com.shelfie.ui.main.childprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.shelfie.R;
import com.shelfie.ui.dialogs.SettingsBottomDialog;
import com.shelfie.ui.user.childprofile.ChildCoinsFragment;
import com.shelfie.ui.dialogs.CustomDialogFragment;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.user.childprofile.ProfileAvatarFragment;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class ChildProfileFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private GuardianUser guardianUser;
    private GuardianUserService guardianUserService;
    private LinearLayout llChildProfilesList;
    private ProgressBar progressCurrentChildProfiles;
    private Button btnProfileSettings;
    private FragmentContainerView fragmentChildProfileChildDataContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        btnProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsBottomDialog settingsBottomDialog = new SettingsBottomDialog();
                settingsBottomDialog.show(getActivity().getSupportFragmentManager(), getString(R.string.settings));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUser = UserSession.getGuardianUser(getContext());
        guardianUserService = retrofitConfig.getGuardianUserService();

        fragmentChildProfileChildDataContainer = getView().findViewById(R.id.fragment_child_profile_child_data_container);

        View view = getView();
        btnProfileSettings = view.findViewById(R.id.btn_profile_settings);
        llChildProfilesList = view.findViewById(R.id.ll_child_profiles_list);
        progressCurrentChildProfiles = view.findViewById(R.id.progress_current_child_profiles);
    }

    private void populateData() {
        setFrProfileChildData();
        getChildProfiles();
    }

    private void mapChildProfiles(List<ChildProfile> childProfiles) {
        llChildProfilesList.removeAllViews();
        FragmentTransaction childProfileTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(ChildProfile childProfile : childProfiles) {
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(childProfile);
            childProfileTransaction.add(llChildProfilesList.getId(), profileAvatarFragment, childProfile.getNickname());
        }
        childProfileTransaction.commit();
    }


    private void getChildProfiles() {
        progressCurrentChildProfiles.setVisibility(View.VISIBLE);
        guardianUserService.getChildProfiles(guardianUser.getGuardianUserId())
                .enqueue(new Callback<ArrayList<ChildProfile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ChildProfile>> call, Response<ArrayList<ChildProfile>> response) {
                        if(response.isSuccessful()) {
                            mapChildProfiles(response.body());
                        }
                        progressCurrentChildProfiles.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ChildProfile>> call, Throwable t) {
                        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                        customDialogFragment.buildDialog(getString(R.string.dialog_server_connection));
                        customDialogFragment.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_tag));
                        progressCurrentChildProfiles.setVisibility(View.GONE);
                    }
                });

    }

    private void setFrProfileChildData(){
        ChildProfile childProfile = UserSession.getChildProfile(getContext());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment currentProfileAvatarFragment = new ChildCoinsFragment();
        fragmentTransaction.add(
                fragmentChildProfileChildDataContainer.getId(),
                currentProfileAvatarFragment,
                childProfile.getNickname());
        fragmentTransaction.commit();
    }
}