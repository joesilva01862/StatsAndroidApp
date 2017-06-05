package com.example.joe.statsapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_mainlayout).setOnTouchListener(this); // used to hide the soft keyboard
        //getWindow().getDecorView().setBackgroundColor(Color.parseColor("#2E8B57"));
        addLoginButonListener();
    }

    /*
        Used to hide the soft keyboard when the user touches outside the text edit box
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    public void addLoginButonListener() {
        final Context context = this;
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, StatsActivity.class);
                startActivity(intent);
            }
        });
    }

}
