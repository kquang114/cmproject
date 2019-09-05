package com.example.cmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cmproject.Model.Driver;
import com.example.cmproject.Utils.CheckConnection;
import com.example.cmproject.Utils.Server;
import com.example.cmproject.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignIn extends AppCompatActivity {

    EditText editPhone;
    Button btnSignIn;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sessionManager = new SessionManager(getApplicationContext());
        editPhone = findViewById(R.id.editPhone);
        btnSignIn = findViewById(R.id.btnSignIn);
        toJSon();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             progressDialog();

            }
        });

    }

    public void progressDialog(){
        final ProgressDialog progressDialog = new ProgressDialog(SignIn.this,R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        XuLyDangNhap();
                    }
                },1000);
    }
    //test parse string object to json
    public void toJSon() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", editPhone.getText().toString());

        JsonObjectRequest req = new JsonObjectRequest(Server.UrlPostDriver, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response:", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error: ", error.getMessage());
            }
        });
    }

    //test handle post json to server
    //using jsonObjectRequest and hashmap
    private void XuLyDangNhap1() {
        if (CheckConnection.haveNetworkConnection(SignIn.this)) {
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String phone = editPhone.getText().toString();
                    JSONObject js = new JSONObject();
                    if(phone.length() > 0){
                        try {
                            js.put("phone",editPhone.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    // Make request for JSONObject
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST, Server.UrlPostDriver, js,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Intent intent = new Intent(SignIn.this, MapsActivity.class);
                                        sessionManager.setLoginState(true);
                                        sessionManager.setPhone(phone);
                                        startActivity(intent);
                                        finish();
                                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.success));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                        @Override
                        public byte[] getBody() {
                            return phone.getBytes();
                        }

                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("phone", phone);
                            return hashMap;
                        }
                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }
                    };

                    // Adding request to request queue
                    Volley.newRequestQueue(SignIn.this).add(jsonObjectRequest);

                }
            });
        } else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.check_connect));
        }
    }

    //using stringRequest and hashmap
    private void XuLyDangNhap() {
        if (CheckConnection.haveNetworkConnection(SignIn.this)) {

                    final String phone = editPhone.getText().toString();
//                    JSONObject js = new JSONObject();
//                    if(phone.length() > 0){
//                        try {
//                            js.put("phone",editPhone.getText().toString());
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    HashMap<String,String> params = new HashMap<String,String>();
//                    params.put("phone",phone);
                    if (phone.length() > 0) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,Server.UrlSignIn, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("ab",response.toString());
                                Intent intent = new Intent(SignIn.this, MapsActivity.class);
                                sessionManager.setLoginState(true);
                                sessionManager.setPhone(phone);
                                startActivity(intent);
                                finish();
                                CheckConnection.ShowToast(getApplicationContext(), getString(R.string.success));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                CheckConnection.ShowToast(getApplicationContext(), error.toString());
                            }
                        }) {
                            @Override
                            public byte[] getBody() {
                                return phone.getBytes();
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            protected Map<String, String> getParams() {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("phone", phone);
                                hashMap.put("name", null);
                                hashMap.put("license", null);
                                hashMap.put("latitude", String.valueOf(0));
                                hashMap.put("longtitude", String.valueOf(0));

                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                        Log.i("cc",stringRequest.toString());
                    } else {
                        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.phone_empty));
                    }
        } else {
            CheckConnection.ShowToast(getApplicationContext(), getString(R.string.check_connect));
        }
    }
}