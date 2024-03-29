package com.sba.covid_19tracker;

import static com.sba.covid_19tracker.Constants.MAP_DATA_URL;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class LiveMapFragment extends Fragment {

    public static final String TAG = "LIVE_MAP_FRAG";

    public LiveMapFragment() {
        // Required empty public constructor
    }

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_map, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.liveMapWebView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(MAP_DATA_URL);
        view.findViewById(R.id.btn_cls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (webView.canGoBack()) {
                    //setEnabled(true);
                    Log.d(TAG, "Page Back");
                    webView.goBack();
                } else {
                    this.setEnabled(false);
                    Navigation.findNavController(view).navigateUp();
                    Log.d(TAG, "Frag Closed");
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        // callback.setEnabled(true);
        //Log.d(TAG,"force as true");
        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }
}

