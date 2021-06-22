package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {


    TextView back, Name, RollNumber, Department, Section, PhoneNumber, ParentName, ParentPhone, Email, DOB;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Button logout;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.textView28);
        Name = findViewById(R.id.nameProfile);
        RollNumber = findViewById(R.id.RollNumberStudent);
        Department = findViewById(R.id.DepartmentStudent);
        Section = findViewById(R.id.SectionStudent);
        PhoneNumber = findViewById(R.id.PhoneStudent2);
        ParentName = findViewById(R.id.ParentName);
        ParentPhone = findViewById(R.id.parentPhone);
        Email = findViewById(R.id.EmailStudent);
        DOB = findViewById(R.id.DateOfBirth);
        logout = findViewById(R.id.buttonLogout);


        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(ProfileActivity.this, gso);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finishAffinity();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentHomePage.class));
                finish();
            }
        });

        String email = googleSignInAccount.getEmail().toString();

        DocumentReference documentReference = firebaseFirestore.collection("Students").document(email);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    RollNumber.setText(documentSnapshot.get("RollNumber").toString());
                    Department.setText(documentSnapshot.get("Department").toString());
                    Section.setText(documentSnapshot.get("Section").toString());
                    PhoneNumber.setText(documentSnapshot.get("Student Phone").toString());
                    ParentName.setText(documentSnapshot.get("Parent Name").toString());
                    ParentPhone.setText(documentSnapshot.get("Parent Phone").toString());
                    Email.setText(documentSnapshot.get("Email_Id").toString());
                    DOB.setText(documentSnapshot.get("Date of Birth").toString());
                    String name = documentSnapshot.get("First Name").toString() +" "+documentSnapshot.get("Last Name").toString();
                    Name.setText(name);
                }
            }
        });

    }
}