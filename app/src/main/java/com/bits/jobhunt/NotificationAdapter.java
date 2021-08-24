package com.bits.jobhunt;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.notificationViewHolder> {

    ArrayList<Model> NotificationDataList;
    String a,b;
    public NotificationAdapter(ArrayList<Model> NotificationDataList) {
        this.NotificationDataList = NotificationDataList;
    }

    @NotNull
    @Override
    public notificationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recyclerview, parent, false);
        return new notificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.notificationViewHolder holder, int position) {

        holder.notification_jobtitle.setText(NotificationDataList.get(position).getJobName());
        holder.applicationstatus.setText(NotificationDataList.get(position).getApplicationStatus());
        holder.status.setText( NotificationDataList.get(position).getStatus());

        a = NotificationDataList.get(position).getApplicationStatus();
        b = NotificationDataList.get(position).getStatus();



        if (a == null)
        {
            holder.title1.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.title1.setVisibility(View.VISIBLE);
        }

        if (b == null)
        {
            holder.title2.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.title2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return NotificationDataList.size();
    }

    class notificationViewHolder extends RecyclerView.ViewHolder {
        TextView notification_jobtitle, applicationstatus,status, title1,title2;


        public notificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_jobtitle = itemView.findViewById(R.id.notification_job_title);
            applicationstatus = itemView.findViewById(R.id.notification_applicationstatus);
            status=itemView.findViewById(R.id.notification_status);
            title1 = itemView.findViewById(R.id.title_temp);
            title2 = itemView.findViewById(R.id.title_temp2);

        }
    }
}
