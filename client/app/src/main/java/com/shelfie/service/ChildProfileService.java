package com.shelfie.service;

import com.shelfie.model.ChildProfile;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChildProfileService {

    @GET("child_profile/")
    Call<ArrayList<ChildProfile>> getAll();

    @GET("child_profile/{id}")
    Call<ChildProfile> getById(@Path("id") Integer id);

    @POST("child_profile/")
    Call<ChildProfile> create(@Body ChildProfile childProfile);

    @PUT("child_profile/{id}")
    Call<ChildProfile> update(@Path("id") Integer id, @Body ChildProfile childProfile);

    @PUT("child_profile/{id}/update_coins/{value}")
    Call<ChildProfile> updateCoins(@Path("id") Integer id, Integer value);

    @DELETE("child_profile/{id}")
    Call<Void> delete(@Path("id") Integer id);
}