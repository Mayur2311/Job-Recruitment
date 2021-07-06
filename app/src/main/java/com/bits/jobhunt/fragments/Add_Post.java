package com.bits.jobhunt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bits.jobhunt.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Add_Post extends Fragment {
EditText et_title,et_company,et_location,et_salary,et_jobtype,et_noOfHires,et_qualification,et_description;
Button add_new;
FirebaseAuth firebaseAuth;
NavController navController;
FirebaseFirestore db;
String fuser;
String ftitle,fcompany,flocation,fsalary,fjobtype,fnoOfHires,fqualification,fdescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add__post, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_title = view.findViewById(R.id.title);
        et_company = view.findViewById(R.id.company);
        et_location = view.findViewById(R.id.location);
        et_jobtype = view.findViewById(R.id.jobtype);
        et_salary = view.findViewById(R.id.salary);
        et_noOfHires = view.findViewById(R.id.numberOfHires);
        et_qualification = view.findViewById(R.id.qualification);
        et_description = view.findViewById(R.id.description);
        firebaseAuth = FirebaseAuth.getInstance();
        add_new = view.findViewById(R.id.add_new);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftitle = et_title.getText().toString();
                fcompany = et_company.getText().toString();
                flocation = et_location.getText().toString();
                fjobtype = et_jobtype.getText().toString();
                fsalary = et_salary.getText().toString();
                fnoOfHires = et_noOfHires.getText().toString();
                fqualification = et_qualification.getText().toString();
                fdescription = et_description.getText().toString();
                if (TextUtils.isEmpty(ftitle)) {
                    et_title.setError("Title is required.");
                    return;
                }
                if (TextUtils.isEmpty(fcompany)) {
                    et_company.setError("Company is required.");
                    return;
                }
                if (TextUtils.isEmpty(flocation)) {
                    et_location.setError("location is required.");
                    return;
                }
                if (TextUtils.isEmpty(fjobtype)) {
                    et_jobtype.setError("jobtype is required.");
                    return;
                }
                if (TextUtils.isEmpty(fsalary)) {
                    et_salary.setError("Salary is required.");
                    return;
                }
                if (TextUtils.isEmpty(fnoOfHires)) {
                    et_noOfHires.setError("Number of Hires is required.");
                    return;
                }
                if (TextUtils.isEmpty(fqualification)) {
                    et_qualification.setError("Qualification is required.");
                    return;
                }
                if (TextUtils.isEmpty(fdescription)) {
                    et_description.setError("Description is required.");
                    return;
                }
                addnewjobpost(ftitle,fcompany,flocation,fjobtype,fsalary,fnoOfHires,fqualification,fdescription);

            }
        });
    }


            public void addnewjobpost(String title, String company, String location, String jobtype, String salary, String noOfHires, String qualification, String description) {

                Map<String, Object> postData = new HashMap<>();
                fuser = firebaseAuth.getCurrentUser().getUid();
                /*postData.put("UserId",fuser);*/
                postData.put("title", title);
                postData.put("company", company);
                postData.put("location", location);
                postData.put("jobtype", jobtype);
                postData.put("salary", salary);
                postData.put("noOFHires", noOfHires);
                postData.put("qualification", qualification);
                postData.put("description", description);
                db.collection("AddPostData").document().set(postData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getContext().getApplicationContext(), "post data inserted successfully", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

    }

