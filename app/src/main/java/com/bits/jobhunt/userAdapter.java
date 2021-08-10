package com.bits.jobhunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.myviewholder>  {


    ArrayList<Model> userlist;
    FirebaseUser user;

    public userAdapter(ArrayList<Model> userlist) {
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_recycleview, parent, false);
        return new myviewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull userAdapter.myviewholder holder, int position) {

        holder.userlist_firstname.setText(userlist.get(position).getFirstName());
        holder.userlist_lastname.setText(userlist.get(position).getLastName());
        holder.userlist_city.setText(userlist.get(position).getCity());
        holder.userlist_mobile.setText(userlist.get(position).getMobilenumber());
        holder.userlist_email.setText(userlist.get(position).getEmail());



    }


    @Override
    public int getItemCount() {

        return userlist.size();

    }



    class myviewholder extends RecyclerView.ViewHolder  {
        TextView userlist_firstname,userlist_lastname,userlist_city, userlist_mobile,userlist_email;



        public myviewholder(@NonNull View itemView) {
            super(itemView);
            userlist_firstname = itemView.findViewById(R.id.user_list_firstname);
            userlist_lastname=itemView.findViewById(R.id.user_list_lastname);
            userlist_city = itemView.findViewById(R.id.user_list_city);
            userlist_mobile = itemView.findViewById(R.id.user_list_mobile);
             userlist_email=itemView.findViewById(R.id.user_list_email);

        }


    }
}



