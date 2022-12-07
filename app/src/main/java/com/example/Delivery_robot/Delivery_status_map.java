package com.example.Delivery_robot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Delivery_status_map extends AppCompatActivity {

    SupportMapFragment robot,Hall,Car_park1,Car_park2,CP_All_Academy,SATIT_PIM;
    FusedLocationProviderClient client;
    float Bearing = 0;
    Location myLocation = null;
    Location myUpdatedLocation = null;
    static Marker carMarker;
//    private final static int LOCATION_REQUEST_CODE = 23;

    public static String now_lati = "";
    public static String now_longi = "";

    public static double now_lati_dou ;
    public static double now_longi_dou ;
    Bitmap BitMapMarker,destination_deli;

    TextView namelocation;
    Button back;


    public static String desti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status_map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        robot = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Car_park1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Car_park2 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        get_status_Destination_lati_long();

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.mark_round_robot_realtime);
        Bitmap b = bitmapdraw.getBitmap();
        BitMapMarker = Bitmap.createScaledBitmap(b, 80, 80, false);

        BitmapDrawable draw = (BitmapDrawable) getResources().getDrawable(R.drawable.location);
        Bitmap b1 = draw.getBitmap();
        destination_deli = Bitmap.createScaledBitmap(b1, 130, 130, false);


        namelocation = findViewById(R.id.namelocation);

        //--------------------------  Thread   --------------------------------//
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    try {
                        get_status_Robot_lati_long();
//                        robot_location();
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();


        client = LocationServices.getFusedLocationProviderClient(this);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true)
                                {
                                    try {
                                        getmylocation();
                                        Thread.sleep(1000);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        thread.start();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();




        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            Intent intent = getIntent();
            String ID_USER = intent.getStringExtra("user_id");

            @Override
            public void onClick(View v) {
                Intent _intent = getIntent();
                String ID_USER = _intent.getStringExtra("user_id");

                Intent intent = new Intent(Delivery_status_map.this, Delivery_status1.class);
                intent.putExtra("user_id",ID_USER);
                intent.putExtra("BACK_","back");
                startActivity(intent);
                finish();
            }
        });
    }

    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//             TODO: Consider calling
//                ActivityCompat#requestPermissions
//             here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults)
//             to handle the case where the user grants the permission. See the documentation
//             for ActivityCompat#requestPermissions for more details.
            return;
        }


        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                myUpdatedLocation = location;
                myLocation = location;

                robot.getMapAsync(googleMap -> {
                    LatLng latLng =new LatLng(now_lati_dou,now_longi_dou);
                    carMarker = googleMap.addMarker(new MarkerOptions().position(latLng).
                            flat(true).icon(BitmapDescriptorFactory.fromBitmap(BitMapMarker)).title("Robot on delivery"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
//                    Bearing = location.getBearing();
                    LatLng updatedLatLng = new LatLng(now_lati_dou, now_longi_dou);
                    changePositionSmoothly (carMarker,updatedLatLng);

                    robot.getView().setEnabled(false);
                    googleMap.getUiSettings().setAllGesturesEnabled(false);

                    Log.d("213123123132123",Double.toString(now_longi_dou));
                });

            }
        });
    }

    void changePositionSmoothly(final Marker myMarker,final LatLng newLatLng) {

        final LatLng startPosition = new LatLng(now_lati_dou, now_longi_dou);
        final LatLng finalPosition = newLatLng;
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 1000;
        final boolean hideMarker = false;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
//                myMarker.setRotation(bearing);
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                        startPosition.longitude * (1 - t) + finalPosition.longitude * t);

                myMarker.setPosition(currentPosition);

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 1000);
                } else {
                    if (hideMarker) {
                        myMarker.setVisible(true);
                    } else {
                        myMarker.setVisible(false);
                    }
                }

            }
        });
    }


    private void get_status_Robot_lati_long() {
        // get ID user
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        String USER_id = "6";

        final String url = "http://150.95.88.167:8083/task/"+ID_USER;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject _employee = response.getJSONObject("data");

                            JSONObject robot = _employee.getJSONObject("robot");

                            JSONObject robot_data = robot.getJSONObject("data");

                            String robot_latitude = robot_data.getString("latitude");

                            String robot_longitude = robot_data.getString("longitude");

                            now_lati = robot_latitude;
                            now_longi = robot_longitude;

                            now_lati_dou = Double.parseDouble(robot_latitude);
                            now_longi_dou = Double.parseDouble(robot_longitude);


                            Log.d("check_lati",Double.toString(now_lati_dou));
                            Log.d("check_longi",Double.toString(now_longi_dou));

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

        private void get_status_Destination_lati_long() {
        // get ID user
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        String USER_id = "6";


        final String url = "http://150.95.88.167:8083/order/"+ID_USER;
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

                                String Location_id_destination = jsonObject_test.getString("Location_id");
                                desti = Location_id_destination;

                            if(Location_id_destination.equals("2")){
                                Car_park1.getMapAsync(googleMap -> {
                                    LatLng latLng =new LatLng(13.90263,100.53250);
                                    MarkerOptions Car_park =new MarkerOptions().position(latLng).title("Pickup point")
                                            .icon(BitmapDescriptorFactory.fromBitmap(destination_deli));
                                    googleMap.addMarker(Car_park);

                                    namelocation.setText("Car park point 1");
                                });

                            }

                            else if(Location_id_destination.equals("3")){
                                Car_park2.getMapAsync(googleMap -> {
                                    LatLng latLng =new LatLng(13.9024667,100.5330424);
                                    MarkerOptions Car_park2 =new MarkerOptions().position(latLng).title("Pickup point")
                                            .icon(BitmapDescriptorFactory.fromBitmap(destination_deli));
                                    googleMap.addMarker(Car_park2);

                                    namelocation.setText("Car park point 2");
                                });
                            }

                                Log.d("Location_id", Location_id_destination);
                                Log.d("4546465465", Location_id_destination);
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
        Intent _intent = getIntent();
        String ID_USER = _intent.getStringExtra("user_id");

        Intent intent = new Intent(Delivery_status_map.this, Delivery_status1.class);
        intent.putExtra("user_id",ID_USER);
        intent.putExtra("BACK_","back");
        startActivity(intent);
        finish();
    }
}

