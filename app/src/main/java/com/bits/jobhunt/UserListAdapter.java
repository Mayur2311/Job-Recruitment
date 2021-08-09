package com.bits.jobhunt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.myviewholder> {

    ArrayList<Model> datalist;

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

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView user_applicantemail;
        public myviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            user_applicantemail = itemView.findViewById(R.id.user_applicantemail);
        }
    }
}
