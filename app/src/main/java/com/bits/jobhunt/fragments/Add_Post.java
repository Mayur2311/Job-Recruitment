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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bits.jobhunt.Model;
import com.bits.jobhunt.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Add_Post extends Fragment implements AdapterView.OnItemSelectedListener {
EditText et_title,et_company,et_location,et_salary,txt_jobtype, et_jobcategory,txt_noOfHires,et_qualification,et_description,txt_salary;
Button add_new;
FirebaseAuth firebaseAuth;
NavController navController;
FirebaseFirestore db;
String fuser;
String ftitle,fcompany,flocation,fsalary,fjobtype, fjobcategory, fnoOfHires,fqualification,fdescription;
Spinner spinner_jobtype, spinner_numberOfHires,spinner_salarytype,spinner_locationtype;
String jobType, numberOfHires,sp_salary,location;
Model model;

String[] JobType = {"Full-time", "Part-time", "Internship", "Permanent", "Temporary"};

String[] NumberOfHires = {"1","2","3", "4", "5", "6", "7", "8", "9", "10"};
String[] Sp_salary={"PER ANNUM","PER HOUR","PER MONTH"};
String[] Location={"Toronto\t-\tOntario",
        "Montréal\t-\tQuebec",
        "Vancouver\t-\tBritish Columbia",
        "Ottawa\t-\tOntario",
        "Edmonton\t-\tAlberta",
        "Calgary\t-\tAlberta",
        "Quebéc\t-\tQuebec",
        "Winnipeg\t-\tManitoba",
        "Hamilton\t-\tOntario",
        "London\t-\tOntario",
       	"Kitchener\t-\tOntario",
        "St Catharines-Niagara\t-\tOntario",
        "Halifax\t-\tNova Scotia",
        "Victoria\t-\tBritish Columbia",
        "Windsor\t-\tOntario",
        "Oshawa\t-\tOntario",
        "Saskatoon\t-\tSaskatchewan",
        "Regina\t-\tSaskatchewan",
        "St John's\t-\tNewfoundland",
        "Sudbury\t-\tOntario",
        "Chicoutimi\t-\tQuebec",
        "Sherbrooke\t-\tQuebec",
        "Kingston\t-\tOntario",
        "Trois-Rivières\t-\tQuebec",
        "Kelowna\t-\tBritish Columbia",
        "Abbotsford\t-\tBritish Columbia",
        "Saint John\t-\tNew Brunswick",
        "Thunder Bay\t-\tOntario",
        "Barrie\t-\tOntario",
        "Sydney\t-\tNova Scotia"};

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
        txt_jobtype = view.findViewById(R.id.job_type);
        et_jobcategory = view.findViewById(R.id.jobcategory);
        et_salary = view.findViewById(R.id.salary);
        txt_noOfHires = view.findViewById(R.id.number_of_hires);
        txt_salary=view.findViewById(R.id.textsalary);
        et_qualification = view.findViewById(R.id.qualification);
        et_description = view.findViewById(R.id.description);
        firebaseAuth = FirebaseAuth.getInstance();
        add_new = view.findViewById(R.id.add_new);
        navController= Navigation.findNavController(view);
        spinner_jobtype = view.findViewById(R.id.spinner_jobtype);
        spinner_jobtype.setOnItemSelectedListener(this);

        spinner_numberOfHires = view.findViewById(R.id.spinner_number_of_hires);
        spinner_numberOfHires.setOnItemSelectedListener(this);

        spinner_salarytype = view.findViewById(R.id.spinner_salarytype);
        spinner_salarytype.setOnItemSelectedListener(this);

        spinner_locationtype = view.findViewById(R.id.spinner_locationtype);
        spinner_locationtype.setOnItemSelectedListener(this);
        model = new Model();

        ArrayAdapter jobType_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, JobType);
        jobType_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_jobtype.setAdapter(jobType_arrayAdapter);

        ArrayAdapter numberOfHires_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, NumberOfHires);
        numberOfHires_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_numberOfHires.setAdapter(numberOfHires_arrayAdapter);

        ArrayAdapter sp_salary_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Sp_salary);
        sp_salary_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_salarytype.setAdapter(sp_salary_arrayAdapter);


        ArrayAdapter Location_arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, Location);
        Location_arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_locationtype.setAdapter(Location_arrayAdapter);

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ftitle = et_title.getText().toString();
                fcompany = et_company.getText().toString();
                flocation = et_location.getText().toString();
                fjobtype = txt_jobtype.getText().toString();
                fjobcategory = et_jobcategory.getText().toString();
                fsalary = et_salary.getText().toString()+""+txt_salary.getText().toString();
                fnoOfHires = txt_noOfHires.getText().toString();
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
                    et_location.setError("Location is required.");
                    return;
                }
                if (TextUtils.isEmpty(fjobtype)) {
                    txt_jobtype.setError("Job type is required.");
                    return;
                }
                if (TextUtils.isEmpty(fjobcategory)) {
                    et_jobcategory.setError("Job category is required.");
                    return;
                }
                if (TextUtils.isEmpty(fsalary)) {
                    et_salary.setError("Salary is required.");
                    return;
                }
                if (TextUtils.isEmpty(fnoOfHires)) {
                    txt_noOfHires.setError("Number of Hires is required.");
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
                addnewjobpost(ftitle,fcompany,flocation,fjobtype, fjobcategory, fsalary,fnoOfHires,fqualification,fdescription);
                navController.navigate(R.id.dashboardFragment);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        jobType = spinner_jobtype.getSelectedItem().toString();
        txt_jobtype.setText(jobType);

        numberOfHires = spinner_numberOfHires.getSelectedItem().toString();
        txt_noOfHires.setText(numberOfHires);

        sp_salary=spinner_salarytype.getSelectedItem().toString();
        txt_salary.setText(sp_salary);

       location =spinner_locationtype.getSelectedItem().toString();
        et_location.setText(location);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

            public void addnewjobpost(String title, String company, String location, String jobtype, String jobcategory, String salary, String noOfHires, String qualification, String description) {

                Map<String, Object> postData = new HashMap<>();
                fuser = firebaseAuth.getCurrentUser().getEmail();
                postData.put("Email",fuser);
                postData.put("JobName", title);
                postData.put("CompanyName", company);
                postData.put("Location", location);
                postData.put("JobType", jobtype);
                postData.put("jobcategory", jobcategory);
                postData.put("Salary", salary);
                postData.put("numberOFHires", noOfHires);
                postData.put("Qualifications", qualification);
                postData.put("Description", description);
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

