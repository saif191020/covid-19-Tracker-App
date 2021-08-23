package com.sba.covid_19tracker.State;

import static com.sba.covid_19tracker.Constants.INDIA_DATA_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.sba.covid_19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CountryFragment extends Fragment implements StateAdapter.OnStateItemClicked {

    private NavController navController;
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private ArrayList<StateModelClass> StateList;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView delCONF, CONF, ACTI, delREC, REC, delDEC, DEC;
    private TextView Last_updated;

    private ImageView Globe_icon;
    private ImageView Menu_icon;
    public static final String TAG = "COUNTRY_LOG";

    public static final String my_pref_key = "sba_data";
    public static final String KEY_FIRST = "FIRST_RUN_v2";


    private SharedPreferences sharedPreferences;

    public CountryFragment() {
        // Required empty public constructor
    }

    private int current_sort = 0;

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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
        Globe_icon = view.findViewById(R.id.Globe_icon);
        Menu_icon = view.findViewById(R.id.Menu_icon);
        Last_updated = view.findViewById(R.id.Lastt_Updated);


        sharedPreferences = getActivity().getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);

        navController = Navigation.findNavController(view);


        Globe_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_countryFragment_to_worldFragment);
            }
        });
        Menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_countryFragment_to_menuFragment2);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout = view.findViewById(R.id.StateRefreshLayout);
        requestQueue = Volley.newRequestQueue(getActivity());
        final StateAdapter.OnStateItemClicked cone = this;
        parseJSON(cone, view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseJSON(cone, view);
            }
        });
        view.findViewById(R.id.sort_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_sort % 3 == 0) {
                    Collections.sort(StateList, new SortByStateName());
                    adapter.notifyDataSetChanged();
                } else if (current_sort % 3 == 1) {
                    Collections.sort(StateList, Collections.reverseOrder(new SortByStateName()));
                    adapter.notifyDataSetChanged();
                } else if (current_sort % 3 == 2) {
                    Collections.sort(StateList, Collections.reverseOrder(new SortByStateCase()));
                    adapter.notifyDataSetChanged();
                }
                current_sort++;
            }
        });

    }

    private void checkFirstRun(@NonNull View view) {
        if (!sharedPreferences.contains(KEY_FIRST)) {
            Log.d(TAG, "FIRST TIME");
            final TapTargetSequence sequence = new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(view.findViewById(R.id.Globe_icon), "World Tracker", "Click this icon to see World Tracker").dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .textColor(android.R.color.black)
                                    .id(1),
                            TapTarget.forView(view.findViewById(R.id.Menu_icon), "Menu", "Click Menu Icon To see more Stuff Like news, etc...")
                                    .dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .textColor(android.R.color.black)
                                    .id(2),
                            TapTarget.forView(view.findViewById(R.id.country_card_View), "India Stats", "This card shows Current India Stats")
                                    .dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .textColor(android.R.color.black)
                                    .id(3),
                            TapTarget.forView(view.findViewById(R.id.state_recycler), "", "Click the State Name(s) for more details")
                                    .dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .textColor(android.R.color.black)
                                    .id(4)

                    );
            sequence.start();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_FIRST, 1);
            editor.commit();
        }
    }

    private void parseJSON(final StateAdapter.OnStateItemClicked con, final View view) {
        String url = INDIA_DATA_URL;
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
                            Last_updated.setVisibility(View.VISIBLE);
                            Last_updated.setText("Last Updated: " + country.getString("lastupdatedtime").split(" ")[1]);
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
                            Collections.sort(StateList, Collections.reverseOrder(new SortByStateCase()));
                            adapter = new StateAdapter(getActivity(), con, StateList);
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                            checkFirstRun(view);

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
                if (isNetworkConnected())
                    Toast.makeText(getActivity(), "Something Went wrong ! Try Again later", Toast.LENGTH_SHORT).show();
                else
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
        //Log.d(TAG,Sname);

        CountryFragmentDirections.ActionCountryFragmentToStateFragment action = CountryFragmentDirections.actionCountryFragmentToStateFragment();
        action.setStaTENAME(Sname);
        navController.navigate(action);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}

class SortByStateName implements Comparator<StateModelClass> {

    @Override
    public int compare(StateModelClass t1, StateModelClass t2) {
        return t1.getStateName().compareTo(t2.getStateName());
    }
}

class SortByStateCase implements Comparator<StateModelClass> {

    @Override
    public int compare(StateModelClass t1, StateModelClass t2) {
        return t1.getCon() - t2.getCon();
    }
}
