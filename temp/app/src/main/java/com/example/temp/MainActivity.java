package com.example.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textView;
    private Sensor temp;
    private boolean isTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.tv);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null)
        {
            temp=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemp=true;
        }
        else {
            textView.setText("temperature sensor isnt available");
            isTemp=false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
      textView.setText(sensorEvent.values[0]+"C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTemp){
            sensorManager.registerListener(this,temp,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isTemp){
            sensorManager.unregisterListener(this);
        }
    }
}