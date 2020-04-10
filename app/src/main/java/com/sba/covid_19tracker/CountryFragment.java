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


public class CountryFragment extends Fragment {
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private ArrayList<StateModelClass> StateList;
    private RequestQueue requestQueue;

    public static final String TAG = "COUNTRY_LOG";

    public CountryFragment() {
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
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"in the activity");
        recyclerView = view.findViewById(R.id.state_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StateList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();

    }

    private void parseJSON() {
        String url = "https://api.covid19india.org/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG,"Country Frage :"+" Request Complete");
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject state = jsonArray.getJSONObject(i);
                                String sname = state.getString("state");
                                int con = Integer.parseInt(state.getString("confirmed"));
                                int dcon = Integer.parseInt(state.getString("deltaconfirmed"));
                                int rec = Integer.parseInt(state.getString("recovered"));
                                int drec = Integer.parseInt(state.getString("deltarecovered"));
                                int dec = Integer.parseInt(state.getString("deaths"));
                                int ddec = Integer.parseInt(state.getString("deltadeaths"));
                                StateList.add(new StateModelClass(sname, con, dcon, dec, ddec, rec, drec));

                            }
                            Log.d(TAG,"Country Frage : "+"done with all stuff");
                            adapter=new StateAdapter(getActivity(),StateList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG,"Country Frage : "+"ERROR");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Log.d(TAG,"Country Frage : "+" Launch Request");
        requestQueue.add(request);

    }
}
