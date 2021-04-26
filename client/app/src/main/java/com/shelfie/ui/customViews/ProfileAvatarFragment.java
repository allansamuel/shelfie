package com.shelfie.ui.customViews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shelfie.R;
import com.shelfie.config.ImageDecoder;
import com.shelfie.model.ChildProfile;
import com.shelfie.model.GuardianUser;
import com.shelfie.ui.formChildProfile.FormChildProfileActivity;

public class ProfileAvatarFragment extends Fragment {

    private static final String ARG_GUARDIAN_USER = "GUARDIAN_USER";
    private static final String ARG_CHILD_PROFILE = "CHILD_PROFILE";
    private static final String ARG_IS_EDIT_MODE = "IS_EDIT_MODE";

    private GuardianUser guardianUser;
    private ChildProfile childProfile;
    private boolean isEditMode;

    private CardView cvChildProfileAvatarContainer;
    private ImageView imgChildProfileAvatar;
    private FloatingActionButton fabChildProfileEdit;
    private TextView tvChildProfileNickname;

    public static ProfileAvatarFragment newInstance(GuardianUser guardianUser,
            ChildProfile childProfile, boolean isEditMode) {
        ProfileAvatarFragment fragment = new ProfileAvatarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GUARDIAN_USER, guardianUser);
        args.putSerializable(ARG_CHILD_PROFILE, childProfile);
        args.putBoolean(ARG_IS_EDIT_MODE, isEditMode);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileAvatarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cvChildProfileAvatarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(childProfile != null) {

                } else {
                    Intent newIntent = new Intent(getActivity().getApplicationContext(), FormChildProfileActivity.class);
                    Bundle newIntentBundle = new Bundle();
                    newIntentBundle.putSerializable("GUARDIAN_USER_DATA", guardianUser);
                    newIntent.putExtras(newIntentBundle);
                    startActivity(newIntent);
                }
            }
        });

        fabChildProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getActivity().getApplicationContext(), FormChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable("CHILD_PROFILE_DATA", childProfile);
                newIntentBundle.putSerializable("GUARDIAN_USER_DATA", guardianUser);
                newIntent.putExtras(newIntentBundle);
                startActivity(newIntent);
            }
        });
        return inflater.inflate(R.layout.fragment_profile_avatar, container, false);
    }

    private void init() {
        if (getArguments() != null) {
            guardianUser = (GuardianUser) getArguments().getSerializable(ARG_GUARDIAN_USER);
            childProfile = (ChildProfile) getArguments().getSerializable(ARG_CHILD_PROFILE);
            isEditMode = getArguments().getBoolean(ARG_IS_EDIT_MODE);
        }
        cvChildProfileAvatarContainer = getActivity()
                .findViewById(R.id.cv_child_profile_avatar_container);
        imgChildProfileAvatar = getActivity()
                .findViewById(R.id.img_child_profile_avatar);
        fabChildProfileEdit = getActivity()
                .findViewById(R.id.fab_child_profile_edit);
        tvChildProfileNickname = getActivity()
                .findViewById(R.id.tv_child_profile_nickname);

        if(childProfile != null) {
            imgChildProfileAvatar.setImageBitmap(ImageDecoder.decodeBase64(childProfile.getCharacter().getCharacterImage()));
            tvChildProfileNickname.setText(childProfile.getNickname());
            if(isEditMode) {
                fabChildProfileEdit.setVisibility(View.VISIBLE);
            }
        }
    }
}