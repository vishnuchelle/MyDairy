package com.example.vishnuchelle.mydairy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Vishnu Chelle on 4/30/2015.
 */
public class DistanceCalcReceiver extends BroadcastReceiver {

    //	public static String ACTION_ALARM = "com.alarammanager.alaram";
    //public static String ACTION_ALARM = "com.example.vishnuchelle.applocation";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.i("Alarm Receiver", "Entered");
//        Toast.makeText(context, "Entered the OnReciever", Toast.LENGTH_SHORT).show();

        //TODO calculate distance for whole day


        //Execute the DictanceCalculator AsyncTask
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        //Pass origin and destination latitude and longitude
        distanceCalculator.execute();


    }
}
