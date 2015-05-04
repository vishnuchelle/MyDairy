package com.example.vishnuchelle.mydairy;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
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
 * Created by Vishnu Chelle on 5/3/2015.
 */
public class UpdateGroupStatus extends AsyncTask<String,Void,Void> {


    //http get request to retrive data from the server.
    public String getGroupStatus(String groupName){

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
        params.add(new BasicNameValuePair("q", "{\"groupName\":\""+groupName+"\"}"));
        params.add(new BasicNameValuePair("f","{\"groupStatus\":1}"));
        params.add(new BasicNameValuePair("apiKey",key));

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
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                Log.i("InputStream:", result);
                return result;
            }
            else{
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
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }


    //add the new status to group and post to server
    private void updateGroupStatusPut(String groupName, String updatedStatus) {

        String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
        String database = "diarytest";
        String collection = "testcollection";
        boolean result = false;
        String message = "";

        String url = "https://api.mongolab.com/api/1/databases/" +
                database +
                "/collections/" +
                collection;

        String setUp = "{\"$set\":"+updatedStatus+"}";

        Log.i("Data being updated",setUp);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //prameters to be added to the url
        params.add(new BasicNameValuePair("q", "{\"groupName\":\""+groupName+"\"}"));
//        params.add(new BasicNameValuePair("f","{\"phone\":1}"));
        params.add(new BasicNameValuePair("apiKey",key));

        String paramsString = URLEncodedUtils.format(params, "UTF-8");

        HttpClient httpPutClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url + "?" + paramsString);

        try {

            //pass the string as data to the POST url
            httpPut.setEntity(new StringEntity(setUp, "UTF8"));
            httpPut.setHeader("Content-type", "application/json");
            HttpResponse resp = httpPutClient.execute(httpPut);

            if (resp != null) {
                if (resp.getStatusLine().getStatusCode() == 200)
                    result = true;
            }
            Log.i("Response Code", resp.getStatusLine().getStatusCode() + "");
            Log.i("Result is", result + "");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Failed", "PUT Not Successful");
        }
    }



    @Override
    protected Void doInBackground(String... params) {
        //params[0] = Group Name
        //params[1] = New Status
        //params[2] = date
        //params[3] = UserName

        String response = getGroupStatus(params[0]);

        try {
            JSONArray jA = new JSONArray(response);
            JSONArray gSArray = ((JSONObject) jA.get(0)).getJSONArray("groupStatus");
            ((JSONObject) jA.get(0)).remove("_id");

            JSONObject newStatus = new JSONObject();
            newStatus.put("date",params[2]);
            newStatus.put("userName",params[3]);
            newStatus.put("status", params[1]);

            gSArray.put(newStatus);

            String updatedStatus = jA.get(0).toString();
            updateGroupStatusPut(params[0], updatedStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

}
