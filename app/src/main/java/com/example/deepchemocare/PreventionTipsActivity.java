package com.example.deepchemocare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class PreventionTipsActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<Integer> images;
    ArrayList<String> titles,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prevention_tips);


        images=new ArrayList<>();
        images.add(R.drawable.sunscream);
        images.add(R.drawable.sun_image);
        images.add(R.drawable.hand);

        titles=new ArrayList<>();
        titles.add("Use Sunscreen Daily \uD83C\uDF1E");
        titles.add("Avoid Peak Sun Hours ⏳");
        titles.add("Check Your Skin Regularly");

        description=new ArrayList<>();
        description.add("Why? The sun’s ultraviolet (UV) rays can damage skin cells, leading to skin cancer.\n" +
                "How?\n" +
                "\n" +
                "Apply a broad-spectrum sunscreen (SPF 30 or higher) on all exposed skin.\n" +
                "Choose water-resistant sunscreen for swimming or sweating.\n" +
                "Reapply every 2 hours or more frequently if sweating or swimming.");
        description.add("Why? The sun’s rays are the strongest between 10 AM – 4 PM, increasing UV exposure and risk of skin cancer.\n" +
                "How?\n" +
                "\n" +
                "Seek shade under trees, umbrellas, or buildings.\n" +
                "Plan outdoor activities early in the morning or late afternoon.");
        description.add("Why? Early detection of suspicious moles or skin changes can save lives.\n" +
                "How?\n" +
                "\n" +
                "Examine your skin once a month for new, changing, or unusual spots.\n" +
                "Follow the ABCDE rule to detect potential melanoma:\n" +
                "Asymmetry – One half of the mole doesn’t match the other.\n" +
                "Border – Irregular, jagged, or blurred edges.\n" +
                "Color – Multiple shades (brown, black, red, white, or blue).\n" +
                "Diameter – Larger than 6mm (size of a pencil eraser).\n" +
                "Evolving – Changes in size, shape, or color over time.\n" +
                "If you notice any of these signs, see a dermatologist immediately.");



        viewPager2=findViewById(R.id.prevention_viewpager);

        PreventionAdapter adapter=new PreventionAdapter(images,titles,description);

        viewPager2.setAdapter(adapter);






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}