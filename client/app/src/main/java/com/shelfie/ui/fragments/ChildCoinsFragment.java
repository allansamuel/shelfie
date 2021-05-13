package com.shelfie.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.utils.ImageDecoder;
import com.shelfie.utils.UserSession;

public class ChildCoinsFragment extends Fragment {

    private static final String ARG_CHILD_PROFILE = "CHILD_PROFILE_DATA";

    private ChildProfile childProfile;
    private ImageView imgLoggedChildProfileAvatar;
    private TextView tvLoggedChildProfileNickname;
    private TextView tvLoggedChildProfileCoins;

    public ChildCoinsFragment() {

    }

    public static ChildCoinsFragment newInstance(ChildProfile childProfile) {
        ChildCoinsFragment fragment = new ChildCoinsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHILD_PROFILE, childProfile);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_child_coins, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        View view = getView();

        imgLoggedChildProfileAvatar = view.findViewById(R.id.img_logged_child_avatar);
        tvLoggedChildProfileNickname = view.findViewById(R.id.tv_logged_child_nickname);
        tvLoggedChildProfileCoins = view.findViewById(R.id.tv_logged_child_coins);

        childProfile = UserSession.getChildProfile(getActivity().getApplicationContext());

        Bitmap profileAvatarImage = ImageDecoder.cropAvatarImage(
                ImageDecoder.decodeBase64(childProfile.getCharacter().getCharacterImage()));
        imgLoggedChildProfileAvatar.setImageBitmap(profileAvatarImage);
        tvLoggedChildProfileNickname.setText(childProfile.getNickname());
        tvLoggedChildProfileCoins.setText(String.valueOf(childProfile.getCoins()));
    }
}