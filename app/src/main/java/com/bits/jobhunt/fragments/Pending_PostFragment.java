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

import com.bits.jobhunt.Model;
import com.bits.jobhunt.Pending_PostAdapter;
import com.bits.jobhunt.R;
import com.bits.jobhunt.adminjobAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Pending_PostFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    Pending_PostAdapter pendingAdapter;
    RecyclerView recycleview;
    ArrayList<Model> datalist;


    public Pending_PostFragment() {
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
        return inflater.inflate(R.layout.fragment_pending__post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recycleview = view.findViewById(R.id.pendingpost_recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist = new ArrayList<>();
        pendingAdapter = new Pending_PostAdapter(datalist);
        recycleview.setAdapter(pendingAdapter);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        fireStore.collection("AddPostData").whereEqualTo("Status","Pending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Model obj = d.toObject(Model.class);
                            datalist.add(obj);
                            pendingAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }
}