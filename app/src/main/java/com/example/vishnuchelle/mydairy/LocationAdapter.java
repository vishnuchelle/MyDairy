package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Vishnu Chelle on 5/2/2015.
 */
public class LocationAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Location> mLocations;

    public LocationAdapter(Context context, ArrayList<Location> locations){
        this.mContext = context;
        this.mLocations = locations;
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public Object getItem(int position) {
        return mLocations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.status_row, parent, false);
        TextView tv = (TextView)rowView.findViewById(R.id.status);
        TextView tvDate = (TextView)rowView.findViewById(R.id.statusDate);
        tv.setText(mLocations.get(position).getFormattedLocation());
//        tvDate.setText(mLocations.get(position).getDate());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(mLocations.get(position).getTimeStamp()));
        String date = (new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")).format(cal.getTime());
        tvDate.setText(date);
        return rowView;
    }
}
