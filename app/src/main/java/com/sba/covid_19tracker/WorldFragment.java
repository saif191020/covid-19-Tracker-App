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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class WorldFragment extends Fragment {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    private ArrayList<CountryModelClass> CountryList;
    private CountryAdapter adapter;
    private ProgressBar p1;
    public static final String TAG = "WORLD_LOG";

    public WorldFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.countries_recycler);
        p1 = view.findViewById(R.id.progressBar3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        CountryList = new ArrayList<>();
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://covidapi.info/api/v1/global/latest";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            p1.setVisibility(View.GONE);
                            Log.d(TAG, "Total : " + response.getString("count"));
                            JSONArray result = response.getJSONArray("result");

                            for (int i = 0; i < result.length(); i++) {
                                JSONObject country = result.getJSONObject(i);
                                Iterator iterator = country.keys();
                                List<String> keysList = new ArrayList<String>();
                                while (iterator.hasNext()) {
                                    String key = (String) iterator.next();
                                    keysList.add(key);
                                }
                                if (i == 0)
                                    Log.d(TAG, keysList.get(0));
                                String CName = keysList.get(0);
                                JSONObject contDetails = country.getJSONObject(CName);
                                int CC = contDetails.getInt("confirmed");
                                int CD = contDetails.getInt("deaths");
                                int CR = contDetails.getInt("recovered");

                                CountryList.add(new CountryModelClass(CName, CC, CD, CR));
                            }

                            Collections.sort(CountryList);
                            adapter = new CountryAdapter(getActivity(), CountryList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                p1.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
