package com.vedmitryapps.news.model.api;


import com.vedmitryapps.news.model.objects.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {

    @GET("/api/v1/feedNews?lang=en&count=10&sources=7,19,13,5,15,16,12,9,10012,10010,10013,10014,10019,10018,10011&feedLineId=5")
    Call<List<News>> getNews();
}
