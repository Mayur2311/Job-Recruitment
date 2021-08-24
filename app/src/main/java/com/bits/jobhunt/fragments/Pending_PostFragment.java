package com.bits.jobhunt.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bits.jobhunt.AdminActivity;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.Pending_PostAdapter;
import com.bits.jobhunt.R;
import com.bits.jobhunt.UserActivity;
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
    AlertDialog.Builder builder;


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
                        }
                        pendingAdapter.notifyDataSetChanged();

                        if(datalist.isEmpty())
                        {
                            builder = new AlertDialog.Builder(getActivity());

                            //Setting message manually and performing action on button click
                            builder.setMessage("Currently, there is no jobs added.")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Toast.makeText(getActivity(), "You are redirected to the Home screen.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(getContext(), AdminActivity.class);
                                            startActivity(i);
                                        }
                                    });

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Pending Posts");
                            alert.show();
                        }

                    }
                });

    }
}