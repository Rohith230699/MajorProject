package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedNotification extends AppCompatActivity {

    TextView title, note, description, backArrow;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notification);

        String Title = getIntent().getStringExtra("Title");

        title = findViewById(R.id.Title);
        note = findViewById(R.id.Note);
        description  = findViewById(R.id.Description);
        backArrow  = findViewById(R.id.backButton);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyNotification.class));
                finish();
            }
        });

        title.setText(Title);

        DocumentReference documentReference = firebaseFirestore.collection("FacultyNotification").document(Title);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    note.setText(documentSnapshot.get("Note").toString());
                    description.setText(documentSnapshot.get("Description").toString());
                }
                else
                    Toast.makeText(DetailedNotification.this, "No such notification is present", Toast.LENGTH_SHORT).show();
            }
        });


    }
}