package com.example.cmproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cmproject.Model.Driver;
import com.example.cmproject.R;

import java.util.ArrayList;

public class DriverAdapter extends BaseAdapter {

    ArrayList<Driver> driverArrayList;
    Context context;

    public DriverAdapter(ArrayList<Driver> driverArrayList, Context context) {
        this.driverArrayList = driverArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return driverArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return driverArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtPhone,txtName,txtLicense,txtLatitude,txtLongtitude;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DriverAdapter.ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new DriverAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_rows_driver, null);
            viewHolder.txtPhone = view.findViewById(R.id.id);
            viewHolder.txtName = view.findViewById(R.id.name);
            viewHolder.txtLicense = view.findViewById(R.id.license);
            viewHolder.txtLatitude = view.findViewById(R.id.latitude);
            viewHolder.txtLongtitude = view.findViewById(R.id.longtitude);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (DriverAdapter.ViewHolder) view.getTag();
        }

        Driver driver = driverArrayList.get(i);
        viewHolder.txtPhone.setText(driver.getPhone());
        viewHolder.txtName.setText(driver.getName());
        viewHolder.txtLicense.setText(driver.getLicense());
//        DecimalFormat df = new DecimalFormat("##,######");
//        viewHolder.txtLatitude.setText(df.format(parking.getLatitude())+"");
//        viewHolder.txtLongtitude.setText(df.format(parking.getLng())+"");
        viewHolder.txtLatitude.setText("" + driver.getLatitude());
        viewHolder.txtLongtitude.setText(""+driver.getLng());
        return view;
    }
}
