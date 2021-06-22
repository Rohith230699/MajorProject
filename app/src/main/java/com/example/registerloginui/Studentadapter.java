package com.example.registerloginui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class Studentadapter extends RecyclerView.Adapter<Studentadapter.myviewholder>
{
    ArrayList<Notifications> datalist;
    Context context;

    public Studentadapter(ArrayList<Notifications> datalist, NotificationActivity notificationActivity) {
        this.datalist = datalist;
        this.context = notificationActivity;
    }

    @NonNull
    @Override
    public Studentadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row,parent,false);
        return new Studentadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Studentadapter.myviewholder holder, int position) {
        holder.t3.setText(datalist.get(position).getTitle());
        holder.t4.setText(datalist.get(position).getNote());
        if(position%2 == 0)
            holder.cardView.setBackgroundColor(Color.parseColor("#9FE2BF"));
        else
            holder.cardView.setBackgroundColor(Color.parseColor("#9FD2E2"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailedNotificationStudent.class);
                intent.putExtra("Title",datalist.get(position).getTitle());
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
        TextView t3,t4;
        RelativeLayout cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            cardView=itemView.findViewById(R.id.cardviewAdapter);
        }
    }
}
