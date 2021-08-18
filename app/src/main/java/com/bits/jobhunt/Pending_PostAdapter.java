package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Pending_PostAdapter extends RecyclerView.Adapter<Pending_PostAdapter.myviewholder> {
    ArrayList<Model> datalist;

    public Pending_PostAdapter(ArrayList<Model> datalist) {
        this.datalist = datalist;
    }

    @NotNull
    @Override
    public Pending_PostAdapter.myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_postrecycleview, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Pending_PostAdapter.myviewholder holder, int position) {
        holder.pending_job_title.setText(datalist.get(position).getJobName());
        holder.pending_company_name.setText(datalist.get(position).getCompanyName());
        holder.pending_company_location.setText(datalist.get(position).getLocation());
        holder.pending_salary.setText(datalist.get(position).getSalary());

        holder.pending_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.pending_job_title.getContext(), PendingPost_JobDetails.class);
                intent.putExtra("CompanyName", datalist.get(position).getCompanyName());
                intent.putExtra("Description", datalist.get(position).getDescription());
                intent.putExtra("JobName", datalist.get(position).getJobName());
                intent.putExtra("JobType", datalist.get(position).getJobType());
                intent.putExtra("Location", datalist.get(position).getLocation());
                intent.putExtra("jobcategory", datalist.get(position).getJobcategory());
                intent.putExtra("Qualifications", datalist.get(position).getQualifications());
                intent.putExtra("Salary", datalist.get(position).getSalary());
                intent.putExtra("numberOFHires", datalist.get(position).getNumberOFHires());
                intent.putExtra("Email", datalist.get(position).getEmail());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pending_job_title.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
    class myviewholder extends RecyclerView.ViewHolder  {
        TextView pending_job_title, pending_company_name, pending_company_location, pending_salary;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            pending_job_title = itemView.findViewById(R.id.notification_job_title);
            pending_company_name = itemView.findViewById(R.id.pending_company_name);
            pending_company_location = itemView.findViewById(R.id.pending_company_location);
            pending_salary = itemView.findViewById(R.id.pending_salary);
        }


    }
}
