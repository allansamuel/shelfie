package com.shelfie.service;

import com.shelfie.model.ChildProfile;
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

    @POST("guardian_user/login/")
    Call<GuardianUser> login(@Body GuardianUser guardianUser);

    @GET("guardian_user/{guardianUserId}/child_profiles/")
    Call<ArrayList<ChildProfile>> getChildProfiles(@Path("guardianUserId") Integer guardianUserId);

    @POST("guardian_user/")
    Call<GuardianUser> create(@Body GuardianUser guardianUser);

    @PUT("guardian_user/{guardianUserId}")
    Call<GuardianUser> update(@Path("guardianUserId") Integer guardianUserId, @Body GuardianUser guardianUser);

    @DELETE("guardian_user/{guardianUserId}")
    Call<Void> delete(@Path("guardianUserId") Integer guardianUserId);
}