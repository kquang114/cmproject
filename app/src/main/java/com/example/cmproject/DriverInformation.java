package com.example.cmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cmproject.Utils.CheckConnection;
import com.example.cmproject.Utils.Server;

import java.util.ArrayList;

public class DriverInformation extends AppCompatActivity {

    EditText editHoTen, editBienSo, editLoai,editViTri;
    Button btnXacNhan,btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_information);

        findId();
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverInformation.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverInformation.this,TestDbActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findId(){
        editHoTen = findViewById(R.id.editName);
        editBienSo = findViewById(R.id.editLicense);
        editLoai = findViewById(R.id.editType);
        editViTri = findViewById(R.id.editLocation);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTest = findViewById(R.id.btnTest);
    }

    private void PostInformation(){
        final String hoTen = editHoTen.getText().toString();
        final String bienSo = editBienSo.getText().toString();
        final String loaiXe = editLoai.getText().toString();
        final String viTri = editViTri.getText().toString();

    }
}
