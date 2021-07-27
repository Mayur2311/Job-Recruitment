package com.bits.jobhunt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bits.jobhunt.AppliedJobs_Adapter;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.Savedadapter;
import com.bits.jobhunt.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobs extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String fuser;
    RecyclerView recyclerView;
    ArrayList<Model> appliedDataList;
    AppliedJobs_Adapter appliedJobs_adapter;


    public AppliedJobs() {
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
        return inflater.inflate(R.layout.fragment_applied_jobs, container, false);
    }


    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recyclerView=view.findViewById(R.id.applied_job_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        appliedDataList =new ArrayList<>();
        appliedJobs_adapter = new AppliedJobs_Adapter(appliedDataList);
        recyclerView.setAdapter(appliedJobs_adapter);

        fireStore.collection("AppliedJob").whereEqualTo("Email",fuser ).whereEqualTo("Status", "Applied").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            appliedDataList.add(obj);
                        }
                        appliedJobs_adapter.notifyDataSetChanged();
                    }
                });

    }

}