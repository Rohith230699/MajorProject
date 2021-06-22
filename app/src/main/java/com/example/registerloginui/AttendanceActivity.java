package com.example.registerloginui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public final class AttendanceActivity extends AppCompatActivity {

    float count1;
    float count;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    TextView working, presented;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        progressBar = findViewById(R.id.progressBarAttendance);
        presented = findViewById(R.id.presented);
        working = findViewById(R.id.working);

        firebaseFirestore.collection("NoOfWorkingDays").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                count = 0;
                for(QueryDocumentSnapshot querySnapshot : value)
                {
                    count++;
                }
                firebaseFirestore.collection(googleSignInAccount.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        count1 = 0;
                        for(QueryDocumentSnapshot querySnapshot : value)
                        {
                            count1++;
                        }
                        presented.setText(Integer.toString((int)count1));
                        working.setText(Integer.toString((int)count));
                        float percent = (float)((count1 / count) * 100);
                        if (percent<=50) {
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F50854")));
                        }
                        else if (percent>50 && percent<75) {
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#EED41B")));
                        }
                        else {
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#0AFB3D")));
                        }
                        progressBar.setProgress((int) percent);
                    }
                });
            }
        });
    }
}