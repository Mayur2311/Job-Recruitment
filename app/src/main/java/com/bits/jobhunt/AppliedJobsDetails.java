package com.bits.jobhunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class AppliedJobsDetails extends AppCompatActivity {

    TextView user_appliedjobdetails_job_title, user_appliedjobdetails_company_name, user_appliedjobdetails_company_location, user_appliedjobdetails_salaryinnumber, user_appliedjobdetails_jobtype1, user_appliedjobdetails_vacancynumber, user_appliedjobdetails_qualificationdetail1, user_appliedjobdetails_company_details;
    Model objModel = new Model();


    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_jobs_details);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        user_appliedjobdetails_job_title = findViewById(R.id.user_appliedjobdetails_job_title);
        user_appliedjobdetails_company_name = findViewById(R.id.user_appliedjobdetails_company_name);
        user_appliedjobdetails_company_location = findViewById(R.id.user_appliedjobdetails_company_location);
        user_appliedjobdetails_salaryinnumber = findViewById(R.id.user_appliedjobdetails_salaryinnumber);
        user_appliedjobdetails_jobtype1 = findViewById(R.id.user_appliedjobdetails_jobtype1);
        user_appliedjobdetails_vacancynumber = findViewById(R.id.user_appliedjobdetails_vacancynumber);
        user_appliedjobdetails_qualificationdetail1 = findViewById(R.id.user_appliedjobdetails_qualificationdetail1);
        user_appliedjobdetails_company_details = findViewById(R.id.user_appliedjobdetails_company_details);

        //Onclick Data
        user_appliedjobdetails_job_title.setText(getIntent().getStringExtra("JobTitle").toString());
        user_appliedjobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        user_appliedjobdetails_company_location.setText(getIntent().getStringExtra("CompanyLocation").toString());
        user_appliedjobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        user_appliedjobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        user_appliedjobdetails_vacancynumber.setText(getIntent().getStringExtra("Vacancy").toString());
        user_appliedjobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualification").toString());
        user_appliedjobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());



    }


}
