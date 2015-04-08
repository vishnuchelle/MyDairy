package com.example.vishnuchelle.mydairy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Vishnu Chelle on 3/16/2015.
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "DAIRY";

    private static final String USER_TABLE = "user";

    private static final String STATUS_TABLE = "status";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create table
        String CREATE_USER_TABLE = "CREATE TABLE " +
                USER_TABLE+
                "( " +
                "first_name TEXT, "+
                "middle_name TEXT, "+
                "last_name TEXT, "+
                "user_name TEXT PRIMARY KEY, "+
                "phone_number TEXT, "+
                "pin INTEGER, "+
                "email TEXT )";

        String CREATE_STATUS_TABLE = "CREATE TABLE " +
                STATUS_TABLE+
                "( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "user_name TEXT, "+
                "date TEXT, "+
                "status TEXT )";


        // create both the tables
        db.execSQL(CREATE_USER_TABLE);

        db.execSQL(CREATE_STATUS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " +
                USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +
                STATUS_TABLE);

        // create fresh table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete)
     */

//  private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_MIDDLE_NAME = "middle_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_PIN = "pin";
    private static final String KEY_EMAIL= "email";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";

    private static final String[] COLUMNS = {KEY_FIRST_NAME,KEY_MIDDLE_NAME,KEY_LAST_NAME,
            KEY_USER_NAME,KEY_PHONE_NUMBER,KEY_PIN,KEY_EMAIL};

    private static final String[] DAIRY_COLUMNS = {KEY_USER_NAME,KEY_DATE,KEY_STATUS};

    public ArrayList<Status> getStatues(String userName){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(STATUS_TABLE, // a. table
                        DAIRY_COLUMNS, // b. column names
                        " user_name = ?", // c. selections
                        new String[] { userName }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        ArrayList<Status> statusList = new ArrayList<Status>();
        if (cursor.moveToFirst()) {
            do {
                // do what you need with the cursor here
                Status status = new Status();
                status.setUserName(cursor.getString(0));
                status.setDate(cursor.getString(1));
                status.setStatus(cursor.getString(2));
                statusList.add(status);
            } while (cursor.moveToNext());
        }

        // 5. return StatusList
        return statusList;

    }

    public void addStatus(Status status){
        Log.d("addUser", status.getStatus());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, status.getUserName()); // get title
        values.put(KEY_DATE, status.getDate()); // get author
        values.put(KEY_STATUS,status.getStatus());

        // 3. insert
        db.insert(STATUS_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public void addPerson(Person person){
        Log.d("addUser", person.getUserName());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, person.getFirstName()); // get title
        values.put(KEY_MIDDLE_NAME, person.getMiddleName()); // get author
        values.put(KEY_LAST_NAME,person.getLastName());
        values.put(KEY_USER_NAME,person.getUserName());
        values.put(KEY_PHONE_NUMBER,person.getPhoneNumber());
        values.put(KEY_PIN,person.getPin());
        values.put(KEY_EMAIL,person.getEmail());
        // 3. insert
        db.insert(USER_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Person getPerson(String username){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(USER_TABLE, // a. table
                        COLUMNS, // b. column names
                        " user_name = ?", // c. selections
                        new String[] { username }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return null;
        }
        // 4. build person object
        Person person = new Person();

        person.setFirstName(cursor.getString(0));
        person.setMiddleName(cursor.getString(1));
        person.setLastName(cursor.getString(2));
        person.setUserName(cursor.getString(3));
        person.setPhoneNumber(cursor.getString(4));
        person.setPin(cursor.getInt(5));
        person.setEmail(cursor.getString(6));

        // 5. return person
        return person;

    }




}
