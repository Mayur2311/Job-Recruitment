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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bits.jobhunt.AddedPostAdapter;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.NotificationAdapter;
import com.bits.jobhunt.R;
import com.bits.jobhunt.UserActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Notification extends Fragment {


    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String fuser;
    RecyclerView recyclerView;
    ArrayList<Model> notificationdatalist;
    NotificationAdapter notificationadapter;
    SwipeRefreshLayout swipeRefreshLayout;
    AlertDialog.Builder builder;

    public Notification() {
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();

        recyclerView = view.findViewById(R.id.notification_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        notificationdatalist = new ArrayList<>();
        notificationadapter = new NotificationAdapter(notificationdatalist);
        recyclerView.setAdapter(notificationadapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        dataCalling();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                notificationdatalist.clear();

                dataCalling();

                Toast.makeText(getActivity(), "Notifications Updated!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void dataCalling()
    {
        fireStore.collection("Notification").whereEqualTo("Email", fuser).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Model obj = d.toObject(Model.class);
                            notificationdatalist.add(obj);
                        }
                        notificationadapter.notifyDataSetChanged();
                        if(notificationdatalist.isEmpty())
                        {
                            builder = new AlertDialog.Builder(getActivity());

                            //Setting message manually and performing action on button click
                            builder.setMessage("Currently, You have no notifications yet !")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Toast.makeText(getActivity(), "You are redirected to the Home screen.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent i= new Intent(getContext(), UserActivity.class);
                                            startActivity(i);
                                        }
                                    });

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Notifications");
                            alert.show();
                        }

                    }
                });

    }
}