package com.shelfie.service;

import com.shelfie.model.Category;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryService {

   @GET("character/page/{pageNumber}")
    Call<ArrayList<Category>> getAll(@Path("pageNumber") int pageNumber);

    @GET("character/{id}")
    Call<Category> getById(@Path("id") Integer id);

    @POST("character/")
    Call<Character> create(@Body Character character );
}
