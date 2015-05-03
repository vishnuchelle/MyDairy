package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.example.vishnuchelle.mydairy.Status;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu Chelle on 4/14/2015.
 */
public class GroupStatusActivity extends ActionBarActivity {

    final Context mContext = this;
    private ListView list;
    private TextView groupNameTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_status);
        Intent i = getIntent();
        String groupName = i.getStringExtra("groupName");

        list = (ListView) findViewById(R.id.grStatusListView);
        groupNameTitle = (TextView) findViewById(R.id.groupNameTitle);

        groupNameTitle.setText(groupName);

        //Get groups status async task
        GetGroupStatus gGS = new GetGroupStatus();
        gGS.execute(groupName);//Pass group Name

    }

//    private void updateList() {
//
//        GetGroupStatus gGS = new GetGroupStatus();
//        gGS.execute();//Pass group Name
//
//        ArrayList<Status> statuses = new ArrayList<Status>();
//
//        //show the elements in statuses in the listView
//        GroupStatusAdapter adapter = new GroupStatusAdapter(GroupStatusActivity.this, statuses);
//        list.setAdapter(adapter);
//
//    }


    //ASYNCTASK for fetching grou status from MONGOLAB
    class GetGroupStatus extends AsyncTask<String, Void, ArrayList<com.example.vishnuchelle.mydairy.Status>> {

        //Get groups list from Mongo lab
        public String getGroupStatus(String groupName) {
            String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
            String database = "diarytest";
            String collection = "testcollection";
//        String url = "https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
//        https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP

            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            //prameters to be added to the url
            params.add(new BasicNameValuePair("q", "{\"groupName\":\"new1\"}"));
            params.add(new BasicNameValuePair("f", "{\"groupStatus\":1}"));
            params.add(new BasicNameValuePair("apiKey", key));

            String paramsString = URLEncodedUtils.format(params, "UTF-8");

            //create http client
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(url + "?" + paramsString);

            InputStream inputStream = null;
            String result = "";
            try {

                // make GET request to the given URL
                HttpResponse response = httpClient.execute(httpGet);

                // receive response as inputStream
                inputStream = response.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                    Log.i("InputStream:", result);
                    return result;
                } else {
                    result = "Input Stream is null";
                    Log.i("InputStream:", "Input Stream is null");
                    return null;
                }

            } catch (Exception e) {
                Log.i("InputStreamException", e.getLocalizedMessage());
            }
            return null;

        }

        // convert inputstream to String
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }

        @Override
        protected ArrayList<com.example.vishnuchelle.mydairy.Status> doInBackground(String... params) {

            //params[0] = group name
            String response = getGroupStatus(params[0]);

            ArrayList<com.example.vishnuchelle.mydairy.Status> groupStatusList = new ArrayList<com.example.vishnuchelle.mydairy.Status>();
            if (response != null) {

                try {
                    JSONArray jA = new JSONArray(response);
                    JSONArray gSArray = ((JSONObject) jA.get(0)).getJSONArray("groupStatus");

                    for (int i = 0; i < gSArray.length(); i++) {

                        JSONObject groupStatus = (JSONObject) gSArray.get(i);
                        com.example.vishnuchelle.mydairy.Status status = new com.example.vishnuchelle.mydairy.Status();
                        status.setUserName(groupStatus.getString("userName"));
                        status.setDate(groupStatus.getString("date"));
                        status.setStatus(groupStatus.getString("status"));
                        groupStatusList.add(status);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return groupStatusList;
            }
            return null;

        }

        //On POST execute


        @Override
        protected void onPostExecute(ArrayList<com.example.vishnuchelle.mydairy.Status> groupStatusList) {
            super.onPostExecute(groupStatusList);

            //show the elements in statuses in the listView
            GroupStatusAdapter adapter = new GroupStatusAdapter(GroupStatusActivity.this, groupStatusList);
            list.setAdapter(adapter);
        }
    }//End of asynctask
}