package com.sba.covid_19tracker;

import android.content.Context;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sba.covid_19tracker.News.NewsAdapter;
import com.sba.covid_19tracker.News.NewsModelClass;
import com.sba.covid_19tracker.Update.UpdateAdapter;
import com.sba.covid_19tracker.Update.UpdateModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class UpdateFragment extends Fragment {

    public static final String TAG = "Update_LOG";
    private RecyclerView recyclerView;
    private ArrayList<UpdateModelClass> updateList;
    private RequestQueue requestQueue;
    private UpdateAdapter adapter;
    private ProgressBar p1;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.update_recycler);
        updateList = new ArrayList<>();
        p1 = view.findViewById(R.id.progressBar5);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());

        Log.d(TAG, "ABOUT TO SEND RQUEST");

        parseJSON();


    }

    private void parseJSON() {
        Log.d(TAG, "Connecting...");
        String url = "https://api.covid19india.org/updatelog/log.json";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            p1.setVisibility(View.GONE);
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = response.getJSONObject(i);

                                String update = object.getString("update");
                                Long timestamp = object.getLong("timestamp");
                                updateList.add(new UpdateModelClass(update, timestamp));
                            }
                            Collections.sort(updateList);
                            adapter = new UpdateAdapter(getActivity(), updateList);
                            recyclerView.setAdapter(adapter);
                            Log.d(TAG, "Done THE job");
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        p1.setVisibility(View.GONE);
                    }

                }
        );
        requestQueue.add(request);


    }
}
