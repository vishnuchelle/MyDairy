package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vishnu Chelle on 3/18/2015.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Status> mStatuses;

    public MyAdapter(Context mContext,ArrayList<Status> statuses){
        this.mContext = mContext;
        this.mStatuses = statuses;

    }

    @Override
    public int getCount() {
        return mStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return mStatuses.get(position);
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
        tv.setText(mStatuses.get(position).getStatus());
        tvDate.setText(mStatuses.get(position).getDate());
        return rowView;
    }
}
