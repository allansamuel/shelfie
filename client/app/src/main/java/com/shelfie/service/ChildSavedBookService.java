package com.shelfie.service;

import com.shelfie.model.ChildSavedBook;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChildSavedBookService {

    @GET("child_saved_book/{id}")
    Call<ChildSavedBook> getById(@Path("id") Integer id);

    @GET("child_saved_book/")
    Call<ArrayList<ChildSavedBook>> getAll();

    @POST("child_saved_book/")
    Call<ChildSavedBook> create(@Body ChildSavedBook childSavedBook );
}
