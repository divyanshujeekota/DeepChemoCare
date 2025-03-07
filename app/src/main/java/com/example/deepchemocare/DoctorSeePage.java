package com.example.deepchemocare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorSeePage extends AppCompatActivity {

    String doctor_name="";
    TextView name,speciality,description;
    Button book,give;
    String username="",hos="";
    String slot="";
    CardView slot1,slot2,slot3,navigate;
    int defaultColor,selectedColor;
    TextView dont;
    RecyclerView recyclerView;

    ArrayList<String> sender,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_see_page);
        name=findViewById(R.id.doctor_name_doctorsee);
        doctor_name=getIntent().getStringExtra("doctor_name").toString();
        name.setText(doctor_name);

        sender=new ArrayList<>();
        feedback=new ArrayList<>();

        ChatAdapter adapter=new ChatAdapter(sender,feedback);


        recyclerView=findViewById(R.id.recyclerview_doctorsee);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback_details").child(doctor_name.toLowerCase());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                 sender.clear();
                 feedback.clear();
                 for (DataSnapshot dataSnapshot:snapshot.getChildren())
                 {
                     String s=dataSnapshot.child("sender").getValue(String.class);
                     String f=dataSnapshot.child("feedback").getValue(String.class);

                     sender.add(s);
                     feedback.add(f);
                 }

                 adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (NullPointerException e)
        {
            //nothing
        }





        FirebaseAuth auth=FirebaseAuth.getInstance();
        username=auth.getCurrentUser().getEmail().toString();
        int index=username.indexOf("@patient.com");
        username=username.substring(0,index);
        give=findViewById(R.id.give_doctorsee);
        defaultColor = getResources().getColor(android.R.color.white); // Normal color
        selectedColor = getResources().getColor(android.R.color.holo_blue_light); // Highlighted color

        // Set default background color

        give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DoctorSeePage.this,DoctorFeebBackActivity.class);
                intent.putExtra("doctor_name",doctor_name);
                startActivity(intent);
            }
        });




        speciality=findViewById(R.id.doctor_special_doctorsee);
        description=findViewById(R.id.description_doctorsee);
        navigate=findViewById(R.id.navigate_doctorsee);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng=null;
                if (hos.equals("Apollo Hospital, Chennai"))
                {
                    latLng=new LatLng(13.084660664644193, 80.27009869142292);
                }
                else if (hos.equals("Fortis Malar Hospital"))
                {
                    latLng=new LatLng(13.010956742626243, 80.25885243967693);
                }
                else if (hos.equals("MIOT International"))
                {
                    latLng=new LatLng(13.022361686895314, 80.18596756688207);
                }
                else
                {
                    latLng=new LatLng(13.051907320673648, 80.21181329571814);
                }

                openGoogleMaps(latLng);



            }
        });




        slot1=findViewById(R.id.firstslot);
        slot2=findViewById(R.id.secondslot);
        slot3=findViewById(R.id.thridslot);


        slot1.setCardBackgroundColor(defaultColor);
        slot2.setCardBackgroundColor(defaultColor);
        slot3.setCardBackgroundColor(defaultColor);


        book=findViewById(R.id.book_now_doctorsee);

        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slot="9:30 AM-11:30 AM";
                updateSelectedCardview(slot1);
            }
        });
        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slot="12:00 PM-2:00 PM";
                updateSelectedCardview(slot2);

            }
        });
        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slot="2:30 PM-04:30 PM";
                updateSelectedCardview(slot3);

            }
        });


        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("doctor_detail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String des=dataSnapshot.child("description").getValue(String.class);
                    String sp=dataSnapshot.child("position").getValue(String.class)+", ";
                    hos=dataSnapshot.child("hospital").getValue(String.class);
                    sp=sp+hos;
                    speciality.setText(sp);
                    description.setText(des);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=""+System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("appointments").child(doctor_name.toLowerCase()).child(key).child("user").setValue(username);
                FirebaseDatabase.getInstance().getReference().child("appointments").child(doctor_name.toLowerCase()).child(key).child("slot").setValue(slot);
                Toast.makeText(DoctorSeePage.this, "Successfully Booked", Toast.LENGTH_SHORT).show();
                String key2=""+System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("appointment_record").child(username.toLowerCase()).child(key2).child("slot").setValue("Appointment slot: "+slot);
                FirebaseDatabase.getInstance().getReference().child("appointment_record").child(username.toLowerCase()).child(key2).child("doctor_name").setValue("Dr. "+doctor_name);


            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void openGoogleMaps(LatLng hospitalLocation)
    {
        String encodedHospitalName= Uri.encode("Chettinad Hospital");
        Uri mapUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
                + hospitalLocation.latitude + "," + hospitalLocation.longitude
                + "&destination_place_id=" + encodedHospitalName);Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
            + hospitalLocation.latitude + "," + hospitalLocation.longitude
            + "&destination_place_id=" + encodedHospitalName);Intent mapIntent=new Intent(Intent.ACTION_VIEW,mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void updateSelectedCardview(CardView selected)
    {
        slot1.setCardBackgroundColor(defaultColor);
        slot2.setCardBackgroundColor(defaultColor);
        slot3.setCardBackgroundColor(defaultColor);
        selected.setCardBackgroundColor(selectedColor);
    }


}