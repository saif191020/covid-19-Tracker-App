package com.sba.covid_19tracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class CountryFragment extends Fragment implements StateAdapter.OnStateItemClicked {
    private NavController navController;
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private ArrayList<StateModelClass> StateList;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView delCONF, CONF, ACTI, delREC, REC, delDEC, DEC;
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
        Log.d(TAG, "in the activity");

        recyclerView = view.findViewById(R.id.state_recycler);
        delCONF = view.findViewById(R.id.delta_conf_title);
        CONF = view.findViewById(R.id.conf_country);
        ACTI = view.findViewById(R.id.act_country);
        delREC = view.findViewById(R.id.delta_rec_title);
        REC = view.findViewById(R.id.rec_country);
        delDEC = view.findViewById(R.id.delta_dec_title);
        DEC = view.findViewById(R.id.dec_country);

        navController = Navigation.findNavController(view);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.StateRefreshLayout);
        requestQueue = Volley.newRequestQueue(getActivity());
        final StateAdapter.OnStateItemClicked cone = this;
        parseJSON(cone);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseJSON(cone);
            }
        });

    }

    private void parseJSON(final StateAdapter.OnStateItemClicked con) {
        String url = "https://api.covid19india.org/data.json";
        swipeRefreshLayout.setRefreshing(true);
        StateList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "Country Frage :" + " Request Complete");
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            JSONObject country = jsonArray.getJSONObject(0);

                            if ((country.getString("deltaconfirmed")).equals("0"))
                                delCONF.setVisibility(View.INVISIBLE);
                            if ((country.getString("deltarecovered")).equals("0"))
                                delREC.setVisibility(View.INVISIBLE);
                            if ((country.getString("deltadeaths")).equals("0"))
                                delDEC.setVisibility(View.INVISIBLE);
                            delCONF.setText("[+" + country.getString("deltaconfirmed") + "]");
                            CONF.setText(country.getString("confirmed"));
                            ACTI.setText(country.getString("active"));
                            delREC.setText("[+" + country.getString("deltarecovered") + "]");
                            REC.setText(country.getString("recovered"));
                            delDEC.setText("[+" + country.getString("deltadeaths") + "]");
                            DEC.setText(country.getString("deaths"));

                            for (int i = 1; i < jsonArray.length(); i++) {
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
                            Log.d(TAG, "Country Frage : " + "done with all stuff");
                            adapter = new StateAdapter(getActivity(), con, StateList);
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Country Frage : " + "ERROR");
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
            }

        });
        Log.d(TAG, "Country Frage : " + " Launch Request");
        requestQueue.add(request);

    }

    @Override
    public void onItemClicked(int position) {
        StateModelClass smc = StateList.get(position);
        String Sname = smc.getStateName();
        // Log.d(TAG,Sname);

        CountryFragmentDirections.ActionCountryFragmentToStateFragment action = CountryFragmentDirections.actionCountryFragmentToStateFragment();
        action.setStaTENAME(Sname);
        navController.navigate(action);
    }
}
