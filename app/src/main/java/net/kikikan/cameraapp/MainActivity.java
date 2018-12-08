package net.kikikan.cameraapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public SeekBar sb;
    public TextView delayTW;
    ImageView cameraView;
    Button takeButton;

    private final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        setContentView(R.layout.activity_main);
        sb = findViewById(R.id.delaySeekBar);
        delayTW = findViewById(R.id.delaytextView);
        cameraView = findViewById(R.id.cameraView);
        takeButton = (Button) findViewById(R.id.takeButton);

        sb.setOnSeekBarChangeListener(new SeekBarManager(this));

        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (delayTW.getText().toString() != "0")
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE);
                        }
                    }, Integer.parseInt(delayTW.getText().toString()) * 1000);
                else
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == CAMERA_REQUEST_CODE)
        {
            if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Matrix matrix = new Matrix();

                matrix.postRotate(90);

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth(), imageBitmap.getHeight(), true);

                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                cameraView.setImageBitmap(rotatedBitmap);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }
}
