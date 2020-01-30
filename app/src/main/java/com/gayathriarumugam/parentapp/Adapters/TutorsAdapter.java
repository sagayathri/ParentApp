package com.gayathriarumugam.parentapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.gayathriarumugam.parentapp.Model.Tutor;
import com.gayathriarumugam.parentapp.R;

import java.util.ArrayList;

public class TutorsAdapter extends RecyclerView.Adapter<TutorsAdapter.TutorViewHolder> {

    private ArrayList<Tutor> mData;
    private LayoutInflater mInflater;
    private TutorsAdapter.ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public TutorsAdapter(Context context, ArrayList<Tutor> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public TutorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.tutor_row, parent, false);
        return new TutorViewHolder(view);
    }

    // binds the data to the TextView in each row
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(TutorViewHolder holder, int position) {
        Tutor item = mData.get(position);
        holder.myTextView.setText(item.getName());
        switch (position) {
            case 0:
                holder.myImageView.setImageResource(R.drawable.john);
                break;
            case 1:
                holder.myImageView.setImageResource(R.drawable.sam);
                break;
            case 2:
                holder.myImageView.setImageResource(R.drawable.daniel);
                break;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Tutor getItem(int position) {
        return mData.get(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class TutorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView myTextView;
        ImageView myImageView;

        TutorViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tutorName);
            myImageView = itemView.findViewById(R.id.tutorImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(TutorsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
