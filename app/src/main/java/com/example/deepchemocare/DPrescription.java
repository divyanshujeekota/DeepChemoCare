package com.example.deepchemocare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DPrescription extends AppCompatActivity {

    EditText e,n;
    Button b,final_submit;
    int c=0;
    ArrayList<EditText> list;

    LinearLayout layout;
    String username="";
    String doctor_name="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dprescription);
        list=new ArrayList<>();
        layout=findViewById(R.id.linear_pres);
        e=findViewById(R.id.edit1);
        b=findViewById(R.id.b1);
        n=findViewById(R.id.edit2);

        FirebaseAuth auth=FirebaseAuth.getInstance();

        doctor_name=auth.getCurrentUser().getEmail().toString();
        int index=doctor_name.indexOf("@");
        doctor_name=doctor_name.substring(0,index);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Integer.valueOf(e.getText().toString());
                username=n.getText().toString().toLowerCase();

                for (int i=0;i<c;i++)
                {
                    EditText editText=new EditText(DPrescription.this);
                    editText.setHint("Enter Medicine Name");
                    list.add(editText);
                    layout.addView(editText);
                }
            }
        });

        final_submit=findViewById(R.id.final_submit);
        final_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText e:list)
                {

                    FirebaseDatabase.getInstance().getReference().child("prescription_details").child(username).push().setValue(e.getText().toString());
                }
                Toast.makeText(DPrescription.this, "Submitted", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("prescription_details").child(username).child("doctor_name").setValue(doctor_name);

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}