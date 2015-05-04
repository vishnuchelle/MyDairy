package com.example.vishnuchelle.mydairy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Vishnu Chelle on 5/2/2015.
 */
public class LocationHistoryActivity extends ActionBarActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);
        mListView = (ListView)findViewById(R.id.lst_view);

        MySqliteHelper dbHelper = new MySqliteHelper(this);
        ArrayList<Location> locationList = dbHelper.getLocations(AppSharedPreference.getCurrentUser(this));
//        ArrayList<Distance> distanceList = dbHelper.getDistanceTravelled(AppSharedPreference.getCurrentUser(this),1430046001000L,1430046009000L);

        LocationAdapter adapter = new LocationAdapter(this,locationList);
        mListView.setAdapter(adapter);

    }

    //insert location details
    public void insertValues(View v){

        MySqliteHelper dbHelper = new MySqliteHelper(this);
        String[] locationDetails = new String[6];

         //From here: 6:00
        locationDetails[0] = "1430046004000"; //TIme stamp
        locationDetails[1] = "4/26/2015"; //Date
        locationDetails[2] = "39.028059"; //Latitude
        locationDetails[3] = "-94.577291"; //Longitude
        locationDetails[4] = "5426 charlette Kansas City"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430067716000"; //TIme stamp
        locationDetails[1] = "4/26/2015"; //Date
        locationDetails[2] = "39.103523"; //Latitude
        locationDetails[3] = "-94.581876"; //Longitude
        locationDetails[4] = " 901 wlanut street Kansas city"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430089256000";
        locationDetails[1] = "4/26/2015";
        locationDetails[2] = "39.037944";
        locationDetails[3] = "-94.598561";
        locationDetails[4] = "Plaza Area, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430110736000";
        locationDetails[1] = "4/26/2015";
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430132516000"; //TIme stamp
        locationDetails[1] = "4/27/2015"; //Date
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430154110000"; //TIme stamp
        locationDetails[1] = "4/27/2015"; //Date
        locationDetails[2] = "39.033669"; //Latitude
        locationDetails[3] = "-94.576278"; //Longitude
        locationDetails[4] = "UMKC, kansas city, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430175650000";
        locationDetails[1] = "4/27/2015";
        locationDetails[2] = "39.085328";
        locationDetails[3] = "-94.585578";
        locationDetails[4] = "sprint center, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430197143000";
        locationDetails[1] = "4/27/2015";
        locationDetails[2] = "39.028059";
        locationDetails[3] = "-94.577291";
        locationDetails[4] = "5426 Charlotte Street, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430218863000"; //TIme stamp
        locationDetails[1] = "4/28/2015"; //Date
        locationDetails[2] = "39.028059";
        locationDetails[3] = "-94.577291";
        locationDetails[4] = "5426 Charlotte Street, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430240463000"; //TIme stamp
        locationDetails[1] = "4/28/2015"; //Date
        locationDetails[2] = "39.030800"; //Latitude
        locationDetails[3] = "-94.576762"; //Longitude
        locationDetails[4] = "5301 Charlotte Street, Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430262063000";
        locationDetails[1] = "4/28/2015";
        locationDetails[2] = "39.045192";
        locationDetails[3] = "-94.443342";
        locationDetails[4] = "11601 E US Hwy 40 Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430283483000";
        locationDetails[1] = "4/28/2015";
        locationDetails[2] = "39.042661";
        locationDetails[3] = "-94.559095";
        locationDetails[4] = "PLAZA TOWERS, Emanuel Cleaver II Boulevard, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430305278000"; //TIme stamp
        locationDetails[1] = "4/29/2015"; //Date
        locationDetails[2] = "39.042661";
        locationDetails[3] = "-94.559095";
        locationDetails[4] = "PLAZA TOWERS, Emanuel Cleaver II Boulevard, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430326878000"; //TIme stamp
        locationDetails[1] = "4/29/2015"; //Date
        locationDetails[2] = "39.028690"; //Latitude
        locationDetails[3] = "-94.576402"; //Longitude
        locationDetails[4] = "5408 Rockhill Rd Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430348478000";
        locationDetails[1] = "4/29/2015";
        locationDetails[2] = "39.036474";
        locationDetails[3] = "-94.578657";
        locationDetails[4] = "5030 Holmes St Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430369940000";
        locationDetails[1] = "4/29/2015";
        locationDetails[2] = "39.028226";
        locationDetails[3] = "-94.577801";
        locationDetails[4] = "706 E 54 Terrace Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430391675000"; //TIme stamp
        locationDetails[1] = "4/30/2015"; //Date
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430413275000"; //TIme stamp
        locationDetails[1] = "4/30/2015"; //Date
        locationDetails[2] = "39.102352"; //Latitude
        locationDetails[3] = "-94.583039"; //Longitude
        locationDetails[4] = "999 Main St Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430434875000";
        locationDetails[1] = "4/30/2015";
        locationDetails[2] = "39.053140";
        locationDetails[3] = "-94.591545";
        locationDetails[4] = "4057 Pennsylvania Ave Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430456295000";
        locationDetails[1] = "4/30/2015";
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430478078000"; //TIme stamp
        locationDetails[1] = "5/1/2015"; //Date
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430499678000"; //TIme stamp
        locationDetails[1] = "5/1/2015"; //Date
        locationDetails[2] = "39.033743"; //Latitude
        locationDetails[3] = "-94.576425"; //Longitude
        locationDetails[4] = "Flarsheim Hall University of Missouri-Kansas City, Volker Campus Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430521278000";
        locationDetails[1] = "5/1/2015";
        locationDetails[2] = "39.035014";
        locationDetails[3] = "-94.576526";
        locationDetails[4] = "800 E 51st St Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430542698000";
        locationDetails[1] = "5/1/2015";
        locationDetails[2] = "39.028059";
        locationDetails[3] = "-94.577291";
        locationDetails[4] = "5426 Charlotte Street, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430564438000"; //TIme stamp
        locationDetails[1] = "5/2/2015"; //Date
        locationDetails[2] = "39.028059";
        locationDetails[3] = "-94.577291";
        locationDetails[4] = "5426 Charlotte Street, Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430586038000"; //TIme stamp
        locationDetails[1] = "5/2/2015"; //Date
        locationDetails[2] = "39.034549"; //Latitude
        locationDetails[3] = "-94.580980"; //Longitude
        locationDetails[4] = "5100 Cherry St Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430607638000";
        locationDetails[1] = "5/2/2015";
        locationDetails[2] = "39.248277";
        locationDetails[3] = "-94.661096";
        locationDetails[4] = "8441 NW Prairie View Road Zona Rosa Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430629118000";
        locationDetails[1] = "5/2/2015";
        locationDetails[2] = "39.150206";
        locationDetails[3] = "-94.483240";
        locationDetails[4] = "3200 North Ameristar Drive Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);


        //6:00
        locationDetails[0] = "1430650863000"; //TIme stamp
        locationDetails[1] = "5/3/2015"; //Date
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430672463000"; //TIme stamp
        locationDetails[1] = "5/3/2015"; //Date
        locationDetails[2] = "38.970642"; //Latitude
        locationDetails[3] = "-94.695059"; //Longitude
        locationDetails[4] = "Louisburg Square 9359 W 87th St Overland Park, KS"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //18:00
        locationDetails[0] = "1430694063000";
        locationDetails[1] = "5/3/2015";
        locationDetails[2] = "39.021444";
        locationDetails[3] = "-94.646741";
        locationDetails[4] = "5399 Martway St Mission, KS"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //23:58
        locationDetails[0] = "1430715543000";
        locationDetails[1] = "5/3/2015";
        locationDetails[2] = "38.970511";
        locationDetails[3] = "-94.607305";
        locationDetails[4] = "8600 Ward Parkway Ste 14 Ward Parkway Center Kansas City, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //6:00
        locationDetails[0] = "1430737263000"; //TIme stamp
        locationDetails[1] = "5/4/2015"; //Date
        locationDetails[2] = "39.029504";
        locationDetails[3] = "-94.575145";
        locationDetails[4] = "5336 harrison street, kansas city, MO"; //address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

        //12:00
        locationDetails[0] = "1430758863000"; //TIme stamp
        locationDetails[1] = "5/4/2015"; //Date
        locationDetails[2] = "39.0350142"; //Latitude
        locationDetails[3] = "-94.576526"; //Longitude
        locationDetails[4] = "University of Missouri-Kansas City, Volker Campus 800 E 51st St Kansas City, MO"; //Formatted address
        locationDetails[5] = AppSharedPreference.getCurrentUser(this);
        dbHelper.addLocation(locationDetails);

    }


    //Update Distance details
    public void insertValuesDistance(View v){

         MySqliteHelper dbHelper = new MySqliteHelper(this);
        String[] distanceDetails = new String[4];

        //From here
        distanceDetails[0] = "4/26/2015";
        distanceDetails[1] = "30"; //Distance Travelled
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430028003000"; // Every day time stamp 1AM
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "4/27/2015";
        distanceDetails[1] = "12";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430114463000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "4/28/2015";
        distanceDetails[1] = "18";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430200868000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "4/29/2015";
        distanceDetails[1] = "15";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430287209000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "4/30/2015";
        distanceDetails[1] = "40";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430373665000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "5/1/2015";
        distanceDetails[1] = "19";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430460064000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "5/2/2015";
        distanceDetails[1] = "33";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430546430000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "5/3/2015";
        distanceDetails[1] = "54";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430632875000";
        dbHelper.addDistance(distanceDetails);

        distanceDetails[0] = "5/4/2015";
        distanceDetails[1] = "74";
        distanceDetails[2] = AppSharedPreference.getCurrentUser(this);
        distanceDetails[3] = "1430719278000";
        dbHelper.addDistance(distanceDetails);

    }



}
