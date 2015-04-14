package com.example.vishnuchelle.mydairy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private ListView list;
    private  MySqliteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mStatus = (EditText)findViewById(R.id.status);
        mUpdate = (Button)findViewById(R.id.makeUpdate);
        list = (ListView)findViewById(R.id.listView);

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
                }else{
                    Toast.makeText(StatusActivity.this, "Please provide an update!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        updateList();
    }

    private void updateList() {

        ArrayList<Status> statuses = db.getStatues(mUserName);
        //FIXME need to show the elements in statuses in the listView
        MyAdapter adapter = new MyAdapter(StatusActivity.this,statuses);
        list.setAdapter(adapter);

    }
}
