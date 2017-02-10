package com.example.joginderpal.torist_guide_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joginderpal on 04-01-2017.
 */
public class RegisterActivity extends Activity {
    EditText ed1,ed2,ed3;
    RequestQueue requestQueue;
    StringRequest request;

    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ed1= (EditText) findViewById(R.id.ed3);
        ed2= (EditText) findViewById(R.id.ed4);
        ed3= (EditText) findViewById(R.id.ed5);
        frame= (FrameLayout) findViewById(R.id.framethree);
        requestQueue= Volley.newRequestQueue(this);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url="http://192.168.0.103/register.php";

                StringRequest request=new StringRequest(Request.Method.POST,url,


                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String connection=jsonObject.getString("Connection");
                                    if (connection.equals("Error")){

                                        Toast.makeText(getApplicationContext(),"check Your Internet Connection",Toast.LENGTH_LONG).show();

                                    }

                                    else{

                                        String success=jsonObject.getString("success");
                                        if (success.equals("Yes")){

                                            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                            i.putExtra("locat",ed3.getText().toString());
                                            startActivity(i);
                                            Toast.makeText(getApplicationContext(),"Register Account successfully",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Please Try Again",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }


                ){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap=new HashMap<String, String>();
                        hashMap.put("email",ed1.getText().toString());
                        hashMap.put("password",ed2.getText().toString());
                        hashMap.put("Location",ed3.getText().toString());
                        return hashMap;

                    }
                };

                requestQueue.add(request);



            }
        });


    }
}
