package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vishnu Chelle on 5/3/2015.
 */
public class GroupStatusAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Status> mStatuses;

    public GroupStatusAdapter(Context mContext, ArrayList<Status> statuses){
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
        View rowView = inflater.inflate(R.layout.group_status_row, parent, false);

        TextView tv = (TextView)rowView.findViewById(R.id.status);
        TextView pUser = (TextView)rowView.findViewById(R.id.postedUser);
        TextView tvDate = (TextView)rowView.findViewById(R.id.statusDate);
        tv.setText(mStatuses.get(mStatuses.size() - position -1).getStatus());
        pUser.setText(mStatuses.get(mStatuses.size() - position -1).getUserName());
        tvDate.setText(mStatuses.get(mStatuses.size() - position -1).getDate());
        return rowView;
    }
}
