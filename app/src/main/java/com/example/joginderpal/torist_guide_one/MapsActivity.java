package com.example.joginderpal.torist_guide_one;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<String> li1;
    List<Double> lat1;
    List<Double> lng1;
    List<String> li2;
    List<String> li3;
    RequestQueue requestQueue;
    Toolbar btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btm= (Toolbar) findViewById(R.id.tb_bottom);
        requestQueue= Volley.newRequestQueue(this);
     //   recyclerView= (RecyclerView) findViewById(R.id.rvsecond);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(getApplicationContext(),"Click plus for more info",Toast.LENGTH_LONG).show();

        btm.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        btm.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            }
        });

        btm.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MapsActivity.this,second.class);
                startActivity(i);

            }
        });

        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    String pass=getIntent().getExtras().getString("pass");
    if (pass.equals("1")){



        final ProgressDialog progressDialog1=ProgressDialog.show(this,"","loading",false,false);
        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+getIntent().getExtras().getString("text")+"&key=AIzaSyB6xDwO_OuT02fhnuVuEivgG0uwzHwspWE", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressDialog1.dismiss();
                            li1=new ArrayList<>();
                            li2=new ArrayList<String>();
                            lat1=new ArrayList<>();
                            lng1=new ArrayList<>();
                            li3=new ArrayList<>();
                            JSONArray results=response.getJSONArray("results");
                            for (int i=0;i<results.length();i++){

                                //        JSONArray photo=results.getJSONObject(i).getJSONArray("photos");
                                //     JSONObject jsonObject1=photo.getJSONObject(0);
                                //       for (int j=0;j<photo.length();j++){
//
                                //                                  JSONObject jsonObject11=photo.getJSONObject(j);
                                //                                String photo_ref= jsonObject11.getString("photo_reference");
                                //                              li2.add(photo_ref);
//
                                //                              }
                                JSONObject jsonObject=results.getJSONObject(i);
                                String addr=jsonObject.getString("formatted_address");
                                li1.add(addr);
                                JSONObject geo=jsonObject.getJSONObject("geometry");
                                JSONObject loca=geo.getJSONObject("location");
                                double lat=loca.getDouble("lat");
                                lat1.add(lat);
                                double lon=loca.getDouble("lng");
                                lng1.add(lon);
                                //     String photo_ref=jsonObject1.getString("photo_reference");
                                //     li2.add(photo_ref);
                                String name=jsonObject.getString("name");
                                li3.add(name);
                            }

                            //      layoutManager=new LinearLayoutManager(second.this);
                            //     recyclerView.setLayoutManager(layoutManager);
                            //     adapter=new RecyclerAdapterone(second.this,li1,li2,lat1,lng1,li3);
                            //     recyclerView.setAdapter(adapter);


                            for (int i=0;i<lat1.size();i++){
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat1.get(i),lng1.get(i))).title(li3.get(i)));
                            }
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat1.get(0),lng1.get(0))).zoom(12).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();


                    }
                }

        );


        requestQueue.add(jsonObjectRequest1);



    }

        else if (pass.equals("2")){


        final ProgressDialog progressDialog=ProgressDialog.show(this,"","loading",false,false);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+getIntent().getExtras().getString("text1")+"in"+getIntent().getExtras().getString("text")+"&key=AIzaSyB6xDwO_OuT02fhnuVuEivgG0uwzHwspWE", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressDialog.dismiss();
                            li1=new ArrayList<>();
                            li2=new ArrayList<String>();
                            lat1=new ArrayList<>();
                            lng1=new ArrayList<>();
                            li3=new ArrayList<>();
                            JSONArray results=response.getJSONArray("results");
                            for (int i=0;i<results.length();i++){

                                //        JSONArray photo=results.getJSONObject(i).getJSONArray("photos");
                                //     JSONObject jsonObject1=photo.getJSONObject(0);
                                //       for (int j=0;j<photo.length();j++){
//
                                //                                  JSONObject jsonObject11=photo.getJSONObject(j);
                                //                                String photo_ref= jsonObject11.getString("photo_reference");
                                //                              li2.add(photo_ref);
//
                                //                              }
                                JSONObject jsonObject=results.getJSONObject(i);
                                String addr=jsonObject.getString("formatted_address");
                                li1.add(addr);
                                JSONObject geo=jsonObject.getJSONObject("geometry");
                                JSONObject loca=geo.getJSONObject("location");
                                double lat=loca.getDouble("lat");
                                lat1.add(lat);
                                double lon=loca.getDouble("lng");
                                lng1.add(lon);
                                //     String photo_ref=jsonObject1.getString("photo_reference");
                                //     li2.add(photo_ref);
                                String name=jsonObject.getString("name");
                                li3.add(name);
                            }

                            //      layoutManager=new LinearLayoutManager(second.this);
                            //     recyclerView.setLayoutManager(layoutManager);
                            //     adapter=new RecyclerAdapterone(second.this,li1,li2,lat1,lng1,li3);
                            //     recyclerView.setAdapter(adapter);


                            for (int i=0;i<lat1.size();i++){
                                mMap.addMarker(new MarkerOptions().position(new LatLng(lat1.get(i),lng1.get(i))).title(li3.get(i)));
                            }
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat1.get(0),lng1.get(0))).zoom(12).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();


                    }
                }

        );


        requestQueue.add(jsonObjectRequest);



    }

        else if (pass.equals("3")){

        double la=getIntent().getExtras().getDouble("lat");
        double ln=getIntent().getExtras().getDouble("lng");
        String titl=getIntent().getExtras().getString("title");
        mMap.addMarker(new MarkerOptions().position(new LatLng(la,ln)).title(titl));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(la,ln)).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }




    }
}
