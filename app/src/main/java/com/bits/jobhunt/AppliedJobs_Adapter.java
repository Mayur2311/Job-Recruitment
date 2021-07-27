package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppliedJobs_Adapter extends RecyclerView.Adapter<AppliedJobs_Adapter.AppliedViewHolder> {

     ArrayList<Model> appliedDataList;

    public AppliedJobs_Adapter(ArrayList<Model> appliedJobAdapter) {
        this.appliedDataList = appliedJobAdapter;
    }

    @Override
    public AppliedViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_appliedjob_recycler_view, parent, false);
        return new AppliedViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  AppliedJobs_Adapter.AppliedViewHolder holder, int position) {
        holder.user_appliedjob_job_title.setText(appliedDataList.get(position).getJobTitle());
        holder.user_appliedjob_company_name.setText(appliedDataList.get(position).getCompanyName());
        holder.user_appliedjob_company_location.setText(appliedDataList.get(position).getCompanyLocation());
        holder.user_appliedjob_salary.setText(appliedDataList.get(position).getSalary());

        holder.user_appliedjob_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.user_appliedjob_job_title.getContext(), AppliedJobsDetails.class);
                intent.putExtra("CompanyName",appliedDataList.get(position).getCompanyName());
                intent.putExtra("Description",appliedDataList.get(position).getDescription());
                intent.putExtra("JobTitle",appliedDataList.get(position).getJobTitle());
                intent.putExtra("JobType",appliedDataList.get(position).getJobType());
                intent.putExtra("CompanyLocation",appliedDataList.get(position).getCompanyLocation());
                intent.putExtra("Qualification",appliedDataList.get(position).getQualification());
                intent.putExtra("Salary",appliedDataList.get(position).getSalary());
                intent.putExtra("Vacancy",appliedDataList.get(position).getVacancy());
                //intent.putExtra("Position",saveddataList.get(position).getUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.user_appliedjob_job_title.getContext().startActivity(intent);
              }
        });

    }

    @Override
    public int getItemCount() {
        return appliedDataList.size();
    }

    class AppliedViewHolder extends RecyclerView.ViewHolder {
        TextView user_appliedjob_job_title, user_appliedjob_company_name, user_appliedjob_company_location, user_appliedjob_salary;

        public AppliedViewHolder(@NonNull View itemView) {
            super(itemView);
            user_appliedjob_job_title = itemView.findViewById(R.id.user_appliedjob_job_title);
            user_appliedjob_company_name = itemView.findViewById(R.id.user_appliedjob_company_name);
            user_appliedjob_company_location = itemView.findViewById(R.id.user_appliedjob_company_location);
            user_appliedjob_salary = itemView.findViewById(R.id.user_appliedjob_salary);

        }
    }
}
