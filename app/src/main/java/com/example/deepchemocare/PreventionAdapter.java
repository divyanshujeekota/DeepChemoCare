package com.example.deepchemocare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.ViewHolder>{

    ArrayList<Integer> images_prevention;
    ArrayList<String> titles_prevention;
    ArrayList<String> descriptions_prevention;

    public PreventionAdapter(ArrayList<Integer> i, ArrayList<String> t, ArrayList<String> d)
    {
        this.images_prevention=i;
        this.titles_prevention=t;
        this.descriptions_prevention=d;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        public ViewHolder(@NonNull CardView cv) {
            super(cv);
            this.cardView=cv;
        }
    }


    @NonNull
    @Override
    public PreventionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.prevention_cardview,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull PreventionAdapter.ViewHolder holder, int position) {

        CardView cv=holder.cardView;
        ImageView image=cv.findViewById(R.id.prevention_imageview);
        TextView title=cv.findViewById(R.id.prevention_title);
        TextView description=cv.findViewById(R.id.preventione_textview);

        image.setImageResource(images_prevention.get(position));
        title.setText(titles_prevention.get(position));
        description.setText(descriptions_prevention.get(position));
    }

    @Override
    public int getItemCount() {
        return titles_prevention.size();
    }
}
