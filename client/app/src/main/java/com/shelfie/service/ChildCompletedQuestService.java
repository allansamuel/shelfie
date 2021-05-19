package com.shelfie.service;

import com.shelfie.model.ChildCompletedQuest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChildCompletedQuestService {

    @GET("child_completed_quest/child_profile/{childProfileId}/quest/{questId}")
    Call<Boolean> isCompleted(@Path("childProfileId") Integer childProfileId, @Path("questId") Integer questId);

    @POST("child_completed_quest/child_profile/{childProfileId}/quest/{questId}")
    Call<ChildCompletedQuest> complete(@Path("childProfileId") Integer childProfileId, @Path("questId") Integer questId);
}
