package com.example.vishnuchelle.mydairy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishnuchelle.mydairy.Status;

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
 * Created by Vishnu Chelle on 4/14/2015.
 */
public class GroupStatusActivity extends ActionBarActivity {


    private ListView list;
    private TextView groupNameTitle;
    private ImageView adduser;
    private String mNewUser;
    private String groupName;
    final Context mContext = this;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_status);
        Intent i = getIntent();
        groupName = i.getStringExtra("groupName");

        list = (ListView) findViewById(R.id.grStatusListView);
        groupNameTitle = (TextView)findViewById(R.id.groupNameTitle);
        adduser = (ImageView)findViewById(R.id.addUSer);
        groupNameTitle.setText(groupName);

        adduser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

//                Log.i("Entered On click","enter onlcick");

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(mContext);
                View addUSerView = li.inflate(R.layout.add_user_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(addUSerView);

                final EditText dialogInput = (EditText) addUSerView.findViewById(R.id.newUser);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("ADD User",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        mNewUser = dialogInput.getText()+"";
                                        String toGroupName = groupName;

                                        //Call add users asynctask
                                        NewUserToGroup nU = new NewUserToGroup();
                                        nU.execute(toGroupName, mNewUser);

//                                        Log.i("New User",newUser);
//                                        Log.i("Into Group",toGroupName);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


            }
        });

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
            String database = "diarydatabase";
            String collection = "groups";
//        String url = "https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
//        https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP

            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            //prameters to be added to the url
            params.add(new BasicNameValuePair("q", "{\"groupName\":\""+groupName+"\"}"));
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
//                    Log.i("InputStream:", result);
                    return result;
                } else {
                    result = "Input Stream is null";
//                    Log.i("InputStream:", "Input Stream is null");
                    return null;
                }

            } catch (Exception e) {
//                Log.i("InputStreamException", e.getLocalizedMessage());
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
    }//End of asynctask1

    //Adding a new user to the group
    class NewUserToGroup extends AsyncTask<String,Void,String> {

        //Check if the username is in the users collection
        public String getAllUsername(String userName){

            String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
            String database = "diarydatabase";
            String collection = "users";
//        String url = "https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
//        https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP

            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            //prameters to be added to the url
            params.add(new BasicNameValuePair("q", "{\"Username\":\""+userName+"\"}"));
//            params.add(new BasicNameValuePair("f","{\"Username\":1}"));
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
//                    Log.i("List of users: ", result);
                    return result;
                }
                else{
//                    result = "Input Stream is null";
                    Log.i("Returned Username", "Returned username is null");
                    return null;
                }

            } catch (Exception e) {
                Log.i("InputStreamException", e.getLocalizedMessage());
            }
            return null;
        }

        //If username is available get the group members from the group
        //http get request to retrive data from the server.
        public String getGroupUsers(String groupName){

            String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
            String database = "diarydatabase";
            String collection = "groups";
//        String url = "https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
//        https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?q={%22use_id%22:%22chelle%22}&f={%22email%22:1}&apiKey=hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP

            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            //prameters to be added to the url
            params.add(new BasicNameValuePair("q", "{\"groupName\":\""+groupName+"\"}"));
            params.add(new BasicNameValuePair("f","{\"groupMembers\":1}"));
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
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }

        //add the new user to group and post the members back.
        private boolean updateUsersPut(String groupName, String updatedMembers) {

            String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
            String database = "diarydatabase";
            String collection = "groups";
            boolean result = false;


            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            String setUp = "{\"$set\":"+updatedMembers+"}";

//            Log.i("Data being updated",setUp);

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
                        return true;
                }
//                Log.i("Response Code", resp.getStatusLine().getStatusCode() + "");
//                Log.i("Result is", result + "");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Failed", "PUT Not Successful");
            }
            return false;
        }

        @Override
        protected String doInBackground(String... params) {

            //params[0] = group Name
            //params[1] = New username

            String usersCheck = getAllUsername(params[1]);

            try {
                JSONArray usersCheckArray = new JSONArray(usersCheck);

                if (usersCheckArray.length() != 0){

                    String response = getGroupUsers(params[0]);
                    try {
                        JSONArray jA = new JSONArray(response);
                        JSONArray gSArray = ((JSONObject) jA.get(0)).getJSONArray("groupMembers");
                        gSArray.put(params[1]);
                        ((JSONObject) jA.get(0)).remove("_id");
                        String updatedMembers = jA.get(0).toString();
                        boolean check = updateUsersPut(params[0], updatedMembers);
                        //true if user is added
                        if (check){
                            return "true";
                        }else{
                            return null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    return "false";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == "true"){
//                Log.i("True","I am True1");
                Toast.makeText(mContext, "Added user " + mNewUser + " to " + groupName, Toast.LENGTH_SHORT).show();
            }else if (result == "false"){
//                Log.i("False","I am False1");
                Toast.makeText(mContext, "Oops! " + mNewUser + " is not an existing user." , Toast.LENGTH_SHORT).show();
            }else{
//                Log.i("False","I am False2");
                Toast.makeText(mContext, "System Exception! Please try later" , Toast.LENGTH_SHORT).show();
            }

        }
    }//End of async task2

}