package com.bits.jobhunt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job_Details extends AppCompatActivity {
    TextView user_jobdetails_job_title, user_jobdetails_company_name, user_jobdetails_company_location, user_jobdetails_salaryinnumber, user_jobdetails_jobtype1, user_jobdetails_vacancynumber, user_jobdetails_qualificationdetail1, user_jobdetails_company_details;
    Button user_jobdetails_apply, user_jobdetails_save,backArrow;
    FirebaseFirestore db;
    String name;
    FirebaseAuth firebaseAuth;
    String fuser, status, docEmail, doctitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job__details);

        backArrow = findViewById(R.id.backArrow_JobDetails);

        user_jobdetails_job_title = findViewById(R.id.user_jobdetails_job_title);
        user_jobdetails_company_name = findViewById(R.id.user_jobdetails_company_name);
        user_jobdetails_company_location = findViewById(R.id.user_jobdetails_company_location);
        user_jobdetails_salaryinnumber = findViewById(R.id.user_jobdetails_salaryinnumber);
        user_jobdetails_jobtype1 = findViewById(R.id.user_jobdetails_jobtype1);
        user_jobdetails_vacancynumber = findViewById(R.id.user_jobdetails_vacancynumber);
        user_jobdetails_qualificationdetail1 = findViewById(R.id.user_jobdetails_qualificationdetail1);
        user_jobdetails_company_details = findViewById(R.id.user_jobdetails_company_details);
        user_jobdetails_apply = findViewById(R.id.user_jobdetails_apply);
        user_jobdetails_save = findViewById(R.id.user_jobdetails_save);
        //Onclick Data
        user_jobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());
        user_jobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        user_jobdetails_company_location.setText(getIntent().getStringExtra("Location").toString());
        user_jobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        user_jobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        user_jobdetails_vacancynumber.setText(getIntent().getStringExtra("numberOFHires").toString());
        user_jobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualifications").toString());
        user_jobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());

        //For Applied Job
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fuser = firebaseAuth.getCurrentUser().getEmail();

        checkIfDataIsAvailableForSaved();
        checkIfDataIsAvailableForApplied();


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),UserActivity.class);
                startActivity(intent);
            }
        });

        user_jobdetails_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                status = "Applied";
                fuser = firebaseAuth.getCurrentUser().getEmail();
                Toast.makeText(getApplicationContext(), "Your application is submitted", Toast.LENGTH_SHORT).show();
                insertAppliedData(getIntent().getStringExtra("JobName").toString(), getIntent().getStringExtra("CompanyName").toString(), getIntent().getStringExtra("Location").toString(), getIntent().getStringExtra("Salary").toString(), getIntent().getStringExtra("JobType").toString(), getIntent().getStringExtra("numberOFHires").toString(), getIntent().getStringExtra("Qualifications").toString(), getIntent().getStringExtra("Description").toString(), status, fuser);
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);


            }
        });

        user_jobdetails_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "Saved";

                Toast.makeText(getApplicationContext(), "Your application is saved successfully", Toast.LENGTH_SHORT).show();
                insertSavedData(getIntent().getStringExtra("JobName").toString(), getIntent().getStringExtra("CompanyName").toString(), getIntent().getStringExtra("Location").toString(), getIntent().getStringExtra("Salary").toString(), getIntent().getStringExtra("JobType").toString(), getIntent().getStringExtra("numberOFHires").toString(), getIntent().getStringExtra("Qualifications").toString(), getIntent().getStringExtra("Description").toString(), status, fuser);
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);
            }
        });

    }

    public void insertAppliedData(String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String qualification, String description, String Status, String email) {


        Map<String, Object> profileData = new HashMap<>();

        profileData.put("JobTitle", jobtitle);
        profileData.put("CompanyName", company_name);
        profileData.put("CompanyLocation", company_location);
        profileData.put("Salary", salary);
        profileData.put("JobType", jobtype);
        profileData.put("Vacancy", vacancy);
        profileData.put("Qualification", qualification);
        profileData.put("Description", description);
        profileData.put("Status", Status);
        profileData.put("Email", email);
        profileData.put("ApplicationStatus","Pending");


        db.collection("AppliedJob").document().set(profileData)
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

        db.collection("SavedJob").whereEqualTo("Email",fuser).whereEqualTo("Status", "Saved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("", document.getId());
                        list.add(document.getId());
                    }
                    updateData(list);
                }
            }
        });

    }

    private void updateData(List<String> list) {
        WriteBatch batch = db.batch();
        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {
            // Update each list item
            DocumentReference ref = db.collection("SavedJob").document(list.get(k));
            batch.update(ref,"Status","Applied" );
        }


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    public void insertSavedData(String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String qualification, String description, String status, String email) {

        Map<String, Object> profileData = new HashMap<>();

        profileData.put("JobTitle", jobtitle);
        profileData.put("CompanyName", company_name);
        profileData.put("CompanyLocation", company_location);
        profileData.put("Salary", salary);
        profileData.put("JobType", jobtype);
        profileData.put("Vacancy", vacancy);
        profileData.put("Qualification", qualification);
        profileData.put("Description", description);
        profileData.put("Status", status);
        profileData.put("Email", email);


        db.collection("SavedJob").document().set(profileData)
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

    public void checkIfDataIsAvailableForApplied() {
        db.collection("AppliedJob").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //  List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d("", document.getId());
                        docEmail = document.getData().get("Email").toString();
                        doctitle = document.getData().get("JobTitle").toString();

                        if (user_jobdetails_job_title.getText().toString().equals(doctitle) && fuser.equals(docEmail)) {
                            Toast.makeText(Job_Details.this, "You have already applied for this job.", Toast.LENGTH_SHORT).show();
                            user_jobdetails_apply.setVisibility(View.INVISIBLE);
                            user_jobdetails_save.setVisibility(View.INVISIBLE);
                        }

                    }
                }
            }
        });
    }

    public void checkIfDataIsAvailableForSaved() {
        db.collection("SavedJob").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //  List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d("", document.getId());
                        docEmail = document.getData().get("Email").toString();
                        doctitle = document.getData().get("JobTitle").toString();

                        if (user_jobdetails_job_title.getText().toString().equals(doctitle) && fuser.equals(docEmail)) {

                            user_jobdetails_save.setVisibility(View.INVISIBLE);
                        }

                    }
                }
            }
        });

    }
}
