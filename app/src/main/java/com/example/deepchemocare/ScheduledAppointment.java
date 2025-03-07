package com.example.deepchemocare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduledAppointment extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<String> sender,message;
    String username="";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scheduled_appointment);

        auth=FirebaseAuth.getInstance();
        username=auth.getCurrentUser().getEmail().toString();
        int index=username.indexOf("@patient.com");
        username=username.substring(0,index);

        recyclerView=findViewById(R.id.recyclerview_scheduled);
        sender=new ArrayList<>();
        message=new ArrayList<>();

        ChatAdapter adapter=new ChatAdapter(sender,message);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("appointment_record").child(username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sender.clear();
                message.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String d=dataSnapshot.child("doctor_name").getValue(String.class);
                    String s=dataSnapshot.child("slot").getValue(String.class);
                    sender.add(d);
                    message.add(s);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}