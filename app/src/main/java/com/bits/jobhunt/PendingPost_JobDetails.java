package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class PendingPost_JobDetails extends AppCompatActivity {
    TextView email,pending_job_title, pending_company_name, pending_company_location, pending_salaryinnumber, pending_jobtype1, pending_vacancynumber, pending_qualificationdetail1, pending_company_details,pending_jobcategory;
    Button approve, disapprove;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String  status,doctitle,jobname,docEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_post_job_details);

        pending_job_title = findViewById(R.id.notification_job_title);
        pending_company_name = findViewById(R.id.pending_company_name);
        pending_company_location = findViewById(R.id.pending_company_location);
        pending_salaryinnumber = findViewById(R.id.pending_salaryinnumber);
        pending_jobtype1 = findViewById(R.id.pending_jobtype1);
        pending_jobcategory = findViewById(R.id.pending_jobcategory1);
        pending_vacancynumber = findViewById(R.id.pending_vacancynumber);
        pending_qualificationdetail1 = findViewById(R.id.pending_qualificationdetail1);
        pending_company_details = findViewById(R.id.pending_company_details);
          email=findViewById(R.id.Pen_email);
        approve = findViewById(R.id.approve);
        disapprove = findViewById(R.id.disapprove);
        //Onclick Data
        pending_job_title.setText(getIntent().getStringExtra("JobName").toString());
        pending_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        pending_company_location.setText(getIntent().getStringExtra("Location").toString());
        pending_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        pending_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        pending_vacancynumber.setText(getIntent().getStringExtra("numberOFHires").toString());
        pending_qualificationdetail1.setText(getIntent().getStringExtra("Qualifications").toString());
        pending_company_details.setText(getIntent().getStringExtra("Description").toString());
        pending_jobcategory.setText(getIntent().getStringExtra("jobcategory").toString());
        email.setText(getIntent().getStringExtra("Email").toString());
        //For Applied Job
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jobname=getIntent().getStringExtra("JobName").toString();
                status = "Approved";
                Toast.makeText(getApplicationContext(), "Job Post is approved", Toast.LENGTH_SHORT).show();
                insertApprovedData(getIntent().getStringExtra("JobName").toString(), getIntent().getStringExtra("CompanyName").toString(), getIntent().getStringExtra("Location").toString(), getIntent().getStringExtra("Salary").toString(), getIntent().getStringExtra("JobType").toString(), getIntent().getStringExtra("numberOFHires").toString(), getIntent().getStringExtra("Qualifications").toString(), getIntent().getStringExtra("jobcategory").toString(), getIntent().getStringExtra("Description").toString(),getIntent().getStringExtra("Email"), status);
                Intent intent = new Intent(getApplication(), AdminActivity.class);
                startActivity(intent);
            }
        });

       disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="Disapproved";
                jobname=getIntent().getStringExtra("JobName").toString();

                db.collection("AddPostData").whereEqualTo("JobName",jobname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId());
                                list.add(document.getId());
                            }
                            disapproveData(list);
                        }
                    }
                });


                /*Notification collection*/
                Map<String, Object> pendingData = new HashMap<>();
                String jobtitle= getIntent().getStringExtra("JobName").toString();
                String company_name=getIntent().getStringExtra("CompanyName").toString();
                String company_location=getIntent().getStringExtra("Location").toString();
                String salary=getIntent().getStringExtra("Salary").toString();
                String jobtype=getIntent().getStringExtra("JobType").toString();
                String vacancy=getIntent().getStringExtra("numberOFHires").toString();
                String qualification=getIntent().getStringExtra("Qualifications").toString();
                String description=getIntent().getStringExtra("jobcategory").toString();
                String jobcategory=getIntent().getStringExtra("Description").toString();
                String Email=getIntent().getStringExtra("Email");
                String Status="Disapproved";
                pendingData.put("JobName", jobtitle);
                pendingData.put("CompanyName", company_name);
                pendingData.put("Location", company_location);
                pendingData.put("Salary", salary);
                pendingData.put("JobType", jobtype);
                pendingData.put("numberOFHires", vacancy);
                pendingData.put("Qualifications", qualification);
                pendingData.put("Description", description);
                pendingData.put("jobcategory",jobcategory);
                pendingData.put("Status", Status);
                pendingData.put("Email", Email);

                db.collection("Notification").document().set(pendingData)
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
                //  Toast.makeText(getApplicationContext(), "Job Post is disapproved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }



    private void disapproveData(List<String> list) {
        WriteBatch batch = db.batch();
        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {
            // Update each list item
            DocumentReference ref = db.collection("AddPostData").document(list.get(k));
            batch.update(ref,"Status","Disapproved" );
            Toast.makeText(getApplicationContext(), "tfgfg", Toast.LENGTH_SHORT).show();
        }


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }




    public void insertApprovedData(String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String qualification,String jobcategory, String description,String Email, String Status) {


            Map<String, Object> pendingData = new HashMap<>();

            pendingData.put("JobName", jobtitle);
            pendingData.put("CompanyName", company_name);
            pendingData.put("Location", company_location);
            pendingData.put("Salary", salary);
            pendingData.put("JobType", jobtype);
            pendingData.put("numberOFHires", vacancy);
            pendingData.put("Qualifications", qualification);
            pendingData.put("Description", description);
            pendingData.put("jobcategory",jobcategory);
            pendingData.put("Status", Status);
            pendingData.put("Email", Email);


            db.collection("Jobs").document().set(pendingData)
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

            db.collection("AddPostData").whereEqualTo("Status", "Pending").whereEqualTo("JobName",jobname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<String> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                          //  docEmail = document.getData().get("Email").toString();
                            Log.d("", document.getId());
                            list.add(document.getId());
                        }
                        updateData(list);
                    }
                }
            });


        db.collection("Notification").document().set(pendingData)
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

        private void updateData(List<String> list) {
            WriteBatch batch = db.batch();
            // Iterate through the list
            for (int k = 0; k < list.size(); k++) {
                // Update each list item
                DocumentReference ref = db.collection("AddPostData").document(list.get(k));
                batch.update(ref,"Status","Approved" );
            }


            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

        }



    }
