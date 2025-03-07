package com.example.deepchemocare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CheckAppointmentAdapter extends RecyclerView.Adapter<CheckAppointmentAdapter.ViewHolder>
{

    ArrayList<String> patient_name,slots;
    Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        public ViewHolder(@NonNull CardView cv) {
            super(cv);
            this.cardView=cv;
        }
    }

    public CheckAppointmentAdapter(ArrayList<String> p,ArrayList<String> s, Context context)
    {
        this.patient_name=p;
        this.slots=s;
        this.context=context;
    }


    @NonNull
    @Override
    public CheckAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.check_appointment_cardview,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckAppointmentAdapter.ViewHolder holder, int position) {
        try {
            CardView cv = holder.cardView;
            TextView name = cv.findViewById(R.id.patient_name_dcheck);
            Button call=cv.findViewById(R.id.call_button_doctor);
            TextView slot = cv.findViewById(R.id.slot_time_dcheck);
            name.setText("Patient Name: " + patient_name.get(position));
            slot.setText(slots.get(position));

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,DoctorCallActivity.class);
                    intent.putExtra("caller_id",patient_name.get(position));
                    Toast.makeText(context, ""+patient_name.get(position), Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);

                }
            });




        }catch (NullPointerException e)
        {
            Toast.makeText(context, "null pointer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return patient_name.size();
    }
}
