package com.shelfie.service;

import com.shelfie.model.ChildProfile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterService {

    @GET("child_profile/")
    Call<ArrayList<ChildProfile>> getAll();

}
