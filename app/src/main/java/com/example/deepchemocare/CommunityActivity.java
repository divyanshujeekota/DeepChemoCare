package com.example.deepchemocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CommunityActivity extends AppCompatActivity {

    Button cakiec,cbasal,cmelanoma,cderma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community);

        cakiec=findViewById(R.id.akiec_chatnow);
        cbasal=findViewById(R.id.basal_chatnow);
        cmelanoma=findViewById(R.id.melanoma_chatnow);
        cderma=findViewById(R.id.derma_chatnow);


        cakiec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CommunityActivity.this,CommunityChatActivity.class);
                intent.putExtra("community","akiec");
                startActivity(intent);
            }
        });

        cbasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CommunityActivity.this,CommunityChatActivity.class);
                intent.putExtra("community","basal");
                startActivity(intent);

            }
        });

        cmelanoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CommunityActivity.this,CommunityChatActivity.class);
                intent.putExtra("community","melanoma");
                startActivity(intent);

            }
        });

        cderma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CommunityActivity.this,CommunityChatActivity.class);
                intent.putExtra("community","derma");
                startActivity(intent);

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}