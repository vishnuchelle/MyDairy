package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vishnu Chelle on 5/3/2015.
 */
public class DistanceHistoryActivity extends ActionBarActivity {

    final Context mContext = this;
    private DatePicker datePick;
    private Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_history);

        datePick = (DatePicker)findViewById(R.id.datePicker);
        showBtn = (Button)findViewById(R.id.showBtn);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePick.getDayOfMonth();
                int month = datePick.getMonth();
                int year = datePick.getYear();

               Calendar cal = Calendar.getInstance();
                cal.set(year,month,day);
                Date d = cal.getTime();
                long endTime = d.getTime();

                //milli seconds for sevendays 7*24*60*60*1000
                long startTime = endTime - 7*24*60*60*1000;

                MySqliteHelper db = new MySqliteHelper(mContext);
                ArrayList<Distance> distanceList =  db.getDistanceTravelled(AppSharedPreference.getCurrentUser(mContext), startTime, endTime);

                Toast.makeText(mContext,"Returned distnces:"+distanceList.size(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}
