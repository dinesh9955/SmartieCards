package com.smartiecards.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String TAG = "APIClient";


    private static final int CONNECTION_TIME_OUT = 30000;
    private static final int READ_TIME_OUT = 30000;
    private static final int WRITE_TIME_OUT = 30000;


    public static Retrofit getClient() {
        Log.e(TAG, "getClient");
       Retrofit retrofit = null;
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(WSContants.BASE_MAIN_URL_ANDROID)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getOkHttpClientTimeOut())
                    .build();
        }
        //return retrofit;

//        Retrofit retrofit = null;
//        if (retrofit == null) {
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//
//            // Can be Level.BASIC, Level.HEADERS, or Level.BODY
//            // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            builder.networkInterceptors().add(httpLoggingInterceptor);
//            retrofit = new Retrofit.Builder()
//                    .client(builder.connectTimeout(0, TimeUnit.SECONDS).writeTimeout(0, TimeUnit.SECONDS).readTimeout(0, TimeUnit.SECONDS).build())
//                    .baseUrl(WSContants.BASE_MAIN_URL_ANDROID)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
        return retrofit;


    }



    private static OkHttpClient getOkHttpClientTimeOut(){
        return new OkHttpClient().newBuilder()
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();

    }






}




