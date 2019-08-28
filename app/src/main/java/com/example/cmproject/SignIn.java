//package com.example.cmproject;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.nfc.Tag;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.cmproject.Model.Driver;
//import com.example.cmproject.Utils.CheckConnection;
//import com.example.cmproject.Utils.Server;
//import com.example.cmproject.Utils.SessionManager;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SignIn extends AppCompatActivity {
//
//    EditText editPhone;
//    Button btnSignIn;
//    SessionManager sessionManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//
//        editPhone = findViewById(R.id.editPhone);
//        btnSignIn = findViewById(R.id.btnSignIn);
//        XuLyDangNhap();
//
//    }
//
//    private void XuLyDangNhap(){
//        if(CheckConnection.haveNetworkConnection(SignIn.this)){
//            btnSignIn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    final String phone = editPhone.getText().toString();
//                    if(phone.length() > 0){
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.UrlPutDriver, new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                if(response.equals("new user, wait for setting up"))
//                                {
//                                    CheckConnection.ShowToast(getApplicationContext(), getString(R.string.sign_in));
//                                }
//                                else
//                                {
//                                    Intent intent = new Intent(SignIn.this, MapsActivity.class);
//                                    sessionManager.setLoginState(true);
//                                    sessionManager.setPhone(phone);
//                                    startActivity(intent);
//                                    finish();
//                                    CheckConnection.ShowToast(getApplicationContext(), getString(R.string.success));
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                CheckConnection.ShowToast(getApplicationContext(), error.toString());
//                            }
//                        }) {
//                    @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            HashMap<String, String> hashMap = new HashMap<String, String>();
//                            hashMap.put("username", username);
//                            hashMap.put("password", password);
//                            return hashMap;
//                        }
//                    };
//                    requestQueue.add(stringRequest);
//                }
//                    else {
//                    CheckConnection.ShowToast(getApplicationContext(), getString(R.string.TK_MK_trong));
//                }
//            }
//        });
//    }
//        else {
//        CheckConnection.ShowToast(getApplicationContext(), getString(R.string.kiem_tra_ket_noi));
//    }
//}
