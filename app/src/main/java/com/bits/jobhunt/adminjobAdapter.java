package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adminjobAdapter extends RecyclerView.Adapter<adminjobAdapter.myviewholder>{
    ArrayList<Model> datalist;
    public adminjobAdapter(ArrayList<Model> datalist) {
        this.datalist = datalist;
        //this.datalistAll = new ArrayList<>(datalist);
    }

    @NonNull
    @Override
    public adminjobAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_dashboard_recycleview, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminjobAdapter.myviewholder holder, int position) {

        holder.admin_dashboard_job_title.setText(datalist.get(position).getJobName());
        holder.admin_dashboard_company_name.setText(datalist.get(position).getCompanyName());
        holder.admin_dashboard_company_location.setText(datalist.get(position).getLocation());
        holder.admin_dashboard_salary.setText(datalist.get(position).getSalary());

        holder.admin_dashboard_job_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.admin_dashboard_job_title.getContext(), adminJob_Details.class);
                intent.putExtra("CompanyName", datalist.get(position).getCompanyName());
                intent.putExtra("Description", datalist.get(position).getDescription());
                intent.putExtra("JobName", datalist.get(position).getJobName());
                intent.putExtra("JobType", datalist.get(position).getJobType());
                intent.putExtra("Location", datalist.get(position).getLocation());
                intent.putExtra("Qualifications", datalist.get(position).getQualifications());
                intent.putExtra("Salary", datalist.get(position).getSalary());
                intent.putExtra("numberOFHires", datalist.get(position).getNumberOFHires());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.admin_dashboard_job_title.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }



    public void filterList(ArrayList<Model> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }

    // class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener
    class myviewholder extends RecyclerView.ViewHolder  {
        TextView admin_dashboard_job_title, admin_dashboard_company_name, admin_dashboard_company_location, admin_dashboard_salary;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            admin_dashboard_job_title = itemView.findViewById(R.id.admin_dashboard_job_title);
            admin_dashboard_company_name = itemView.findViewById(R.id.admin_dashboard_company_name);
            admin_dashboard_company_location = itemView.findViewById(R.id.admin_dashboard_company_location);
            admin_dashboard_salary = itemView.findViewById(R.id.admin_dashboard_salary);


        }


    }
}




