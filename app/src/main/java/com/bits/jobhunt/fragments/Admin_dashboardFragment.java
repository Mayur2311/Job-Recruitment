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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.adminjobAdapter;
import com.bits.jobhunt.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class Admin_dashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    adminjobAdapter ajobAdapter;
    RecyclerView recview;
    ArrayList<Model> datalist;
    EditText search_bar;
    Spinner filterSpinner;
    ArrayAdapter admin_jobType_arrayAdapter;


    //-------------------------------------
    ArrayList<Model> filteredList =  new ArrayList<>();
    String[] searchCategory = {"Location", "Industry", "CompanyName","EmployeementType", "JobName"};
    String selectedCategory ;



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
        recview = view.findViewById(R.id.admin_dashboard_recycleView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist = new ArrayList<>();
        ajobAdapter = new adminjobAdapter(datalist);
        recview.setAdapter(ajobAdapter);
        filterSpinner = view.findViewById(R.id.admin_spinner_filter);
        filterSpinner.setOnItemSelectedListener(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        admin_jobType_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, searchCategory);
        admin_jobType_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(admin_jobType_arrayAdapter);


        fireStore.collection("Jobs").whereEqualTo("Status","Approved").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Model obj = d.toObject(Model.class);
                            datalist.add(obj);
                            ajobAdapter.notifyDataSetChanged();
                        }
                        search_bar.clearFocus();

                    }
                });



        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ajobAdapter = new adminjobAdapter(datalist);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredList.clear();

                if(s.toString().isEmpty())
                {
                    recview.setAdapter(new myadapter(datalist));
                    ajobAdapter.notifyDataSetChanged();
                }
                else
                {
                    filter(s.toString());
                }
            }
        });

    }
    private void filter(String text) {

        for (Model model : datalist) {

            if (selectedCategory == searchCategory[0])
            {
                if (model.getLocation().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            else if (selectedCategory == searchCategory[1])
            {
                if (model.getJobType().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            else if(selectedCategory == searchCategory[2])
            {
                if (model.getCompanyName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            else if(selectedCategory == searchCategory[3])
            {
                if (model.getJobType().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            else if(selectedCategory.equals(searchCategory[4]))
            {
                if (model.getJobName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(model);
                }
            }

            recview.setAdapter(new adminjobAdapter(filteredList));
            ajobAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCategory = filterSpinner.getSelectedItem().toString();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


