package com.bits.jobhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
        holder.user_applidejob_company_location.setText(appliedDataList.get(position).getCompanyLocation());
        holder.user_appliedjob_salary.setText(appliedDataList.get(position).getSalary());

    }

    @Override
    public int getItemCount() {
        return appliedDataList.size();
    }

    class AppliedViewHolder extends RecyclerView.ViewHolder {
        TextView user_appliedjob_job_title, user_appliedjob_company_name, user_applidejob_company_location, user_appliedjob_salary;

        public AppliedViewHolder(@NonNull View itemView) {
            super(itemView);
            user_appliedjob_job_title = itemView.findViewById(R.id.user_added_job_title);
            user_appliedjob_company_name = itemView.findViewById(R.id.user_appliedjob_company_name);
            user_applidejob_company_location = itemView.findViewById(R.id.user_appliedjob_company_location);
            user_appliedjob_salary = itemView.findViewById(R.id.user_appliedjob_salary);


        }


    }
}
