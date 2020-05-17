package com.sba.covid_19tracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.sba.covid_19tracker.District.DistrictAdapter;
import com.sba.covid_19tracker.District.DistrictModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class StateFragment extends Fragment {
    private static final String TAG = "STATELOG";
    private TextView State_name;
    private RecyclerView recyclerView;
    private ArrayList<DistrictModelClass> DistrictList;
    private RequestQueue requestQueue;
    private String sname;
    private DistrictAdapter adapter;
    private ProgressBar p1;
    private Button Zone_btn;
    NavController navController;
    public static final String my_pref_key = "sba_data";
    public static final String KEY_STATE_FIRST = "FIRST_STATE_RUN";
    private SharedPreferences sharedPreferences;

    public StateFragment() {
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
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sname = StateFragmentArgs.fromBundle(getArguments()).getStaTENAME();

        State_name = view.findViewById(R.id.state_STATENAME);
        recyclerView = view.findViewById(R.id.dict_recycler);
        p1 = view.findViewById(R.id.progressBar2);
        Zone_btn = view.findViewById(R.id.Zone_btn);
        navController = Navigation.findNavController(view);
        Log.d(TAG, "STATE_NAME" + sname);
        sharedPreferences = getActivity().getSharedPreferences(my_pref_key, Context.MODE_PRIVATE);
        State_name.setText(sname);

        Zone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateFragmentDirections.ActionStateFragmentToZonesFragment action = StateFragmentDirections.actionStateFragmentToZonesFragment();
                action.setZOnENAME(sname);
                navController.navigate(action);
            }
        });
        DistrictList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        //  getActivity().get;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestQueue = Volley.newRequestQueue(getActivity());
        parseJson();
    }

    private void parseJson() {
        String url = "https://api.covid19india.org/v2/state_district_wise.json";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            int stateIndex = -21;
                            p1.setVisibility(View.GONE);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject state = response.getJSONObject(i);
                                if (sname.equals(state.getString("state"))) {
                                    stateIndex = i;
                                    break;
                                }
                            }
                            if (stateIndex != -21) {
                                JSONObject state = response.getJSONObject(stateIndex);
                                JSONArray districtdata = state.getJSONArray("districtData");
                                for (int k = 0; k < districtdata.length(); k++) {
                                    JSONObject district = districtdata.getJSONObject(k);
                                    String dist_name = district.getString("district");
                                    int confirmed = Integer.parseInt(district.getString("confirmed"));
                                    int dcon = Integer.parseInt(district.getJSONObject("delta").getString("confirmed"));
                                    DistrictList.add(new DistrictModelClass(dist_name, confirmed, dcon));
                                }
                                Collections.sort(DistrictList);
                                adapter = new DistrictAdapter(getActivity(), DistrictList);
                                recyclerView.setAdapter(adapter);
                                Log.d(TAG, "done");
                            } else {
                                State_name.setText("😊 No Covid-19 Cases");
                            }
                            checkFirstStateActivityRun();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                p1.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    private void checkFirstStateActivityRun() {
        if (!sharedPreferences.contains(KEY_STATE_FIRST)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_STATE_FIRST, 1);
            editor.commit();
            final TapTargetSequence sequence = new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(Zone_btn, "Districts Zone(s)", "Click this icon to see District Zone Colour").dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .textColor(android.R.color.black)
                                    .id(1),
                            TapTarget.forView(recyclerView, "District Stats", "This card shows District Stats")
                                    .dimColor(android.R.color.black)
                                    .outerCircleColor(R.color.colorAccent)
                                    .targetCircleColor(android.R.color.black)
                                    .transparentTarget(true)
                                    .cancelable(true)
                                    .textColor(android.R.color.black)
                                    .id(2)
                    );
            sequence.start();
        }

    }
}
