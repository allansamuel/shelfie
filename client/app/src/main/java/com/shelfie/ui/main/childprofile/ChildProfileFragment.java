package com.shelfie.ui.main.childprofile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.snackbar.Snackbar;
import com.shelfie.R;
import com.shelfie.config.RetrofitConfig;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.service.ChildProfileService;
import com.shelfie.service.GuardianUserService;
import com.shelfie.ui.fragments.ProfileAvatarFragment;
import com.shelfie.utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class ChildProfileFragment extends Fragment {

    private RetrofitConfig retrofitConfig;
    private GuardianUser guardianUser;
    private GuardianUserService guardianUserService;
    private LinearLayout llChildProfilesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        retrofitConfig = new RetrofitConfig();
        guardianUser = UserSession.getGuardianUser(getContext());
        guardianUserService = retrofitConfig.getGuardianUserService();

        llChildProfilesList = getView().findViewById(R.id.ll_child_profiles_list);
        mapChildProfiles(guardianUser.getChildProfiles());
    }


    private void mapChildProfiles(List<ChildProfile> childProfiles) {
        FragmentTransaction childProfileTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        for(ChildProfile childProfile : childProfiles) {
            Fragment profileAvatarFragment = ProfileAvatarFragment.newInstance(childProfile);
            childProfileTransaction.add(R.id.ll_child_profiles_list, profileAvatarFragment, childProfile.getNickname());
        }
        childProfileTransaction.commit();
    }

}