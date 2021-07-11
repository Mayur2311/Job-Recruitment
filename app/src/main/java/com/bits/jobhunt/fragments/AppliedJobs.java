package com.bits.jobhunt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bits.jobhunt.AppliedJobs_Adapter;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.Savedadapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AppliedJobs extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String fuser;
    EditText search_bar;
    RecyclerView recyclerView;
    ArrayList<Model> appliedDataList;
    AppliedJobs_Adapter appliedJobs_adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AppliedJobs() {
        // Required empty public constructor
    }

   public static AppliedJobs newInstance(String param1, String param2) {
        AppliedJobs fragment = new AppliedJobs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_applied_jobs, container, false);
    }


    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getUid();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recyclerView=view.findViewById(R.id.saved_job_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        appliedDataList =new ArrayList<>();
        appliedJobs_adapter = new AppliedJobs_Adapter(appliedDataList
        );
        recyclerView.setAdapter(appliedJobs_adapter);


    }
}