package com.example.vishnuchelle.mydairy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Vishnu Chelle on 5/4/2015.
 */
public class YelpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new YelpSuggestions())
                    .commit();
        }
    }
}
