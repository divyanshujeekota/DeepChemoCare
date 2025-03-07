package com.example.deepchemocare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deepchemocare.repository.MainRepository;
import com.example.deepchemocare.utils.DataModelType;

import org.webrtc.SurfaceViewRenderer;

public class DoctorCallActivity extends AppCompatActivity implements MainRepository.Listener {

    private MainRepository mainRepository;
    private Boolean isCameraMuted = false;
    private Boolean isMicrophoneMuted = false;


    SurfaceViewRenderer localView,remoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_call);

        localView=findViewById(R.id.local_view_doctor);
        remoteView=findViewById(R.id.remote_view_doctor);

        init();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void init()
    {
        String targetUser=getIntent().getStringExtra("caller_id").toString();

        mainRepository = MainRepository.getInstance();
        mainRepository.sendCallRequest(targetUser,()->{
            Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show();
        });

        mainRepository.initLocalView(localView);
        mainRepository.initRemoteView(remoteView);
        mainRepository.listener = this;


        mainRepository.subscribeForLatestEvent(data->{
            if (data.getType()== DataModelType.StartCall){
                runOnUiThread(()->{
                    //star the call here
                    mainRepository.startCall(data.getSender());

                });
            }
        });




    }

    @Override
    public void webrtcConnected() {
        runOnUiThread(() -> {
            Toast.makeText(this, "WebRTC connected", Toast.LENGTH_SHORT).show();
            Log.d("webrtcConnected","webrtcConnected");
        });
    }


    @Override
    public void webrtcClosed() {
        runOnUiThread(this::finish);
    }
}