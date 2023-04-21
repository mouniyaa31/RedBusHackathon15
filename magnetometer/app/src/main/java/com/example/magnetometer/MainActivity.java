package com.example.magnetometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView magnet;
    Sensor sensor;
    SensorManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        magnet = findViewById(R.id.magnet);

      sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sensor != null){
            sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        else {
            Toast.makeText(getApplicationContext(), "Not Available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float asimuth = Math.round(sensorEvent.values[0]);
        float pitch = Math.round(sensorEvent.values[1]);
        float roll = Math.round(sensorEvent.values[2]);

        double tesla = Math.sqrt((asimuth*asimuth)+(pitch*pitch)+(roll*roll));

    String text = String.format("%.0f",tesla);

    magnet.setText(text);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}