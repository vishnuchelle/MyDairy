package com.example.vishnuchelle.mydairy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Vishnu Chelle on 3/18/2015.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText userName;
    private EditText password;
    private Button signIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);

        signIn = (Button)findViewById(R.id.sign_in);
        signUp = (Button)findViewById(R.id.sign_up);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
               MySqliteHelper db = new MySqliteHelper(this);

                if((!(userName.getText()+"").equals("")) && (!(password.getText()+"").equals(""))){
                    Person person = db.getPerson(userName.getText()+"");
                    if(person!=null){
                        if((person.getPin()+"").equals(password.getText()+"")){
                            AppSharedPreference.getInstance().updateCurrentUser(this,person.getUserName());
                            Intent intent = new Intent(LoginActivity.this,StatusActivity.class);
                            intent.putExtra("userName",person.getUserName());
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Invalid UserName/Password",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid UserName/Password",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"Provide UserName/Password",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sign_up:
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    //Alarm manager for Location tracker
    public void locationTrackAlarm() {
        try {
            AlarmManager alarm1 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            Intent intent1 = new Intent(getApplicationContext(), LocationTrackReceiver.class);
            //intent.putExtra(AlarmReceiver.ACTION_ALARM, AlarmReceiver.ACTION_ALARM);

            final PendingIntent pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0);

            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());

            //Set the time in different intervals of a day 6hrs interval

            if(calendar.get(calendar.HOUR_OF_DAY) <= 6){

                calendar.set(Calendar.HOUR_OF_DAY, 6);

            }else if(calendar.get(calendar.HOUR_OF_DAY) <= 12){
                calendar.set(Calendar.HOUR_OF_DAY, 12);

            }else if(calendar.get(calendar.HOUR_OF_DAY) <= 18){
                calendar.set(Calendar.HOUR_OF_DAY, 12);

            }else{
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
            }

            alarm1.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),  6*60*60*1000 , pIntent1);
            //21600000 ms for 6 hour interval

//            Toast.makeText(this,"Started...",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Alarm manager for Location tracker
    public void distanceCalcAlarm() {
        try {
            AlarmManager alarm2 = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            Intent intent2 = new Intent(getApplicationContext(), DistanceCalcReceiver.class);
            //intent.putExtra(AlarmReceiver.ACTION_ALARM, AlarmReceiver.ACTION_ALARM);

            final PendingIntent pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0);

            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());

            //Set the time in different intervals of a day 24hrs interval

            if(calendar.get(calendar.HOUR_OF_DAY) <= 1){

                calendar.set(Calendar.HOUR_OF_DAY, 1);

            }else{
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 1);
            }

            alarm2.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent2);
//            Toast.makeText(this,"Started...",Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
