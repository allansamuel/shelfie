package com.shelfie.service;

import com.shelfie.model.GuardianUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GuardianUserService {

    @GET("guardian_user/")
    Call<ArrayList<GuardianUser>> getAll();

    @POST("guardian_user/")
    Call<GuardianUser> create(@Body GuardianUser guardianUser);

    @PUT("guardian_user/{id}")
    Call<GuardianUser> update(@Path("id") Integer id, @Body GuardianUser guardianUser);

    @DELETE("guardian_user/{id}")
    Call<Void> delete(@Path("id") Integer id);
}