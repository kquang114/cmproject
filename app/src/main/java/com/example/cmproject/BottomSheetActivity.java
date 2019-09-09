//package com.example.cmproject;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentActivity;
//
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//
//public class BottomSheetActivity extends FragmentActivity {
//
//    private BottomSheetBehavior sheetBehavior;
//    private LinearLayout bottom_sheet;
//    Button btn_bottom_sheet;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_main);
//        bottom_sheet = findViewById(R.id.bottom_sheet);
//        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
//        btn_bottom_sheet = findViewById(R.id.btn_bottom_sheet);
//        BottomSheet();
//    }
//    //function what make bottom sheet layout do
//    private void BottomSheet() {
//        btn_bottom_sheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    btn_bottom_sheet.setText("Close sheet");
//                } else {
//                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    btn_bottom_sheet.setText("Expand sheet");
//                }
//            }
//        });
//
//        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View view, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btn_bottom_sheet.setText("Close Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btn_bottom_sheet.setText("Expand Sheet");
//                    }
//                    break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        break;
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View view, float v) {
//
//            }
//        });
//    }
//}


//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:app="http://schemas.android.com/apk/res-auto"
//        android:id="@+id/bottom_sheet"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:background="#ffffff"
//        android:orientation="vertical"
//        android:padding="16dp"
//        app:behavior_hideable="false"
//        app:behavior_peekHeight="90dp"
//        app:layout_behavior="android.support.design.widget.BottomSheetBehavior">
//
//<LinearLayout
//        android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:orientation="horizontal">
//
//<View
//            android:layout_width="0dp"
//                    android:layout_height="match_parent"
//                    android:layout_weight="1" />
//
//<LinearLayout
//            android:layout_width="0dp"
//                    android:layout_height="wrap_content"
//                    android:layout_weight="5"
//                    android:orientation="vertical">
//
//<TextView
//                android:layout_width="wrap_content"
//                        android:layout_height="wrap_content"
//                        android:text="Dandelion Chocolate"
//                        android:textColor="#000000"
//                        android:textSize="24sp"
//                        android:textStyle="bold" />
//
//<LinearLayout
//                android:layout_width="wrap_content"
//                        android:layout_height="wrap_content"
//                        android:layout_marginTop="8dp"
//                        android:orientation="horizontal">
//
//<RatingBar
//                    style="@style/Base.Widget.AppCompat.RatingBar.Small"
//                            android:layout_width="wrap_content"
//                            android:layout_height="wrap_content"
//                            android:numStars="5"
//                            android:rating="4" />
//
//<TextView
//                    android:layout_width="wrap_content"
//                            android:layout_height="wrap_content"
//                            android:text="4.7" />
//
//<TextView
//                    android:layout_width="wrap_content"
//                            android:layout_height="wrap_content"
//                            android:layout_marginLeft="8dp"
//                            android:text="(51)" />
//
//</LinearLayout>
//
//<View
//                android:layout_width="match_parent"
//                        android:layout_height="0.5dp"
//                        android:layout_marginTop="12dp"
//                        android:layout_marginBottom="12dp"
//                        android:background="#5d5d5d" />
//
//<TextView
//                android:layout_width="wrap_content"
//                        android:layout_height="wrap_content"
//                        android:text="12 min away"
//                        android:textColor="#5692F5" />
//
//<View
//                android:layout_width="match_parent"
//                        android:layout_height="0.5dp"
//                        android:layout_marginTop="12dp"
//                        android:layout_marginBottom="12dp"
//                        android:background="#5d5d5d" />
//
//</LinearLayout>
//
//</LinearLayout>
//
//<TextView
//        android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_margin="8dp"
//                android:drawableLeft="@drawable/icon_park"
//                android:drawablePadding="16dp"
//                android:text="740 Valencia St, San Fracisco, CA" />
//
//<TextView
//        android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_margin="8dp"
//                android:drawableLeft="@drawable/icon_phone"
//                android:drawablePadding="16dp"
//                android:text="(415) 349-0942" />
//
//<TextView
//        android:layout_width="wrap_content"
//                android:layout_height="wrap_content"
//                android:layout_margin="8dp"
//                android:drawableLeft="@drawable/icon_time"
//                android:drawablePadding="16dp"
//                android:text="Wed, 10 AM - 9 PM" />
//
//<Button
//        android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:layout_marginTop="16dp"
//                android:background="#000"
//                android:text="PROCEED PAYMENT"
//                android:textColor="#fff" />
//
//</LinearLayout>
