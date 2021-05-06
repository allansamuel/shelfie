package com.shelfie.service;

import com.shelfie.model.Author;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthorService {

    @GET("author/{id}")
    Call<Author> getById(@Path("id") Integer id);

    @GET("author/")
    Call<ArrayList<Author>> getAll();

    @POST("author/")
    Call<Author> create(@Body Author author);
}
