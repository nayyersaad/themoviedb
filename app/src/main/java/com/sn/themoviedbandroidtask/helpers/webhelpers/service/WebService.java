package com.sn.themoviedbandroidtask.helpers.webhelpers.service;

import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;
import com.sn.themoviedbandroidtask.models.NowPlayingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Saad Nayyer on 5/8/2018.
 */

public interface WebService {

    @GET(ApiConstants.MOVIES_NOW_PLAYING_URL)
    Call<NowPlayingResponse> GetNowPlayingMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
