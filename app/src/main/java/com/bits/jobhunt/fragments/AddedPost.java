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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bits.jobhunt.AddedPostAdapter;
import com.bits.jobhunt.Job_Details;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.Savedadapter;
import com.bits.jobhunt.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AddedPost extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String fuser;
    RecyclerView recyclerView;
    ArrayList<Model> addeddatalist;
    AddedPostAdapter addedadapter;


    public AddedPost() {
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
        return inflater.inflate(R.layout.fragment_added_post2, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recyclerView=view.findViewById(R.id.added_job_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        addeddatalist =new ArrayList<>();
        addedadapter = new AddedPostAdapter(addeddatalist);
        recyclerView.setAdapter(addedadapter);


        fireStore.collection("AddPostData").whereEqualTo("Email",fuser ).whereEqualTo("Status", "Approved").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            addeddatalist.add(obj);
                        }
                        addedadapter.notifyDataSetChanged();

                    }
                });

        fireStore.collection("AddPostData").whereEqualTo("Email",fuser).whereEqualTo("Status", "Approved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d("", document.getId());
                        list.add(document.getId());


                    }
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("You haven't added any jobs yet, Press 'OK' to go back to Home")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i=new Intent(getContext(), UserActivity.class);
                                    startActivity(i);

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }
}