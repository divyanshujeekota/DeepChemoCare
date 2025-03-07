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

import com.example.deepchemocare.repository.MainRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorLoginPage extends AppCompatActivity {

    EditText email,password;
    CardView login;
    FirebaseAuth auth;
    private MainRepository mainRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_login_page);

        mainRepository = MainRepository.getInstance();


        email=findViewById(R.id.email_edittext_doctor_login_page);
        password=findViewById(R.id.password_edittext_doctor_login_page);

        auth=FirebaseAuth.getInstance();

        login=findViewById(R.id.login_cardview_doctor_login_page);

        TextView create=findViewById(R.id.dont_have_account_doctor_login_page);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginPage.this,DSignupActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text=email.getText().toString().trim();
                String pass_text=password.getText().toString().trim();

                int index=email_text.indexOf("@doctor.com");
                String s=email_text.substring(0,index).toLowerCase();
                mainRepository.login(
                        s, getApplicationContext(), () -> {
                            //if success then we want to move to call activity
                            loginUser(email_text,pass_text);

                        }
                );
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void loginUser(String email,String password)
    {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    startActivity(new Intent(DoctorLoginPage.this,DMainPage.class));
                }
            }
        });

    }
}