package com.example.philipp.asvzroulette;

import android.content.Context;
import android.content.SharedPreferences;

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
    //8!!! currentLessons deprecated !!! should be current list or something
    public void loadArray(Context mCont,ArrayList<?> currentLessons,String arrayName)
    {
        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        currentLessons.clear();
        int size = sp.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            if (arrayName.equals("currenLessons")){
                ArrayList<SportsLesson> sports = (ArrayList<SportsLesson>) currentLessons;
                sports.add(new SportsLesson(mCont,sp.getString("Status_" + i, null)));
            }else{
                ArrayList<String> noneSports = (ArrayList<String>) currentLessons;
                noneSports.add(sp.getString("Status_" + i, null));
            }

        }

    }

    //helper function to save to current lessons
    public boolean saveArray(Context mCont,ArrayList<?> currentLessons,String arrayName)
    {

        SharedPreferences sp = mCont.getSharedPreferences(arrayName, MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.putInt("Status_size", currentLessons.size());

        for(int i=0;i<currentLessons.size();i++)
        {
            mEdit1.remove("Status_" + i);

            if (arrayName.equals("currentLessons")){
                SportsLesson sportsLesson = (SportsLesson)currentLessons.get(i);
                mEdit1.putString("Status_" + i, sportsLesson.title);
            }
            else {
                String string = (String)currentLessons.get(i);
                mEdit1.putString("Status_" + i, string);
            }

        }

        return mEdit1.commit();
    }


}
