package com.example.deepchemocare;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientProfilePage extends AppCompatActivity {

    CardView cardView;
    TextView name,email,predicted;
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_profile_page);
        cardView=findViewById(R.id.scheduled_appointment);

        name=findViewById(R.id.tv_patient_name);
        email=findViewById(R.id.tv_email);
        predicted=findViewById(R.id.tv_disease);


        FirebaseAuth auth=FirebaseAuth.getInstance();
        username=auth.getCurrentUser().getEmail().toString();
        email.setText("Email "+username);
        int index=username.indexOf("@");
        username=username.substring(0,index);
        name.setText("Name "+username);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("predicted_disease");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if (dataSnapshot.getKey().equals(username))
                    {
                        predicted.setText("Predicted Disease: "+dataSnapshot.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientProfilePage.this, ScheduledAppointment.class));
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}