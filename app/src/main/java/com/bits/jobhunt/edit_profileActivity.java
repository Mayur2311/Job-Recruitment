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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class edit_profileActivity extends AppCompatActivity {
    EditText edt_profilename, edt_mobile,edt_city,edt_select_PDF, edt_aboutme, edt_education1, edt_education2, edt_education3, edt_experience1, edt_experience2, edt_experience3;
    Button submit_PDF,btn_save;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edt_profilename =findViewById(R.id.name);
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

        // readFireStore();
        submit_PDF=findViewById(R.id.submit_PDF);
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        submit_PDF.setEnabled(false);
        edt_select_PDF.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectPDF();
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

  /*  public void readFireStore() {
        user = fAuth.getCurrentUser();

        DocumentReference docRef = firebaseFirestore.collection("ProfileUpdate").document(user.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    String Name = documentSnapshot.getString("firstName")+documentSnapshot.getString("lastName");
                    String City = documentSnapshot.getString("City");
                    String mobile=documentSnapshot.getString("mobilenumber");

                    edt_mobile.setText(mobile);
                    edt_city.setText(City);
                    edt_profilename.setText(Name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(edit_profileActivity.this, "error in data importing", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


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
                        firebaseFirestore.collection("Upload pdf").document().set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}