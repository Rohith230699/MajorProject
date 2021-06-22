package com.example.registerloginui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recviewStudent;
    ArrayList<Notifications> datalist;
    FirebaseFirestore db;
    Studentadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recviewStudent=(RecyclerView)findViewById(R.id.recviewStudent);
        recviewStudent.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new Studentadapter(datalist, NotificationActivity.this);
        recviewStudent.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("StudentNotification").get()
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