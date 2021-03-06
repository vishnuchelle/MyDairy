package com.example.vishnuchelle.mydairy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by VishnuChelle on 3/18/2015.
 */
public class StatusActivity extends ActionBarActivity {

    private String mUserName;
    private EditText mStatus;
    private Button mUpdate;
    private Button sUpdate;
    private Button qUpdate;
    private ListView list;


    //group list
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private String[] groupArray;
    private String[] template;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    final Context mContext = this;

    private boolean flag = false;
    private  MySqliteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mStatus = (EditText)findViewById(R.id.status);
        mUpdate = (Button)findViewById(R.id.makeUpdate);
        sUpdate = (Button)findViewById(R.id.shareUpdate);
        qUpdate = (Button)findViewById(R.id.quickUpdate);
        list = (ListView)findViewById(R.id.listView);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userName");
        db = new MySqliteHelper(this);


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Status statusObj = new Status();
                DateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                String date = format.format(calendar.getTime());
                String userName = mUserName;
                String status = mStatus.getText()+"";
                if (!status.equals("")){
                    statusObj.setUserName(mUserName);
                    statusObj.setDate(date);
                    statusObj.setStatus(status);
                    db.addStatus(statusObj);
                    updateList();
                    mStatus.setText("");
                }else{
                    Toast.makeText(StatusActivity.this, "Please provide an update!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(checkInternetConnection()){

                    //Show a dialog with list of groups
                    ListView promptsView = new ListView(mContext);
                    ArrayAdapter mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getGroupsList(groupArray));

                    promptsView.setAdapter(mAdapter);

                    final String status = mStatus.getText() + "";

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    alertDialogBuilder.setTitle("Select a Group to Share!!!");

                    // create alert dialog
                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    if (!status.equals("")) {
                        // show it
                        alertDialog.show();
                        flag = false;
                    }else{
                        Toast.makeText(StatusActivity.this, "Please provide an update!", Toast.LENGTH_SHORT).show();
                    }

                    promptsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //To add in the local DB and users private status...
                            Status statusObj = new Status();
                            DateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                            Calendar calendar = Calendar.getInstance();
                            String date = format.format(calendar.getTime());
                            String userName = mUserName;

                            if (!status.equals("")) {

                                if (!flag) {
                                    flag = true;
                                    statusObj.setUserName(userName);
                                    statusObj.setDate(date);
                                    statusObj.setStatus(status);
                                    db.addStatus(statusObj);
                                    updateList();
                                }

                                String groupName = groupArray[position + 1];
//                                Log.i("Clicked on", groupName);
                                Toast.makeText(StatusActivity.this, "Shared with " + groupName, Toast.LENGTH_SHORT).show();

                                //POST Update Status AsyncTask
                                UpdateGroupStatus uGS = new UpdateGroupStatus();
                                uGS.execute(groupName, status, date, AppSharedPreference.getCurrentUser(mContext));

                                mStatus.setText("");

                                //Dismiss dialog
//                                alertDialog.dismiss();

                            } else {
                                Toast.makeText(StatusActivity.this, "Please provide an update!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



//                    Toast.makeText(StatusActivity.this,"Success Internet connectivity is On...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(StatusActivity.this,"Update cannot be shared when Internet is off!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        qUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ListView promptsView = new ListView(mContext);
                ArrayAdapter mAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getTemplateStatus());

                promptsView.setAdapter(mAdapter);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder.setTitle("Select an update. Quick!!!");

                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

                        promptsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String selectedTemplate = promptsView.getItemAtPosition(position)+"";

//                                Log.i("Selected Template",selectedTemplate);
                                mStatus.setText(selectedTemplate);

                                alertDialog.dismiss();

                    }
                });


            }
        });

        updateList();
        setupDrawer();
        //CheckInternetConnectivity
        if(checkInternetConnection()){
            //DONE need to call async task

            GetGroupsList getGroupsList = new GetGroupsList(new GetGroupsList.IGroupsCallBack() {
                @Override
                public void onGroupsReceived(String groupList) {
                    addDrawerItems(groupList);
                }

                @Override
                public void onError(String msg) {

                }
            });
            getGroupsList.execute(mUserName);
        }
//        else{
//            Toast.makeText(StatusActivity.this,"Please check Internet connectivity...",Toast.LENGTH_SHORT).show();
//        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private String[] getGroupsList(String[] groupArray) {
        String[] list = new String[groupArray.length-1];
        for(int i=1;i<groupArray.length;i++){
            list[i-1] = groupArray[i];
        }
        return list;
    }

    //Templates for quick updates
    private String[] getTemplateStatus(){
        String[] templateList = {
                "Just awake!",
                "Just had breakfast.",
                "Just had lunch",
                "Reached office",
                "Fun time! Playing Baseball.",
                "Time to sleep",
                "Meeting with my boss",
                "Long drive!",
                "Shopping!!!",
                "Had Dinner",
                "Back at home!",
                "Party time!!!",
                "Headache :(",
                "Boozing!!!"
        };

        return templateList;

    }

    /**
     * Checks if the device has Internet connection.
     *
     * @return <code>true</code> if the phone is connected to the Internet.
     */

    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    private void updateList() {

        ArrayList<Status> statuses = db.getStatues(mUserName);
        //show the elements in statuses in the listView
        StatusAdapter adapter = new StatusAdapter(StatusActivity.this,statuses);
        list.setAdapter(adapter);

    }

    private void addDrawerItems(String groupsJson) {

        try {
            JSONArray groupsList = new JSONArray(groupsJson);

            ArrayList<String> groupList = new ArrayList<String>();
            groupList.add("Create New Group");
            for(int i = 0; i < groupsList.length(); i++){
                groupList.add(groupsList.getJSONObject(i).getString("groupName"));
            }

        groupArray = groupList.toArray(new String[groupList.size()]);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    //DONE create new group

                    // get create_new_group.xml view
                    LayoutInflater li = LayoutInflater.from(mContext);
                    View createGroupView = li.inflate(R.layout.create_new_group, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    // set create_new_group.xml to alertdialog builder
                    alertDialogBuilder.setView(createGroupView);

                    final EditText dialogInput = (EditText) createGroupView.findViewById(R.id.groupName);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Create",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input
                                            // edit text
                                            String groupName = dialogInput.getText()+"";

                                            PostNewGroup ng = new PostNewGroup();
                                            ng.execute(groupName, mUserName);
//                                            Toast.makeText(StatusActivity.this,"Successfully created "+groupName+" group",Toast.LENGTH_SHORT);

//                                            Log.i("Entered Group Name",groupName);
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

//                    Toast.makeText(StatusActivity.this, "Show dialog to create group", Toast.LENGTH_SHORT).show();



                }else{

                    //DONE start Activity with group name and user name
                    String groupName = groupArray[position];

                    Intent i = new Intent(StatusActivity.this,GroupStatusActivity.class);
                    i.putExtra("groupName",groupName);
                    StatusActivity.this.startActivity(i);

//                    Toast.makeText(StatusActivity.this, "call new activity with group name and user name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Groups!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(mDrawerToggle!=null){
            mDrawerToggle.syncState();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mDrawerToggle!=null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

         if(id == R.id.action_location_history){
            //DONE update layout
            openLocationHistory();
            return true;
        }else if(id == R.id.action_dist_graph){
             openDistanceHistory();

         }else if(id == R.id.yelp_info){
             openYelpSuggestions();

         }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if(checkInternetConnection()){
                return true;
            }else{
                Toast.makeText(StatusActivity.this,"Please check Internet connectivity...",Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void openYelpSuggestions() {
        Intent yelpI = new Intent(StatusActivity.this,YelpActivity.class);
        startActivity(yelpI);
    }

    private void openDistanceHistory() {
        Intent distanceGraphIntent = new Intent(StatusActivity.this,DistanceHistoryActivity.class);
        startActivity(distanceGraphIntent);
    }

    private void openLocationHistory() {
        Intent locationHistoryIntent = new Intent(StatusActivity.this,LocationHistoryActivity.class);
        startActivity(locationHistoryIntent);
//        finish();
    }



}
