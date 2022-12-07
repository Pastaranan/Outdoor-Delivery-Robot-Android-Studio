package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;



public class Warning extends AppCompatActivity {
    Button closebox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        closebox = findViewById(R.id.closebox);
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Warning.this,Thank.class);
                startActivity(i);
                return;
            }
        });
    }
}