package com.bits.jobhunt.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bits.jobhunt.R;


public class Job_DetailsFragment extends Fragment {

    TextView user_jobdetails_job_title, user_jobdetails_company_name, user_jobdetails_company_location, user_jobdetails_salaryinnumber, user_jobdetails_jobtype1, user_jobdetails_vacancynumber, user_jobdetails_qualificationdetail1, user_jobdetails_company_details;
    Button user_jobdetails_apply, user_jobdetails_save;

    public Job_DetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_job__details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_jobdetails_job_title = view.findViewById(R.id.user_jobdetails_job_title);
        user_jobdetails_company_name=view.findViewById(R.id.user_jobdetails_company_name);
        user_jobdetails_company_location=view.findViewById(R.id.user_jobdetails_company_location);
        user_jobdetails_salaryinnumber=view.findViewById(R.id.user_jobdetails_salaryinnumber);
        user_jobdetails_jobtype1=view.findViewById(R.id.user_jobdetails_jobtype1);
        user_jobdetails_vacancynumber=view.findViewById(R.id.user_jobdetails_vacancynumber);
        user_jobdetails_qualificationdetail1=view.findViewById(R.id.user_jobdetails_qualificationdetail1);
        user_jobdetails_company_details=view.findViewById(R.id.user_jobdetails_company_details);
        user_jobdetails_apply=view.findViewById(R.id.user_jobdetails_apply);
        user_jobdetails_save=view.findViewById(R.id.user_jobdetails_save);

      /*  user_jobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());*/
    }
}