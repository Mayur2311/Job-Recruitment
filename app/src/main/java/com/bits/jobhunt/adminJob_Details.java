package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adminJob_Details extends AppCompatActivity {
    TextView admin_jobdetails_job_title, admin_jobdetails_company_name, admin_jobdetails_company_location, admin_jobdetails_salaryinnumber, admin_jobdetails_jobtype1, admin_jobdetails_vacancynumber, admin_jobdetails_qualificationdetail1, admin_jobdetails_company_details;
       FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    Button backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job__details);
        admin_jobdetails_job_title = findViewById(R.id.admin_jobdetails_job_title);
        admin_jobdetails_company_name = findViewById(R.id.admin_jobdetails_company_name);
        admin_jobdetails_company_location = findViewById(R.id.admin_jobdetails_company_location);
        admin_jobdetails_salaryinnumber = findViewById(R.id.admin_jobdetails_salaryinnumber);
        admin_jobdetails_jobtype1 = findViewById(R.id.admin_jobdetails_jobtype1);
        admin_jobdetails_vacancynumber = findViewById(R.id.admin_jobdetails_vacancynumber);
        admin_jobdetails_qualificationdetail1 = findViewById(R.id.admin_jobdetails_qualificationdetail1);
        admin_jobdetails_company_details = findViewById(R.id.admin_jobdetails_company_details);

        //Onclick Data
        admin_jobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());
        admin_jobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        admin_jobdetails_company_location.setText(getIntent().getStringExtra("Location").toString());
        admin_jobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        admin_jobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        admin_jobdetails_vacancynumber.setText(getIntent().getStringExtra("numberOFHires").toString());
        admin_jobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualifications").toString());
        admin_jobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());

        //For Applied Job
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        backArrow=findViewById(R.id.backArrow_adminJobDetails);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),AdminActivity.class);
                startActivity(intent);
            }
        });



    }
}


