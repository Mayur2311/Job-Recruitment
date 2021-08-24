package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminJob_Details extends AppCompatActivity {
    TextView admin_jobdetails_job_title, admin_jobdetails_company_name, admin_jobdetails_company_location, admin_jobdetails_salaryinnumber, admin_jobdetails_jobtype1, admin_jobdetails_vacancynumber, admin_jobdetails_qualificationdetail1, admin_jobdetails_company_details;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    Button backArrow,delete;
    String status,jobname;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job__details);
        admin_jobdetails_job_title = findViewById(R.id.admin_jobdetails_job_title);
        admin_jobdetails_company_name = findViewById(R.id.admin_jobdetails_company_name);
        admin_jobdetails_company_location = findViewById(R.id.admin_jobdetails_company_location);
        admin_jobdetails_salaryinnumber = findViewById(R.id.admin_jobdetails_salaryinnumber);
        admin_jobdetails_jobtype1 = findViewById(R.id.admin_jobdetails_jobtype1);
        admin_jobdetails_vacancynumber = findViewById(R.id.admin_jobdetails_vacancynumber);
        admin_jobdetails_qualificationdetail1 = findViewById(R.id.admin_jobdetails_qualificationdetail1);
        admin_jobdetails_company_details = findViewById(R.id.admin_jobdetails_company_details);

        toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Onclick Data
        admin_jobdetails_job_title.setText(getIntent().getStringExtra("JobName").toString());
        admin_jobdetails_company_name.setText(getIntent().getStringExtra("CompanyName").toString());
        admin_jobdetails_company_location.setText(getIntent().getStringExtra("Location").toString());
        admin_jobdetails_salaryinnumber.setText(getIntent().getStringExtra("Salary").toString());
        admin_jobdetails_jobtype1.setText(getIntent().getStringExtra("JobType").toString());
        admin_jobdetails_vacancynumber.setText(getIntent().getStringExtra("numberOFHires").toString());
        admin_jobdetails_qualificationdetail1.setText(getIntent().getStringExtra("Qualifications").toString());
        admin_jobdetails_company_details.setText(getIntent().getStringExtra("Description").toString());


        //For Applied Job
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        delete = findViewById(R.id.deletepost);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               jobname= getIntent().getStringExtra("JobName").toString();
                status = "deleted";
                db.collection("Jobs").whereEqualTo("JobName",jobname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId());
                                list.add(document.getId());
                            }
                            deleteData(list);
                        }
                    }
                });
                Intent intent = new Intent(getApplication(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteData(List<String> list) {
        WriteBatch batch = db.batch();
        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {
            DocumentReference ref = db.collection("Jobs").document(list.get(k));
            batch.update(ref,"Status","Delete" );
            Toast.makeText(getApplicationContext(), "Job Post is deleted", Toast.LENGTH_SHORT).show();


        }


        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

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








