package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class InputPasswordActivity extends AppCompatActivity {
    PatternLockView mPatternLockView;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_password);

        //title bar color & background color setup
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(InputPasswordActivity.this,R.color.white));



        SharedPreferences sharedPreferences = getSharedPreferences("PREFS",0);
        password = sharedPreferences.getString("password","0");

        mPatternLockView = (PatternLockView) findViewById(R.id.input_pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                //check if the pattern is right or wrong
                if(password.equals(PatternLockUtils.patternToString(mPatternLockView,pattern))){
                    Intent intent = new Intent(InputPasswordActivity.this,ProgramActivity.class);
                    startActivity(intent);
                    finish();
                    mPatternLockView.clearPattern();
                }
                else {
                    Toast.makeText(InputPasswordActivity.this, "Wrong Pattern", Toast.LENGTH_SHORT).show();
                    mPatternLockView.clearPattern();
                }

            }

            @Override
            public void onCleared() {

            }
        });
    }
}