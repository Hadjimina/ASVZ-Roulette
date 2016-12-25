package com.example.philipp.asvzroulette.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.philipp.asvzroulette.javaClassFiles.SportsLesson;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HelperClass {

    //Is beer parameter enabled ?
    public boolean beerQuestion(String beerString){

        if (beerString.equals("true")){
            Random generator = new Random();
            int q = generator.nextInt(3) + 1;

            return q == 1;
        }
        else{
            return  false;
        }
    }



    //helperfunction to retrive all current lessons
   public void loadArray(Context mCont, ArrayList<SportsLesson> lessons, String arrayName)
    {
        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        lessons.clear();
        int size = sp.getInt(arrayName+" Status_size", 0);

        for(int i=0;i<size;i++)
        {
            lessons.add(new SportsLesson(mCont,sp.getString("Status_" + i,null), arrayName));
        }

    }

    //helper function to save to current lessons
    public boolean saveArray(Context mCont, ArrayList<SportsLesson> lessons, String arrayName)
    {

        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.putInt(arrayName+" Status_size", lessons.size());

        for(int i=0;i<lessons.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, lessons.get(i).title);

        }

        return mEdit1.commit();
    }
}
