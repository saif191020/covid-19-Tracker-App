package com.sba.covid_19tracker.News;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sba.covid_19tracker.News.model.Article;
import com.sba.covid_19tracker.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<Article> newsList;


    public NewsAdapter(Context context, List<Article> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        final Article news = newsList.get(position);

        holder.source.setText(news.getSource().getName());
        holder.title.setText(news.getTitle());
        holder.decription.setText(news.getDescription());
        Glide
                .with(context)
                .load(news.getUrlToImage())
                .into(holder.newsImage);

        holder.readMore.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView source, title, decription;
        public Button readMore;
        public ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            source = itemView.findViewById(R.id.news_source_text);
            title = itemView.findViewById(R.id.news_title_text);
            decription = itemView.findViewById(R.id.news_description);
            readMore = itemView.findViewById(R.id.full_story_btn);
            newsImage = itemView.findViewById(R.id.news_img);


        }
    }
}
