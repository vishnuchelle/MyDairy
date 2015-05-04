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
    private boolean flag = false;
    private DayGraphView mGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_history);

        datePick = (DatePicker)findViewById(R.id.datePicker);
        showBtn = (Button)findViewById(R.id.showBtn);

        mGraphView = (DayGraphView)findViewById(R.id.day_graph);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = !flag;

                if(flag) {
                    datePick.setVisibility(View.GONE);

                    int day = datePick.getDayOfMonth();
                    int month = datePick.getMonth();
                    int year = datePick.getYear();

                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, day);
                    Date d = cal.getTime();
                    long endTime = d.getTime();

                    //milli seconds for sevendays 7*24*60*60*1000
                    long startTime = endTime - 7 * 24 * 60 * 60 * 1000;

                    MySqliteHelper db = new MySqliteHelper(mContext);
                    ArrayList<Distance> distanceList = db.getDistanceTravelled(AppSharedPreference.getCurrentUser(mContext), startTime, endTime);
                    mGraphView.setVisibility(View.VISIBLE);
                    int[] dayValues = new int[7];
                    int[] date = new int[7];
                    int avgSum = 0;
                    int i =0;
                    for(i=0;i<distanceList.size();i++){
                        dayValues[i] = Integer.parseInt(distanceList.get(i).getDistanceTravelled());
                        avgSum = avgSum + dayValues[i];
                        String[] splits = distanceList.get(i).getDate().split("/");
                        date[i] =  Integer.parseInt(splits[1]);

                    }
                    if(i==0){
                        i++;
                    }
                    mGraphView.setDayValues(dayValues,date, avgSum / i, 1);
                    mGraphView.invalidate();
//                    Toast.makeText(mContext, "Returned distnces:" + distanceList.size(), Toast.LENGTH_SHORT).show();
                    showBtn.setText("Select Different Date");
                }else{

                    datePick.setVisibility(View.VISIBLE);
                    mGraphView.setVisibility(View.GONE);
                    showBtn.setText("Show Statistics");

                }

            }
        });

    }
}
