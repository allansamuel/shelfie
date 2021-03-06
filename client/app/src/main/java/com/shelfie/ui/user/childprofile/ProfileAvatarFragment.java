package com.shelfie.ui.user.childprofile;

import android.content.Intent;
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
import com.shelfie.ui.main.MainActivity;
import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.utils.ImageDownloader;
import com.shelfie.utils.UserSession;

import org.jetbrains.annotations.NotNull;

public class ProfileAvatarFragment extends Fragment {

    private static final String ARG_CHILD_PROFILE = "CHILD_PROFILE_DATA";

    private ChildProfile childProfile;
    private CardView cvChildProfileAvatarContainer;
    private ImageView imgChildProfileAvatar;
    private FloatingActionButton fabChildProfileEdit;
    private TextView tvChildProfileNickname;

    public static ProfileAvatarFragment newInstance(ChildProfile childProfile) {
        ProfileAvatarFragment fragment = new ProfileAvatarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHILD_PROFILE, childProfile);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileAvatarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childProfile = (ChildProfile) getArguments().getSerializable(ARG_CHILD_PROFILE);
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
            if (UserSession.isFormInReadMode(getActivity().getApplicationContext())) {
                getActivity().finish();
                UserSession.setChildProfile(getActivity().getApplicationContext(), childProfile);
                Intent accessChildProfileIntent = new Intent(requireActivity().getApplicationContext(), MainActivity.class);
                startActivity(accessChildProfileIntent);
            }
        });

        fabChildProfileEdit.setOnClickListener(view12 -> {
            UserSession.setFormInteractionMode(getActivity().getApplicationContext(), UserSession.EDIT_MODE);
            Intent editChildProfileIntent = new Intent(requireActivity().getApplicationContext(), FormChildProfileActivity.class);
            UserSession.setChildProfile(getActivity().getApplicationContext(), childProfile);
//            editChildProfileIntent.putExtra(ARG_CHILD_PROFILE, childProfile);
            startActivity(editChildProfileIntent);
        });
    }

    private void init() {
        View view = getView();
        cvChildProfileAvatarContainer = view.findViewById(R.id.cv_child_profile_avatar_container);
        imgChildProfileAvatar = view.findViewById(R.id.img_child_profile_avatar);
        fabChildProfileEdit = view.findViewById(R.id.fab_child_profile_edit);
        tvChildProfileNickname = view.findViewById(R.id.tv_child_profile_nickname);

        ImageDownloader imageDownloader = new ImageDownloader(imgChildProfileAvatar);
        imageDownloader.execute(getString(R.string.url_character_get_image, childProfile.getCharacter().getCharacterId()), getString(R.string.avatar));
        tvChildProfileNickname.setText(childProfile.getNickname());
        if(!UserSession.isFormInReadMode(getActivity().getApplicationContext())) {
            fabChildProfileEdit.setVisibility(View.VISIBLE);
        }

    }
}