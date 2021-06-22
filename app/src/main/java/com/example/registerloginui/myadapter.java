package com.example.registerloginui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
   ArrayList<model> datalist;
    Context context;

    public myadapter(ArrayList<model> datalist, FacultyAttendance facultyAttendance) {
        this.datalist = datalist;
        this.context = facultyAttendance;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
      holder.t1.setText(datalist.get(position).getRollNumber());
      holder.t2.setText(datalist.get(position).getEmail_Id());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context,DetailedReport.class);
              intent.putExtra("Email_Id",datalist.get(position).getEmail_Id());
              v.getContext().startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
       TextView t1,t2;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
        }
    }
}
