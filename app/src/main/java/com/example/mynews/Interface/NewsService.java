package com.example.mynews.Interface;

import com.example.mynews.Model.News;
import com.example.mynews.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET ("v2/sources?language=fr")
    Call<WebSite> getSources() ;

    @GET
    Call<News> getNewestArticles(@Url String url);
}
