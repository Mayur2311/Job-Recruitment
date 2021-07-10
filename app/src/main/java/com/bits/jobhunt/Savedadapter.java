package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Savedadapter extends RecyclerView.Adapter<Savedadapter.savedviewholder> {

    ArrayList<Model> saveddataList;

    public Savedadapter(ArrayList<Model> saveddata) {
        this.saveddataList = saveddata;
    }
    @NonNull

    @Override
    public savedviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_saved_jobs_recycleview, parent, false);
        return new savedviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  Savedadapter.savedviewholder holder, int position) {
        holder.user_savedjob_job_title.setText(saveddataList.get(position).getJobTitle());
        holder.user_savedjob_company_name.setText(saveddataList.get(position).getCompanyName());
        holder.user_savedjob_company_location.setText(saveddataList.get(position).getCompanyLocation());
        holder.user_savedjob_salary.setText(saveddataList.get(position).getSalary());

       holder.user_savedjob_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.user_savedjob_job_title.getContext(), SavedJobsDetails_activity.class);
                intent.putExtra("CompanyName",saveddataList.get(position).getCompanyName());
                intent.putExtra("Description",saveddataList.get(position).getDescription());
                intent.putExtra("JobTitle",saveddataList.get(position).getJobTitle());
                intent.putExtra("JobType",saveddataList.get(position).getJobType());
                intent.putExtra("CompanyLocation",saveddataList.get(position).getCompanyLocation());
                intent.putExtra("Qualification",saveddataList.get(position).getQualification());
                intent.putExtra("Salary",saveddataList.get(position).getSalary());
                intent.putExtra("Vacancy",saveddataList.get(position).getVacancy());
                intent.putExtra("Position",saveddataList.get(position).getUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.user_savedjob_job_title.getContext().startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return saveddataList.size();
    }

    class savedviewholder extends RecyclerView.ViewHolder {
        TextView user_savedjob_job_title, user_savedjob_company_name, user_savedjob_company_location, user_savedjob_salary;

        public savedviewholder(@NonNull View itemView) {
            super(itemView);
            user_savedjob_job_title = itemView.findViewById(R.id.user_savedjob_job_title);
            user_savedjob_company_name = itemView.findViewById(R.id.user_savedjob_company_name);
            user_savedjob_company_location = itemView.findViewById(R.id.user_savedjob_company_location);
            user_savedjob_salary = itemView.findViewById(R.id.user_savedjob_salary);


        }

    }
}