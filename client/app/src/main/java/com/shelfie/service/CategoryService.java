package com.shelfie.service;

import com.shelfie.model.Category;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("character/")
    Call<ArrayList<Category>> getAll();

}
