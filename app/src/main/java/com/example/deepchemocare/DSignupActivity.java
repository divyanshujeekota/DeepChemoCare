package com.example.deepchemocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DSignupActivity extends AppCompatActivity {

    EditText email,password,name,special,gender,description;
    Spinner hospital;
    CardView signup;
    String hospital_text="";
    FirebaseAuth auth;
    ArrayList<String> hospital_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dsignup);

        auth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email_edittext_doctor_login_page);
        password=findViewById(R.id.password_edittext_doctor_login_page);
        name=findViewById(R.id.name_edittext_doctor_login_page);
        special=findViewById(R.id.special_edittext_doctor_login_page);
        gender=findViewById(R.id.gender_edittext_doctor_login_page);
        description=findViewById(R.id.description_edittext_doctor_login_page);

        hospital=findViewById(R.id.hospital_spinner);
        hospital_name=new ArrayList<>();

        hospital_name.add("Apollo Hospital, Chennai");
        hospital_name.add("MGM Healthcare");
        hospital_name.add("MIOT International, Chennai");
        hospital_name.add("SIMS Hospital, Vadapalani, Chennai");

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,hospital_name);
        hospital.setAdapter(adapter);





        hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospital_text=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup=findViewById(R.id.signup_cardview_doctor_login_page);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text=email.getText().toString().trim();
                String pass_text=password.getText().toString().trim();
                String name_text=name.getText().toString().trim();
                String special_text=special.getText().toString().trim();
                String gender_text=gender.getText().toString().trim();
                String description_text=description.getText().toString().trim();
                FirebaseDatabase.getInstance().getReference().child("doctor_detail").push().setValue(new DoctorClass(name_text,description_text,hospital_text,special_text,gender_text));

                signupUser(email_text,pass_text);



            }
        });






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void signupUser(String email,String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(DSignupActivity.this,DoctorLoginPage.class));
                }
            }
        });
    }


}