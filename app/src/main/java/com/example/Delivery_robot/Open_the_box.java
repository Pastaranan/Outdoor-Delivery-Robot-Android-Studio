package com.example.Delivery_robot;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Open_the_box extends AppCompatActivity {
    Button Open_box;
    Dialog myDialog;
    VideoView videoView_red_box;
    TextView time_count;
    public static String stoptime = "nonstop";

    public static String Goal_robot_Time ;

    public static String robot_id;



    private Handler handler;
    private String endDateTime="2021-09-26 16:01:00";

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_the_box);

        myDialog = new Dialog(this);

        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        VDO_PLAY();
        time_count = findViewById(R.id.time_count);

        time_count_method();

        get_Goal_robot_Time();

        get_ID_robot();

//        count_down();


        Open_box = findViewById(R.id.Open_box);
        Open_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get status box close
//                GetboxClose();
                //Open_box_put();
                popup_pick();
                Put_Open_box_1_();

                Animation animation = AnimationUtils.loadAnimation(Open_the_box.this,R.anim.fadein_half);
                Open_box.startAnimation(animation);

                time_count.setText("Please pick up your order");
                time_count.setTextColor(getResources().getColor(R.color.old_green));
                stoptime = "stop";
            }
        });

    }

    private void get_Goal_robot_Time() {

            Intent intent = getIntent();
            String ID_USER = intent.getStringExtra("user_id");

            final String url = "http://150.95.88.167:8083/task/"+ID_USER;
            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject _employee = response.getJSONObject("data");


                                String Order_Goal_time = _employee.getString("end");
                                Order_Goal_time = Order_Goal_time.replace("'", "");
                                Goal_robot_Time = Order_Goal_time;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("1231312312", response.toString());
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

    private void get_ID_robot() {

        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        final String url = "http://150.95.88.167:8083/task/"+ID_USER;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject _employee = response.getJSONObject("data");
                            JSONObject _robot = _employee.getJSONObject("robot");

                            robot_id = _robot.getString("robot_id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("1231312312", response.toString());
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

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id, jsonObject,
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

    private void popup_pick() {myDialog.setContentView(R.layout.activity_popup_pickup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView confirmPickup = myDialog.findViewById(R.id.confirmpickup);

        confirmPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Put_stats_success();

                Put_Close_box_1_();

                //GET box close or open
                // IF open
//                waring_close();
            }
        });

        myDialog.show();
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

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, "http://150.95.88.167:8083/robot/box/"+robot_id, jsonObject,
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


    private void Put_stats_success() {
        //PUT sign to open box
        Intent id_page_intent = getIntent();
        String ID_order_page = id_page_intent.getStringExtra("page_order_id");

        JSONObject jsonObject= new JSONObject();

        try {

            jsonObject.put("id",ID_order_page);  //page order id
            jsonObject.put("status","success");

            Thank_activity();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("789456123", jsonObject.toString());

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


    private void waring_close() {
        //GET LOOP status robot something...
        //
        Intent intent = new Intent(Open_the_box.this,Warning.class);
        startActivity(intent);
        finish();
    }

    private void Time_up_activity() {
        //PUT  status robot something...

        Intent intent = new Intent(Open_the_box.this,Pickup.class);
        startActivity(intent);
        finish();
    }

    private void Thank_activity() {
        //PUT  status complete robot...

        Intent intent = new Intent(Open_the_box.this,Thank.class);
        startActivity(intent);
        finish();
    }

    private void VDO_PLAY() {
        //-------- VDO - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        videoView_red_box = (VideoView) findViewById(R.id.red_box_VDO);
        String URIpath1 = "android.resource://" + getPackageName() + "/" + R.raw.vdo_yellow_box_close_robot;
        Uri uri1 = Uri.parse(URIpath1);
        videoView_red_box.setVideoURI(uri1);
        videoView_red_box.requestFocus();
        videoView_red_box.start();
    }


    private void time_count_method() {

//        // ---------------- old
        new CountDownTimer(120000, 1000) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
                if (Ssec < 10) {
                    time_count.setText("0" + Mmin + ":0" + Ssec + " minutes");
                }

                else time_count.setText("0" + Mmin + ":" + Ssec + " minutes");
            }

            public void onFinish() {
                if (stoptime.equals("nonstop")){
                    Time_up_activity();
                    cancel();
                }
            }
        }.start();

    }

    private void count_down() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(120000, 1000) {
                    public void onTick(long duration) {
                        long Mmin = (duration / 1000) / 60;
                        long Ssec = (duration / 1000) % 60;
                        if (Ssec < 10)
                        {
                            time_count.setText("" + Mmin + ":0" + Ssec +" minutes");
                        }
                        else time_count.setText("" + Mmin + ":" + Ssec +" minutes");
                    }
                    public void onFinish() {
                        time_count.setText("00:00" +" minutes");
                    }
                }.start();
            }
        },1000);
    }



    @Override
    public void onBackPressed() {

    }
}