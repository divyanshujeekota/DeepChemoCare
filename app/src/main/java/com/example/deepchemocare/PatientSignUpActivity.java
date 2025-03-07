
package com.example.deepchemocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignUpActivity extends AppCompatActivity {


    EditText email,password,name,gender;
    CardView signup;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_sign_up);

        auth= FirebaseAuth.getInstance();

        email=findViewById(R.id.email_edittext_doctor_login_page);
        password=findViewById(R.id.password_edittext_doctor_login_page);
        name=findViewById(R.id.name_edittext_doctor_login_page);
        gender=findViewById(R.id.gender_edittext_doctor_login_page);

        signup=findViewById(R.id.signup_cardview_doctor_login_page);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text=email.getText().toString().trim();
                String pass_text=password.getText().toString().trim();
                String name_text=name.getText().toString().trim();
                String gender_text=gender.getText().toString().trim();
                FirebaseDatabase.getInstance().getReference().child("patient_detail").push().setValue(new StudentClass(name_text,gender_text));
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
                    startActivity(new Intent(PatientSignUpActivity.this, PatientLoginPage.class));
                }
            }
        });
    }


}