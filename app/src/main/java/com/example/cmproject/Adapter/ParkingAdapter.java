package com.example.cmproject.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cmproject.Model.Parking;
import com.example.cmproject.R;

import java.util.ArrayList;

public class ParkingAdapter  extends BaseAdapter {

    ArrayList<Parking> parkingArrayList;
    Context context;

    public ParkingAdapter(ArrayList<Parking> parkingArrayList, Context context) {
        this.parkingArrayList = parkingArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return parkingArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return parkingArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtId,txtName,txtAddress,txtPrice,txtLatitude,txtLongtitude,txtType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_rows, null);
            viewHolder.txtId = view.findViewById(R.id.id);
            viewHolder.txtName = view.findViewById(R.id.name);
            viewHolder.txtAddress = view.findViewById(R.id.address);
            viewHolder.txtPrice = view.findViewById(R.id.price);
            viewHolder.txtLatitude = view.findViewById(R.id.latitude);
            viewHolder.txtLongtitude = view.findViewById(R.id.longtitude);
            viewHolder.txtType = view.findViewById(R.id.type);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Parking parking = parkingArrayList.get(i);
        viewHolder.txtId.setText("id = " + parking.getId());
        viewHolder.txtName.setText(parking.getName());
        viewHolder.txtAddress.setText(parking.getAddress());
        viewHolder.txtPrice.setText(""+parking.getPrice());
//        DecimalFormat df = new DecimalFormat("##,######");
//        viewHolder.txtLatitude.setText(df.format(parking.getLatitude())+"");
//        viewHolder.txtLongtitude.setText(df.format(parking.getLng())+"");

        viewHolder.txtLatitude.setText("" + parking.getLatitude());
        viewHolder.txtLongtitude.setText(""+parking.getLng());
        viewHolder.txtType.setText(parking.getType());

        return view;
    }
}
