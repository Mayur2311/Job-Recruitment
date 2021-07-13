package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class edit_profileActivity extends AppCompatActivity {
    EditText et_firstname, et_lastname, edt_mobile,edt_city,edt_select_PDF, edt_aboutme, edt_education1, edt_education2, edt_education3, edt_experience1, edt_experience2, edt_experience3;
    Button submit_PDF,btn_save;
    StorageReference storageReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    FirebaseUser user;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
        storageReference= FirebaseStorage.getInstance().getReference();
        db=FirebaseFirestore.getInstance();
        submit_PDF.setEnabled(false);
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
                editProfile();
            }
        });

    }
@Override
 protected void onStart(){
        super.onStart();
         user=fAuth.getCurrentUser();
        // String currentuid=user.getUid();
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


                 }else{
                     Toast.makeText(edit_profileActivity.this,"No Profile",Toast.LENGTH_SHORT).show();
                 }


             }
         });

}
   private void editProfile() {
        String finame=et_firstname.getText().toString();
        String laname=et_lastname.getText().toString();
        String mn=edt_mobile.getText().toString();
        String ci=edt_city.getText().toString();
       final DocumentReference sDoc=db.collection("ProfileUpdate").document("user.getUid()");
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
               // DocumentSnapshot snapshot = transaction.get(sfDocRef);


                transaction.update(sDoc, "firstName", finame);
                transaction.update(sDoc,"lastName",laname);
                transaction.update(sDoc,"City",ci);
                transaction.update(sDoc,"mobilenumber",mn);

                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(edit_profileActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }
                });






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
                        db.collection("Upload pdf").document().set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(edit_profileActivity.this, "File Upload", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(edit_profileActivity.this,"failed",Toast.LENGTH_SHORT);
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

   /* public void insertAppliedData (String jobtitle, String company_name, String company_location, String salary, String jobtype, String vacancy, String
            qualification, String description){

        fuser = firebaseAuth.getCurrentUser().getUid();

        Map<String, Object> profileData = new HashMap<>();

        profileData.put("JobTitle", jobtitle);
        profileData.put("CompanyName", company_name);
        profileData.put("CompanyLocation", company_location);
        profileData.put("Salary", salary);
        profileData.put("JobType", jobtype);
        profileData.put("Vacancy", vacancy);
        profileData.put("Qualification", qualification);
        profileData.put("Description", description);

        db.collection("AppliedJob").document(fuser).collection("user_appliedJobs").document().set(profileData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(), "", Toast.LENGTH_LONG).show();

                        deleteJob();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }*/
}