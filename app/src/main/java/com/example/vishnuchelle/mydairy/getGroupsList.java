package com.example.vishnuchelle.mydairy;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu Chelle on 4/14/2015.
 */
public class GetGroupsList extends AsyncTask<String, Void, String> {

    private IGroupsCallBack listener;

    public GetGroupsList(IGroupsCallBack listener){
        this.listener = listener;
    }
    //http get request to retrive data from the server.
    public String httpGetRetrieve(String userName){
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

        //Prameters to be added to the url
        params.add(new BasicNameValuePair("q", "{\"groupMembers\":\""+userName+"\"}"));
        params.add(new BasicNameValuePair("f","{\"groupName\":1}"));
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
            }

        } catch (Exception e) {
            Log.i("InputStreamException", e.getLocalizedMessage());
        }
        return null;
    }

    // Convert InputStream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    @Override
    protected String doInBackground(String... params) {

        String groupsList = httpGetRetrieve(params[0]);
        return groupsList;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(listener!=null && result!=null){
            listener.onGroupsReceived(result);
        }
        else if(listener!=null ){

            listener.onError("Error occurred");
        }

    }

    interface IGroupsCallBack{
        public void onGroupsReceived(String json);
        public void onError(String msg);
    }
}
