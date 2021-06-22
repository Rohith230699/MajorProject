package com.example.registerloginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class StudentActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView back;

    TextInputEditText calender, firstname, lastname, parentname, studentPhone, parentPhone, rollNumber, password, email;
    AutoCompleteTextView autoCompleteTextView, sectionTextView, yearTextView, departmentTextView;
    Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
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
        parentname = findViewById(R.id.fatherName);
        studentPhone = findViewById(R.id.studentPhone);
        parentPhone = findViewById(R.id.fatherPhone);
        rollNumber = findViewById(R.id.studentRoll);
        email = findViewById(R.id.email);
        nextStep = findViewById(R.id.next2);

        email.setText(googleSignInAccount.getEmail());

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

        sectionTextView = findViewById(R.id.section);
        String[] sections = {"A", "B", "C", "D"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, R.layout.dropdownmenu, sections);
        sectionTextView.setText(null,false);
        sectionTextView.setAdapter(arrayAdapter1);

        yearTextView = findViewById(R.id.year);
        String[] years = {"1", "2", "3", "4"};
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, R.layout.dropdownmenu, years);
        yearTextView.setText(null,false);
        yearTextView.setAdapter(arrayAdapter3);

        departmentTextView = findViewById(R.id.department);
        String[] departments = {"CSE", "Mech", "ECE", "Civil", "IT"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.dropdownmenu, departments);
        departmentTextView.setText(null,false);
        departmentTextView.setAdapter(arrayAdapter2);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    String phoneNumber = "+91"+studentPhone.getText().toString().trim();
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(StudentActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                            Toast.makeText(StudentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {
                                            Toast.makeText(StudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                            Intent intent = new Intent(getApplicationContext(),PhoneOTPVerification.class);
                                            intent.putExtra("StudentNumber",studentPhone.getText().toString());
                                            intent.putExtra("verificationId",verificationId);
                                            intent.putExtra("FirstName",firstname.getText().toString());
                                            intent.putExtra("LastName",lastname.getText().toString());
                                            intent.putExtra("ParentName",parentname.getText().toString());
                                            intent.putExtra("ParentPhone",parentPhone.getText().toString());
                                            intent.putExtra("DOB",calender.getText().toString());
                                            intent.putExtra("Year",yearTextView.getText().toString());
                                            intent.putExtra("Section",sectionTextView.getText().toString());
                                            intent.putExtra("Gender",autoCompleteTextView.getText().toString());
                                            intent.putExtra("Department",departmentTextView.getText().toString());
                                            intent.putExtra("RollNumber",rollNumber.getText().toString());
                                            intent.putExtra("Email",email.getText().toString());
                                            startActivity(intent);
                                        }
                                    })          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                    Intent intent = new Intent(getApplicationContext(), PhoneOTPVerification.class);
                    intent.putExtra("StudentNumber",studentPhone.getText().toString());
                    intent.putExtra("FirstName",firstname.getText().toString());
                    intent.putExtra("LastName",lastname.getText().toString());
                    intent.putExtra("ParentName",parentname.getText().toString());
                    intent.putExtra("ParentPhone",parentPhone.getText().toString());
                    intent.putExtra("DOB",calender.getText().toString());
                    intent.putExtra("Year",yearTextView.getText().toString());
                    intent.putExtra("Section",sectionTextView.getText().toString());
                    intent.putExtra("Gender",autoCompleteTextView.getText().toString());
                    intent.putExtra("Department",departmentTextView.getText().toString());
                    intent.putExtra("RollNumber",rollNumber.getText().toString());
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
        if (parentname.getText().toString().isEmpty()) {
            parentname.requestFocus();
            return false;
        }
        if (studentPhone.getText().toString().isEmpty()) {
            studentPhone.requestFocus();
            return false;
        }
        else
        {
            String phone = studentPhone.getText().toString();
            int mobileChecker = Pattern.matches("^[6-9][0-9]{9}$", phone) ? 1 : -1;
            if (mobileChecker == -1) {
                studentPhone.requestFocus();
                studentPhone.setText("");
                Toast.makeText(this, "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (parentPhone.getText().toString().isEmpty()) {
            parentPhone.requestFocus();
            return false;
        }
        else
        {
            String phone = parentPhone.getText().toString();
            int mobileChecker = Pattern.matches("^[6-9][0-9]{9}$", phone) ? 1 : -1;
            if (mobileChecker == -1) {
                parentPhone.requestFocus();
                parentPhone.setText("");
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
        if (rollNumber.getText().toString().isEmpty()) {
            rollNumber.requestFocus();
            return false;
        }
        if (yearTextView.getText().toString().isEmpty()) {
            yearTextView.requestFocus();
            return false;
        }
        if (sectionTextView.getText().toString().isEmpty()) {
            sectionTextView.requestFocus();
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
        return true;
    }
}