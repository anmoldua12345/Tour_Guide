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
public class LoginActivity extends Activity {

    EditText ed1,ed2;
    FrameLayout f1,f2;
    RequestQueue requestQueue;
    StringRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ed1= (EditText) findViewById(R.id.ed1);
        ed2= (EditText) findViewById(R.id.ed2);
        f1= (FrameLayout) findViewById(R.id.frame);
        f2= (FrameLayout) findViewById(R.id.frametwo);
        requestQueue= Volley.newRequestQueue(this);
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                request=new StringRequest(Request.Method.POST, "http://192.168.0.103/log1.php"
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String Connection=jsonObject.getString("Connection");
                            if (Connection.equals("Error")){
                                Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_LONG).show();
                            }
                            else{
                                String success=jsonObject.getString("success");
                                if (success.equals("Yes")) {
                                    String loc=jsonObject.getString("location");
                                    Toast.makeText(getApplicationContext(),"Account Exist", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                    i.putExtra("locat",loc);
                                    startActivity(i);
                                }
                                else{
                                    Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),"Register First", Toast.LENGTH_LONG).show();
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
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("email",ed1.getText().toString());
                        hashMap.put("password",ed2.getText().toString());

                        return hashMap;
                    }

                };

                requestQueue.add(request);
            }
        });




        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);






            }
        });




    }
}
