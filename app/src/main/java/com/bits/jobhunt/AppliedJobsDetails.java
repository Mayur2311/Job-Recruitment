package com.bits.jobhunt;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

public class AppliedJobsDetails extends AppCompatActivity {

    TextView user_appliedjobdetails_job_title, user_appliedjobdetails_company_name, user_appliedjobdetails_company_location, user_appliedjobdetails_salaryinnumber, user_appliedjobdetails_jobtype1, user_appliedjobdetails_vacancynumber, user_appliedjobdetails_qualificationdetail1, user_appliedjobdetails_company_details;
    Model objModel = new Model();
    String checkAppstatus;
    Toolbar toolbar;


    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_jobs_details);

        toolbar = findViewById(R.id.applied_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

//         checkAppstatus=getIntent().getStringExtra("ApplicationStatus");
//
//         if(checkAppstatus.equals("Approved")){
//             Toast.makeText(this, "Your application has been approved further notice would be updated", Toast.LENGTH_LONG).show();
//         }
//         else if(checkAppstatus.equals("Disapproved")){
//             Toast.makeText(this, "Sorry better luck next time . Your profile doesn't satisfy for our job requirement", Toast.LENGTH_LONG).show();
//         }
//         else{
//             Toast.makeText(this, "The employer havn't yet viewed your application yet please come back later", Toast.LENGTH_LONG).show();
//         }
//

     }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
