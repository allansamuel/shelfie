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

    @GET("child_profile/{childProfileId}")
    Call<ChildProfile> getById(@Path("childProfileId") Integer childProfileId);

    @POST("child_profile/")
    Call<ChildProfile> create(@Body ChildProfile childProfile);

    @PUT("child_profile/{childProfileId}")
    Call<ChildProfile> update(@Path("childProfileId") Integer childProfileId, @Body ChildProfile childProfile);

    @PUT("child_profile/{childProfileId}/update_coins/{value}")
    Call<ChildProfile> updateCoins(@Path("id") Integer childProfileId, Integer value);

    @DELETE("child_profile/{childProfileId}")
    Call<Void> delete(@Path("childProfileId") Integer id);
}