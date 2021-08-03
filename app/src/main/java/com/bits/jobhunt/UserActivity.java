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
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();

        setupNavigation();


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
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}