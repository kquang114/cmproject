package com.example.cmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cmproject.Adapter.DriverAdapter;
import com.example.cmproject.Adapter.ParkingAdapter;
import com.example.cmproject.Model.Driver;
import com.example.cmproject.Model.Parking;
import com.example.cmproject.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestDbActivity extends AppCompatActivity {

    ParkingAdapter parkingAdapter;
    DriverAdapter driverAdapter;
    ArrayList<Parking> parkingArrayList;
    ArrayList<Driver> driverArrayList;
    Button btnGoMap;
    ListView listViewParking,listViewDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        findId();

        parkingArrayList = new ArrayList<>();
        parkingAdapter = new ParkingAdapter(parkingArrayList,this);
        listViewParking.setAdapter(parkingAdapter);


        driverArrayList = new ArrayList<>();
        driverAdapter = new DriverAdapter(driverArrayList,this);
        listViewDriver.setAdapter(driverAdapter);

        setButtonGoMap();
        GetDriver();
        GetParking();

    }

    void findId(){
        btnGoMap = findViewById(R.id.btnOk);
        listViewParking = findViewById(R.id.listViewParking);
        listViewDriver = findViewById(R.id.listViewDriver);
    }

    void setButtonGoMap(){
        btnGoMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestDbActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    //String url = "http://10.184.159.6:8080/restapi/webapi/parkings";
    private void GetParking(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.UrlGetParking, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        parkingArrayList.add(new Parking(
                                jsonObject.getInt("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("address"),
                                jsonObject.getInt("price"),
                                jsonObject.getDouble("latitude"),
                                jsonObject.getDouble("longtitude"),
                                jsonObject.getString("type")
                        ));
                        parkingAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    parkingAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestDbActivity.this,"loi ket noi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //String url = "http://10.184.159.6:8080/restapi/webapi/drivers";
    private void GetDriver(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.UrlGetDriver, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        driverArrayList.add(new Driver(
                                jsonObject.getString("phone"),
                                jsonObject.getString("name"),
                                jsonObject.getString("license"),
                                jsonObject.getDouble("latitude"),
                                jsonObject.getDouble("longtitude")
                        ));
                        driverAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    driverAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestDbActivity.this,"loi ket noi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
