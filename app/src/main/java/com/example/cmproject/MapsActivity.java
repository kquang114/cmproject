package com.example.cmproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cmproject.Model.Parking;
import com.example.cmproject.Utils.DirectionFinder;
import com.example.cmproject.Utils.DirectionFinderListener;
import com.example.cmproject.Utils.Route;
import com.example.cmproject.Utils.Server;
import com.example.cmproject.Utils.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        DirectionFinderListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener {

    Parking parking;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    GoogleApiClient mGoogleApiClient;
    FloatingActionButton floatingActionButton;
    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout tapactionlayout;
    View bottomSheet;
    public static  final int RequestCode = 101;
    Location myLocation;
    ImageView imgDriver,imgParking, imgGarage, imgMylocation;
    ImageView imgTraffic,imgSetting;

    SessionManager sessionManager;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    private ProgressDialog progressDialog;

    ArrayList<LatLng> lists;


    private void findId(){
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        tapactionlayout = findViewById(R.id.tap_action_layout);
        bottomSheet = findViewById(R.id.bottom_sheet1);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(120);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(i == BottomSheetBehavior.STATE_COLLAPSED){
                    tapactionlayout.setVisibility(view.VISIBLE);
                }

                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    tapactionlayout.setVisibility(View.GONE);
                }

                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    tapactionlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        tapactionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
                {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
//        // Find the toolbar view inside the activity layout
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        // Sets the Toolbar to act as the ActionBar for this Activity window.
//        // Make sure the toolbar exists in the activity and is not null
//        setActionBar(toolbar);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
//        getActionBar().setTitle("Search here");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setLoginState(false);
                startActivity(new Intent(MapsActivity.this,SignIn.class));
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //library to get myself location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sessionManager = new SessionManager(this);
        findId();
        //init map
        if(initMap()) {
            Toast.makeText(this, "ready to map", Toast.LENGTH_SHORT).show();
        }
        sendRequest();

    }

    private void sendRequest() {
        if(imgParking.isClickable()){
            new DirectionFinder(this, "My location",parking.getName().toString().trim());
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
        imgDriver = findViewById(R.id.imgDriver);
        imgParking = findViewById(R.id.imgParking);
        imgMylocation = findViewById(R.id.imgMyLocation);
        imgGarage = findViewById(R.id.imgGarage);
        imgTraffic = findViewById(R.id.imgTraffic);
        imgSetting = findViewById(R.id.setting);

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDriver();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        imgParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMarkers();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////                LatLng latLng = new LatLng(10.737696, 106.665194);
////                mMap.addMarker(new MarkerOptions()
////                        .position(latLng)
////                        .title("garage"));
//             //   polyline();
            }
        });
        imgMylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        imgTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap.isTrafficEnabled()){
                    Toast.makeText(MapsActivity.this,"Traffic Information already",Toast.LENGTH_SHORT).show();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                else{
                    mMap.setTrafficEnabled(true);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]
                {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, RequestCode);

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
                                    .draggable(true) //Making the marker draggable
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
                                    .draggable(true) //Making the marker draggable
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
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            String ShipMoney = route.distance.text.trim().replaceAll("[^\\d]", "");
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
