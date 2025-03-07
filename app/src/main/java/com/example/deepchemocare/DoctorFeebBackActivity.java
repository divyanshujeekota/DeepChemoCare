package com.example.deepchemocare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorFeebBackActivity extends AppCompatActivity {


    EditText editText;
    Button submit;
    String doctor_name="";
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_feeb_back);

        FirebaseAuth auth=FirebaseAuth.getInstance();

        username=auth.getCurrentUser().getEmail().toString();
        int index=username.indexOf("@");
        username=username.substring(0,index);
        editText=findViewById(R.id.feedback_edittext);
        submit=findViewById(R.id.submit_feedback);
        doctor_name=getIntent().getStringExtra("doctor_name").toString();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f=editText.getText().toString();
                String key=""+System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("feedback_details").child(doctor_name.toLowerCase()).child(key).child("sender").setValue(username);
                FirebaseDatabase.getInstance().getReference().child("feedback_details").child(doctor_name.toLowerCase()).child(key).child("feedback").setValue(f);
                editText.setText("");
                Toast.makeText(DoctorFeebBackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}