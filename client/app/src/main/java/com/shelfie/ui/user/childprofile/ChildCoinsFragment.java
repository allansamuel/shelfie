package com.shelfie.ui.user.childprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shelfie.R;
import com.shelfie.model.ChildProfile;
import com.shelfie.service.ChildProfileService;
import com.shelfie.utils.ImageDownloader;
import com.shelfie.utils.RetrofitConfig;
import com.shelfie.utils.UserSession;

public class ChildCoinsFragment extends Fragment {

    private ImageView imgLoggedChildProfileAvatar;
    private TextView tvLoggedChildProfileNickname;
    private TextView tvLoggedChildProfileCoins;

    public ChildCoinsFragment() {

    }

    public static ChildCoinsFragment newInstance() {
        ChildCoinsFragment fragment = new ChildCoinsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onResume() {
        super.onResume();
        getCurrentChildProfileData();
    }

    private void init() {
        View view = getView();
        imgLoggedChildProfileAvatar = view.findViewById(R.id.img_logged_child_avatar);
        tvLoggedChildProfileNickname = view.findViewById(R.id.tv_logged_child_nickname);
        tvLoggedChildProfileCoins = view.findViewById(R.id.tv_logged_child_coins);
    }

    private void getCurrentChildProfileData() {
        ChildProfile lastChildProfile = UserSession.getChildProfile(getActivity().getApplicationContext());
        RetrofitConfig retrofitConfig = new RetrofitConfig();
        ChildProfileService childProfileService = retrofitConfig.getChildProfileService();
        childProfileService.getById(lastChildProfile.getChildProfileId()).enqueue(new Callback<ChildProfile>() {
            @Override
            public void onResponse(Call<ChildProfile> call, Response<ChildProfile> response) {
                if(response.isSuccessful()) {
                    UserSession.setChildProfile(getContext(), response.body());
                    ChildProfile currentChildProfile = UserSession.getChildProfile(getContext());

                    ImageDownloader imageDownloader = new ImageDownloader(imgLoggedChildProfileAvatar);
                    imageDownloader.execute(getString(R.string.url_character_get_image, currentChildProfile.getCharacter().getCharacterId()), getString(R.string.avatar));
                    tvLoggedChildProfileNickname.setText(currentChildProfile.getNickname());
                    tvLoggedChildProfileCoins.setText(String.valueOf(currentChildProfile.getCoins()));
                }
            }

            @Override
            public void onFailure(Call<ChildProfile> call, Throwable t) {
            }
        });
    }
}