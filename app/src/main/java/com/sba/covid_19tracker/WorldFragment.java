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
import android.widget.TextView;
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
    private TextView nCo, Co, nRe, Re, nDe, De;

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

        nCo = view.findViewById(R.id.wdelta_conf_title);
        Co = view.findViewById(R.id.wconf_country);
        nRe = view.findViewById(R.id.wdelta_rec_title);
        Re = view.findViewById(R.id.wrec_country);
        nDe = view.findViewById(R.id.wdelta_dec_title);
        De = view.findViewById(R.id.wdec_country);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        CountryList = new ArrayList<>();
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            p1.setVisibility(View.GONE);
                            //Log.d(TAG, "Total : " + response.getString("count"));

                            JSONArray Countries = response.getJSONArray("Countries");
                            JSONObject world = response.getJSONObject("Global");

                            nCo.setText("[+" + (world.getInt("NewConfirmed")) + "]");
                            Co.setText(Integer.toString(world.getInt("TotalConfirmed")));
                            nRe.setText("[+" + (world.getInt("NewRecovered")) + "]");
                            Re.setText(Integer.toString(world.getInt("TotalRecovered")));
                            nDe.setText("[+" + (world.getInt("NewDeaths")) + "]");
                            De.setText(Integer.toString(world.getInt("TotalDeaths")));


                            for (int i = 0; i < Countries.length(); i++) {
                                JSONObject country = Countries.getJSONObject(i);
                                String Cname = country.getString("Country").trim();
                                int Con = country.getInt("TotalConfirmed");
                                int Rec = country.getInt("TotalRecovered");
                                int Dea = country.getInt("TotalDeaths");
                                CountryList.add(new CountryModelClass(Cname, Con, Dea, Rec));
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
                Toast.makeText(getActivity(), "Uh oh. Something went Wrong\nTry Again later", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
