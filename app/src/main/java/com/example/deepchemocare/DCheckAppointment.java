package com.example.deepchemocare;

import android.os.Bundle;
import android.widget.Toast;

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

public class DCheckAppointment extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<String> pname;
    ArrayList<String> slot_time;
    FirebaseAuth auth;
    String dname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dcheck_appointment);

        recyclerView=findViewById(R.id.dcheck_recycler);
        pname=new ArrayList<>();
        slot_time=new ArrayList<>();
        auth=FirebaseAuth.getInstance();

        dname=auth.getCurrentUser().getEmail().toString();
        int index=dname.indexOf("@doctor.com");
        dname=dname.substring(0,index);


        CheckAppointmentAdapter adapter=new CheckAppointmentAdapter(pname,slot_time,DCheckAppointment.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("appointments").child(dname);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pname.clear();
                slot_time.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String pn=dataSnapshot.child("user").getValue(String.class);
                    String s=dataSnapshot.child("slot").getValue(String.class);
                    pname.add(pn);
                    slot_time.add(s);
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