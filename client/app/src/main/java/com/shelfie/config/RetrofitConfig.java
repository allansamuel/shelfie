package com.shelfie.config;


import com.shelfie.R;
import com.shelfie.service.GuardianUserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.103:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public GuardianUserService getProductService() {
        return this.retrofit.create(GuardianUserService.class);
    }
}