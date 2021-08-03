package com.bits.jobhunt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.myviewholder>  {


    ArrayList<Model> userlist;


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


    }


    @Override
    public int getItemCount() {

        return userlist.size();

    }



    class myviewholder extends RecyclerView.ViewHolder  {
        TextView userlist_firstname,userlist_lastname,userlist_city, userlist_mobile;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            userlist_firstname = itemView.findViewById(R.id.user_list_firstname);
            userlist_lastname=itemView.findViewById(R.id.user_list_lastname);
            userlist_city = itemView.findViewById(R.id.user_list_city);
            userlist_mobile = itemView.findViewById(R.id.user_list_mobile);
        }


    }
}



