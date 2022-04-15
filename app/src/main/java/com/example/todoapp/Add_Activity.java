package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.dbutil.DbHelper;
import com.example.todoapp.model.ItemModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Add_Activity extends AppCompatActivity {
    Button backBtn,btnSave;
    ArrayList<ItemModel> list;
    EditText eTitle;
    EditText eDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //title bar color & background color setup
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(Add_Activity.this,R.color.white));


        setContentView(R.layout.add_activity);
        eTitle = findViewById(R.id.addTitle);
        eDescription  =findViewById(R.id.addDescription);
        backBtn = findViewById(R.id.backBtn);
        btnSave = findViewById(R.id.btnSave);

        DbHelper db = new DbHelper(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(Add_Activity.this, "Item Added", Toast.LENGTH_SHORT).show();

                Log.e("title",""+eTitle.getText());
                Log.e("Description",""+eDescription.getText());

                ItemModel model = new ItemModel();
                model.setTitle(eTitle.getText().toString());
                model.setDescription(eDescription.getText().toString());
                model.setTime(new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date()));
                model.setFlag(0);
                insertData(model);
                Intent i = new Intent(Add_Activity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    private void insertData(ItemModel model) {
        DbHelper db = new DbHelper(Add_Activity.this);
        db.insertDataToDB(model);

    }
}