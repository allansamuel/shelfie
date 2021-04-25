package com.shelfie.service;

import com.shelfie.model.Character;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterService {

    @GET("child_profile/")
    Call<ArrayList<Character>> getAll();

}
