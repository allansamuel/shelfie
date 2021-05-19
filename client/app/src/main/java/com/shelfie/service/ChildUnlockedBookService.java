package com.shelfie.service;

import com.shelfie.model.ChildProfile;
import com.shelfie.model.ChildUnlockedBook;
import com.shelfie.model.InteractiveBook;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChildUnlockedBookService {

    @POST("child_unlocked_books/child_profile/{id}/unlock_book/{interactiveBookId}")
    Call<ChildProfile> unlock(@Path("id") Integer id, @Path("interactiveBookId") Integer interactiveBookId);

    @GET("child_unlocked_books/child_profile/{childProfileId}/interactive_book/{interactiveBookId}")
    Call<ChildUnlockedBook> getByChildAndBook(@Path("childProfileId") Integer childProfileId, @Path("interactiveBookId") Integer interactiveBookId);

}
