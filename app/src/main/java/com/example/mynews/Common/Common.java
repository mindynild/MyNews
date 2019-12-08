package com.example.mynews.Common;

import com.example.mynews.Interface.FaviconFinderService;
import com.example.mynews.Interface.NewsService;
import com.example.mynews.Model.FaviconFinder;
import com.example.mynews.Remote.FaviconFinderClient;
import com.example.mynews.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public static final String API_KEY="6bbed62e53d042cb91b21b663a178b2f";

    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static FaviconFinderService getIconService() {
        return FaviconFinderClient.getClient(BASE_URL).create(FaviconFinderService.class);
    }

    //https://newsapi.org/v2/everything?q=apple&from=2019-12-07&to=2019-12-07&sortBy=popularity&apiKey=6bbed62e53d042cb91b21b663a178b2f
    //lien donné par leprof : https://newsapi.org/v2/everything? apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&languag e=fr&sources=google-news-fr

    public static String getAPIUrl (String source, String sortBy, String apiKey)
    {
        StringBuilder apiURL = new StringBuilder("https://newsapi.org/v2/everything?");
        return apiURL.append("sortBy=")
                .append(sortBy)
                .append("&apiKey=")
                .append(apiKey)
                .append("&sources=")
                .append(source)

    }



}
