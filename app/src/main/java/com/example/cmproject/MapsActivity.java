package com.example.cmproject;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cmproject.Utils.DirectionFinderListener;
import com.example.cmproject.Utils.Route;
import com.example.cmproject.Utils.Server;
import com.example.cmproject.Utils.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    public static  final int RequestCode = 101;
    Location myLocation;
    Button btnParking, btnMyLocation,btnFind,btnDrivers;

    SessionManager sessionManager;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    private ProgressDialog progressDialog;

    ArrayList<LatLng> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        sessionManager = new SessionManager(this);
      if(initMap()){
          Toast.makeText(this,"ready to map",Toast.LENGTH_SHORT).show();
      }
      else{

      }
    }

    private boolean initMap() {
        if(mMap == null){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return mMap != null;
    }

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},RequestCode);
        }
        mMap.setMyLocationEnabled(true);
        Task<Location> task = mFusedLocationClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    myLocation = location;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10.0f));
                }
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        btnDrivers = findViewById(R.id.btnDrivers);
        btnParking = findViewById(R.id.btnParkings);
        btnMyLocation = findViewById(R.id.btnmyLocation);
        btnFind = findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polyline();
            }
        });
        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });

        btnDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDriver();
            }
        });
        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMarkers();
//                LatLng latLng = new LatLng(10.737696, 106.665194);
//                mMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .title("garage"));
             //   polyline();

            }
        });
        mMap.setTrafficEnabled(true);
    }

    private void GetDriver(){
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.UrlGetDriver, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            //duyet mang duoc phan hoi tu json
                            JSONObject jsonObject = response.getJSONObject(i);
                            Double lat = jsonObject.getDouble("latitude");
                            Double lng = jsonObject.getDouble("longtitude");
                            String license = jsonObject.getString("license");
                            String name = jsonObject.getString("name");
                            LatLng latLng = new LatLng(lat,lng);
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(name)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.automobile))
                                    .snippet(license));
                            marker.showInfoWindow();
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8.0f));;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this,"loi ket noi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //get data from mysql to display on google map
   private void createMarkers(){
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.UrlGetParking, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            //duyet mang duoc phan hoi tu json
                            JSONObject jsonObject = response.getJSONObject(i);
                            Double lat = jsonObject.getDouble("latitude");
                            Double lng = jsonObject.getDouble("longtitude");
                            Integer price = jsonObject.getInt("price");
                            String name = jsonObject.getString("name");
                            LatLng latLng = new LatLng(lat,lng);
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(String.valueOf(price)+"$")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_park))
                                    .snippet(name));
                            marker.showInfoWindow();
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8.0f));;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this,"loi ket noi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void polyline(){
        lists = new ArrayList<LatLng>();
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.UrlGetDriver, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for(int i = 0; i < response.length(); i ++) {
                        try {
                            //duyet mang duoc phan hoi tu json
                            JSONObject jsonObject = response.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            lists.add((new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longtitude"))));
                            Log.i("cc", valueOf(lists.size()));

//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),6.0f));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Polyline line = mMap.addPolyline(new PolylineOptions()
                                .clickable(true)
                                .addAll(lists)
                                .width(5)
                                .color(Color.RED));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapsActivity.this,"loi ket noi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
