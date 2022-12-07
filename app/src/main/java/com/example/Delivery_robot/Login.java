package com.example.Delivery_robot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button login;
    EditText Username, Password;
    TextView singup,  forgot;
    ImageView facebook, gmail;

    private long backPressedTime;
    private Toast backToast;

    public static String User_check, Status_check,ID_put,ID_user_check_order;

    public static String status_task_Goal="",status_task_Confirm="",status_task_Box="",task_page="";

    public static boolean loop_D = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.loginphon);
        Password = findViewById(R.id.logpass);
        login = findViewById(R.id.login);

        singup = findViewById(R.id.singup);
        facebook = findViewById(R.id.facebook);
        gmail = findViewById(R.id.gmail);
        forgot = findViewById(R.id.forgot);

        //--------------------------  Thread  confirm --------------------------------//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (loop_D)
//                {
//                    try {
//                        check_status_task_user();
//                        Thread.sleep(200);
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(Login.this, "Sing Up is under development", Toast.LENGTH_SHORT).show();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "being development", Toast.LENGTH_SHORT).show();
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "being development", Toast.LENGTH_SHORT).show();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "This feature is under development", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(Login.this,R.anim.fadein_half);
                login.startAnimation(animation);

                if (TextUtils.isEmpty(Username.getText().toString()) && TextUtils.isEmpty(Password.getText().toString())) {
                    // displaying a toast message if the edit text is empty.
                    Toast.makeText(Login.this, "Please enter your account", Toast.LENGTH_SHORT).show();
                }

                else {
                    jsonLogin();
//                    Toast.makeText(Login.this, "Account not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void jsonLogin() {
        final String url = "http://150.95.88.167:8083/users";
        RequestQueue queue = Volley.newRequestQueue(this);

        String user = Username.getText().toString();
        String password = Password.getText().toString();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("78945642134", response.toString());
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String ID = employee.getString("User_id");
                                String user_phone_get = employee.getString("User_phone_num");
                                String password_get = employee.getString("User_password");
                                String User_status_get = employee.getString("User_status");

                                if (user.equals(user_phone_get) && password.equals(password_get) && User_status_get.equals("Admin")) {

                                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                                    intent.putExtra("user_id",ID);  //send data

                                    startActivity(intent);
                                    finish();
                                }

                                else if (user.equals(user_phone_get) && password.equals(password_get) && User_status_get.equals("User"))
                                {
                                    ID_user_check_order = ID;
                                    Log.d("8888", ID_user_check_order);

                                    check_status_order_user();
                                    check_status_task_user();

//                                    Intent intent = new Intent(getApplicationContext(),Delivery_location.class);
//                                    intent.putExtra("user_id",ID);  //send data
//                                    startActivity(intent);
//                                    finish();
                                }
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

    private void check_status_task_user() {
        final String url = "http://150.95.88.167:8083/task/"+ID_user_check_order;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("456464848", response.toString());
                        try {
                            JSONObject _employee = response.getJSONObject("data");

                            String Order_status_get = _employee.getString("status");
                            String Order_Box_get_goal = _employee.getString("box");
                            String page_order_ID = _employee.getString("order_id");
//                            Order_status_get_goal = Order_status_get_goal.replace("'", "");
//                            Order_status_get_goal = Order_status_get_goal.replace("\"", "");
                            task_page = page_order_ID;

                                if (Order_status_get.equals("confirm")) {
                                    status_task_Goal = "confirm";
                                    Log.d("con", status_task_Goal);
                                }

                                if (Order_status_get.equals("Goal") && Order_Box_get_goal.equals("1")) {
                                    status_task_Goal = "Goal_1";
                                    status_task_Box = "1";
                                    Log.d("goal1", status_task_Goal);
                                }

                                if (Order_status_get.equals("Goal") && Order_Box_get_goal.equals("2")) {
                                    status_task_Goal = "Goal_2";
                                    status_task_Box = "2";
                                    Log.d("goal2", status_task_Goal);
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

    private void check_status_order_user() {
        final String url = "http://150.95.88.167:8083/order/"+ID_user_check_order;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("6666", response.toString());
                        try
                        {
                            JSONObject jsonObject_test = response.getJSONObject("data");

                            if ( jsonObject_test.length() == 0)
                            {
                                Intent intent = new Intent(getApplicationContext(), Delivery_location.class);
                                intent.putExtra("user_id", ID_user_check_order);  //send data
                                intent.putExtra("BACK_","yet");
                                startActivity(intent);
                                finish();
                            }

                            else {
                                String ID = jsonObject_test.getString("User_id");
                                String User_PageOrder_status = jsonObject_test.getString("PageOrder_status");

                                Log.d("12212", User_PageOrder_status);
                                Log.d("22456462", status_task_Goal);

                                if ( User_PageOrder_status.equals("unsuccess") && status_task_Goal.equals(""))
                                {
                                    Intent intent = new Intent(getApplicationContext(),Delivery_status1.class);
                                    intent.putExtra("user_id",ID_user_check_order);  //send data
                                    intent.putExtra("BACK_","yet");
                                    startActivity(intent);
                                    finish();

                                    Log.d("222", User_PageOrder_status);
                                    Log.d("112", ID);

                                    Log.d("1114", "unsuccess && __");
                                }

                                if ( User_PageOrder_status.equals("delivery") && status_task_Goal.equals("confirm"))
                                {
                                    Intent intent = new Intent(getApplicationContext(),Delivery_status1.class);
                                    intent.putExtra("user_id",ID_user_check_order);  //send data
                                    intent.putExtra("BACK_","yet");
                                    startActivity(intent);
                                    finish();

                                    Log.d("4545", User_PageOrder_status);
                                    Log.d("123333", status_task_Goal);

                                    Log.d("1115", "delivery && __");
                                }

                                if (User_PageOrder_status.equals("delivery") && status_task_Goal.equals("Goal_1") && status_task_Box.equals("1"))
                                {

                                    Intent Intent = new Intent(Login.this, Open_the_box.class);
                                    Intent.putExtra("user_id", ID_user_check_order);
                                    Intent.putExtra("page_order_id", task_page);

                                    startActivity(Intent);
                                    finish();

                                    Log.d("1116", "delivery && Goal_1");
                                }

                                if (User_PageOrder_status.equals("delivery") && status_task_Goal.equals("Goal_2") && status_task_Box.equals("2"))
                                {
                                    Intent Intent = new Intent(Login.this, Open_the_box2.class);
                                    Intent.putExtra("user_id", ID_user_check_order);
                                    Intent.putExtra("page_order_id", task_page);


                                    startActivity(Intent);
                                    finish();

                                    Log.d("1117", "delivery && Goal_2");
                                }
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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    private void Stop_loop_Goal() {
        new CountDownTimer(1000, 250) {

            public void onTick(long duration) {
                //tTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext resource id
                // Duration
                long Mmin = (duration / 1000) / 60;
                long Ssec = (duration / 1000) % 60;
            }

            public void onFinish() {
                loop_D = false;
            }
        }.start();
    }
}


