package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideExtension;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UserPersona extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth fauth;
    TextView t_firstname, t_lastname, t_mobile,t_city,t_select_PDF, t_aboutme, t_education1, t_education2, t_education3, t_experience1, t_experience2, t_experience3;

    DocumentReference documentReference;
    FirebaseUser user;
    ImageView userPicture;
    String imgUrl;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_persona);

        toolbar = findViewById(R.id.user_persona_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        t_firstname =findViewById(R.id.firstName);
        t_lastname =findViewById(R.id.lastName);
        t_mobile =findViewById(R.id.mobile);
        t_city=findViewById(R.id.city);
        t_education1 =findViewById(R.id.txt_education1);
        t_education2 =findViewById(R.id.txt_education2);
        t_education3 =findViewById(R.id.txt_education3);
        t_aboutme =findViewById(R.id.txt_about_me);
        t_experience1=findViewById(R.id.txt_experience1);
        t_experience2=findViewById(R.id.txt_experience2);
        t_experience3=findViewById(R.id.txt_experience3);
        userPicture = findViewById(R.id.fetchimages);

        fauth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        user=fauth.getCurrentUser();

        documentReference= db.collection("ProfileUpdate").document(user.getUid());

        db.collection("Upload image").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists())
                {
                    imgUrl = task.getResult().getString("imageurl");

                       Glide.with(getApplicationContext())
                            .load(imgUrl)
                            .into(userPicture);
                }
            }
        });

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){

                    String firstnameResult=task.getResult().getString("firstName");
                    String lastnameResult=task.getResult().getString("lastName");
                    String mobilenumber=task.getResult().getString("mobilenumber");
                    String city=task.getResult().getString("City");
                    String taboutme=task.getResult().getString("Aboutme");
                    String edu1=task.getResult().getString("Secondary Education");
                    String edu2=task.getResult().getString("Intermediate");
                    String edu3=task.getResult().getString("Other Education");
                    String exp1=task.getResult().getString("Experience1");
                    String exp2=task.getResult().getString("Experience2");
                    String exp3=task.getResult().getString("Experience3");

                    t_firstname.setText(firstnameResult);
                    t_lastname.setText(lastnameResult);
                    t_mobile.setText(mobilenumber);
                    t_city.setText(city);
                    t_aboutme.setText(taboutme);
                    t_education1.setText(edu1);
                    t_education2.setText(edu2);
                    t_education3.setText(edu3);
                    t_experience1.setText(exp1);
                    t_experience2.setText(exp2);
                    t_experience3.setText(exp3);

                }else{
                    Toast.makeText(UserPersona.this,"No Profile",Toast.LENGTH_SHORT).show();
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


