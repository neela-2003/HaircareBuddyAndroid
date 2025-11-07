package com.saveetha.haircarebuddy.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://t3fn5l73-80.inc1.devtunnels.ms/haircare/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {

            // Logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Log request + response

            // Custom OkHttpClient with timeout and logging
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)     // Connection timeout
                    .readTimeout(30, TimeUnit.SECONDS)        // Read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)       // Write timeout
                    .addInterceptor(logging)                  // Add logging
                    .build();

            // Retrofit instance with custom OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)                     // Use custom client
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
