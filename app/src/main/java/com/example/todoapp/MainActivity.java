package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.dbutil.DbHelper;
import com.example.todoapp.model.ItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity  implements GestureDetector.OnGestureListener {
    FloatingActionButton fab;
    RecyclerView view;
    ArrayList<ItemModel> list;
    CustomAdapter customAdapter;
    private static final String Tag = "Swipe position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //title bar color & background color setup
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));


        DbHelper db = new DbHelper(this);
        list = new ArrayList<ItemModel>();

        customAdapter = new CustomAdapter(MainActivity.this, list);

        list = db.retrieveData();
        view = findViewById(R.id.rv);

        view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        view.setAdapter(new CustomAdapter(MainActivity.this, list));

        this.gestureDetector = new GestureDetector(MainActivity.this, this);



        EditText searchView = findViewById(R.id.searchView);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        TouchItem t = new TouchItem(db, MainActivity.this, view);
        t.touch();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Add_Activity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab, "transition_fab");
                startActivity(i, options.toBundle());

            }
        });


    }

    private void filter(String text) {
        ArrayList<ItemModel> filteredList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(list.get(i));
            }
        }
        view.setAdapter(new CustomAdapter(MainActivity.this, filteredList));
    }

    // insert data into database table
    public void insertData(ItemModel model) {
        DbHelper db = new DbHelper(MainActivity.this);
        db.insertDataToDB(model);
        finish();

    }

    // slide down for pattern security
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 =event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 =event.getY();

                float valueY = y2 - y1;

                if(Math.abs(valueY)> MIN_DISTANCE){
                    if( y2 > y1){
                        //stored password value into sharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
                        String password = sharedPreferences.getString("password","0");
                        if(password.equals("0")){
                            Intent intent = new Intent(getApplicationContext(),CreatePasswordActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            Intent intent = new Intent(getApplicationContext(), InputPasswordActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }


        }


        return super.onTouchEvent(event);
    }


     @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}