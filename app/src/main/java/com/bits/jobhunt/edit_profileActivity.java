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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class edit_profileActivity extends AppCompatActivity {

    EditText et_firstname, et_lastname, edt_mobile,edt_city,edt_select_PDF, edt_aboutme, edt_education1, edt_education2, edt_education3, edt_experience1, edt_experience2, edt_experience3;
    Button submit_PDF,btn_save;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseUser user ;
    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    Toolbar toolbar;
    String userEmail;
    String imgUrl;
    StorageReference stref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.edit_profile_toolbar);
//      toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        profilePic=findViewById(R.id.profilePic);
        et_firstname =findViewById(R.id.firstName);
        et_lastname =findViewById(R.id.lastName);
        edt_mobile =findViewById(R.id.mobile);
        edt_city=findViewById(R.id.city);
        edt_education1 =findViewById(R.id.edt_education1);
        edt_education2 =findViewById(R.id.edt_education2);
        edt_education3 =findViewById(R.id.edt_education3);
        edt_aboutme =findViewById(R.id.edt_about_me);
        edt_experience1=findViewById(R.id.edt_experience1);
        edt_experience2=findViewById(R.id.edt_experience2);
        edt_experience3=findViewById(R.id.edt_experience3);
        btn_save=findViewById(R.id.btn_save);
        edt_select_PDF=findViewById(R.id.edt_select_PDF);

        submit_PDF=findViewById(R.id.submit_PDF);
        storage=FirebaseStorage.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        db=FirebaseFirestore.getInstance();
        submit_PDF.setEnabled(false);

        user=fAuth.getCurrentUser();
        userEmail = user.getEmail();

        //------------Getting and Setting image on the image view-------------//


        stref = FirebaseStorage.getInstance().getReference("images/"+userEmail);

        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");
             stref.getFile(localfile)
                     .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            profilePic.setImageBitmap(bitmap);

                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }



        //----------------------------------------------------------------------------------------------------//


        documentReference= db.collection("ProfileUpdate").document(user.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){

                    String firstnameResult=task.getResult().getString("firstName");
                    String lastnameResult=task.getResult().getString("lastName");
                    String mobilenumber=task.getResult().getString("mobilenumber");
                    String city=task.getResult().getString("City");

                    et_firstname.setText(firstnameResult);
                    et_lastname.setText(lastnameResult);
                    edt_mobile.setText(mobilenumber);
                    edt_city.setText(city);

                }
                else {
                    Toast.makeText(edit_profileActivity.this,"No Profile",Toast.LENGTH_SHORT).show();
                }
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
        edt_select_PDF.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectPDF();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
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

    public void update(){

        String finame=et_firstname.getText().toString();
        String laname=et_lastname.getText().toString();
        String mn=edt_mobile.getText().toString();
        String ci=edt_city.getText().toString();
        String am=edt_aboutme.getText().toString();
        String ex1=edt_experience1.getText().toString();
        String ex2=edt_experience2.getText().toString();
        String ex3=edt_experience3.getText().toString();
        String e1=edt_education1.getText().toString();
        String e2=edt_education2.getText().toString();
        String e3=edt_education3.getText().toString();
        user=fAuth.getCurrentUser();

        Map<String, Object> updateData = new HashMap<>();

        updateData.put("firstName",finame);
        updateData.put("lastName",laname);
        updateData.put("City",ci);
        updateData.put("mobilenumber",mn);
        updateData.put("Aboutme",am);
        updateData.put("Experience1",ex1);
        updateData.put("Experience2",ex2);
        updateData.put("Experience3", ex3);
        updateData.put("Secondary Education", e1);
        updateData.put("Intermediate", e2);
        updateData.put("Other Education", e3);


        db.collection("ProfileUpdate").document(user.getUid()).set(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(edit_profileActivity.this,"",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(edit_profileActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private void selectPDF()
    {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK &&data.getData()!=null){

            imageUri=data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture(data.getData());
        }

        if(requestCode==12&& resultCode==RESULT_OK &&data.getData()!=null){
            submit_PDF.setEnabled(true);
            edt_select_PDF.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1));
            submit_PDF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPicture(Uri data) {
        final ProgressDialog pd=new ProgressDialog(this);
        user=FirebaseAuth.getInstance().getCurrentUser();
        pd.setTitle("Uploading Image....");
        pd.show();
        String randomKey= user.getEmail();
        StorageReference imagesRef = storageReference.child("images/"+randomKey);

        imagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                Model model = new Model( imageUri.toString());

                db.collection("Upload image").document(user.getEmail()).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(edit_profileActivity.this, "Image Upload", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"failed to upload",Toast.LENGTH_SHORT).show();
            }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent=(100.00* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Percentage:"+(int)progressPercent+"%");
            }
        });
    }

    private void uploadPDFFileFirebase(Uri data)
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("file is loading");
        progressDialog.show();
        StorageReference reference=storageReference.child("uploadPDF" + System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        Model model = new Model(edt_select_PDF.getText().toString(), uri.toString());
                        db.collection("Upload pdf").document(user.getEmail()).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(edit_profileActivity.this, "File Upload", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                             //   Toast.makeText(edit_profileActivity.this,"failed",Toast.LENGTH_SHORT);
                            }
                        });
                    }

                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Updated...."+(int)progress+"%");
            }
        });
    }
}