package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MakeNotification extends AppCompatActivity {

    MaterialCardView student, faculty;
    int flag1 = 0, flag2 =0, status = 0;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Button sendNotify;
    TextInputEditText title, note, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_notification);

        student = findViewById(R.id.cardViewStudent);
        faculty = findViewById(R.id.cardViewFaculty);
        title = findViewById(R.id.TitleNotification);
        note = findViewById(R.id.NotificationNote);
        description = findViewById(R.id.NotificationDescription);
        sendNotify = findViewById(R.id.SendNotification);

        sendNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == 0 && flag2 == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please select whom to send the notification", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(flag1 == 1){
                        DocumentReference StudentReference = firebaseFirestore.collection("StudentNotification").document(title.getText().toString());
                        HashMap<String,String> StudentNotify = new HashMap<>();
                        StudentNotify.put("Title",title.getText().toString());
                        StudentNotify.put("Note",note.getText().toString());
                        StudentNotify.put("Description",description.getText().toString());

                        StudentReference.set(StudentNotify).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                status = 1;
                            }
                        });
                    }
                    if(flag2 == 1){
                        DocumentReference FacultyReference = firebaseFirestore.collection("FacultyNotification").document(title.getText().toString());
                        HashMap<String,String> FacultyNotify = new HashMap<>();
                        FacultyNotify.put("Title",title.getText().toString());
                        FacultyNotify.put("Note",note.getText().toString());
                        FacultyNotify.put("Description",description.getText().toString());

                        FacultyReference.set(FacultyNotify).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                status = 1;
                            }
                        });
                    }
                }
                    startActivity(new Intent(getApplicationContext(), FacultyNotification.class));
                    finish();
            }
        });
    }

    public void StudentUpdate(View view) {
        if(!student.isChecked()) {
            student.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#C1B6B6")));
            flag1 = 1;
            student.setChecked(true);
        }
        else {
            flag1 = 0;
            student.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            student.setChecked(false);
        }
    }

    public void FacultyUpload(View view) {
        if(!faculty.isChecked()) {
            faculty.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#C1B6B6")));
            flag2 = 1;
            faculty.setChecked(true);
        }
        else {
            flag2 = 0;
            faculty.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            faculty.setChecked(false);
        }
    }
}