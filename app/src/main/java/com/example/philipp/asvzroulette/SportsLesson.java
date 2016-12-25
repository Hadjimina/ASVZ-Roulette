package com.example.philipp.asvzroulette;

import android.content.Context;

class SportsLesson {
    String title,desc,type;
    int coverImg;

    private int[] Icons = {
            R.drawable.ballet,
            R.drawable.default_img

    };

    SportsLesson(Context c, String title,String type){
        this.title = title;
        String[] SportNames = c.getResources().getStringArray(R.array.sports_lessons_names);

        //Set type of lesson
        this.type = type;

        //automagically sets image
        coverImg = title == "Ballett" ? Icons[0]:Icons[1];
        //coverImg = title == "Beer" ? Icons[0]:Icons[2];
        //coverImg = Arrays.asList(SportNames).contains(title) ? Icons[Arrays.asList(SportNames).indexOf(title)-1] : Icons[Icons.length-1]; USE ONCE ALL IMAGES ARE THERE
        desc = "Your sports activity is " + title+", awesome !";
    }
}
