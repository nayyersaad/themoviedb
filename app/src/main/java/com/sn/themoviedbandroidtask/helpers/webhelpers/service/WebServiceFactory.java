package com.sn.themoviedbandroidtask.helpers.webhelpers.service;

import android.app.Activity;

import com.sn.themoviedbandroidtask.helpers.apphelpers.ApiConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Saad Nayyer on 5/8/2018.
 */

public class WebServiceFactory {
    private static WebService webService;

    public static WebService getInstance(Activity activity) {

        if (webService == null) {

//            final BasePreferenceHelper preHelper = new BasePreferenceHelper(activity);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.readTimeout(2, TimeUnit.MINUTES);


            // add your other interceptors …
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request request = null;
                    try {
                        Request.Builder requestBuilder = original.newBuilder();
                        request = requestBuilder.build();
                    } catch (Exception ex) {
                        Request.Builder requestBuilder = original.newBuilder();
                        request = requestBuilder.build();
                    }

                    return chain.proceed(request);
                }
            });

            httpClient.addInterceptor(logging);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            webService = retrofit.create(WebService.class);
        }

        return webService;
    }
}
