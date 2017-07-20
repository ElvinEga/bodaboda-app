package com.beast.bodaboda.bodaboda.networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by beast on 7/17/17.
 */

public class ApiClient {
    private static final String BASE_URL= "https://bodaboda1.herokuapp.com";
    private static Retrofit retrofit = null;


    /**
    *Desciption : Retrofit client
    *@param  : void
    *@return retrofit
    *@throws null
    */
    public static Retrofit getClient() {
        retrofit=null;
        if (retrofit==null) {
            //Provide Logging on android Monitor
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //Setup the retrofit HTTP client
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
