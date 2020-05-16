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
import com.sba.covid_19tracker.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<NewsModelClass> newsList;


    public NewsAdapter(Context context, ArrayList<NewsModelClass> newsList) {
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
        //TODO
        final NewsModelClass news = newsList.get(position);

        holder.source.setText(news.getSourceName());
        holder.title.setText(news.getTitle());
        holder.decription.setText(news.getDescription());
        Glide.with(context).load(news.getUrlImage()).into(holder.newsImage);

        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
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
