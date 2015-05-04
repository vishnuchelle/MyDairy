package com.example.vishnuchelle.mydairy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                                    Person person = new Person(firstName,lastName,middleName,userName,phoneNumber,Integer.parseInt(pin),email);
                                    addPerson(person);
                                    Intent intent = new Intent(SignUpActivity.this,StatusActivity.class);
                                    intent.putExtra("userName",person.getUserName());
                                    startActivity(intent);
                                    finish();
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

    private void setPersonFields(Person person) {
        ((EditText)findViewById(R.id.first_name)).setText(person.getFirstName());
        ((EditText)findViewById(R.id.middle_name)).setText(person.getMiddleName());
        ((EditText)findViewById(R.id.last_name)).setText(person.getLastName());
        ((EditText)findViewById(R.id.user_name)).setText("vishnu.chelle@gmail.com");
        ((EditText)findViewById(R.id.phone_number)).setText("3205");
        ((EditText)findViewById(R.id.pin)).setText("53");
        ((EditText)findViewById(R.id.email)).setText("v@v.com");
    }

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

}
