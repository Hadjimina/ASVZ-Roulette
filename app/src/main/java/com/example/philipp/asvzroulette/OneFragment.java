package com.example.philipp.asvzroulette;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class OneFragment extends Fragment{

    private ArrayList<SportsLesson> currentLessons;
    private MyAdapter adapter;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        //get currentLessons data from MainActivity
        MainActivity activity = (MainActivity) getActivity();
        currentLessons = activity.getCurrentLessons();
        adapter = new MyAdapter(currentLessons);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    //update currentLessons list & notify adapter of the change
    public void updateCards(){


        //get currentLessons data from MainActivity
        MainActivity activity = (MainActivity) getActivity();
        currentLessons = activity.getCurrentLessons();

        Log.v("lessons",currentLessons.toString());

        adapter.notifyDataSetChanged();

    }
}
