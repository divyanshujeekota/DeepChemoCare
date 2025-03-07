package com.example.deepchemocare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder>{

    ArrayList<String> doctorNames;
    ArrayList<String> sp;
    Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        public ViewHolder(@NonNull CardView cv) {
            super(cv);
            this.cardView=cv;

        }
    }


    public DoctorListAdapter(ArrayList<String> doctorNames,ArrayList<String> s,Context context)
    {
        this.doctorNames=doctorNames;
        this.context=context;
        this.sp=s;
    }



    @NonNull
    @Override
    public DoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_cardview,parent,false);
        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.ViewHolder holder, int position) {
        CardView cardView=holder.cardView;
        TextView names=cardView.findViewById(R.id.doctor_name);
        TextView special=cardView.findViewById(R.id.special_doctor_list);
        Button book=cardView.findViewById(R.id.book_appointment_button_doctorlist);
        String n=doctorNames.get(position);
        special.setText(sp.get(position));

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,DoctorSeePage.class);
                intent.putExtra("doctor_name",n);
                context.startActivity(intent);


            }
        });

        names.setText(n);
    }

    @Override
    public int getItemCount() {
        return doctorNames.size();
    }
}
