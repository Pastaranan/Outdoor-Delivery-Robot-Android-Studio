package com.example.Delivery_robot;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    private EditText Edit_Text;
    private RequestQueue Post_Queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Edit_Text = findViewById(R.id.text_edit);
        Button buttonPost = findViewById(R.id.button_post);
        Button buttonBack = findViewById(R.id.button_back);

        //--------------------------  METHOD   --------------------------------//
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonPost();
                //volleyPost();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private void jsonPost() {
        String postUrl = "http://150.95.88.167:8083/users";

        JSONObject postData = new JSONObject();

        StringRequest StringRequest_POST = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PostActivity.this, response.trim(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            //Add parameters to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("User_fname",Edit_Text.getText().toString());
                return  params;
            }
        };
        Post_Queue = Volley.newRequestQueue(this);
        Post_Queue.add(StringRequest_POST);
    }

    private void openMainActivity() {
        Intent intent = new Intent (this, Post_Activity_2.class);
        startActivity(intent);
    }
}



