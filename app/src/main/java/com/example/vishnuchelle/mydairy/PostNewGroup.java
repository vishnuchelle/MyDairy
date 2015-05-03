package com.example.vishnuchelle.mydairy;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Vishnu Chelle on 5/3/2015.
 */
public class PostNewGroup extends AsyncTask<String,Void,Void> {

    public void httpPostInsertGroup(String groupName, String groupUser){

        boolean result = false;
        HttpClient hc = new DefaultHttpClient();
        String message;
        String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
        String database = "diarytest";
        String collection = "testcollection";


        String url = "https://api.mongolab.com/api/1/databases/" +
                database +
                "/collections/" +
                collection +
                "?apiKey=" + key;

        //Json array for group members
        JSONArray members = new JSONArray();
        members.put(groupUser);//pass the group Name Entered

        //Empty Status Array
        JSONArray status = new JSONArray();

        //Group JSON object
        JSONObject group = new JSONObject();
        try {
            group.put("groupName", groupName);//Pass from user input group name
            group.put("groupMembers", members);
            group.put("groupStatus", status);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Http POST url
        HttpPost p = new HttpPost(url);

        try {
            //convert json object to string
            message = group.toString();

//            Log.i("final message",message);

            //pass the string as data to the POST url
            p.setEntity(new StringEntity(message, "UTF8"));
            p.setHeader("Content-type", "application/json");
            HttpResponse resp = hc.execute(p);
            if (resp != null) {
                if (resp.getStatusLine().getStatusCode() == 200)
                    result = true;
            }
            Log.i("Response Code", resp.getStatusLine().getStatusCode() + "");
            Log.i("SUCCESS","Group SUCCESSFULLY Inserted");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Failed", "Group Not Inserted");

        }

    }

    @Override
    protected Void doInBackground(String... params) {

        //params[0]-Group Name
        //params[1]- GroupUser

        httpPostInsertGroup(params[0],params[1]);
        return null;
    }
}
