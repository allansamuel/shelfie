package com.shelfie.utils;


import com.shelfie.R;
import com.shelfie.model.InteractiveBook;
import com.shelfie.service.CategoryService;
import com.shelfie.service.CharacterService;
import com.shelfie.service.ChildProfileService;
import com.shelfie.service.GuardianUserService;
import com.shelfie.service.InteractiveBookService;

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

    public GuardianUserService getGuardianUserService() {
        return this.retrofit.create(GuardianUserService.class);
    }

    public ChildProfileService getChildProfileService() {
        return this.retrofit.create(ChildProfileService.class);
    }

    public CharacterService getCharacterService() {
        return this.retrofit.create(CharacterService.class);
    }

    public CategoryService getCategoryService() {
        return this.retrofit.create(CategoryService.class);
    }

    public InteractiveBookService getInteractiveBookService() {
        return this.retrofit.create(InteractiveBookService.class);
    }
}