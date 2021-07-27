package com.bits.jobhunt;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>  {


    ArrayList<Model> datalist;


    public myadapter(ArrayList<Model> datalist) {
        this.datalist = datalist;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_dashboard_recycleview, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {

        holder.user_dashboard_job_title.setText(datalist.get(position).getJobName());
        holder.user_dashboard_company_name.setText(datalist.get(position).getCompanyName());
        holder.user_dashboard_company_location.setText(datalist.get(position).getLocation());
        holder.user_dashboard_salary.setText(datalist.get(position).getSalary());

        holder.user_dashboard_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.user_dashboard_job_title.getContext(), Job_Details.class);
                intent.putExtra("CompanyName", datalist.get(position).getCompanyName());
                intent.putExtra("Description", datalist.get(position).getDescription());
                intent.putExtra("JobName", datalist.get(position).getJobName());
                intent.putExtra("JobType", datalist.get(position).getJobType());
                intent.putExtra("Location", datalist.get(position).getLocation());
                intent.putExtra("Qualifications", datalist.get(position).getQualifications());
                intent.putExtra("Salary", datalist.get(position).getSalary());
                intent.putExtra("numberOFHires", datalist.get(position).getNumberOFHires());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.user_dashboard_job_title.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }



    class myviewholder extends RecyclerView.ViewHolder  {
        TextView user_dashboard_job_title, user_dashboard_company_name, user_dashboard_company_location, user_dashboard_salary;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            user_dashboard_job_title = itemView.findViewById(R.id.user_dashboard_job_title);
            user_dashboard_company_name = itemView.findViewById(R.id.user_dashboard_company_name);
            user_dashboard_company_location = itemView.findViewById(R.id.user_dashboard_company_location);
            user_dashboard_salary = itemView.findViewById(R.id.user_dashboard_salary);
        }


    }
}

