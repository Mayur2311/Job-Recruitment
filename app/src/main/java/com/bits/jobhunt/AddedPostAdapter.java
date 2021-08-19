package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddedPostAdapter extends RecyclerView.Adapter<AddedPostAdapter.addedviewholder> {

    ArrayList<Model> addeddatalist;

    public AddedPostAdapter(ArrayList<Model> saveddata) {
        this.addeddatalist = saveddata;
    }

    @NonNull
    @Override
    public addedviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_added_jobs_recycleview, parent, false);
        return new addedviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AddedPostAdapter.addedviewholder holder, int position) {
        holder.user_addedjob_job_title.setText(addeddatalist.get(position).getJobName());
        holder.user_addedjob_company_name.setText(addeddatalist.get(position).getCompanyName());
        holder.user_addedjob_company_location.setText(addeddatalist.get(position).getLocation());
        holder.user_addedjob_salary.setText(addeddatalist.get(position).getSalary());

        holder.user_addedjob_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.user_addedjob_job_title.getContext(), AddedJobsDetails_activity.class);
                intent.putExtra("CompanyName",addeddatalist.get(position).getCompanyName());
                intent.putExtra("Description",addeddatalist.get(position).getDescription());
                intent.putExtra("JobName",addeddatalist.get(position).getJobName());
                intent.putExtra("JobType",addeddatalist.get(position).getJobType());
                intent.putExtra("Location",addeddatalist.get(position).getLocation());
                intent.putExtra("Qualifications",addeddatalist.get(position).getQualifications());
                intent.putExtra("Salary",addeddatalist.get(position).getSalary());
                intent.putExtra("numberOFHires",addeddatalist.get(position).getNumberOFHires());
                intent.putExtra("Email",addeddatalist.get(position).getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.user_addedjob_job_title.getContext().startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {
        return addeddatalist.size();
    }

    class addedviewholder extends RecyclerView.ViewHolder {
        TextView user_addedjob_job_title, user_addedjob_company_name, user_addedjob_company_location, user_addedjob_salary;

        public addedviewholder(@NonNull View itemView) {
            super(itemView);
            user_addedjob_job_title = itemView.findViewById(R.id.user_addedjob_job_title);
            user_addedjob_company_name = itemView.findViewById(R.id.user_addedjob_company_name);
            user_addedjob_company_location = itemView.findViewById(R.id.user_addedjob_company_location);
            user_addedjob_salary = itemView.findViewById(R.id.user_addedjob_salary);


        }

    }
}