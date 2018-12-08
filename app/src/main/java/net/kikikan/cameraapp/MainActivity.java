package net.kikikan.cameraapp;

import android.Manifest;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.hardware.camera2.CameraDevice;


public class MainActivity extends AppCompatActivity implements OnClickListener{

    public SeekBar sb;
    public TextView delayTW;


    TextureView cameraView;
    TextureView.SurfaceTextureListener SurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width,height);

        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };



    Button takeButton;
    CameraDevice cameraDevice;
    CameraDevice.StateCallback cameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
                cameraDevice = camera;
        }

        @Override
        public void onDisconnected( CameraDevice camera) {
                camera.close();
                cameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            cameraDevice = null;
        }
    };


    private final int CAMERA_REQUEST_CODE = 100;
    public String CameraId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        setContentView(R.layout.activity_main);
        sb = findViewById(R.id.delaySeekBar);
        delayTW = findViewById(R.id.delaytextView);
        cameraView = findViewById(R.id.cameraView);
        takeButton = findViewById(R.id.takeButton);

        takeButton.setOnClickListener(this);
        sb.setOnSeekBarChangeListener(new SeekBarManager(this));
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(cameraView.isAvailable()){setupCamera(cameraView.getWidth(),cameraView.getHeight());}
        else{cameraView.setSurfaceTextureListener(SurfaceTextureListener);}


    }



    public void onClick(View v){




    }


    private void closeCamera(){
        if(cameraDevice != null){cameraDevice.close();
        cameraDevice = null;}
    }

    private void setupCamera(int width, int height){


        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {

            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)== CameraCharacteristics.LENS_FACING_FRONT){continue;}
                CameraId = cameraId;
                return;

            }
        } catch(CameraAccessException e){e.printStackTrace();}


    }

    private void connectCamera(){
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraManager.openCamera(CameraId,cameraStateCallback);

    }

}
