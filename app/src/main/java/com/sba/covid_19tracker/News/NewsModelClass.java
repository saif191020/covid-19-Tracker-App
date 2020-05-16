package com.sba.covid_19tracker.News;

public class NewsModelClass {
    private String sourceName, date, title, description, url, urlImage, content;

    public NewsModelClass(String sourceName, String date, String title, String description, String url, String urlImage, String content) {
        this.sourceName = sourceName;
        this.date = date;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlImage = urlImage;
        this.content = content;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
