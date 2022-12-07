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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Delivery_location extends AppCompatActivity {

    SupportMapFragment The_Tara,Hall,Car_park1,Car_park2,CP_All_Academy,SATIT_PIM,seven_eleven,marker_all;
    FusedLocationProviderClient client;

    Button submit, cancel;
    TextView namelocation, location_txt;

    public static String getloca = "";
    public static String post_id_local_select;

    public static double la_get, long_get;
    public static String name_local_get;
    public static boolean loop_marker = true ;

    public static ArrayList<LatLng> locationArrayList;
    public static ArrayList<String> name_locationArrayList;

    Bitmap Gray_Marker,Seven_Marker;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location);

        namelocation = findViewById(R.id.namelocation);
        location_txt = findViewById(R.id.location);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        marker_all = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        The_Tara = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Hall = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Car_park1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Car_park2 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        CP_All_Academy = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        SATIT_PIM = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        seven_eleven = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.gray_marker);
        Bitmap H = bitmapdraw.getBitmap();
        Gray_Marker = Bitmap.createScaledBitmap(H, 90, 90, false);

        BitmapDrawable bitmap_seven = (BitmapDrawable) getResources().getDrawable(R.drawable.seven_eleven_marker);
        Bitmap seven = bitmap_seven.getBitmap();
        Seven_Marker = Bitmap.createScaledBitmap(seven, 60, 60, false);

        get_all_data_location();

        client = LocationServices.getFusedLocationProviderClient(this);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                while (loop_marker)
//                                {
//                                    try {
                        getmylocation();

//                                        Thread.sleep(1000);
//
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        });
//                        thread.start();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        cancel = findViewById(R.id.cancel_lo);
        cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (backPressedTime + 2000 > System.currentTimeMillis())
                        {
                            Animation animation = AnimationUtils.loadAnimation(Delivery_location.this,R.anim.fadein_half);
                            cancel.startAnimation(animation);

                            Intent intent = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            backToast = Toast.makeText(getBaseContext(), "Press cancel again to Logout", Toast.LENGTH_SHORT);
                            backToast.show();
                        }
                        backPressedTime = System.currentTimeMillis();
                    }
                });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            Intent intent = getIntent();
            String ID_USER = intent.getStringExtra("user_id");

            @Override
            public void onClick(View v) {
//                if (getloca.equals("Car Park 1")) {
//
//                    postdata_1();
//
//                    Intent intent = new Intent(Delivery_location.this, Delivery_status1.class);
//                    intent.putExtra("user_id",ID_USER);
//                    intent.putExtra("BACK_","yet");
//                    intent.putExtra("Name","Car Park 1");
//
//                    startActivity(intent);
//                    finish();
//                }
//
//                if (getloca.equals("Car Park 2")) {
//
//                    postdata_2();
//
//                    Intent intent = new Intent(Delivery_location.this, Delivery_status1.class);
//                    intent.putExtra("user_id",ID_USER);
//                    intent.putExtra("BACK_","yet");
//                    intent.putExtra("Name","Car Park 2");
//
//                    startActivity(intent);
//                    finish();
//                }
                Animation animation = AnimationUtils.loadAnimation(Delivery_location.this,R.anim.fadein_half);
                submit.startAnimation(animation);

                if (getloca.equals("THE TARA")) {

                    postdata_3();

                    Intent intent = new Intent(Delivery_location.this, Delivery_status1.class);
                    intent.putExtra("user_id",ID_USER);
                    intent.putExtra("BACK_","yet");
                    intent.putExtra("Name","TARA");

                    startActivity(intent);
                    finish();
                }



                else {
                    Toast.makeText(Delivery_location.this, "Delivery only THE TARA", Toast.LENGTH_LONG).show();
                }
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

        Task<Location> task_ = client.getLastLocation();
        task_.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                marker_all.getMapAsync(googleMap -> {
                    LatLng latLng =new LatLng(la_get,long_get);

                    for (int i = 0; i < locationArrayList.size(); i++) {

                        MarkerOptions marker_all = new MarkerOptions().position(locationArrayList.get(i)).title(name_locationArrayList.get(i));
                        googleMap.addMarker(marker_all);

                        googleMap.addMarker(marker_all);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {

                                if (marker.getTitle().equals("Car Park 1")) {
                                    namelocation.setText("Car Park 1");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("85/1 Thanon Chaeng Watthana,Tambon Bang Talat, Amphoe Pak Kret, Chang Wat Nonthaburi 11120");
                                    post_id_local_select = "2";
                                }

                                if (marker.getTitle().equals("Car Park 2")) {
                                    namelocation.setText("Car Park 2");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("85/1 Thanon Chaeng Watthana,Tambon Bang Talat, Amphoe Pak Kret, Chang Wat Nonthaburi 11120");
                                    post_id_local_select = "3";
                                }

                                if (marker.getTitle().equals("THE TARA")) {
                                    namelocation.setText("THE TARA");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("85/1 Moo 2, Chaengwattana Road, Pak Kret District, Nonthaburi 11120");
                                    post_id_local_select = "1";
                                }

                                if (marker.getTitle().equals("Hall")) {
                                    namelocation.setText("Hall");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("85/1 Moo 2, Chaengwattana Road, Pak Kret District, Nonthaburi 11120");
                                    post_id_local_select = "5";
                                }

                                if (marker.getTitle().equals("CP All Academy")) {
                                    namelocation.setText("CP All Academy");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("85/1 Moo 2, Chaengwattana Road, Pak Kret District, Nonthaburi 11120");
                                    post_id_local_select = "4";
                                }

                                if (marker.getTitle().equals("SATIT PIM")) {
                                    namelocation.setText("SATIT PIM");
                                    getloca = namelocation.getText().toString();
                                    location_txt.setText("45/23 Moo 2, Chaeng Watthana - Pak Kret 28, Pak Kret District, Nonthaburi 11120");
                                    post_id_local_select = "6";
                                }
                                return false;
                            }
                        });
                    }
                });

                seven_eleven.getMapAsync(googleMap -> {
                    LatLng latLng =new LatLng(13.90263,100.53225);
                    MarkerOptions seven_eleven=new MarkerOptions().position(latLng).title("7-ELEVEN").icon(BitmapDescriptorFactory.fromBitmap(Seven_Marker));
                    googleMap.addMarker(seven_eleven);
                });
//
//                Car_park2.getMapAsync(googleMap -> {
//                    LatLng latLng =new LatLng(13.9024667,100.5330424);
//                    MarkerOptions Car_park2=new MarkerOptions().position(latLng).title("Car Park 2").icon(BitmapDescriptorFactory.fromBitmap(Gray_Marker));
//                    googleMap.addMarker(Car_park2);
//
//                });
//
//                The_Tara.getMapAsync(googleMap -> {
//                    LatLng latLng = new LatLng(13.901698,100.531508);
//                    MarkerOptions The_Tara = new MarkerOptions().position(latLng).title("THE TARA");
//
//                    googleMap.addMarker(The_Tara);
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
//
//                });
//
//                Hall.getMapAsync(googleMap -> {
//                    LatLng latLng =new LatLng(13.90197,100.53294);
//                    MarkerOptions Hall=new MarkerOptions().position(latLng).title("Hall") .icon(BitmapDescriptorFactory.fromBitmap(Gray_Marker));
//                    googleMap.addMarker(Hall);
//                });
//
//                CP_All_Academy.getMapAsync(googleMap -> {
//                    LatLng latLng =new LatLng(13.90172,100.53328);
//                    MarkerOptions CP_All_Academy =new MarkerOptions().position(latLng).title("CP All Academy").icon(BitmapDescriptorFactory.fromBitmap(Gray_Marker));
//                    googleMap.addMarker(CP_All_Academy);
//
//                });
//
//                SATIT_PIM.getMapAsync(googleMap -> {
//                    LatLng latLng =new LatLng(13.9022,100.53150);
//                    MarkerOptions SATIT_PIM=new MarkerOptions().position(latLng).title("SATIT PIM").icon(BitmapDescriptorFactory.fromBitmap(Gray_Marker));
//                    googleMap.addMarker(SATIT_PIM);
//
//                });
            }
        });
//        Stop_loop_confirm();
    }

    // --  API  --
    private  void postdata_1()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://150.95.88.167:8083/order",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { Log.i("Response", response);
                        System.out.println(response);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Log.d("Error.Response", "Unsuccess");
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", ID_USER);              //this is user_id
                params.put("basket_id", "20");
                params.put("location_id", "2");
                params.put("payment_id", "1");
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    private  void postdata_2()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://150.95.88.167:8083/order",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { Log.i("Response", response);
                        System.out.println(response);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Log.d("Error.Response", "Unsuccess");
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", ID_USER);              //this is user_id
                params.put("basket_id", "19");
                params.put("location_id", "3");
                params.put("payment_id", "2");
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    private  void postdata_3()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,"http://150.95.88.167:8083/order",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) { Log.i("Response", response);
                        System.out.println(response);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("volley", "Error: " + error.getMessage());
                        error.printStackTrace();
                        Log.d("Error.Response", "Unsuccess");
                    }
                }
        )

        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", ID_USER);              //this is user_id
                params.put("basket_id", "21");
                params.put("location_id", post_id_local_select);
                params.put("payment_id", "3");
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjRequest);

    }

    private  void get_all_data_location()
    {
        Intent intent = getIntent();
        String ID_USER = intent.getStringExtra("user_id");

        final String url = "http://150.95.88.167:8083/locations";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("784116", response.toString());

                        try
                        {
                            JSONArray _locations_data = response.getJSONArray("data");
                            locationArrayList = new ArrayList<>();
                            name_locationArrayList = new ArrayList<>();

                            for(int i = 0; i <= _locations_data.length(); i++){

                                JSONObject location_index = _locations_data.getJSONObject(i);
                                String Location_get_status = location_index.getString("Location_status");


                                Log.d("333333", Location_get_status);

                                if ( Location_get_status.equals("able") ) {
                                    String Location_get_name = location_index.getString("Location_name");
                                    Double Location_latitude  = location_index.getDouble("Location_latitude");
                                    Double Location_longitude  = location_index.getDouble("Location_longitude");

                                    name_local_get = Location_get_name;
                                    name_locationArrayList.add(Location_get_name);

                                    la_get = Location_latitude;
                                    long_get = Location_longitude;

                                    LatLng asa = new LatLng(Location_latitude, Location_longitude);
                                    locationArrayList.add(asa);

                                    getmylocation();

                                    Log.d("951753", la_get+", "+long_get+"");
                                }
                            }


                        }
                        catch (JSONException e)
                        {
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


}