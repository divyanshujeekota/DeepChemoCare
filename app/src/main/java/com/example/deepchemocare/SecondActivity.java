package com.example.deepchemocare;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deepchemocare.repository.MainRepository;
import com.example.deepchemocare.utils.DataModelType;

import org.webrtc.SurfaceViewRenderer;

public class SecondActivity extends AppCompatActivity implements MainRepository.Listener{

    private MainRepository mainRepository;
    SurfaceViewRenderer localView,remoteView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        localView=findViewById(R.id.local_view_doctor);
        remoteView=findViewById(R.id.remote_view_doctor);

        init();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void init() {
        mainRepository = MainRepository.getInstance();

        // Ensure login() is completed before initializing views
        mainRepository.login("eshaan", this, () -> {
            // Now webRTCClient is initialized, safe to call initLocalView()
            mainRepository.initLocalView(localView);
            mainRepository.initRemoteView(remoteView);

            mainRepository.listener = this;

            // Handle potential null value for target
            String target = getIntent().getStringExtra("target");
            if (target != null) {
                mainRepository.startCall(target);
            } else {
                Toast.makeText(this, "Error: Target user is null", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void webrtcConnected() {
        Toast.makeText(this, "webrtc connected", Toast.LENGTH_SHORT).show();
        localView.setVisibility(View.VISIBLE);
        remoteView.setVisibility(View.VISIBLE);

    }

    @Override
    public void webrtcClosed() {

    }
}