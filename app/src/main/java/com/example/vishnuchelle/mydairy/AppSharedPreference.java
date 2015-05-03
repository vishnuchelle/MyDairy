package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Vishnu Chelle on 5/2/2015.
 */
public class AppSharedPreference {

    private static AppSharedPreference INSTANCE;

    private AppSharedPreference(){

    }

    public static AppSharedPreference getInstance(){
        if(INSTANCE !=null){
            return INSTANCE;
        }else{
            INSTANCE = new AppSharedPreference();
            return INSTANCE;

        }
    }


    public static void updateCurrentUser(Context context,String username){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username); // value to store
        editor.commit();
    }

    public static String getCurrentUser(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // then you use
      return  prefs.getString("username","");

    }

}
