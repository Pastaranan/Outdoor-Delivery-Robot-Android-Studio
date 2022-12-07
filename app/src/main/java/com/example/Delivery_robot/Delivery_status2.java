package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class Delivery_status2 extends AppCompatActivity {
    ImageButton backorro,seemap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status2);
        backorro = findViewById(R.id.backorro);

        //loop_get();

        seemap = findViewById(R.id.seemap);
        seemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Delivery_status2.this,Delivery_status_map.class);
                startActivity(i);
                return;
            }
        });

    }

//    private void loop_get(){
//        // get status order
//        final String url = "http://150.95.88.167:8083/users";
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject employee = jsonArray.getJSONObject(i);
//
//                                String Order_status_get = employee.getString("User_status");
//                                String Order_name_get = employee.getString("User_phone_num");
//
//                                if (Order_status_get.equals("Admin") ) {
//                                    Intent intent = new Intent(getApplicationContext(), Delivery_status2.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        );
//
//        queue.add(getRequest);
//
//    }
}