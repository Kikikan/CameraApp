package net.kikikan.cameraapp;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public SeekBar sb;
    public TextView delayTW;
    TextureView cameraView;

    private final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        setContentView(R.layout.activity_main);
        sb = findViewById(R.id.delaySeekBar);
        delayTW = findViewById(R.id.delaytextView);
        cameraView = findViewById(R.id.cameraView);

        sb.setOnSeekBarChangeListener(new SeekBarManager(this));
    }
}
