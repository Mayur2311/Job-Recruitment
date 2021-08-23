package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddedJobs_userListDetails extends AppCompatActivity {

    DocumentReference documentReference;
    FirebaseFirestore db;
    String email;
    FirebaseUser user;
    String pdfUrl;
    ImageView userProfilePic;
    StorageReference storageReference;
    TextView firstname, lastname, City, mobile_no, about_text, experience_text1, experience_text2, experience_text3, Education_text1, Education_text2, Education_text3,resume_title, resume_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_jobs_user_list_details);

        userProfilePic = findViewById(R.id.userProfilePic);
        firstname = findViewById(R.id.firstName_addedJob_userList);
        lastname = findViewById(R.id.lastName_addedJob_userList);
        City = findViewById(R.id.City__addedJob_userList);
        mobile_no = findViewById(R.id.mobile_no_addedJob_userList);
        about_text = findViewById(R.id.about_text);
        experience_text1 = findViewById(R.id.experience_text1);
        experience_text2 = findViewById(R.id.experience_text2);
        experience_text3 = findViewById(R.id.experience_text3);
        Education_text1 = findViewById(R.id.Education_text1);
        Education_text2 = findViewById(R.id.Education_text2);
        Education_text3 = findViewById(R.id.Education_text3);
        resume_title = findViewById(R.id.resume_title);
        resume_file = findViewById(R.id.resume_file);

        email = getIntent().getStringExtra("Email");
        user=FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("images/"+email);

        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            userProfilePic.setImageBitmap(bitmap);
                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }

        //--------------------------
        storageReference = FirebaseStorage.getInstance().getReference("uploadPDF/"+email+"/.pdf");

        try
        {
            File localfile = File.createTempFile("Resume",".pdf");

            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            String name = localfile.getName();
                            resume_file.setText(name);
                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }

        //-----------------------  fetching url  ----------------------------------//

        resume_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Upload pdf").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if(task.getResult().exists())
                        {
                            pdfUrl = task.getResult().getString("url");

                            Intent intent = new Intent(getApplicationContext(), PdfViewer.class);
                            intent.putExtra("url", pdfUrl);
                            startActivity(intent);
                        }
                    }
                });


            }
        });

        //---------------------------------------------------------------------------------//

        //---------------------------------//
        db.collection("ProfileUpdate").whereEqualTo("Email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.d("", document.getId());
                        list.add(document.getId());
                        Toast.makeText(AddedJobs_userListDetails.this, document.getId(), Toast.LENGTH_SHORT).show();

                        firstname.setText(document.get("firstName").toString());
                        lastname.setText(document.get("lastName").toString());

                        mobile_no.setText(document.get("mobilenumber").toString());
                        City.setText(document.get("City").toString());
                        about_text.setText(document.get("Aboutme").toString());
                        experience_text1.setText(document.get("Experience1").toString());
                        experience_text2.setText(document.get("Experience2").toString());
                        experience_text3.setText(document.get("Experience3").toString());
                        Education_text1.setText(document.get("Secondary Education").toString());
                        Education_text2.setText(document.get("Intermediate").toString());
                        Education_text3.setText(document.get("Other Education").toString());

                    }
                }
            }
            });




}
}

