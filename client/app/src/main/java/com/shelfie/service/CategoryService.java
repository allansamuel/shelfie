package com.shelfie.service;

import com.shelfie.model.Category;
import com.shelfie.model.InteractiveBook;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("category/page/{pageNumber}")
    Call<ArrayList<Category>> getAll(@Path("pageNumber") int pageNumber);

    @GET("category/{id}/interactive_books")
    Call<ArrayList<InteractiveBook>> getInteractiveBooks(@Path("id") int id);

    @GET("category/{id}")
    Call<Category> getById(@Path("id") Integer id);

    @POST("category/")
    Call<Character> create(@Body Character character );
}
