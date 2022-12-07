package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;



public class Pickup extends AppCompatActivity {
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_late);


        //PUT method



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Pickup.this,Login.class);
                startActivity(intent);
                finish();
            }
        },3000);



    }

    @Override
    public void onBackPressed() {

    }
}