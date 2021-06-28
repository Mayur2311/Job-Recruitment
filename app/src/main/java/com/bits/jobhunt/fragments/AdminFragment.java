package com.example.jobhunt.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jobhunt.AdminActivity;
import com.example.jobhunt.R;
import com.example.jobhunt.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AdminFragment extends Fragment {

    Button user_btn, btn_adminLogIn;
    NavController navController;
    EditText admin_email, admin_password;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String a,e,pass;




    public AdminFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user_btn = view.findViewById(R.id.btn_user);
        navController = Navigation.findNavController(view);
        btn_adminLogIn = view.findViewById(R.id.btn_adminlogin);
        admin_email = view.findViewById(R.id.etadminemail);
        admin_password = view.findViewById(R.id.etadminpassword);

        //--------------------------Firebase components-----------------//


        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.loginFragment);
            }
        });



        db.collection("Admin_credentials").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //  List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Log.d("", document.getId());
                        a = document.getData().get("admin").toString();
                        //Toast.makeText(AdminLogin.this,""+a,Toast.LENGTH_SHORT).show();

                        btn_adminLogIn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                e = admin_email.getText().toString();
                                pass = admin_password.getText().toString();

                                if(e.equals(a))
                                {

                                    firebaseAuth.signInWithEmailAndPassword(e,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(getContext().getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getActivity().getApplicationContext(), AdminActivity.class));

                                            }else {
                                                Toast.makeText(getContext().getApplicationContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                    //    Intent i=new Intent(AdminLogin.this,AdminPanel.class);
                                    //    startActivity(i);
                                }

                            }
                        });



                    }
                }
            }
        });

    }
}