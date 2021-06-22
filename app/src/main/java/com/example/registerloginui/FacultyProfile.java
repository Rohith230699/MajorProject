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

public class FacultyProfile extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Button logout;
    GoogleSignInClient mGoogleSignInClient;
    TextView back, Department, PhoneNumber, Email, DOB, Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        back = findViewById(R.id.bckarrow);
        Department = findViewById(R.id.FacultyDepartment);
        PhoneNumber = findViewById(R.id.FacultyPhone);
        Email = findViewById(R.id.EmailFaculty);
        DOB = findViewById(R.id.dateOfBirthFaculty);
        logout = findViewById(R.id.FacultyLogout);
        Name = findViewById(R.id.FacultyNameProfile);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(FacultyProfile.this, gso);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyHomePage.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finishAffinity();
            }
        });

        String email = googleSignInAccount.getEmail().toString();

        DocumentReference documentReference = firebaseFirestore.collection("Faculty").document(email);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Department.setText(documentSnapshot.get("Department").toString());
                    PhoneNumber.setText(documentSnapshot.get("Phone").toString());
                    Email.setText(documentSnapshot.get("Email Id").toString());
                    DOB.setText(documentSnapshot.get("Date of Birth").toString());
                    String name = documentSnapshot.get("First Name").toString() +" "+documentSnapshot.get("Last Name").toString();
                    Name.setText(name);
                }
            }
        });

    }
}