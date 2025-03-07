package com.example.deepchemocare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.deepchemocare.repository.MainRepository;
import com.example.deepchemocare.utils.DataModelType;

import org.webrtc.SurfaceViewRenderer;

public class PMainPage extends AppCompatActivity implements MainRepository.Listener {

    CardView book,preventionTips,yoga,heatMap,community,detection,prescription,sos,setting,success;

    private MainRepository mainRepository;
    private Boolean isCameraMuted = false;
    private Boolean isMicrophoneMuted = false;

    SurfaceViewRenderer localView,remoteView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pmain_page);

        success=findViewById(R.id.success_cardview);
        preventionTips=findViewById(R.id.prevention_tips_cardview);
        book=findViewById(R.id.schedule_now_cardview_pmain_page);
        yoga=findViewById(R.id.yoga_pmain_page);

        heatMap=findViewById(R.id.forth_cardview_pmainpage);
        community=findViewById(R.id.community_pmain_page);
        prescription=findViewById(R.id.prescription_pmain_page);

        localView=findViewById(R.id.local_view_patient);
        remoteView=findViewById(R.id.remote_view_patient);


        sos=findViewById(R.id.Secondary_tips_cardview);

        setting=findViewById(R.id.profile_button_pmainpage);

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this, MotivationActivity.class));
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,PatientProfilePage.class));
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,SOSDoctorActivity.class));
            }
        });


        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,PrescriptionViewPatient.class));
            }
        });

        detection=findViewById(R.id.cancer_detection_pmainpage);

        detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this, ModelActivity.class));
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,CommunityActivity.class));
            }
        });

        heatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,HeatMapActivity.class));
            }
        });


        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,YogaActivity.class));
            }
        });



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this, BookAppointmentPatient.class));
            }
        });

        preventionTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PMainPage.this,PreventionTipsActivity.class));
            }
        });

        init();



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void init() {

            mainRepository = MainRepository.getInstance();

            mainRepository.initLocalView(localView);
            mainRepository.initRemoteView(remoteView);
            mainRepository.listener = this;
            mainRepository.subscribeForLatestEvent(data -> {
                if (data.getType() == DataModelType.StartCall) {
                    runOnUiThread(() -> {
                        //star the call here
                        Intent intent=new Intent(PMainPage.this,SecondActivity.class);
                        intent.putExtra("target",data.getSender());
                        startActivity(intent);

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

        localView.setVisibility(View.GONE);
        remoteView.setVisibility(View.GONE);

    }

}