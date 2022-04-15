package com.example.todoapp;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.model.ItemModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context c;
    ArrayList<ItemModel> data;
    public CustomAdapter(Context c, ArrayList<ItemModel> list){
        this.c=c;
        this.data=list;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(c).inflate(R.layout.single_item,null);
        MyViewHolder view =new MyViewHolder(convertView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());
        holder.time.setText(data.get(position).getTime());
        int id = data.get(position).getId();
        holder.itemView.setTag(id);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // put value in intent putExtra for detail page
                Intent i = new Intent(c,Edit_Activity.class);
                i.putExtra("title",data.get(position).getTitle());
                i.putExtra("description",data.get(position).getDescription());
                i.putExtra("id",data.get(position).getId());

                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
       return  data.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title,description,time;
    CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleView);
            description =itemView.findViewById(R.id.descriptionView);
            time = itemView.findViewById(R.id.timeView);
            cardView =itemView.findViewById(R.id.card);

        }
    }


}
