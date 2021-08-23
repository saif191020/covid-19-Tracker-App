package com.sba.covid_19tracker.News;

import static com.sba.covid_19tracker.Constants.NEWS_API_KEY;

import com.sba.covid_19tracker.News.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {

    @GET("v2/top-headlines?country=in&apiKey=" + NEWS_API_KEY + "&q=covid")
    Call<NewsModel> getNews();
}