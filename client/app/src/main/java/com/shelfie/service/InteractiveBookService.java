package com.shelfie.service;

import com.shelfie.model.InteractiveBook;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InteractiveBookService {

    @GET("interactive_book/page/{pageNumber}")
    Call<ArrayList<InteractiveBook>> getAll(@Path("pageNumber") int pageNumber);

    @GET("interactive_book/{id}")
    Call<InteractiveBook> getById(@Path("id") Integer id);

    @POST("interactive_book/")
    Call<InteractiveBook> create(@Body InteractiveBook interactiveBook);

    //por como o parametro de imagem?
    @PUT("interactive_book/{id}/upload_image")
    Call<Void> uploadImage(@Path("id") Integer id);

    @PUT("interactive_book/{id}")
    Call<InteractiveBook> update(@Path("id") Integer id, @Body InteractiveBook interactiveBook);

    @DELETE("interactive_book/{id}")
    Call<Void> delete(@Path("id") Integer id);

}
