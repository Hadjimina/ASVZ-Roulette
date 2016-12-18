package com.example.philipp.asvzroulette;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {

    //private Toolbar myChildToolbar;
    private String beerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox beer = (CheckBox)findViewById(R.id.checkBox);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Setup beer button
        //Shared prefs for beer
        SharedPreferences prefs = getSharedPreferences("Beer", MODE_PRIVATE);
        String restoredText = prefs.getString("beer", null);
        if (restoredText != null) {
            beerString = prefs.getString("beer", "false");//"No name defined" is the default value.
            if (beerString.equals("true")){
                beer.setChecked(true);
            }else{
                beer.setChecked(false);
                }
            }

    }



    public void checkClicked(View v) {
        //code to check if this checkbox is checked!
        SharedPreferences.Editor editor = getSharedPreferences("Beer", MODE_PRIVATE).edit();
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            Log.v("Settings","true");
            editor.putString("beer", "true");
        }
        else{
            Log.v("Settings","false");
            editor.putString("beer", "false");
        }
        editor.commit();
    }
}
