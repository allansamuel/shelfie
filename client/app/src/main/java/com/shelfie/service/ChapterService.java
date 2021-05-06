package com.shelfie.service;

import com.shelfie.model.Chapter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChapterService {

    @GET("chapter/{id}")
    Call<Chapter> getById(@Path("id") Integer id);

    @GET("chapter/")
    Call<ArrayList<Chapter>> getAll();

    @POST("chapter/")
    Call<Chapter> create(@Body Chapter chapter);
}
