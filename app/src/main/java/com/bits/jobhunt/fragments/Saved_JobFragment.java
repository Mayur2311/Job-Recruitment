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
import android.widget.Toast;

import com.bits.jobhunt.Job_Details;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.Savedadapter;
import com.bits.jobhunt.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Saved_JobFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String fuser;
    RecyclerView recyclerView;
    ArrayList<Model> saveddatalist;
    Savedadapter savedadapter;
    AlertDialog.Builder builder;


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

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);

        recyclerView=view.findViewById(R.id.saved_job_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        saveddatalist =new ArrayList<>();
        savedadapter = new Savedadapter(saveddatalist);
        recyclerView.setAdapter(savedadapter);



        fireStore.collection("SavedJob").whereEqualTo("Email",fuser ).whereEqualTo("Status", "Saved").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            saveddatalist.add(obj);
                        }
                        savedadapter.notifyDataSetChanged();

                    }
                });

        fireStore.collection("SavedJob").whereEqualTo("Email",fuser).whereEqualTo("Status", "Saved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d("", document.getId());
                        list.add(document.getId());


                    }

                    if (list.isEmpty())
                    {
                        builder = new AlertDialog.Builder(getActivity());

                        //Setting message manually and performing action on button click
                        builder.setMessage("Currently, You don't have any saved job.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Toast.makeText(getActivity(), "You are redirected to Home screen.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent i= new Intent(getContext(),UserActivity.class);
                                        startActivity(i);
                                    }
                                });

                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Saved Jobs");
                        alert.show();
                    }

                }
            }
            });
        }
}


