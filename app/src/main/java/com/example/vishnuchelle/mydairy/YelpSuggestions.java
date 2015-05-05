package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vishnu Chelle on 5/4/2015.
 */
public class YelpSuggestions extends Fragment {

    private ArrayAdapter<String> mYelpAdapter;

    public YelpSuggestions() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateYelp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_yelp_view, container, false);


        mYelpAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.yelp_list_item,
                R.id.list_item_textview,
                new ArrayList<String>());

        ListView listView = (ListView)rootView.findViewById(R.id.yelpListView);
        listView.setAdapter(mYelpAdapter);

        return rootView;
    }

    private void updateYelp() {
        FetchYelpTask backTask = new FetchYelpTask();
//            weatherTask.execute("94043");
        backTask.execute();
    }

    //Async Task to get Yelp suggestions
    public class FetchYelpTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            YelpAPI yelpApi = new YelpAPI(YelpAPI.CONSUMER_KEY, YelpAPI.CONSUMER_SECRET, YelpAPI.TOKEN, YelpAPI.TOKEN_SECRET);
            String YelpResult = YelpAPI.queryAPI(yelpApi);
//            Log.i("Yelp Reponse",YelpResult);
            return YelpResult;

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try {

                JSONObject results = new JSONObject(s);

                JSONArray businesses = results.getJSONArray("businesses");
                int size = businesses.length();
                String[] name = new String[size];
                for (int i=0;i<size;i++){

                    name[i] = businesses.getJSONObject(i).getString("name")+"\n"
                            +"Yelp Rating: " + businesses.getJSONObject(i).getString("rating")+"\n"
                            +"Contact: " + businesses.getJSONObject(i).getString("phone")
                    ;

                    mYelpAdapter.add(name[i]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
