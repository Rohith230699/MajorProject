package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FacultyNotification extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recview;
    ArrayList<Notifications> datalist;
    FirebaseFirestore db;
    adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_notification);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MakeNotification.class));
                finish();
            }
        });

        recview=(RecyclerView)findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new adapter(datalist, FacultyNotification.this);
        recview.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("FacultyNotification").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Notifications obj=d.toObject(Notifications.class);
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


    }

}