package com.example.vishnuchelle.mydairy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Vishnu Chelle on 4/30/2015.
 */
public class LocationTrackReceiver extends BroadcastReceiver {

    //	public static String ACTION_ALARM = "com.alarammanager.alaram";
    //public static String ACTION_ALARM = "com.example.vishnuchelle.applocation";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.i("Alarm Receiver", "Entered");
//        Toast.makeText(context, "Entered the OnReciever", Toast.LENGTH_SHORT).show();

        //Execute the locationDetails AsynTask
        LocationDetails locationDetails = new LocationDetails(context);
        locationDetails.execute();

    }
}
