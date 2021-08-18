package com.bits.jobhunt.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.jobhunt.LogInActivity;
import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.bits.jobhunt.myadapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String userId;
    TextView verifyMsg;
    EditText search_bar;
    Button resendCode;
    RecyclerView recview;
    ArrayList<Model> datalist;
    myadapter adapter;
    Spinner filterSpinner;
    ArrayAdapter jobType_arrayAdapter;
    NavigationView navigationView;


    //-----------------------------------------------------------------------------------//

    ArrayList<Model> filteredList =  new ArrayList<>();
    String[] searchCategory = {"Location", "Industry", "CompanyName","EmployeementType", "JobName"};
    String selectedCategory ;


    public DashboardFragment() {
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //navigationView = view.findViewById(R.id.navigationView);


        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        search_bar = view.findViewById(R.id.searchBar_dashboard);


        userId = firebaseAuth.getCurrentUser().getUid();
        navigationView = view.findViewById(R.id.navigationView);

        resendCode = view.findViewById(R.id.resendcode);
        verifyMsg = view.findViewById(R.id.email_verification);

        filterSpinner = view.findViewById(R.id.spinner_filter);
        filterSpinner.setOnItemSelectedListener(this);


        recview=view.findViewById(R.id.pendingpost_recycleView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist=new ArrayList<>();
        adapter=new myadapter(datalist);
        recview.setAdapter(adapter);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        jobType_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, searchCategory);
        jobType_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(jobType_arrayAdapter);


        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    filter(s.toString());
                }

            }
        });

        if (!user.isEmailVerified()) {

            search_bar.setVisibility(View.INVISIBLE);
            resendCode.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
            recview.setVisibility(View.INVISIBLE);
            filterSpinner.setVisibility(View.INVISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(view.getContext(), "Verification Email Has been Sent", Toast.LENGTH_SHORT);
                            firebaseAuth.signOut();

                            Intent intent = new Intent(getActivity(), LogInActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });

                }
            });
        }

        fireStore.collection("Jobs").whereEqualTo("Status","Approved").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            datalist.add(obj);
                            adapter.notifyDataSetChanged();
                        }
                        search_bar.clearFocus();
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

            recview.setAdapter(new myadapter(filteredList));
            adapter.notifyDataSetChanged();
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