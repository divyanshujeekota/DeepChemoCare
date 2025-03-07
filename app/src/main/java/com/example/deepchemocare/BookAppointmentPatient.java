package com.example.deepchemocare;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookAppointmentPatient extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<String> doctorList;
    ArrayList<String> special;
    ArrayList<String> filteredList;
    DoctorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment_patient);

        recyclerView=findViewById(R.id.doctor_recycler_view);
        searchView=findViewById(R.id.doctor_searchview);

        doctorList=new ArrayList<>();



        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("doctor_detail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                filteredList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String obj=dataSnapshot.child("doctor_name").getValue(String.class);
                    String sp=dataSnapshot.child("position").getValue(String.class)+", "+dataSnapshot.child("hospital").getValue(String.class);
                    special.add(sp);
                    doctorList.add(obj);
                }
                filteredList.addAll(doctorList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        filteredList=new ArrayList<>(doctorList);
        special=new ArrayList<>();

        adapter=new DoctorListAdapter(filteredList,special,BookAppointmentPatient.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterQuery(newText);
                return true;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void filterQuery(String query)
    {
        filteredList.clear();
        for (String doctorNames : doctorList)
        {
            if (doctorNames.toLowerCase().contains(query.toLowerCase()))
            {
                filteredList.add(doctorNames);
            }
        }
        adapter.notifyDataSetChanged();

    }

}