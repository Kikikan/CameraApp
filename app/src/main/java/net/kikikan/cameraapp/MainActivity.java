package net.kikikan.cameraapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        takeButton = findViewById(R.id.takeButton);

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
                if (imageBitmap == null)
                    return;
                cameraView.setImageBitmap(imageBitmap);

                try {
                    String path = Environment.getExternalStorageDirectory().toString();
                    OutputStream fOut = null;
                    File file = new File(path, "test.png");
                    fOut = new FileOutputStream(file);

                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                    MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                }
                
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

            }
    }

    }
}
