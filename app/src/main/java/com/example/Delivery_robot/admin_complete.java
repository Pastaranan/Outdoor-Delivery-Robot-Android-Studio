package com.example.Delivery_robot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class admin_complete extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complete);

        Intent intent = getIntent();
        String ID_robot = intent.getStringExtra("robot_id");
        String ID_Admin = intent.getStringExtra("user_id");


        TextView ID_robot_text = findViewById(R.id.ID_robot);
        ID_robot_text.setText("Robot ID " + ID_robot + " on delivery");

        Log.d("852456", ID_Admin);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(admin_complete.this,Admin.class);
                intent.putExtra("user_id",ID_Admin);
                startActivity(intent);
                finish();
            }
        },2500);
    }
}
