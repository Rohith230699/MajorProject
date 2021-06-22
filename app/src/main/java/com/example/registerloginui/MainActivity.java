package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    Thread timer;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null) {

            lottieAnimationView = findViewById(R.id.splash);

            lottieAnimationView.animate();

            timer = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this){
                            wait(2000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };
            timer.start();

            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
            String user = googleSignInAccount.getEmail();
            DocumentReference documentReference = firebaseFirestore.collection("Students").document(user);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        startActivity(new Intent(getApplicationContext(), StudentHomePage.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), FacultyHomePage.class));
                        finish();
                    }
                }
            });
        }else {
            lottieAnimationView = findViewById(R.id.splash);

            lottieAnimationView.animate();

            timer = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this){
                            wait(2000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            };
            timer.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}