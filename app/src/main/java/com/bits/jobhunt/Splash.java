package com.bits.jobhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Splash extends AppCompatActivity {

      FirebaseFirestore db = FirebaseFirestore.getInstance();
     FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
     FirebaseUser fuser = firebaseAuth.getCurrentUser();



    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        imageView=findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotation);
        imageView.startAnimation(animation);
        imageView.animate().scaleX(0.6f).scaleY(0.6f).setDuration(5000);

        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    if ( !(fuser == null))
                    {
                        Intent i = new Intent(Splash.this, UserActivity.class);
                        finish();
                        startActivity(i);
                    }



                    Intent i = new Intent(Splash.this, LogInActivity.class);
                    finish();
                    startActivity(i);

                }
            }
        };
        timer.start();

    }
}
