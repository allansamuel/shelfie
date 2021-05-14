package com.shelfie.service;

import com.shelfie.model.Character;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("character/{id}")
    Call<Character> getById(@Path("id") Integer id);

    @GET("character/")
    Call<ArrayList<Character>> getAll();

    @POST("character/")
    Call<Character> create(@Body Character character);

    @PUT("character/{id}")
    Call<Character> update(@Path("id") Integer id, @Body Character character);
}
