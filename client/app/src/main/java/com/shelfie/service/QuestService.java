package com.shelfie.service;

import com.shelfie.model.Quest;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestService {

    @GET("quest/{id}")
    Call<Quest> getById(@Path("id") Integer id);

    @GET("quest/")
    Call<ArrayList<Quest>> getAll();

    @POST("quest/")
    Call<Quest> create(@Body Quest quest );
}
