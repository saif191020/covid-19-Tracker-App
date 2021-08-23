package com.sba.covid_19tracker.News;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sba.covid_19tracker.News.model.Article;
import com.sba.covid_19tracker.News.model.NewsModel;
import com.sba.covid_19tracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


public class NewsFragment extends Fragment {

    public static final String TAG = "NEWS_LOG";
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar p1;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.news_recycler_view);
        p1 = view.findViewById(R.id.progressBar4);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d(TAG, "ABOUT TO SEND RQUEST");
        loadNews();

    }

    private void loadNews() {
        NewsApi newsApi = NewsService.getNewsApi();

        Call<NewsModel> responseCall = newsApi.getNews();

        responseCall.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(@NonNull Call<NewsModel> call, @NonNull retrofit2.Response<NewsModel> response) {
                if (response.code() == 200) {
                    List<Article> list = new ArrayList<>();
                    if (response.body() != null) {
                        list = response.body().getArticles();
                    } else
                        Toast.makeText(getActivity(), "No Article Found!", Toast.LENGTH_SHORT).show();
                    adapter = new NewsAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                } else {
                    try {
                        if (response.body() != null) {
                            Log.e(TAG, "onResponse: (Error) " + response.body().toString());
                        } else
                            Log.e(TAG, "onResponse: response is NULL | Possible reason : Invalid API Key or url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                p1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() + "+" + t.toString());
                p1.setVisibility(View.GONE);
                if (isNetworkConnected())
                    Toast.makeText(getActivity(), "Something Went wrong ! Try Again later", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
