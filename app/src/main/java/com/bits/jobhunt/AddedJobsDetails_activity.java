package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bits.jobhunt.fragments.DashboardFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class AddedJobsDetails_activity extends AppCompatActivity {

     TextView user_addedjobdetails_job_title, user_addedjobdetails_company_name, user_addedjobdetails_company_location;

    FirebaseFirestore firestore;
    String jobTitle,email;
    ArrayList<Model> addeddatalist;
    UserListAdapter userListAdapterObj;
    RecyclerView recview2;
    ConstraintLayout layout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_jobs_details);

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();

        recview2 = findViewById(R.id.userList_addedPost);
        addeddatalist = new ArrayList<>();
        recview2.setLayoutManager(new LinearLayoutManager(getApplication().getApplicationContext()));
        userListAdapterObj =  new UserListAdapter(addeddatalist);
        recview2.setAdapter(userListAdapterObj);

        user_addedjobdetails_job_title = findViewById(R.id.user_addedjobdetails_job_title);
        user_addedjobdetails_company_name = findViewById(R.id.user_addedjobdetails_company_name);
        user_addedjobdetails_company_location = findViewById(R.id.user_addedjobdetails_company_location);


        //Onclick Data
        user_addedjobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());
        user_addedjobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        user_addedjobdetails_company_location.setText(getIntent().getStringExtra("Location").toString());

        layout = findViewById(R.id.AddedJobDetailsLayout);
        jobTitle = getIntent().getStringExtra("JobName");

        firestore.collection("AppliedJob").whereEqualTo("JobTitle",jobTitle ).whereEqualTo("ApplicationStatus","Pending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Model obj=d.toObject(Model.class);
                            addeddatalist.add(obj);
                        }
                         userListAdapterObj.notifyDataSetChanged();

                        if(addeddatalist.isEmpty())
                        {
                            Snackbar snackbar = Snackbar.make(layout,"Currently, No one has applied for this job.", Snackbar.LENGTH_LONG)
                                .setAction("Home",new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {

                                    }
                                });

                            snackbar.show();
                        }

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}