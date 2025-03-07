package com.example.deepchemocare;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class YogaDescriptionactivity extends AppCompatActivity {


    VideoView videoView;
    TextView title,description;
    String d="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga_descriptionactivity);




        videoView=findViewById(R.id.prevention_videoview);
        title=findViewById(R.id.prevention_title);
        description=findViewById(R.id.preventione_textview);

        String pose=getIntent().getStringExtra("pose").toString();

        String videoPath = "android.resource://" + getPackageName() + "/";

        if (pose.equals("Vrikshasana"))
        {
            videoPath=videoPath+R.raw.e1;
            d="es! Tree Pose (Vrikshasana) can be beneficial for managing skin cancer treatment side effects in several ways:\n" +
                    "\n" +
                    "Benefits of Tree Pose (Vrikshasana)\n" +
                    "\uD83C\uDF3F Improves Balance & Stability – Helps regain physical strength, especially if treatment has caused weakness.\n" +
                    "\uD83C\uDF3F Reduces Stress & Anxiety – Balancing postures require focus, promoting mindfulness and relaxation.\n" +
                    "\uD83C\uDF3F Enhances Blood Circulation – Better circulation supports skin healing and detoxification.\n" +
                    "\uD83C\uDF3F Boosts Immunity – Helps strengthen the nervous system and overall resilience.\n" +
                    "\uD83C\uDF3F Improves Posture & Strength – Aids in strengthening the legs, core, and spine, which may become weak due to prolonged inactivity.";
        }
        else if (pose.equals("Bhujangasana"))
        {
            videoPath=videoPath+R.raw.e2;
            d=" Cobra Pose (Bhujangasana) is an excellent yoga pose for managing skin cancer treatment side effects.\n" +
                    "\n" +
                    "Benefits of Cobra Pose for Recovery\n" +
                    "\uD83D\uDC0D Boosts Circulation – Improves blood flow, which supports skin healing.\n" +
                    "\uD83D\uDCAA Strengthens the Back & Core – Helps counteract weakness from prolonged rest.\n" +
                    "\uD83E\uDE78 Opens the Chest & Lungs – Enhances deep breathing and oxygen supply, reducing fatigue.\n" +
                    "\uD83D\uDD25 Reduces Stress & Anxiety – Activates the parasympathetic nervous system for relaxation.\n" +
                    "\uD83C\uDF3F Improves Digestion & Immunity – Stimulates abdominal organs, aiding in digestion and detoxification.";

        }
        else if (pose.equals("Setu Bandhasana"))
        {
            videoPath=videoPath+R.raw.e3;
            d="Bridge Pose (Setu Bandhasana) is a great yoga pose to help manage the side effects of skin cancer medication by improving circulation, reducing stress, and strengthening the body.\n" +
                    "\n" +
                    "Benefits of Bridge Pose for Skin Cancer Recovery\n" +
                    "\uD83C\uDF3F Boosts Blood Circulation – Helps oxygenate the skin and detoxify the body.\n" +
                    "\uD83E\uDDD8 Reduces Stress & Anxiety – Opens the chest and calms the nervous system.\n" +
                    "\uD83D\uDCAA Strengthens the Spine & Legs – Helps with weakness caused by medication side effects.\n" +
                    "\uD83E\uDE78 Improves Digestion & Immunity – Stimulates abdominal organs for better digestion.\n" +
                    "\uD83D\uDD25 Relieves Fatigue – Increases energy levels and helps with overall healing.\n";
        }
        else
        {
            videoPath=videoPath+R.raw.e4;
            d="Yes! Bow Pose (Dhanurasana) is an excellent yoga pose to help with skin cancer medication side effects by improving circulation, digestion, and reducing fatigue.\n" +
                    "\n" +
                    "Benefits of Bow Pose for Skin Cancer Recovery\n" +
                    "\uD83C\uDF3F Improves Blood Flow – Helps detoxify the body and promote skin healing.\n" +
                    "\uD83D\uDCAA Strengthens the Back & Core – Helps counteract weakness and improves posture.\n" +
                    "\uD83D\uDD25 Boosts Energy & Reduces Fatigue – Stimulates the nervous system, increasing vitality.\n" +
                    "\uD83E\uDE78 Enhances Digestion & Immunity – Stimulates abdominal organs, helping in detoxification.\n" +
                    "\uD83D\uDE0C Reduces Stress & Anxiety – Opens the chest and promotes deep breathing.\n";

        }

        Uri videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnCompletionListener(mp -> videoView.start());
        title.setText(pose);
        description.setText(d);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}