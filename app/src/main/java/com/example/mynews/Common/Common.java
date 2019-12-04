package com.example.mynews.Common;

import com.example.mynews.Interface.NewsService;
import com.example.mynews.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public static final String API_KEY="6bbed62e53d042cb91b21b663a178b2f";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

}
