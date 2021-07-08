package com.bits.jobhunt.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class DashboardFragment extends Fragment  {
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;

    NavController navController;
    String userId;
    TextView verifyMsg, txt_headerText;
    EditText search_bar;
    Button resendCode, findjob;
    RecyclerView recview;
    ArrayList<Model> datalist;
    myadapter adapter;



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

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        NavigationView navigationView = getActivity().findViewById(R.id.navigationView);
      

        resendCode = view.findViewById(R.id.resendcode);
        verifyMsg = view.findViewById(R.id.email_verification);
        search_bar = view.findViewById(R.id.user_dashboard_searchbar);



        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (!user.isEmailVerified()) {
            search_bar.setVisibility(View.INVISIBLE);
            resendCode.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(view.getContext(), "Verification Email Has been Sent", Toast.LENGTH_SHORT);
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


        recview=view.findViewById(R.id.dashboard_recycleView);
        recview.setLayoutManager(new LinearLayoutManager(view.getContext()));
        datalist=new ArrayList<>();
        adapter=new myadapter(datalist);
        recview.setAdapter(adapter);


        fireStore.collection("Jobs").orderBy("JobName").get()
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

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter=new myadapter(datalist);
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

        adapter.filterList(filteredList);
    }
}