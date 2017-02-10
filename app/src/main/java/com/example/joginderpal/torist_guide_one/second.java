package com.example.joginderpal.torist_guide_one;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joginderpal on 22-01-2017.
 */
public class second extends Activity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    List<String> li1;
    List<Double> lat1;
    List<Double> lng1;
    List<String> li2;
    List<String> li3;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        requestQueue= Volley.newRequestQueue(this);
        recyclerView= (RecyclerView) findViewById(R.id.rvsecond);
        final ProgressDialog progressDialog=ProgressDialog.show(this,"","loading",false,false);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+places+in+Delhi&key=AIzaSyB6xDwO_OuT02fhnuVuEivgG0uwzHwspWE", null,

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

                       //         JSONArray photo=results.getJSONObject(i).getJSONArray("photos");
                                //     JSONObject jsonObject1=photo.getJSONObject(0);
  //                              for (int j=0;j<photo.length();j++){
//
    //                                JSONObject jsonObject11=photo.getJSONObject(j);
      //                              String photo_ref= jsonObject11.getString("photo_reference");
        //                          li2.add(photo_ref);

          //                      }
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

                            layoutManager=new LinearLayoutManager(second.this);
                            recyclerView.setLayoutManager(layoutManager);
                            adapter=new RecyclerAdapterone(second.this,li1,li2,lat1,lng1,li3);
                            recyclerView.setAdapter(adapter);


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
}
