package com.example.deepchemocare;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.deepchemocare.ml.EfficientnetB0;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ModelActivity extends AppCompatActivity {


    CardView select,upload;
    TextView t;
    ImageView imageView;
    String room,block;
    String Url="";
    String reg;
    FirebaseAuth auth;
    Bitmap desired;
    String username="";


    private static final int IMAGE_SIZE = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_model);

        auth=FirebaseAuth.getInstance();
        t=findViewById(R.id.b);
        imageView=findViewById(R.id.upload_imageview);
        select=findViewById(R.id.select_image_button);
        upload=findViewById(R.id.upload_image_button);
        upload.setVisibility(View.GONE);

        username=auth.getCurrentUser().getEmail().toString();
        int index=username.indexOf("@");
        username=username.substring(0,index);




        select.setClickable(true);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,100);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage();
            }
        });
        upload.setVisibility(View.VISIBLE);




//        try {
//            // Load image from drawable
//            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.bkl);
//            ByteBuffer byteBuffer = convertBitmapToByteBuffer(image); // Convert to ByteBuffer
//
//            EfficientnetB0 model = EfficientnetB0.newInstance(this);
//
//            // Create input tensor
//            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, IMAGE_SIZE, IMAGE_SIZE, 3}, DataType.FLOAT32);
//            inputFeature0.loadBuffer(byteBuffer);
//
//            // Run inference
//            EfficientnetB0.Outputs outputs = model.process(inputFeature0);
//            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//            // Convert output to string
//            float[] confidenceScores = outputFeature0.getFloatArray();
//            int predictedIndex = getMaxIndex(confidenceScores);
//            String label = getLabel(predictedIndex);  // Convert index to label
//
//            Toast.makeText(this, "Prediction: "+label, Toast.LENGTH_SHORT).show();
//
//            model.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, true);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
        resizedBitmap.getPixels(intValues, 0, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE);

        int pixel = 0;
        for (int i = 0; i < IMAGE_SIZE; i++) {
            for (int j = 0; j < IMAGE_SIZE; j++) {
                int val = intValues[pixel++]; // RGB value
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f); // R
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);  // G
                byteBuffer.putFloat((val & 0xFF) / 255.0f);         // B
            }
        }
        return byteBuffer;
    }



    private ByteBuffer preprocessImage(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, true);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[IMAGE_SIZE * IMAGE_SIZE];
        resizedBitmap.getPixels(intValues, 0, IMAGE_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE);

        float[] mean = {0.485f, 0.456f, 0.406f};  // Same as PyTorch
        float[] std = {0.229f, 0.224f, 0.225f};   // Same as PyTorch

        int pixel = 0;
        for (int i = 0; i < IMAGE_SIZE; i++) {
            for (int j = 0; j < IMAGE_SIZE; j++) {
                int val = intValues[pixel++];

                // Convert to 0-1 range
                float r = ((val >> 16) & 0xFF) / 255.0f;
                float g = ((val >> 8) & 0xFF) / 255.0f;
                float b = (val & 0xFF) / 255.0f;

                // Apply PyTorch Normalization: (value - mean) / std
                byteBuffer.putFloat((r - mean[0]) / std[0]);
                byteBuffer.putFloat((g - mean[1]) / std[1]);
                byteBuffer.putFloat((b - mean[2]) / std[2]);
            }
        }
        return byteBuffer;
    }


    private int getMaxIndex(float[] arr) {
        int maxIndex = 0;
        for (int i = 0; i < 7; i++) {
            if (arr[i] > arr[maxIndex]) maxIndex = i;
        }
        return maxIndex;
    }

    // ✅ Ensure labels match training order
    private String getLabel(int index) {
        String[] labels = {
                "Actinic Keratoses and Intraepithelial Carcinoma (AKIEC)",
                "Basal Cell Carcinoma (Basal)",
                "Vascular Lesions (VASC)",
                "Dermatofibroma (DF)",
                "Melanoma (MEL)",
                "Melanocytic Nevi (NV)",
                "Benign Keratosis-like Lesions (BKL)"
        };
        return (index >= 0 && index < labels.length) ? labels[index] : "Unknown";
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && data!=null && data.getData()!=null)
        {
            try {
                Uri imageUri = data.getData();
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
                desired=bitmap;





            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void checkImage()
    {
        try {
            // Load image from drawable
            ByteBuffer byteBuffer = preprocessImage(desired); // Apply correct preprocessing

            EfficientnetB0 model = EfficientnetB0.newInstance(this);

            // Create input tensor
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, IMAGE_SIZE, IMAGE_SIZE, 3}, DataType.FLOAT32);
            inputFeature0.loadBuffer(byteBuffer);

            // Run inference
            EfficientnetB0.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Convert output to string
            float[] confidenceScores = outputFeature0.getFloatArray();
            int predictedIndex = getMaxIndex(confidenceScores);
            String label = getLabel(predictedIndex);  // Convert index to label
            t.setText("Skin Lesion: "+label);
            FirebaseDatabase.getInstance().getReference().child("predicted_disease").child(username).setValue(label);

            model.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}