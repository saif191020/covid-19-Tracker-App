package com.sba.covid_19tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.sba.covid_19tracker.Zone.ZoneAdapter;
import com.sba.covid_19tracker.Zone.ZoneModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ZonesFragment extends Fragment {

    private String zname;
    private TextView Zone_title;
    private ProgressBar p1;

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private ZoneAdapter adapter;
    private ArrayList<ZoneModelClass> ZoneList;

    public static final String my_pref_key = "sba_data";
    public static final String KEY_ZONE_FIRST = "FIRST_ZONE_RUN";
    private SharedPreferences sharedPreferences;

    public ZonesFragment() {
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
        return inflater.inflate(R.layout.fragment_zones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zname = ZonesFragmentArgs.fromBundle(getArguments()).getZOnENAME();

        Zone_title = view.findViewById(R.id.Zone_head);
        recyclerView = view.findViewById(R.id.Zone_recycler);
        Zone_title.setText(zname);
        p1 = view.findViewById(R.id.progressBar3);
        sharedPreferences = getActivity().getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);


        ZoneList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://api.covid19india.org/zones.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            p1.setVisibility(View.GONE);
                            ZoneList.add(new ZoneModelClass("Green Zone", "Green AAAAAAAAAA", "null"));
                            ZoneList.add(new ZoneModelClass("Orange Zone", "Orange AAAAAAAAAA", "null"));
                            ZoneList.add(new ZoneModelClass("Red Zone", "Red AAAAAAAAAA", "null"));

                            JSONArray zones = response.getJSONArray("zones");
                            for (int i = 0; i < zones.length(); i++) {
                                JSONObject zone = zones.getJSONObject(i);
                                if (!zone.getString("state").equals(zname))
                                    continue;
                                if (zone.getString("zone").equals(""))
                                    continue;
                                String dname = zone.getString("district");
                                String zone_clr = zone.getString("zone") + " " + dname;
                                String last_update = zone.getString("lastupdated");

                                ZoneList.add(new ZoneModelClass(dname, zone_clr, last_update));
                            }
                            Collections.sort(ZoneList);
                            adapter = new ZoneAdapter(getActivity(), ZoneList);
                            recyclerView.setAdapter(adapter);
                            checkFirstZoneRun();

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
                        if (isNetworkConnected())
                            Toast.makeText(getActivity(), "Something Went wrong ! Try Again later", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void checkFirstZoneRun() {
        if (!sharedPreferences.contains(KEY_ZONE_FIRST)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_ZONE_FIRST, 1);
            editor.commit();
            Toast.makeText(getActivity(), "Long press any District name To see when it was updated", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
