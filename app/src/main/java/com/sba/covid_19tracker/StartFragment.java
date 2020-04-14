package com.sba.covid_19tracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartFragment extends Fragment {

    private static final String START_LOG = "Start_Fragment_Log";
    private ProgressBar startProgress;
    private TextView FeedBackText;
    private TextView AppName;

    private FirebaseAuth firebaseAuth;
    private NavController navController;
    private Handler mWaitHandler = new Handler();

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        startProgress = view.findViewById(R.id.start_progressBar);
        FeedBackText = view.findViewById(R.id.start_FeedBack);
        AppName = view.findViewById(R.id.startAppname);

        navController = Navigation.findNavController(view);

        FeedBackText.setText("Checking User Account ...");


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        if (currentuser == null) {
            // Create New User
            FeedBackText.setText("Creating Account ...");
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FeedBackText.setText("Account Created ...");


                        navController.navigate(R.id.action_startFragment_to_countryFragment);
                    } else {
                        Log.d(START_LOG, "Start Log: " + task.getException());
                    }
                }
            });

        } else {
            //Navigate to next Page with Transition
            FeedBackText.setText("Logging In...");

            mWaitHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        navController.navigate(R.id.action_startFragment_to_countryFragment);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 2000);


        }
    }
}
