package com.example.mynews.Interface;

import com.example.mynews.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET ("v2/sources?apiKey=6bbed62e53d042cb91b21b663a178b2f")
    Call<WebSite> getSources() ;
}
