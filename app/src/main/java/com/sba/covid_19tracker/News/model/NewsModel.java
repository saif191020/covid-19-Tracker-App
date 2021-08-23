
package com.sba.covid_19tracker.News.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NewsModel {

    @SerializedName("articles")
    private List<Article> mArticles;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalResults")
    private Long mTotalResults;

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public String toString() {
        return "NewsModel{" +
                "mArticles=" + mArticles +
                ", mStatus='" + mStatus + '\'' +
                ", mTotalResults=" + mTotalResults +
                '}';
    }
}
