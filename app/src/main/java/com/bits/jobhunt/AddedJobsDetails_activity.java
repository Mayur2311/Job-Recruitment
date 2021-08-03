package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class AddedJobsDetails_activity extends AppCompatActivity {

    TextView user_addedjobdetails_job_title, user_addedjobdetails_company_name, user_addedjobdetails_company_location, user_addedjobdetails_salaryinnumber, user_addedjobdetails_jobtype1, user_addedjobdetails_vacancynumber, user_addedjobdetails_qualificationdetail1, user_addedjobdetails_company_details;
    Button user_addedjobdetails_apply;
    DocumentReference documentReference;
    FirebaseFirestore db;
    String name;
    FirebaseAuth firebaseAuth;
    String fuser,status;

    Model objModel = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_jobs_details);

        user_addedjobdetails_job_title = findViewById(R.id.user_addedjobdetails_job_title);
        user_addedjobdetails_company_name = findViewById(R.id.user_addedjobdetails_company_name);
        user_addedjobdetails_company_location = findViewById(R.id.user_addedjobdetails_company_location);
        user_addedjobdetails_salaryinnumber = findViewById(R.id.user_addedjobdetails_salaryinnumber);
        user_addedjobdetails_jobtype1 = findViewById(R.id.user_addedjobdetails_jobtype1);
        user_addedjobdetails_vacancynumber = findViewById(R.id.user_addedjobdetails_vacancynumber);
        user_addedjobdetails_qualificationdetail1 = findViewById(R.id.user_addedjobdetails_qualificationdetail1);
        user_addedjobdetails_company_details = findViewById(R.id.user_addedjobdetails_company_details);
//        user_addedjobdetails_apply = findViewById(R.id.user_addedjobdetails_apply);

        //Onclick Data
        user_addedjobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());
        user_addedjobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        user_addedjobdetails_company_location.setText(getIntent().getStringExtra("Location").toString());
        user_addedjobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        user_addedjobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        user_addedjobdetails_vacancynumber.setText(getIntent().getStringExtra("numberOFHires").toString());
        user_addedjobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualifications").toString());
        user_addedjobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());


//        //For Applied Job
//        firebaseAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//
//
//        user_addedjobdetails_apply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                status="Approved";
//                fuser = firebaseAuth.getCurrentUser().getEmail();
//                Toast.makeText(getApplicationContext(), "Your application is submitted", Toast.LENGTH_SHORT).show();
//                insertAppliedData(getIntent().getStringExtra("JobTitle").toString(), getIntent().getStringExtra("CompanyName").toString(), getIntent().getStringExtra("CompanyLocation").toString(), getIntent().getStringExtra("Salary").toString(), getIntent().getStringExtra("JobType").toString(), getIntent().getStringExtra("Vacancy").toString(), getIntent().getStringExtra("Qualification").toString(), getIntent().getStringExtra("Description").toString(),status,fuser);
//                //updateData(view);
//                Intent intent = new Intent(getApplication(), UserActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }


//    public void insertAppliedData (String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String
//            qualification, String description,String status,String email){
//        Map<String, Object> profileData = new HashMap<>();
//        profileData.put("JobTitle", jobtitle);
//        profileData.put("CompanyName", company_name);
//        profileData.put("CompanyLocation", company_location);
//        profileData.put("Salary", salary);
//        profileData.put("JobType", jobtype);
//        profileData.put("Vacancy", vacancy);
//        profileData.put("Qualification", qualification);
//        profileData.put("Description", description);
//        profileData.put("Email",email);
//        profileData.put("Status",status);
//        fuser = firebaseAuth.getCurrentUser().getEmail();
//        db.collection("AppliedJob").document().set(profileData)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplication(), "", Toast.LENGTH_LONG).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplication(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        db.collection("AddPostData").whereEqualTo("Email",fuser).whereEqualTo("Status", "Approved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<String> list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("", document.getId());
//                        list.add(document.getId());
//                    }
//                    updateData(list);
//                }
//            }
//        });
//
//
//    }

//    private void updateData(List<String> list) {
//        WriteBatch batch = db.batch();
//        // Iterate through the list
//        for (int k = 0; k < list.size(); k++) {
//            // Update each list item
//            DocumentReference ref = db.collection("AddPostData").document(list.get(k));
//            batch.update(ref,"Status","Approved" );
//        }
//
//
//        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        });
//
//    }


}