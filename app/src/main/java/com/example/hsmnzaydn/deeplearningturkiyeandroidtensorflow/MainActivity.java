package com.example.hsmnzaydn.deeplearningturkiyeandroidtensorflow;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {


    private Interpreter tflite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lite dosya yolu
        String modelFile= "modelim.lite";

        // Kurucu yöntemi tanımlıyoruz
        try {
            tflite = new Interpreter(loadModelFile(this, modelFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Input veriyoruz
        float n1 = 1;
        float n2 = 89;
        float n3 = 66;
        float n4 = 23;
        float n5 = 94;
        float n6 = 28.1f;
        float n7 = 0.167f;
        float n8 = 21;
        float[][] inp = new float[][]{{n1, n2, n3, n4, n5, n6, n7, n8}};
        float[][] out = new float[][]{{0}};

        // Modele inputları veriyoruz
        tflite.run(inp, out);

        // Sonucu yazdırıyoruz
        Log.d("Sonuç", String.valueOf(Math.round(out[0][0])));

    }

    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
