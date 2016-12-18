package com.example.philipp.asvzroulette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<SportsLesson> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTextViewTitle,mTextViewDesc;
        ImageView mImageView;
        Button mButtonDone,mButtonBlacklist;

        MyViewHolder(View v) {
            super(v);

            mButtonDone = (Button) v.findViewById(R.id.buttonDone);
            mButtonBlacklist = (Button) v.findViewById(R.id.buttonBlacklist);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextViewTitle = (TextView) v.findViewById(R.id.title);
            mTextViewDesc = (TextView) v.findViewById(R.id.description);
            mImageView = (ImageView) v.findViewById(R.id.thumbnail);

            mButtonBlacklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get Blacklist

                }
            });

            mButtonDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    MyAdapter(ArrayList<SportsLesson>myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewTitle.setText(mDataset.get(position).title);
        holder.mTextViewDesc.setText(mDataset.get(position).desc);
        holder.mImageView.setImageResource(mDataset.get(position).coverImg);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}