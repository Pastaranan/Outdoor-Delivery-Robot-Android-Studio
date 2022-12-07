package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Post_Activity_5 extends AppCompatActivity {

    EditText t1,t2,t3;
    Button sbmt;
    private static final String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_5);

        sbmt = (Button)findViewById(R.id.sbmt);
        Button buttonNext = findViewById(R.id.button_next);


        sbmt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                insertdata();
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

    }


    private  void insertdata()
    {
        TextView text = findViewById(R.id.text);
//
//        JSONObject jsonObject= new JSONObject();
//        JSONArray items= new JSONArray();
//        JSONObject[] item= new JSONObject[5];

            JSONObject jsonObject= new JSONObject();
            JSONArray user_ids= new JSONArray();
            JSONArray order_ids= new JSONArray();
        try {

            jsonObject.put("id",45);
            jsonObject.put("robot_id",2);
            user_ids.put(87);
            user_ids.put(95);
            order_ids.put(63);
            order_ids.put(42);
            jsonObject.put("user_ids",user_ids);
            jsonObject.put("order_ids",order_ids);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://150.95.88.167:8083/task", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        text.setText(response.toString());
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(postRequest);
    }

    private void openActivity() {
        Intent intent = new Intent (this,MainActivity.class);
        startActivity(intent);
    }
}
