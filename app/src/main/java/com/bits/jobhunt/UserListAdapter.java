package com.bits.jobhunt;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.myviewholder> {

    ArrayList<Model> datalist;
    FirebaseFirestore db;




    public UserListAdapter(ArrayList<Model> datalist) {
        this.datalist = datalist;

    }

    @NonNull
    @NotNull
    @Override
    public UserListAdapter.myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addedpost_userlist_recyclerview, parent, false);
        return new UserListAdapter.myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull UserListAdapter.myviewholder holder, int position) {

        holder.user_applicantemail.setText(datalist.get(position).getEmail());

        holder.Jobtitle = datalist.get(position).getJobTitle();




        holder.user_applicantemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.user_applicantemail.getContext(), AddedJobs_userListDetails.class);
                i.putExtra("Email", datalist.get(position).getEmail());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.user_applicantemail.getContext().startActivity(i);

            }
        });

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.email=datalist.get(position).getEmail();
                String status = "Approved";
                db = FirebaseFirestore.getInstance();

                db.collection("AppliedJob").whereEqualTo("JobTitle", holder.Jobtitle).whereEqualTo("ApplicationStatus", "Pending").whereEqualTo("Email",holder.email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId());
                                list.add(document.getId());
                            }
                            updateData(list);
                            Intent i = new Intent(holder.approve.getContext(),UserActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            holder.approve.getContext().startActivity(i);

                        }
                    }
                });

            }


            private void updateData(List<String> list) {
                WriteBatch batch = db.batch();
                // Iterate through the list
                for (int k = 0; k < list.size(); k++) {
                    // Update each list item
                    DocumentReference ref = db.collection("AppliedJob").document(list.get(k));
                    batch.update(ref, "ApplicationStatus", "Approved");


                   Toast.makeText(holder.approve.getContext(), "Job Application approved", Toast.LENGTH_SHORT).show();
                }


                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });

        holder.disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.email=datalist.get(position).getEmail();
                db = FirebaseFirestore.getInstance();

                db.collection("AppliedJob").whereEqualTo("JobTitle", holder.Jobtitle).whereEqualTo("ApplicationStatus", "Pending").whereEqualTo("Email",holder.email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId());
                                list.add(document.getId());
                            }
                            updateData(list);
                            Intent i = new Intent(holder.disapprove.getContext(), UserActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            holder.disapprove.getContext().startActivity(i);



                        }
                    }
                });

            }


            private void updateData(List<String> list) {
                WriteBatch batch = db.batch();
                // Iterate through the list
                for (int k = 0; k < list.size(); k++) {
                    // Update each list item
                    DocumentReference ref = db.collection("AppliedJob").document(list.get(k));
                    batch.update(ref, "ApplicationStatus", "Disapproved");

                    Toast.makeText(holder.approve.getContext(), "Application Disapproved", Toast.LENGTH_SHORT).show();
                }


                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

            }
        });
    }




    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView user_applicantemail;
        String Jobtitle,email;

        Button approve,disapprove;
        public myviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            user_applicantemail = itemView.findViewById(R.id.user_applicantemail);
            approve=itemView.findViewById(R.id.approved_btn);
            disapprove=itemView.findViewById(R.id.disapprove_btn);
        }
    }
}
