package com.shelfie.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shelfie.R;
import com.shelfie.ui.login.LoginActivity;
import com.shelfie.ui.userregister.FormGuardianUserActivity;
import com.shelfie.ui.userregister.ManageChildProfileActivity;
import com.shelfie.utils.UserSession;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetLayout extends BottomSheetDialogFragment {

    private Button btnEditGuardianUser;
    private Button btnEditChildProfiles;
    private Button btnLogoff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        btnLogoff.setOnClickListener(view1 -> {
            UserSession.clearSession(getActivity().getApplicationContext());
            getActivity().finish();
            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        btnEditGuardianUser.setOnClickListener(view12 -> {
            UserSession.setFormInteractionMode(getActivity().getApplicationContext(), UserSession.EDIT_MODE);
            getActivity().finish();
            Intent intent = new Intent(requireActivity().getApplicationContext(), FormGuardianUserActivity.class);
            startActivity(intent);
        });

        btnEditChildProfiles.setOnClickListener(view13 -> {
            UserSession.setFormInteractionMode(getActivity().getApplicationContext(), UserSession.REGISTER_MODE);
            getActivity().finish();
            Intent intent = new Intent(requireActivity().getApplicationContext(), ManageChildProfileActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        View view = getView();
        btnEditGuardianUser = view.findViewById(R.id.btn_edit_guardian_user);
        btnEditChildProfiles = view.findViewById(R.id.btn_edit_child_profiles);
        btnLogoff = view.findViewById(R.id.btn_logoff);
    }
}
