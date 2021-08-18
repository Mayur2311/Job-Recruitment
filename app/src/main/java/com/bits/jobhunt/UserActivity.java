  package com.bits.jobhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

  public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;

    View headview;
    ImageView headerImage;
    StorageReference storageRef;
    String ueml;
    TextView header_textview_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();
        ueml = firebaseAuth.getCurrentUser().getEmail();

        setupNavigation();

        header_textview_email.setText(ueml);


            storageRef = FirebaseStorage.getInstance().getReference("images/"+ueml);
            try
            {
                File localfile = File.createTempFile("tempfile",".jpg");
                storageRef.getFile(localfile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                headerImage.setImageBitmap(bitmap);
                            }
                        });
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "Failed to retrive image", Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setCheckable(true);

        drawerLayout.closeDrawers();

        int id = item.getItemId();

        if (id == R.id.dash) {
            navController.navigate(R.id.dashboardFragment);
        }
        else if (id == R.id.job_post){}
       else if (id == R.id.savedpost){
           navController.navigate(R.id.saved_JobFragment);
        }
       else if (id == R.id.postadded){
            navController.navigate(R.id.addedPost);
        }
       else if(id==R.id.addnewjobpost){
           navController.navigate(R.id.add_postFragment);
        }
       else if (id == R.id.appliedpost){
           navController.navigate(R.id.applied_postFragment);
        }
       else if (id == R.id.updateprofile){
           Intent intent= new Intent(this,edit_profileActivity.class);
           startActivity(intent);
        }
        else if (id == R.id.notification){
           navController.navigate(R.id.notification);
        }
       else if (id == R.id.userpersona){
           Intent intent= new Intent(this,UserPersona.class);
           startActivity(intent);
        }
       else if (id == R.id.logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    public void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawerDashLayout);
        navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navController = Navigation.findNavController(this, R.id.host_fragment_user);

        headview = navigationView.getHeaderView(0);
        headerImage = headview.findViewById(R.id.userHeadImage);
        header_textview_email = headview.findViewById(R.id.txt_header_email);

        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),edit_profileActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}