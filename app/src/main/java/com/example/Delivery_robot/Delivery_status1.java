package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Delivery_status1 extends AppCompatActivity {
    ImageButton backpre;
    ImageButton detail;
    ImageButton seemap;
    pl.droidsonroids.gif.GifImageView Img_prepare;
    ImageView Status_;
    TextView time_count ,namelocation;

    public static String Status_to_GIF="";

    public static String Status_get;
    public static String Status_box;

    public static boolean loop = true ;
    public static boolean loop_D = true ;
    public static boolean loop_B = false ;

    public static Integer time_confirm = 320000 ;
    public static Integer time_Order_1 = 605000 ;
    public static Integer time_Order_2 = 605000 ;

    public static CountDownTimer yourCountDownTimer;

    public static String check_back_map="";

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status1);

        namelocation = findViewById(R.id.namelocation);
        time_count = findViewById(R.id.time_count);
//        time_count_prepare_method();
        yourCountDownTimer = new CountDownTimer(time_confirm, 1000)
        {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;

                if (Ssec < 10) {
                    time_count.setText("" + Mmin);
                }
                else time_count.setText("" + Mmin);
//                if(Mmin == 0){
//                    time_confirm = 350000;
//                    Toast.makeText(Delivery_status1.this, "Please wait for the preparation", Toast.LENGTH_SHORT).show();
//                }
            }

            public void onFinish() {
            }

        }.start();


        //-------------------------- set text location -------------------------------//

        Intent intent_name = getIntent();
        String check_name = intent_name.getStringExtra("Name");

//        Log.d("check_back_map",check_name);

//        if (check_name.equals("TARA")) {
            namelocation.setText("THE TARA");
//        }
//
//        else if (check_name.equals("Car Park 1")) {
//            namelocation.setText("Car Park");
//        }
//
//        else if (check_name.equals("Car Park 2")) {
//            namelocation.setText("Car Park");
//        }

        //--------------------------  Thread  confirm --------------------------------//
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop)
                {
                    try {
                        loop_get_status_confirm();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        seemap = findViewById(R.id.seemap);
        seemap.setVisibility(View.INVISIBLE);
        seemap.setOnClickListener(new View.OnClickListener() {

            Intent intent = getIntent();
            String ID_USER = intent.getStringExtra("user_id");

            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Delivery_status1.this,Delivery_status_map.class);
                    intent.putExtra("user_id",ID_USER);
                    startActivity(intent);
                    finish();
//                Toast.makeText(Delivery_status1.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent_back = getIntent();
        String check_back = intent_back.getStringExtra("BACK_");

        Log.d("check_back_map",check_back);

        if(check_back.equals("yet"))
        {
            loop_B = false;
        }

        else if(check_back.equals("back"))
        {
            loop_B = true;
        }

        //--------------------------  Thread  confirm --------------------------------//
        Thread thread_b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop_B)
                {
                    try {
                        loop_get_status_confirm();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_b.start();

    }


    private void GIF_method() {
        if (Status_to_GIF.equals("GIF")){
            Status_ = findViewById(R.id.status_);
            seemap = findViewById(R.id.seemap);
            seemap.setVisibility(View.VISIBLE);
            Img_prepare = findViewById(R.id.Img_prepare);

            Img_prepare.setImageResource(R.drawable.gif_delivery_green);
            Status_.setImageResource(R.drawable.show_status_2_bg);
        }
    }


//    protected void time_count_prepare_method() {
//        CountDownTimer yourCountDownTimer = new CountDownTimer(350000, 1000) {
//
//            public void onTick(long duration) {
//                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
//                //here you can have your logic to set text to edittext resource id
//                // Duration
//                long Mmin = (duration / 1000) / 60;
//                long Ssec = (duration / 1000) % 60;
//                if (Ssec < 10) {
//                    time_count.setText("" + Mmin);
//                }
//                else time_count.setText("" + Mmin);
//            }
//
//            public void onFinish() {
//            }
//        }.start();
//    }

    private void time_count_delivery_oreder_1() {
        new CountDownTimer(time_Order_1, 1000) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
                if (Ssec < 10) {
                    time_count.setText("" + Mmin);
                }
                else time_count.setText("" + Mmin);
            }

            public void onFinish() {
                time_Order_1 = 185000;
                Toast.makeText(Delivery_status1.this, "Robot on delivery, Please wait for your order ", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void time_count_delivery_oreder_2() {
        new CountDownTimer(time_Order_2, 1000) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
                if (Ssec < 10) {
                    time_count.setText("" + Mmin);
                }
                else time_count.setText("" + Mmin);
            }

            public void onFinish() {
                time_Order_2 = 185000;
                Toast.makeText(Delivery_status1.this, "Robot on delivery, Please wait for your order ", Toast.LENGTH_LONG).show();
            }
        }.start();
    }


    private void loop_get_status_confirm(){
        // get status order   Admin
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

                                    String Order_status_get = _employee.getString("status");
                                    String Order_Box_get = _employee.getString("box");

//                                    Order_status_get = Order_status_get.replace("'", "");
//                                    Order_status_get = Order_status_get.replace("\"", "");
                                    Log.d("456789", Order_status_get);

                                    if (Order_status_get.equals("confirm")) {
                                        GIF_method();
                                        Status_to_GIF = "GIF";
                                        Stop_loop_confirm();
                                        loop_D = true;
                                        Thread_loop_Goal();

                                        yourCountDownTimer.cancel();
                                        get_for_show_time_order();
                                    }

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

    private void get_for_show_time_order() {
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

                            String Order_Box_get = _employee.getString("box");

//                                    Order_status_get = Order_status_get.replace("'", "");
//                                    Order_status_get = Order_status_get.replace("\"", "");
                            Log.d("879879", Order_Box_get);

                            if (Order_Box_get.equals("1")) {
                                time_count_delivery_oreder_1();
                            }

                            if (Order_Box_get.equals("2")) {
                                time_count_delivery_oreder_2();
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

    private void Stop_loop_confirm() {
        new CountDownTimer(500, 500) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;

            }

            public void onFinish() {
                loop = false;
                loop_B = false;
            }
        }.start();
    }


    private void Thread_loop_Goal() {
        //--------------------------  Thread  delivery --------------------------------//
        Thread thread_D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop_D)
                {
                    try {
                        loop_get_status_Goal();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_D.start();
    }

    private void loop_get_status_Goal(){
        // get status order   Admin
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

                            String Order_status_get_goal = _employee.getString("status");
                            String Order_Box_get_goal = _employee.getString("box");
                            String page_order_ID = _employee.getString("order_id");

//                            Order_status_get_goal = Order_status_get_goal.replace("'", "");
//                            Order_status_get_goal = Order_status_get_goal.replace("\"", "");


                            Log.d("741",Order_status_get_goal);

                            if (Order_status_get_goal.equals("Goal") && Order_Box_get_goal.equals("1")) {
                                Intent Intent = new Intent(Delivery_status1.this, Open_the_box.class);
                                Intent.putExtra("user_id",ID_USER);
                                Intent.putExtra("page_order_id",page_order_ID);
                                Stop_loop_Goal();
                                startActivity(Intent);
                                finish();
                            }

                            if (Order_status_get_goal.equals("Goal") && Order_Box_get_goal.equals("2")) {
                                Intent Intent = new Intent(Delivery_status1.this, Open_the_box2.class);
                                Intent.putExtra("user_id",ID_USER);
                                Intent.putExtra("page_order_id",page_order_ID);
                                Stop_loop_Goal();
                                startActivity(Intent);
                                finish();

                            }

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

    private void Stop_loop_Goal() {
        new CountDownTimer(1, 1) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
            }

            public void onFinish() {
                loop_D = false;
                loop = true;
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }


//----------------------------------------------------------------------------------------------------------- BACK MAP

    private void Thread_loop_comfrim_back_map() {
        //--------------------------  Thread  delivery --------------------------------//
        Thread thread_D = new Thread(new Runnable() {
            @Override
            public void run() {
                while (loop)
                {
                    try {
                        loop_get_status_confirm();
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_D.start();
    }

}