package com.example.cmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmproject.Firebaseotp.LogIn;
import com.example.cmproject.Utils.SessionManager;


public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button btnXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        if(sessionManager.getLoginState()){
            startActivity(new Intent(MainActivity.this,MapsActivity.class));
        }
        findId();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        });


    }

    private void findId(){

        btnXacNhan = findViewById(R.id.btnLogIn);
    }

//    private void PostInformation(){
//        final String hoTen = editHoTen.getText().toString();
//        final String bienSo = editBienSo.getText().toString();
//        final String loaiXe = editLoai.getText().toString();
//        final String viTri = editViTri.getText().toString();
//        btnXacNhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(hoTen.length() > 0 && bienSo.length() > 0 && loaiXe.length() > 0 && viTri.length() >0){
//                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, Server.UrlPostDriver, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if(response.equals("duplicate!!")){
//
//                            }
//                        }
//                    })
//                }
//            }
//        });
//    }
}
