package com.example.deepchemocare;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrescriptionViewPatient extends AppCompatActivity {

    LinearLayout linearLayout;
    TextView doctor_name,special,patient_detail;

    ArrayList<String> medicine;
    String doctor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prescription_view_patient);

        doctor_name=findViewById(R.id.doctor_name);
        special=findViewById(R.id.doctor_speciality);
        patient_detail=findViewById(R.id.patient_details);

        FirebaseAuth auth=FirebaseAuth.getInstance();
        String u=auth.getCurrentUser().getEmail().toString().toLowerCase();
        int index=u.indexOf("@");
        u=u.substring(0,index);
        patient_detail.setText(u);
        medicine=new ArrayList<>();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("prescription_details").child(u);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor=snapshot.child("doctor_name").getValue(String.class);
                doctor_name.setText(doctor);
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if (!dataSnapshot.getValue().toString().equals(doctor))
                    {
                        medicine.add(dataSnapshot.getValue(String.class));
                    }
                }
                for (int i=0;i<medicine.size();i++)
                {
                    TextView textView=new TextView(PrescriptionViewPatient.this);
                    textView.setText(medicine.get(i));
                    linearLayout.addView(textView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        linearLayout=findViewById(R.id.medicine_linearlayout);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}