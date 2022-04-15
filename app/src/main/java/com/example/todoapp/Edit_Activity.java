package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todoapp.dbutil.DbHelper;
import com.example.todoapp.model.ItemModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Edit_Activity extends AppCompatActivity {
    EditText editTitle, editDescription;
    Button editBackBtn, editBtnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        //title bar color & background color setup
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(Edit_Activity.this,R.color.white));

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);

        editBackBtn = findViewById(R.id.editBackBtn);
        editBtnSave = findViewById(R.id.editBtnSave);


        editTitle.setText(getIntent().getExtras().getString("title"));
        editDescription.setText(getIntent().getExtras().getString("description"));

        int id = getIntent().getExtras().getInt("id");

        editBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(Edit_Activity.this);
                ItemModel model = new ItemModel();
                model.setTitle(editTitle.getText().toString());
                model.setDescription(editDescription.getText().toString());
                model.setTime(new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date()));
                // save updated data into database and return to main activity
                db.updateRecord(model, id);
                Intent intent = new Intent(Edit_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}