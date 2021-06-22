package com.example.registerloginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class FacultyActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView back;

    TextInputEditText calender, firstname, lastname, middlename, Phone, password, email;
    AutoCompleteTextView autoCompleteTextView, departmentTextView;
    Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        back = findViewById(R.id.textView3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SelectionActivity.class));
            }
        });

        calender = findViewById(R.id.studentDOB);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        middlename = findViewById(R.id.fatherName);
        Phone = findViewById(R.id.studentPhone);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        nextStep = findViewById(R.id.next2);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select your Date of Birth");
        MaterialDatePicker materialDatePicker = builder.build();
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "Date_Picker");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                calender.setText(materialDatePicker.getHeaderText());
            }
        });

        autoCompleteTextView = findViewById(R.id.gender);
        String[] gender = {"Male", "Female"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdownmenu, gender);
        autoCompleteTextView.setText(null,false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        departmentTextView = findViewById(R.id.department);
        String[] departments = {"CSE", "Mech", "ECE", "Civil", "IT"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.dropdownmenu, departments);
        departmentTextView.setText(null,false);
        departmentTextView.setAdapter(arrayAdapter2);nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    String phoneNumber = "+91"+Phone.getText().toString().trim();
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(FacultyActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            Toast.makeText(FacultyActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            Toast.makeText(FacultyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            Intent intent = new Intent(getApplicationContext(),FacultyOTPVerify.class);
                                            intent.putExtra("mobile",Phone.getText().toString());
                                            intent.putExtra("verificationId",verificationId);
                                            intent.putExtra("FirstName",firstname.getText().toString());
                                            intent.putExtra("LastName",lastname.getText().toString());
                                            intent.putExtra("MiddleName",middlename.getText().toString());
                                            intent.putExtra("DOB",calender.getText().toString());
                                            intent.putExtra("Gender",autoCompleteTextView.getText().toString());
                                            intent.putExtra("Department",departmentTextView.getText().toString());
                                            intent.putExtra("Email",email.getText().toString());
                                            intent.putExtra("Password",password.getText().toString());
                                            startActivity(intent);
                                        }
                                    })          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                    Intent intent = new Intent(getApplicationContext(), FacultyOTPVerify.class);
                    intent.putExtra("mobile",Phone.getText().toString());
                    intent.putExtra("FirstName",firstname.getText().toString());
                    intent.putExtra("LastName",lastname.getText().toString());
                    intent.putExtra("MiddleName",middlename.getText().toString());
                    intent.putExtra("Password",password.getText().toString());
                    intent.putExtra("DOB",calender.getText().toString());
                    intent.putExtra("Gender",autoCompleteTextView.getText().toString());
                    intent.putExtra("Department",departmentTextView.getText().toString());
                    intent.putExtra("Email",email.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    private boolean validateInput() {
        if (firstname.getText().toString().isEmpty()) {
            firstname.requestFocus();
            return false;
        }
        if (lastname.getText().toString().isEmpty()) {
            lastname.requestFocus();
            return false;
        }
        if (middlename.getText().toString().isEmpty()) {
            middlename.requestFocus();
            return false;
        }
        if (Phone.getText().toString().isEmpty()) {
            Phone.requestFocus();
            return false;
        }
        else
        {
            String phone = Phone.getText().toString();
            int mobileChecker = Pattern.matches("^[6-9][0-9]{9}$", phone) ? 1 : -1;
            if (mobileChecker == -1) {
                Phone.requestFocus();
                Phone.setText("");
                Toast.makeText(this, "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (autoCompleteTextView.getText().toString().isEmpty()) {
            autoCompleteTextView.requestFocus();
            return false;
        }
        if (calender.getText().toString().isEmpty()) {
            calender.requestFocus();
            return false;
        }
        if (departmentTextView.getText().toString().isEmpty()) {
            departmentTextView.requestFocus();
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.requestFocus();
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            return false;
        }
        return true;
    }
}