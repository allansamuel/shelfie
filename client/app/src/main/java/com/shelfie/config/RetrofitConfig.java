package com.shelfie.config;


import com.shelfie.R;
import com.shelfie.service.GuardianUserService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.server_baseurl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public GuardianUserService getProductService() {
        return this.retrofit.create(GuardianUserService.class);
    }
}