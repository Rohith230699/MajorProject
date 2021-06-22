package com.example.registerloginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FacultyOTPVerify extends AppCompatActivity {

    private EditText inputCode1;
    private EditText inputCode2;
    private EditText inputCode3;
    private EditText inputCode4;
    private EditText inputCode5;
    private EditText inputCode6;

    Button verify;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_o_t_p_verify);

        String FirstName = getIntent().getStringExtra("FirstName");
        String LastName = getIntent().getStringExtra("LastName");
        String MiddleName = getIntent().getStringExtra("MiddleName");
        String DOB = getIntent().getStringExtra("DOB");
        String Department = getIntent().getStringExtra("Department");
        String Gender = getIntent().getStringExtra("Gender");
        String Phone = getIntent().getStringExtra("mobile");
        String Email = getIntent().getStringExtra("Email");
        String Password = getIntent().getStringExtra("Password");

        inputCode1 = findViewById(R.id.inputcode1);
        inputCode2 = findViewById(R.id.inputcode2);
        inputCode3 = findViewById(R.id.inputcode3);
        inputCode4 = findViewById(R.id.inputcode4);
        inputCode5 = findViewById(R.id.inputcode5);
        inputCode6 = findViewById(R.id.inputcode6);

        verify = findViewById(R.id.next3);

        String verificationId = getIntent().getStringExtra("verificationId");

        setupOTPInputs();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCode1.getText().toString().isEmpty()
                        || inputCode2.getText().toString().isEmpty()
                        || inputCode3.getText().toString().isEmpty()
                        || inputCode4.getText().toString().isEmpty()
                        || inputCode5.getText().toString().isEmpty()
                        || inputCode6.getText().toString().isEmpty()){
                    Toast.makeText(FacultyOTPVerify.this, "Please Enter valid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inputCode1.getText().toString()+inputCode2.getText().toString()+inputCode3.getText().toString()+inputCode4.getText().toString()+inputCode5.getText().toString()+inputCode6.getText().toString();
                if (verificationId != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        UploadToFirebase();
                                    }else {
                                        Toast.makeText(FacultyOTPVerify.this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            private void UploadToFirebase() {
                DocumentReference documentReference = firebaseFirestore.collection("Faculty").document(Email);
                HashMap<String,String> Users = new HashMap<>();
                Users.put("First Name",FirstName);
                Users.put("Last Name",LastName);
                Users.put("Phone",Phone);
                Users.put("Middle Name",MiddleName);
                Users.put("Date of Birth",DOB);
                Users.put("Department",Department);
                Users.put("Email Id",Email);
                Users.put("Gender",Gender);
                Users.put("Password",Password);

                documentReference.set(Users).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),FacultyHomePage.class));
                        finishAffinity();
                    }
                });

            }
        });


    }

    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}