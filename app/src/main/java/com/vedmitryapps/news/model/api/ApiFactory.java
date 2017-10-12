package com.vedmitryapps.news.model.api;


import com.vedmitryapps.news.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static NewsService service;

    public static NewsService getService(){
        if(service == null) {
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = builder.create(NewsService.class);
        }
        return service;
    }
}
