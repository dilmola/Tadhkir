package com.example.tadhkirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class QiblatActivity extends AppCompatActivity implements SensorEventListener{


    private ImageView imageView;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float alan = 0f;
    private float currentalan = 0f;
    private SensorManager mSensorManager;
    ImageButton previousbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiblat);

        imageView = findViewById(R.id.compass);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);



        previousbtn = findViewById(R.id.back_button);

        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();;
        mSensorManager.registerListener((SensorEventListener) this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_GAME);

        mSensorManager.registerListener((SensorEventListener) this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha= 0.97f;
        synchronized (this){
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                mGravity[0] = alpha*mGravity[0]+(1-alpha)*sensorEvent.values[0];
                mGravity[1] = alpha*mGravity[1]+(1-alpha)*sensorEvent.values[1];
                mGravity[2] = alpha*mGravity[2]+(1-alpha)*sensorEvent.values[2];

            }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            {
                mGeomagnetic[0] = alpha*mGeomagnetic[0]+(1-alpha)*sensorEvent.values[0];
                mGeomagnetic[1] = alpha*mGeomagnetic[1]+(1-alpha)*sensorEvent.values[1];
                mGeomagnetic[2] = alpha*mGeomagnetic[2]+(1-alpha)*sensorEvent.values[2];
            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                alan = (float)Math.toDegrees(orientation[0]);
                alan = (alan+360)%360;

                Animation anim = new RotateAnimation(-currentalan,-alan,Animation.RELATIVE_TO_SELF,
                        0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                currentalan = alan;

                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                imageView.startAnimation(anim);

            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}