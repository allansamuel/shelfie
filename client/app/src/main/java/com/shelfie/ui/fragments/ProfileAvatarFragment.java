package com.shelfie.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import com.shelfie.ui.userregister.FormChildProfileActivity;
import com.shelfie.utils.ApplicationStateManager;

import org.jetbrains.annotations.NotNull;

public class ProfileAvatarFragment extends Fragment {

    private static final String ARG_GUARDIAN_USER = "GUARDIAN_USER_DATA";
    private static final String ARG_CHILD_PROFILE = "CHILD_PROFILE_DATA";
    private static final String ARG_FORM_INTERACTION_MODE = "FORM_INTERACTION_MODE";

    private GuardianUser guardianUser;
    private ChildProfile childProfile;
    private int formInteractionMode;

    private CardView cvChildProfileAvatarContainer;
    private ImageView imgChildProfileAvatar;
    private FloatingActionButton fabChildProfileEdit;
    private TextView tvChildProfileNickname;

    public static ProfileAvatarFragment newInstance(GuardianUser guardianUser,
            ChildProfile childProfile, int formInteractionMode) {
        ProfileAvatarFragment fragment = new ProfileAvatarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GUARDIAN_USER, guardianUser);
        args.putSerializable(ARG_CHILD_PROFILE, childProfile);
        args.putInt(ARG_FORM_INTERACTION_MODE, formInteractionMode);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileAvatarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            guardianUser = (GuardianUser) getArguments().getSerializable(ARG_GUARDIAN_USER);
            childProfile = (ChildProfile) getArguments().getSerializable(ARG_CHILD_PROFILE);
            formInteractionMode = getArguments().getInt(ARG_FORM_INTERACTION_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_avatar, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {

        init();

        cvChildProfileAvatarContainer.setOnClickListener(view1 -> {
            if(childProfile == null) {
                Intent newIntent = new Intent(requireActivity().getApplicationContext(), FormChildProfileActivity.class);
                Bundle newIntentBundle = new Bundle();
                newIntentBundle.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
                newIntent.putExtras(newIntentBundle);
                startActivity(newIntent);
            }
        });

        fabChildProfileEdit.setOnClickListener(view12 -> {
            Intent newIntent = new Intent(requireActivity().getApplicationContext(), FormChildProfileActivity.class);
            Bundle newIntentBundle = new Bundle();
            newIntentBundle.putSerializable(getString(R.string.bundle_child_profile), childProfile);
            newIntentBundle.putSerializable(getString(R.string.bundle_guardian_user), guardianUser);
            newIntent.putExtras(newIntentBundle);
            startActivity(newIntent);
        });
    }

    private void init() {
        View view = getView();
        assert view != null;
        cvChildProfileAvatarContainer = view.findViewById(R.id.cv_child_profile_avatar_container);
        imgChildProfileAvatar = view.findViewById(R.id.img_child_profile_avatar);
        fabChildProfileEdit = view.findViewById(R.id.fab_child_profile_edit);
        tvChildProfileNickname = view.findViewById(R.id.tv_child_profile_nickname);

        if(childProfile != null) {
            imgChildProfileAvatar.setPadding(0, 0, 0, 0);
            Bitmap profileAvatarImage = ImageDecoder.decodeBase64(childProfile.getCharacter().getCharacterImage());
            profileAvatarImage = Bitmap.createBitmap(profileAvatarImage, 0, 0, 1000, 1000);
            imgChildProfileAvatar.setImageBitmap(profileAvatarImage);
            imgChildProfileAvatar.setBackgroundColor(getResources().getColor(R.color.blue_200));
            tvChildProfileNickname.setText(childProfile.getNickname());
            if(formInteractionMode == ApplicationStateManager.EDIT_MODE) {
                fabChildProfileEdit.setVisibility(View.VISIBLE);
            }
        }
    }
}