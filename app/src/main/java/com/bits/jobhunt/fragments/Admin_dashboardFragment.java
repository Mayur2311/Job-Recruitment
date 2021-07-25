package com.bits.jobhunt.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bits.jobhunt.LogInActivity;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.adminjobAdapter;
import com.bits.jobhunt.myadapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class Admin_dashboardFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    adminjobAdapter ajobAdapter;
    RecyclerView recview;
    ArrayList<Model> datalist;
    EditText search_bar;


    public Admin_dashboardFragment() {
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
        return inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
        search_bar = view.findViewById(R.id.admin_dashboard_searchbar);
        recview=view.findViewById(R.id.admin_dashboard_recycleView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist=new ArrayList<>();
        ajobAdapter=new adminjobAdapter(datalist);
        recview.setAdapter(ajobAdapter);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        fireStore.collection("Jobs").orderBy("JobName").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            datalist.add(obj);
                            ajobAdapter.notifyDataSetChanged();
                        }
                        search_bar.clearFocus();

                    }
                });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ajobAdapter=new adminjobAdapter(datalist);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text)
    {
        ArrayList<Model> filteredList = new ArrayList<>();

        for(Model model : datalist)
        {
            if (model.getJobName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(model);
            }
        }

        ajobAdapter.filterList(filteredList);
    }
}


