package com.sn.themoviedbandroidtask.helpers.webhelpers.methods;

import android.app.Activity;

import com.sn.themoviedbandroidtask.R;
import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;
import com.sn.themoviedbandroidtask.helpers.apphelpers.NetworkHelper;
import com.sn.themoviedbandroidtask.helpers.apphelpers.UIHelpers;
import com.sn.themoviedbandroidtask.helpers.webhelpers.service.WebService;
import com.sn.themoviedbandroidtask.helpers.webhelpers.service.WebServiceFactory;
import com.sn.themoviedbandroidtask.listeners.NowPlayingResponseCallback;
import com.sn.themoviedbandroidtask.models.NowPlayingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saad Nayyer on 5/8/2018.
 */

public class WebApiRequest {
    static WebService webService;
    static Activity activity;
    static WebApiRequest Instance = new WebApiRequest();

    public static WebApiRequest getInstance(Activity activity) {
        return new WebApiRequest(activity);
    }

    WebApiRequest(){}

    private WebApiRequest(Activity activity){
        webService = WebServiceFactory.getInstance(activity);
        this.activity = activity;
    }

    public void getNowPlayingMovies(String apiKey, String language, int page, final NowPlayingResponseCallback callback){
        if (!NetworkHelper.isNetworkAvailable(activity)) {
            callback.onNotConnected();
            return;
        }

        Call<NowPlayingResponse> call = webService.GetNowPlayingMovies(apiKey, language, page);
        call.enqueue(new Callback<NowPlayingResponse>() {
            @Override
            public void onResponse(Call<NowPlayingResponse> call, Response<NowPlayingResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NowPlayingResponse> call, Throwable t) {
                callback.onError();
            }
        });
    }
}
