package com.bits.jobhunt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bits.jobhunt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class DashboardFragment extends Fragment {
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    NavController navController;
    TextView txt_Name;

    public DashboardFragment() {
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
      //  readFireStore();



    }

  /*  *//*User name retrieve code *//*
    public void readFireStore() {
        user = firebaseAuth.getCurrentUser();
        DocumentReference docRef = fireStore.collection("ProfileUpdate").document(user.getUid());

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists()) {
                    Log.d("DashboardFragment", doc.getData().toString());

                    txt_Name.setText("Welcome " + doc.get("firstName") + doc.get("lastName") + " !");


                }

            }
        });
    }*/
}