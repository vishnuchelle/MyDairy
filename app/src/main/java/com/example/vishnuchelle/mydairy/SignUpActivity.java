package com.example.vishnuchelle.mydairy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SignUpActivity extends ActionBarActivity {

    private MySqliteHelper db;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = new MySqliteHelper(this);
        signUp = (Button)findViewById(R.id.sign_up);
//        fillValues();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = ((EditText)findViewById(R.id.first_name)).getText()+"";
                String middleName = ((EditText)findViewById(R.id.middle_name)).getText()+"";
                String lastName =  ((EditText)findViewById(R.id.last_name)).getText()+"";
                String userName = ((EditText)findViewById(R.id.user_name)).getText()+"";
                String phoneNumber = ((EditText)findViewById(R.id.phone_number)).getText()+"";

                String pin = ((EditText)findViewById(R.id.pin)).getText()+"";
                String email = ((EditText)findViewById(R.id.email)).getText()+"";

                //FIXED need to add validations

                if((!firstName.equals("")) && (!lastName.equals(""))
                        && (!userName.equals("")) && (!(pin.length() == 0))
                        && (!phoneNumber.equals("")) && (!email.equals(""))){

                    if((Pattern.matches("[a-z A-Z]+", firstName)) && (Pattern.matches("[a-z A-Z]+", middleName)) && (Pattern.matches("[a-z A-Z]+", lastName))){
                        if (Pattern.matches("[a-zA-Z0-9]+", userName)){
                            if((Pattern.matches("[0-9]+", phoneNumber))&&(phoneNumber.length() == 10)){
                                if (Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", email)){

                                    //TODO Insert new username field in users collection
                                    //TODO username should be unique and if not toast an error

                                    AddNewUsername addU = new AddNewUsername();
                                    addU.execute(userName);

                                }else{
                                    Toast.makeText(SignUpActivity.this, "Email should be of the format a@a.com", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(SignUpActivity.this, "PhoneNumber should be Numeric and 10 digit. Cannot contain Blank Spaces. Example: 1234567890", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignUpActivity.this, "UserName should be Alphanumeric. Cannot contain Special characters", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "FirstName/LastName/MiddleName can contain only Alphabets", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SignUpActivity.this, "Provide FirstName/LastName/UserName/PhoneNumber/Pin/Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Person person = getPerson("vishnu.chelle@gmail.com");
//        setPersonFields(person);

    }


    //Dummy data used for testing
    private void setPersonFields(Person person) {
        ((EditText)findViewById(R.id.first_name)).setText(person.getFirstName());
        ((EditText)findViewById(R.id.middle_name)).setText(person.getMiddleName());
        ((EditText)findViewById(R.id.last_name)).setText(person.getLastName());
        ((EditText)findViewById(R.id.user_name)).setText("vishnu.chelle@gmail.com");
        ((EditText)findViewById(R.id.phone_number)).setText("3205");
        ((EditText)findViewById(R.id.pin)).setText("53");
        ((EditText)findViewById(R.id.email)).setText("v@v.com");
    }
    //Dummy data used for testing
    private void fillValues() {

        ((EditText)findViewById(R.id.first_name)).setText("Vishnu");
        ((EditText)findViewById(R.id.middle_name)).setText("C");
        ((EditText)findViewById(R.id.last_name)).setText("Chelle");
        ((EditText)findViewById(R.id.user_name)).setText("vishnu.chelle@gmail.com");
        ((EditText)findViewById(R.id.phone_number)).setText("9288467412");
        ((EditText)findViewById(R.id.pin)).setText("7777");
       ((EditText)findViewById(R.id.email)).setText("v@v.com");
    }


    public void addPerson(Person person){
        /**
         * CRUD Operations
         * */
        db.addPerson(person);
    }

    public Person getPerson(String userName){
       return db.getPerson(userName);
    }

    class AddNewUsername extends AsyncTask<String,Void,String>{

        //http get request to retrieve data from the server.
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

        private boolean updatedUsernamePut(String updatedUsername) {
            String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
            String database = "diarydatabase";
            String collection = "users";

            String url = "https://api.mongolab.com/api/1/databases/" +
                    database +
                    "/collections/" +
                    collection;

            String setUp = "{\"$set\":"+updatedUsername+"}";

            Log.i("Data being updated",setUp);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //prameters to be added to the url
//            params.add(new BasicNameValuePair("q", "{\"groupName\":\""+groupName+"\"}"));
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

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Failed", "PUT Not Successful");

            }

            return false;
        }

        //params[0] - new username
        @Override
        protected String doInBackground(String... params) {

            String validate = getAllUsername(params[0]);
//            Log.i("resonse: ",response);

            try {
                JSONArray jA = new JSONArray(validate);

                if(jA.length() == 0){

                    String response = getAllUsername("admin");
                    JSONArray array = new JSONArray(response);

                    JSONArray usersArray = ((JSONObject) array.get(0)).getJSONArray("Username");
                    usersArray.put(params[0]);
                    ((JSONObject) array.get(0)).remove("_id");
                    String updatedUsername = array.get(0).toString();
                    boolean update = updatedUsernamePut(updatedUsername);
                    //return true if username is updated successfully in server
                    if(update){
                        return "true";

                    }else{
                        return "false";
                    }

                }else{
                    return "false"; //username already exists
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == "true"){

                String firstName = ((EditText)findViewById(R.id.first_name)).getText()+"";
                String middleName = ((EditText)findViewById(R.id.middle_name)).getText()+"";
                String lastName =  ((EditText)findViewById(R.id.last_name)).getText()+"";
                String userName = ((EditText)findViewById(R.id.user_name)).getText()+"";
                String phoneNumber = ((EditText)findViewById(R.id.phone_number)).getText()+"";

                String pin = ((EditText)findViewById(R.id.pin)).getText()+"";
                String email = ((EditText)findViewById(R.id.email)).getText()+"";

                Person person = new Person(firstName,lastName,middleName,userName,phoneNumber,Integer.parseInt(pin),email);
                addPerson(person);
                Intent intent = new Intent(SignUpActivity.this,StatusActivity.class);
                intent.putExtra("userName", person.getUserName());
                startActivity(intent);
                finish();

//                Toast.makeText(SignUpActivity.this,"Success! Username updated.",Toast.LENGTH_SHORT).show();

            }else if (result == "false"){
                Toast.makeText(SignUpActivity.this,"Username already exists! Please choose a different Username.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SignUpActivity.this,"Server Exception! Please try again.",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
