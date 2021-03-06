package com.example.philipp.asvzroulette.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.philipp.asvzroulette.helper.HelperClass;
import com.example.philipp.asvzroulette.fragments.OneFragment;
import com.example.philipp.asvzroulette.R;
import com.example.philipp.asvzroulette.javaClassFiles.SportsLesson;
import com.example.philipp.asvzroulette.fragments.ThreeFragment;
import com.example.philipp.asvzroulette.fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private int[] tabIcons = {
            R.drawable.ic_home_white,
            R.drawable.ic_check_white,
            R.drawable.ic_people_white
    };
    private ViewPagerAdapter adapter;
    private ArrayList<SportsLesson> currentLessons,blacklistLessons,doneLessons;
    private String beerString;
    private HelperClass helperClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shared prefs for beer
        SharedPreferences prefs = getSharedPreferences("Beer", MODE_PRIVATE);
        String restoredText = prefs.getString("beer", null);
        if (restoredText != null) {
            beerString = prefs.getString("beer", "false");//"No name defined" is the default value.

        }

        //get Helper class object
        this.helperClass = new HelperClass();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //setup Lessons ArrayLists & initialize
        currentLessons = new ArrayList<>();
        helperClass.loadArray(getApplicationContext(),currentLessons,"currentLessons");
        blacklistLessons = new ArrayList<>();
        helperClass.loadArray(getApplicationContext(),blacklistLessons,"blacklistLessons");
        doneLessons = new ArrayList<>();
        helperClass.loadArray(getApplicationContext(),doneLessons,"DoneLessons");




        //fab setup & onclick
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SportsLesson randLesson = generateRandomSportlesson(helperClass);
                currentLessons.add(randLesson);
                Toast.makeText(MainActivity.this, "You will be doing : "+randLesson.title, Toast.LENGTH_SHORT).show();

                //invoke fragment recyclerview update
                OneFragment fragment = (OneFragment) adapter.getRegisteredFragment(0);
                fragment.updateCards();
                helperClass.saveArray(getApplicationContext(),currentLessons,"currentLessons");
            }
        });
    }

    //Save currentLesson,BlackList & DoneLessons onPause
    @Override
    protected void onPause() {
        super.onPause();
        helperClass.saveArray(getApplicationContext(),currentLessons,"currentLessons");
        helperClass.saveArray(getApplicationContext(),blacklistLessons,"blacklistLessons");
        helperClass.saveArray(getApplicationContext(),doneLessons,"doneLessons");
    }

    //Helper functions to pass Lessons to Fragments
    public ArrayList<SportsLesson> getCurrentLessons() {
        return currentLessons;
    }
    public ArrayList<SportsLesson> getDoneLessons() {
        return doneLessons;
    }
    public ArrayList<SportsLesson> getBlacklistLessons() {
        return blacklistLessons;
    }

    //Generate random lesson
    public SportsLesson generateRandomSportlesson(HelperClass helperClass){
        //check for beer probability
        //TODO add settings button for beer

        if ( helperClass.beerQuestion(beerString)){
            return new SportsLesson(getApplicationContext(), "Beer","currentLessons");

        }else {
            //Get lesson
            String[] possibleLessons = getResources().getStringArray(R.array.sports_lessons_names);
            int max = possibleLessons.length -1;
            int min = 0;

            Random r = new Random();
            int i = r.nextInt(max - min + 1) + min;

            return new SportsLesson(getApplicationContext(), possibleLessons[i],"currentLessons");
        }
    }


    //Disable back button
    @Override
    public void onBackPressed() {}

    //Options Menu
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //Tabs Settup
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        //setup tabs
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");


        viewPager.setAdapter(adapter);


        //setup fab
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.show();
                        break;

                    default:
                        fab.hide();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
}