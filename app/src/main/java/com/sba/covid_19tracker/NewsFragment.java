package com.sba.covid_19tracker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sba.covid_19tracker.News.NewsAdapter;
import com.sba.covid_19tracker.News.NewsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsFragment extends Fragment {

    public static final String TAG = "NEWS_LOG";
    private RecyclerView recyclerView;
    private ArrayList<NewsModelClass> newsList;
    private RequestQueue requestQueue;
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
        newsList = new ArrayList<>();
        p1 = view.findViewById(R.id.progressBar4);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());

        Log.d(TAG, "ABOUT TO SEND RQUEST");

        parseJSON();


    }

    private void parseJSON() {
        Log.d(TAG, "Connecting...");
        String url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=" + NewsApi.key + "&q=covid";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "Connected");
                        try {
                            p1.setVisibility(View.GONE);
                            JSONArray articles = response.getJSONArray("articles");
                            Log.d(TAG, "Got Articles");
                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject article = articles.getJSONObject(i);
                                String date = article.getString("publishedAt").trim();
                                String source = article.getJSONObject("source").getString("name").trim();
                                String title = article.getString("title").trim();
                                String desc = article.getString("description").trim();
                                String content = article.getString("content").trim();
                                String url = article.getString("url").trim();
                                String urlimg = article.getString("urlToImage").trim();

                                newsList.add(new NewsModelClass(source, date, title, desc, url, urlimg, content));
                            }
                            adapter = new NewsAdapter(getActivity(), newsList);
                            recyclerView.setAdapter(adapter);
                            Log.d(TAG, "DOnE tHE job");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            p1.setVisibility(View.GONE);
                            Log.d(TAG, "Problem With Articles");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        p1.setVisibility(View.GONE);
                        Log.d(TAG, "SOME PROBLEM " + String.valueOf(error));
                        Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);

    }
}
