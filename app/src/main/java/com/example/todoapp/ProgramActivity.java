package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todoapp.dbutil.DbHelper;
import com.example.todoapp.model.ItemModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ProgramActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ItemModel> lists;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        //title bar color & background color setup
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(ProgramActivity.this,R.color.white));

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProgramActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lists=new ArrayList<ItemModel>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        DbHelper db = new DbHelper(this);
        lists =db.retrieveHiddenData();
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        Log.e("Lists",""+lists.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(ProgramActivity.this));
        recyclerView.setAdapter(new CustomAdapter(ProgramActivity.this,lists));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
             @Override
             public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                         db.RemoveItemFromDb((int) viewHolder.itemView.getTag());
                         Toast.makeText(ProgramActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                         recyclerView.setAdapter(new CustomAdapter(ProgramActivity.this, db.retrieveHiddenData()));
                 }

             @Override
             public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                 new RecyclerViewSwipeDecorator.Builder(ProgramActivity.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                         .addSwipeLeftBackgroundColor(ContextCompat.getColor(ProgramActivity.this, android.R.color.holo_red_dark))
                         .addSwipeRightBackgroundColor(ContextCompat.getColor(ProgramActivity.this, R.color.design_default_color_secondary_variant))
                         .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                         .addSwipeRightActionIcon(R.drawable.ic_baseline_lock_24)
                         .create()
                         .decorate();

                 super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
             }
         }).attachToRecyclerView(recyclerView);

    }
}