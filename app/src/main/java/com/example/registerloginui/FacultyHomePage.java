package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FacultyHomePage extends AppCompatActivity {

    MaterialCardView notification, attendance, profile;
    TextView Faculty;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home_page);

        notification = findViewById(R.id.Notification);
        attendance = findViewById(R.id.Attendance);
        profile = findViewById(R.id.Profile);
        Faculty = findViewById(R.id.FacultyName);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String email = googleSignInAccount.getEmail();

        DocumentReference documentReference = firebaseFirestore.collection("Faculty").document(email);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String firstName = documentSnapshot.get("First Name").toString();
                    String lastName = documentSnapshot.get("Last Name").toString();
                    String Name = firstName+" "+lastName;
                    Faculty.setText(Name);
                }
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyNotification.class));
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyAttendance.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyProfile.class));
            }
        });

    }
}