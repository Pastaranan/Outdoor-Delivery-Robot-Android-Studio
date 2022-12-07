package com.example.Delivery_robot;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Admin extends AppCompatActivity {
    LinearLayout box1,box2;
    Dialog myDialog;
    Button button_delivery;
    TextView ID_robot_text;
    TextView status_box_1,status_box_2;
    TextView text_Order_ID_box_1,text_Order_ID_box_2;
    TextView location_box_1,location_box_2;

    private long backPressedTime;
    private Toast backToast;

    public static String task_user_id_1,task_user_id_2;
    public static String task_page_order_id_1,task_page_order_id_2;

    public static String checkbox_red,checkbox_yellow;

    public static String robot_id_task;

    public static boolean loop = true ;

    public static boolean loop_robot = true ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        box1 = findViewById(R.id.box1);
        box2 = findViewById(R.id.box2);
        button_delivery = findViewById(R.id.button_delivery);
        ID_robot_text = findViewById(R.id.ID_robot);

        Intent intent = getIntent();
        String ID_Admin = intent.getStringExtra("user_id");

        Log.d("4564564456", ID_Admin);

        getOrder_ID_change_method();

        // get_order_loop
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop)
                {
                    try {
                        getOrder_ID_change_method();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        // get_robot_loop
        Thread thread_r = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop_robot)
                {
                    try {
                        get_robot_status();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_r.start();

        text_Order_ID_box_1 = findViewById(R.id.text_Order_ID_box_1);
        text_Order_ID_box_2 = findViewById(R.id.text_Order_ID_box_2);


//        getOrder_ID_method();
        //if robot not available set not show

        status_box_1 = findViewById(R.id.status_box_1);
        status_box_2 = findViewById(R.id.status_box_2);
        myDialog = new Dialog(this);


        button_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox_red = status_box_1.getText().toString();
                checkbox_yellow = status_box_2.getText().toString();

                if(checkbox_red.equals("Preparing") && checkbox_yellow.equals("Preparing"))
                {
                    Toast.makeText(Admin.this, "Please Confirm order to boxes", Toast.LENGTH_SHORT).show();
                }

                else if(checkbox_red.equals("Complete") || checkbox_yellow.equals("Complete"))
                {
                    comfirm_delivery_poup();
                }

                else if(checkbox_red.equals("") && checkbox_yellow.equals(""))
                {
                    Toast.makeText(Admin.this, "Please waiting for order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void get_robot_status() {
        final String url = "http://150.95.88.167:8083/robots";
        RequestQueue queue = Volley.newRequestQueue(this);

        ID_robot_text = findViewById(R.id.ID_robot);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {   JSONArray jsonArray_robot = response.getJSONArray("data");

                            if ( jsonArray_robot.length() <= 0 )
                            {
                                ID_robot_text.setText("Robot Unavailable");
                                ID_robot_text.setTextColor(getResources().getColor(R.color.gray));
                            }

                            else if ( jsonArray_robot.length() > 0 ) {
                                JSONObject objectRobot = jsonArray_robot.getJSONObject(0);
                                Integer Robot_ID_get = objectRobot.getInt("Robot_id");
                                String Robot_Status = objectRobot.getString("Robot_status");

                                ID_robot_text.setText(Robot_ID_get.toString());
                                ID_robot_text.setTextColor(getResources().getColor(R.color.black));

                                robot_id_task = Robot_ID_get.toString();
                            }

                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue.add(getRequest);
    }

    private void getOrder_ID_change_method() {
        final String url = "http://150.95.88.167:8083/orders";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                                JSONArray jsonArray_test = response.getJSONArray("data");

                                if ( jsonArray_test.length() == 0 ) {

                                        text_Order_ID_box_1.setText("waiting for order . . ");
                                        text_Order_ID_box_2.setText("waiting for order . .");

                                        status_box_1.setText("");
                                        status_box_2.setText("");

                                    LinearLayout l = (LinearLayout) findViewById(R.id.box1);
                                    l.setAlpha((float) 0.4);

                                    LinearLayout ll = (LinearLayout) findViewById(R.id.box2);
                                    ll.setAlpha((float) 0.4);
//                                    box1.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Toast.makeText(Admin.this, "Please waiting for order", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                    box2.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Toast.makeText(Admin.this, "Please waiting for order", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                }
                            else if ( jsonArray_test.length() >= 2 ) {

                                JSONObject object_box_1 = jsonArray_test.getJSONObject(0);
                                Integer order_ID_object_box_1 = object_box_1.getInt("PageOrder_id");
                                String order_Status_object_box_1 = object_box_1.getString("PageOrder_status");
                                String order_User_ID_1 = object_box_1.getString("User_id");

                                JSONArray jsonArray_test_2 = response.getJSONArray("data");
                                JSONObject object_box_2 = jsonArray_test_2.getJSONObject(1);
                                Integer order_ID_object_box_2 = object_box_2.getInt("PageOrder_id");
                                String order_Status_object_box_2 = object_box_2.getString("PageOrder_status");
                                String order_User_ID_2 = object_box_2.getString("User_id");

                                text_Order_ID_box_1 = findViewById(R.id.text_Order_ID_box_1);
                                text_Order_ID_box_2 = findViewById(R.id.text_Order_ID_box_2);

                                task_user_id_1 = order_User_ID_1;
                                task_user_id_2 = order_User_ID_2;

                                task_page_order_id_1 = order_ID_object_box_1.toString();
                                task_page_order_id_2 = order_ID_object_box_2.toString();

                                status_box_1.setText("Preparing");
                                status_box_2.setText("Preparing");

                                if(order_ID_object_box_1!=null && order_ID_object_box_1!=null){
                                    text_Order_ID_box_1.setText("Order ID: "+order_ID_object_box_1.toString());
                                    text_Order_ID_box_2.setText("Order ID: "+order_ID_object_box_2.toString());
                                }

                                box1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        openbox_red();
                                        Put_Open_box_1_();
                                    }
                                });


                                box2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        openbox_yellow();
                                        Put_Open_box_2();
                                    }
                                });

                                LinearLayout l = (LinearLayout) findViewById(R.id.box1);
                                l.setAlpha((float) 1);

                                LinearLayout ll = (LinearLayout) findViewById(R.id.box2);
                                ll.setAlpha((float) 1);
                            }

                            else if ( jsonArray_test.length() == 1 ) {

//                               Stop_loop_get_id_thread();

                                JSONObject object_box_1 = jsonArray_test.getJSONObject(0);
                                Integer order_ID_object_box_1 = object_box_1.getInt("PageOrder_id");
                                String order_Status_object_box_1 = object_box_1.getString("PageOrder_status");
                                String order_User_ID_1 = object_box_1.getString("User_id");

                                text_Order_ID_box_1 = findViewById(R.id.text_Order_ID_box_1);
                                text_Order_ID_box_2 = findViewById(R.id.text_Order_ID_box_2);

                                task_user_id_1 = order_User_ID_1;
                                task_page_order_id_1 = order_ID_object_box_1.toString();

                                text_Order_ID_box_1.setText("Order ID: "+order_ID_object_box_1.toString());
                                status_box_1.setText("Preparing");

                                text_Order_ID_box_2.setText("waiting for order . .");
                                status_box_2.setText("");
                                LinearLayout ll = (LinearLayout) findViewById(R.id.box2);
                                ll.setAlpha((float) 0.4);

                                box1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        openbox_red();
                                        Put_Open_box_1_();
                                    }
                                });

                                box2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(Admin.this, "Please Waiting Order", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                LinearLayout l = (LinearLayout) findViewById(R.id.box1);
                                l.setAlpha((float) 1);
                            }



                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue.add(getRequest);
    }

    private void Stop_loop_get_id_thread() {
            new CountDownTimer(1, 1) {

                public void onTick(long duration) {
                    //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                    //here you can have your logic to set text to edittext resource id
                    // Duration
                    long Mmin = (duration / 1000) / 60;
                    long Ssec = (duration / 1000) % 60;

                }

                public void onFinish() {
                    loop = false;
                }
            }.start();
    }

    private void openbox_red(){
        myDialog.setContentView(R.layout.activity_boxa);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        status_box_1.setText("Complete");
        status_box_1.setTextColor(getResources().getColor(R.color.old_green));

//        location_box_1 = findViewById(R.id.location_box_1);
//        get_location_box_1();

        Button Confirm_box_1 = myDialog.findViewById(R.id.Confirm_box_1);
        Confirm_box_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Put_Close_box_1_();
                Stop_loop_get_id_thread();

                status_box_1.setText("Complete");
                status_box_1.setTextColor(getResources().getColor(R.color.old_green));
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void openbox_yellow(){
        myDialog.setContentView(R.layout.activity_boxb);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        location_box_2 = findViewById(R.id.location_box_2);
//        get_location_box_2();

        Button Confirm_box_2 = myDialog.findViewById(R.id.Confirm_box_2);
        Confirm_box_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop_loop_get_id_thread();
                Put_Close_box_2();

                status_box_2.setText("Complete");
                status_box_2.setTextColor(getResources().getColor(R.color.old_green));
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void comfirm_delivery_poup() {myDialog.setContentView(R.layout.activity_admin_comfirm_delivery_poup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView ID_Robot_text_popup = myDialog.findViewById(R.id.textView6);
        TextView Box1_text_popup = myDialog.findViewById(R.id.textView8);
        TextView Box2_text_popup = myDialog.findViewById(R.id.textView9);

        ID_robot_text = findViewById(R.id.ID_robot);

        status_box_1 = findViewById(R.id.status_box_1);
        status_box_2 = findViewById(R.id.status_box_2);

        ID_Robot_text_popup.setText(ID_robot_text.getText().toString());
        Box1_text_popup.setText(status_box_1.getText().toString());
        Box2_text_popup.setText(status_box_2.getText().toString());

        Intent intent = getIntent();
        String ID_Admin = intent.getStringExtra("user_id");

        TextView confirm = myDialog.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkbox_red.equals("Complete") && checkbox_yellow.equals("Complete"))
                {
                    Post_task_admin_both_complete();
                    PostPage_order_1_status();
                    PostPage_order_2_status();
                }

                if(checkbox_red.equals("Complete") && checkbox_yellow.equals("Preparing"))
                {
                    Post_task_admin_box_1_complete();
                    PostPage_order_1_status();
                }

                if(checkbox_red.equals("Complete") && checkbox_yellow.equals(""))
                {
                    Post_task_admin_box_1_complete();
                    PostPage_order_1_status();
                }

                if(checkbox_red.equals("Preparing") && checkbox_yellow.equals("Complete"))
                {
                    Post_task_admin_box_2_complete();
                    PostPage_order_2_status();
                }
                myDialog.dismiss();

                Intent intent = new Intent(Admin.this, admin_complete.class);
                intent.putExtra("robot_id",robot_id_task);
                intent.putExtra("user_id",ID_Admin);
                startActivity(intent);
                loop = true;
                finish();
            }
        });myDialog.show();

        TextView cancel = myDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    private void PostPage_order_1_status() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id",task_page_order_id_1);
            jsonObject.put("status","delivery");

        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("___order_status", jsonObject.toString());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/order", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
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

    private void PostPage_order_2_status() {

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id",task_page_order_id_2);
            jsonObject.put("status","delivery");

        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("___order_status", jsonObject.toString());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/order", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

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

    private void Post_task_admin_both_complete() {

        Intent intent = getIntent();
        String ID_Admin = intent.getStringExtra("user_id");

        Log.d("111111", ID_Admin);

        JSONObject jsonObject= new JSONObject();
        JSONArray user_ids= new JSONArray();
        JSONArray order_ids= new JSONArray();

        Log.d("333333_ar", user_ids.toString());

        try {
            jsonObject.put("id",ID_Admin);
            jsonObject.put("robot_id",robot_id_task);

            user_ids.put(task_user_id_1);
            user_ids.put(task_user_id_2);

            order_ids.put(task_page_order_id_1);
            order_ids.put(task_page_order_id_2);

            jsonObject.put("user_ids",user_ids);
            jsonObject.put("order_ids",order_ids);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("1412214", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://150.95.88.167:8083/task", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

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

    private void Post_task_admin_box_1_complete() {

        Intent intent = getIntent();
        String ID_Admin = intent.getStringExtra("user_id");

        Log.d("222222", ID_Admin);

        JSONObject jsonObject= new JSONObject();
        JSONArray user_ids= new JSONArray();
        JSONArray order_ids= new JSONArray();
        try {

            jsonObject.put("id",ID_Admin);
            jsonObject.put("robot_id",robot_id_task);

            user_ids.put(task_user_id_1);
//            user_ids.put("");

            order_ids.put(task_page_order_id_1);
//            order_ids.put("");

            jsonObject.put("user_ids",user_ids);
            jsonObject.put("order_ids",order_ids);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("1412214", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://150.95.88.167:8083/task", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

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

    private void Post_task_admin_box_2_complete() {

        Intent intent = getIntent();
        String ID_Admin = intent.getStringExtra("user_id");

        Log.d("333333", ID_Admin);

        JSONObject jsonObject= new JSONObject();
        JSONArray user_ids= new JSONArray();
        JSONArray order_ids= new JSONArray();

        Log.d("333333_ar", user_ids.toString());
        try {

            jsonObject.put("id",ID_Admin);
            jsonObject.put("robot_id",robot_id_task);

//            user_ids.put("");
            user_ids.put(task_user_id_2);

//            order_ids.put("");
            order_ids.put(task_page_order_id_2);

            jsonObject.put("user_ids",user_ids);
            jsonObject.put("order_ids",order_ids);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("1412214", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://150.95.88.167:8083/task", jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

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


    private void Put_Open_box_1_() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("robot_box_id",0);  //page order id
            jsonObject.put("status","open");
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id_task, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
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

    private void Put_Open_box_2(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("robot_box_id",1);  //page order id
            jsonObject.put("status","open");
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id_task, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
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

    private void Put_Close_box_1_(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("robot_box_id",0);  //page order id
            jsonObject.put("status","close");
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id_task, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
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

    private void Put_Close_box_2(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("robot_box_id",1);  //page order id
            jsonObject.put("status","close");
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id_task, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to Logout", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    private void Stop_loop_get_ROBOT_thread() {
        new CountDownTimer(1, 1) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;

            }

            public void onFinish() {
                loop_robot = false;
            }
        }.start();
    }



}