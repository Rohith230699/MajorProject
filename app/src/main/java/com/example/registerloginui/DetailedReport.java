package com.example.registerloginui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailedReport extends AppCompatActivity {

    float count1;
    float count;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_report);
        String Email_Id = getIntent().getStringExtra("Email_Id");

        progressBar = findViewById(R.id.attendanceIndicator);
        firebaseFirestore.collection("NoOfWorkingDays").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                count = 0;
                for(QueryDocumentSnapshot querySnapshot : value)
                {
                    count++;
                }
                firebaseFirestore.collection(Email_Id).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        count1 = 0;
                        for(QueryDocumentSnapshot querySnapshot : value)
                        {
                            count1++;
                        }
                        float percent = (float)((count1 / count) * 100);
                        if (percent<=50)
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#F50854")));
                        else if (percent>50 && percent<75)
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#EED41B")));
                        else
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#0AFB3D")));
                        progressBar.setProgress((int) percent);

                    }
                });
            }
        });
    }
}