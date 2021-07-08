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


import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.Savedadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Saved_JobFragment extends Fragment {

    FirebaseFirestore fireStore;



    RecyclerView recycleview;
    ArrayList<Model>saveddata;
    Savedadapter adapter;

    public Saved_JobFragment() {
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
        return inflater.inflate(R.layout.fragment_saved__job, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fireStore = FirebaseFirestore.getInstance();
        recycleview=view.findViewById(R.id.saved_job_recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        saveddata=new ArrayList<>();
        adapter=new Savedadapter(saveddata);
        recycleview.setAdapter(adapter);
        fireStore.collection("SavedJob").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            saveddata.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }
}