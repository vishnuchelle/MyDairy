package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Vishnu Chelle on 3/18/2015.
 */
public class StatusAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Status> mStatuses;

    public StatusAdapter(Context mContext, ArrayList<Status> statuses){
        this.mContext = mContext;
        this.mStatuses = statuses;

    }

    @Override
    public int getCount() {
        return mStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return mStatuses.get(mStatuses.size() - position -1 );
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
        tv.setText(mStatuses.get(mStatuses.size() - position -1).getStatus());
        tvDate.setText(mStatuses.get(mStatuses.size() - position -1).getDate());
        return rowView;
    }
}
