package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Post_Activity_3 extends AppCompatActivity {

    EditText t1,t2,t3;
    Button sbmt;
    private static final String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_3);

        sbmt = (Button)findViewById(R.id.sbmt);
        Button buttonNext = findViewById(R.id.button_next);


        sbmt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                insertdata();
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

    }

    private void put_data() {

    }


//    private  void insertdata()
//    {   JSONObject jsonObject= new JSONObject();
//        JSONArray user_ids= new JSONArray();
//        JSONArray order_ids= new JSONArray();
//
//        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://150.95.88.167:8083/task", jsonObject,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                    }
//                }
//        );
//
//        Log.d("741852", jsonObject.toString());
//
//        {
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//        {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", "5");
//                params.put("basket_id", "5");
//                params.put("location_id", "546");
//                params.put("payment_id", "464");
//                return params;
//            }
//        };
//
//        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//        queue.add(postRequest);
//    }

    private void openActivity() {
        Intent intent = new Intent (this,Post_Activity_5.class);
        startActivity(intent);
    }
}

//    StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://150.95.88.167:8083/order",
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) { Log.i("Response", response);
//                }
//            }, new Response.ErrorListener() {
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            VolleyLog.d("volley", "Error: " + error.getMessage());
//            error.printStackTrace();
//            Log.d("Error.Response", "Success");
//        }
//    }) {
//
//        @Override
//        public String getBodyContentType() {
//            return "application/x-www-form-urlencoded; charset=UTF-8";
//        }
//
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("id", "1");
//            params.put("basket_id", "999");
//            params.put("location_id", "999");
//            params.put("payment_id", "999");
//            return params;
//        }
//
//    };


//    private  void insertdata()
//    {
//        t1 = (EditText)findViewById(R.id.t1);
//        t2 = (EditText)findViewById(R.id.t2);
//        t3 = (EditText)findViewById(R.id.t3);
//
//        final String name= t1.getText().toString().trim();
//        final String uname=t2.getText().toString().trim();
//        final String pwd=  t3.getText().toString().trim();
//
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                t1.setText("");
//                t2.setText("");
//                t3.setText("");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError
//            {
//                Map<String, String> params = new HashMap<>();
//                params.put("t1",name);
//                params.put("t2",uname);
//                params.put("t3",pwd);
//                return  params;
//            }
//        };
//
//        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
//    }