package com.example.Delivery_robot;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Post_Activity_2 extends AppCompatActivity {
    TextView textView1;
//    Thread thread;
//    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        Button buttonPost = findViewById(R.id.button_post_2);
        Button buttonBack = findViewById(R.id.button_back_2);
        Button button_clear = findViewById(R.id.button_clear);

        TextView textView1 = findViewById(R.id.textView1);

//        //--------------------------  Thread   --------------------------------//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true)
//                {
//                    try {
////                        Gettest();
//                        loop_get_status_Robot();
//                        Thread.sleep(1000);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();



        //--------------------------  METHOD   --------------------------------//
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Gettest();
//                loop_get_status_task();
//                  loop_get_status_Robot();
//                loop_get_status_Robot();
                loop_get_status_confirm();

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView1.setText("");
            }
        });
    }


    private void loop_get_status_confirm(){
        // get status order   Admin
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        String USER_id = "6";

        final String url = "http://150.95.88.167:8083/task/"+USER_id;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject _employee = response.getJSONObject("data");

                            String Order_status_get = _employee.getString("status");
                            String Order_Box_get = _employee.getString("box");

//                                    Order_status_get = Order_status_get.replace("'", "");
//                                    Order_status_get = Order_status_get.replace("\"", "");
                            Log.d("456789", Order_status_get);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("12345689", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(getRequest);
    }

    private void get_status_Robot_lati_long() {
        // get status order   Admin
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        String USER_id = "2";

        final String url = "http://150.95.88.167:8083/task/2";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("rerer",response.toString());

                        try {

                            JSONObject _employee = response.getJSONObject("data");

                            JSONObject robot = _employee.getJSONObject("robot");

                            JSONObject robot_data = robot.getJSONObject("data");

                            String robot_latitude = robot_data.getString("latitude");

                            String robot_longitude = robot_data.getString("longitude");
//
//                            String Order_status_get = _employee.getString("status");
//                            String Order_Box_get = _employee.getString("box");

                            TextView textView1 = findViewById(R.id.textView1);

                            textView1.append(robot_latitude +" ");

                            Log.d("check",_employee.toString());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(getRequest);

    }

    private void loop_get_status_Robot() {
        final String url = "http://150.95.88.167:8083/robots";
        RequestQueue queue = Volley.newRequestQueue(this);

        TextView textView1 = findViewById(R.id.textView1);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");


                                JSONObject robot = jsonArray.getJSONObject(0);


                                String Robot_ID_get = robot.getString("Robot_id");
                                String Robot_status_get = robot.getString("Robot_status");

                                if (Robot_status_get.equals("available")) {

                                    TextView textView1 = findViewById(R.id.textView1);

                                    textView1.append(Robot_ID_get +" ");

                                }
                                else {

                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(getRequest);

    }


    private void loop_get_status_task(){
        // get status order   Admin
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        final String url = "http://150.95.88.167:8083/task/6";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                                JSONObject _employee = response.getJSONObject("data");

                                String Order_status_get = _employee.getString("status");
                                String Order_Box_get = _employee.getString("box");

                                TextView textView1 = findViewById(R.id.textView1);

                                textView1.append(Order_status_get +" ");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(getRequest);

    }

    private void Gettest() {

        final String url = "http://150.95.88.167:8083/users";
        RequestQueue queue = Volley.newRequestQueue(this);

        TextView textView1 = findViewById(R.id.textView1);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String Order_status_get = employee.getString("User_status");
                                String Order_name_get = employee.getString("User_phone_num");

                                textView1.append(Order_status_get+" ");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        queue.add(getRequest);
    }


//    public void volleyPost(){
//        // get status order
//
//        final String url = "http://150.95.88.167:8083/users";
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            int ID = 0;
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject employee = jsonArray.getJSONObject(i);
//
//                                ID = employee.getInt("User_id");
//                                String user_phone_get = employee.getString("User_phone_num");
//                                String password_get = employee.getString("User_password");
//                                String User_status_get = employee.getString("User_status");
//
//                                if (User_status_get.equals("Admin")) {
//                                    Intent intent = new Intent(getApplicationContext(), Delivery_status2.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                else {
//                                    textView1.setText(User_status_get);
//                                }
//
//                            }
//                        }
//
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                            finish();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        );
//        queue.add(getRequest);
////        String postUrl = "https://reqres.in/api/order";
////        RequestQueue requestQueue = Volley.newRequestQueue(this);
////
////        JSONObject postData = new JSONObject();
////        try {
////            postData.put("id", "1");
////            postData.put("basket_id", "95");
////            postData.put("location_id", "8798");
////            postData.put("payment_id", "985496");
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////
////        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
////            @Override
////            public void onResponse(JSONObject response) {
////                System.out.println(response);
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                error.printStackTrace();
////            }
////        });
////
////        requestQueue.add(jsonObjectRequest);
//    }

    private void openMainActivity() {
        Intent intent = new Intent (this,Post_Activity_3.class);
        startActivity(intent);
    }


}