package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SavedJobsDetails_activity extends AppCompatActivity {

    TextView user_savedjobdetails_job_title, user_savedjobdetails_company_name, user_savedjobdetails_company_location, user_savedjobdetails_salaryinnumber, user_savedjobdetails_jobtype1, user_savedjobdetails_vacancynumber, user_savedjobdetails_qualificationdetail1, user_savedjobdetails_company_details;
    Button user_savedjobdetails_apply;
    FirebaseFirestore db;
    String name;
    FirebaseAuth firebaseAuth;
    String fuser;
    Model objModel = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_jobs_details);

        user_savedjobdetails_job_title = findViewById(R.id.user_savedjobdetails_job_title);
        user_savedjobdetails_company_name = findViewById(R.id.user_savedjobdetails_company_name);
        user_savedjobdetails_company_location = findViewById(R.id.user_savedjobdetails_company_location);
        user_savedjobdetails_salaryinnumber = findViewById(R.id.user_savedjobdetails_salaryinnumber);
        user_savedjobdetails_jobtype1 = findViewById(R.id.user_savedjobdetails_jobtype1);
        user_savedjobdetails_vacancynumber = findViewById(R.id.user_savedjobdetails_vacancynumber);
        user_savedjobdetails_qualificationdetail1 = findViewById(R.id.user_savedjobdetails_qualificationdetail1);
        user_savedjobdetails_company_details = findViewById(R.id.user_savedjobdetails_company_details);
        user_savedjobdetails_apply = findViewById(R.id.user_savedjobdetails_apply);

        //Onclick Data
        user_savedjobdetails_job_title.setText(getIntent().getStringExtra("JobTitle").toString());
        user_savedjobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        user_savedjobdetails_company_location.setText(getIntent().getStringExtra("CompanyLocation").toString());
        user_savedjobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        user_savedjobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        user_savedjobdetails_vacancynumber.setText(getIntent().getStringExtra("Vacancy").toString());
        user_savedjobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualification").toString());
        user_savedjobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());


        //For Applied Job
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user_savedjobdetails_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your application is submitted", Toast.LENGTH_SHORT).show();
                insertAppliedData(getIntent().getStringExtra("JobTitle").toString(), getIntent().getStringExtra("CompanyName").toString(), getIntent().getStringExtra("CompanyLocation").toString(), getIntent().getStringExtra("Salary").toString(), getIntent().getStringExtra("JobType").toString(), getIntent().getStringExtra("Vacancy").toString(), getIntent().getStringExtra("Qualification").toString(), getIntent().getStringExtra("Description").toString());
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);

            }
        });
    }


    public void insertAppliedData (String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String
            qualification, String description){

        fuser = firebaseAuth.getCurrentUser().getUid();

        Map<String, Object> profileData = new HashMap<>();

        profileData.put("JobTitle", jobtitle);
        profileData.put("CompanyName", company_name);
        profileData.put("CompanyLocation", company_location);
        profileData.put("Salary", salary);
        profileData.put("JobType", jobtype);
        profileData.put("Vacancy", vacancy);
        profileData.put("Qualification", qualification);
        profileData.put("Description", description);

        db.collection("AppliedJob").document(fuser).collection("user_appliedJobs").document().set(profileData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(), "", Toast.LENGTH_LONG).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}


