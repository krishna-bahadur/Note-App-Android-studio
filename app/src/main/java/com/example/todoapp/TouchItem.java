package com.example.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.dbutil.DbHelper;
import com.example.todoapp.model.ItemModel;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchItem {
    DbHelper db;
    Context c;
    RecyclerView view;
    public TouchItem(DbHelper db, Context c,RecyclerView view){
        this.db=db;
        this.c=c;
        this.view=view;


    }
    public void touch(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction){
                    case ItemTouchHelper.LEFT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Are you sure to delete this ?");
                        builder.setIcon(R.drawable.ic_baseline_delete_24);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.RemoveItemFromDb((int) viewHolder.itemView.getTag());
                                Toast.makeText(c, "Item deleted", Toast.LENGTH_SHORT).show();
                                view.setAdapter(new CustomAdapter(c, db.retrieveData()));
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                view.setAdapter(new CustomAdapter(c, db.retrieveData()));
                            }
                        });

                        builder.show();




                        break;

                    case ItemTouchHelper.RIGHT:
                        AlertDialog.Builder b = new AlertDialog.Builder(c);
                        b.setTitle("Confirmation");
                        b.setMessage("Are you sure to Hide this ?");
                        b.setIcon(R.drawable.ic_baseline_lock_24);
                        b.setCancelable(false);
                        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ItemModel model = new ItemModel();
                                model.setFlag(1);
                                db.updateFlag(model,(int) viewHolder.itemView.getTag());
                                Toast.makeText(c, "Item hidden", Toast.LENGTH_SHORT).show();
                                view.setAdapter(new CustomAdapter(c, db.retrieveData()));
                            }
                        });
                        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                view.setAdapter(new CustomAdapter(c, db.retrieveData()));
                            }
                        });
                        b.show();
                        break;
                }



            }

            @Override
            public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c,canvas,view,viewHolder,dX,dY,actionState,isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(c, android.R.color.holo_red_dark))
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(c, R.color.design_default_color_secondary_variant))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_lock_24)
                        .create()
                        .decorate();

                super.onChildDraw(canvas, view, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(view);

    }
}
