package com.example.mynews.Interface;

import com.example.mynews.Model.FaviconFinder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FaviconFinderService {
    @GET
    Call<FaviconFinder>getIconUrl(@Url String url);
}
