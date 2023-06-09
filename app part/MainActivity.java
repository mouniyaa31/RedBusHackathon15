// Gravity sensor : accelerometer
// Proximity sensor : Value will be set if object exceed the minimum range(10cm).
// Barometer : Our height and environment air pressure.
// Luminosity : Light sensor

package com.example.hackathon1;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
{
    TextView x, y, z, p, b, l;
    SensorManager sm; // It is a service which we can get using getSystemService() method
    SensorManager sm1;
    FirebaseDatabase db;
    DatabaseReference ref;
    DatabaseReference child1;
    DatabaseReference child2;
    DatabaseReference child3;
    DatabaseReference child4;
    DatabaseReference child5;
    DatabaseReference child6;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Accelerometer");
        x = findViewById(R.id.x);
        y = findViewById(R.id.y);
        z = findViewById(R.id.z);
        p = findViewById(R.id.p);
        b = findViewById(R.id.b);
        l = findViewById(R.id.l);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm1 = (SensorManager) getSystemService(SENSOR_SERVICE);

        db = FirebaseDatabase.getInstance(); // Returns object of entire database
        // ref = db.getReference();  --> Returns root node address
        // ref = db.getReference(String childpath) --> Returns passed child node path
        ref = db.getReference();
        child1 = db.getReference("Accelerometer x : ");
        child2 = db.getReference("Accelerometer y : ");
        child3 = db.getReference("Accelerometer z :");
        child4 = db.getReference("Proximity : ");
        child5 = db.getReference("Luminosity : ");
        child6 = db.getReference("Barometer : ");

        String X = x.getText().toString();
        String Y = y.getText().toString();
        String Z = z.getText().toString();
        String Pro = p.getText().toString();


        if(sm == null) // Normally every device will have sensor in it so this is not necessary
        {
            Toast.makeText(this, "Sensor service is not detected in your phone.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Sensor s, s1, s2, s3;
            s  = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            s1 = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            s2 = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
            s3 = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

            if(s == null)
            {
                Toast.makeText(this, "Accelerometer sensor not supported", Toast.LENGTH_SHORT).show();
                finish();
            }

            if(s1 == null)
            {
                Toast.makeText(this, "Proximity sensor not supported", Toast.LENGTH_SHORT).show();
                finish();
            }

            if(s3 == null)
            {
                Toast.makeText(this, "Luminosity or light sensor not supported", Toast.LENGTH_SHORT).show();
                finish();
            }

            if(s2 == null)
            {
                Toast.makeText(this, "Barometer or pressure sensor not supported", Toast.LENGTH_SHORT).show();
                b.setText("Pressure : not supported");
            }

            // registerListener(SensorEventListener listener, Sensor sensor, int delay)
            sm.registerListener(new SensorEventListener()
            {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) // x, y, z will be in values[] array in this method
                {
                    float x1 = sensorEvent.values[0];
                    float y1 = sensorEvent.values[1];
                    float z1 = sensorEvent.values[2];

                    x.setText("X val : " + x1);
                    y.setText("Y val : " + y1);
                    z.setText("Z val : " + z1);

                    String x = Float.toString(x1);
                    String y = Float.toString(y1);
                    String z = Float.toString(z1);
                    child1.setValue(x);
                    child2.setValue(y);
                    child3.setValue(z);
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            }, s, SensorManager.SENSOR_DELAY_NORMAL);

            sm1.registerListener(new SensorEventListener()
            {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) // x, y, z will be in values[] array in this method
                {
                    if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
                    {
                        float val = sensorEvent.values[0];
                        p.setText("Proximity : " + val);

                        if(val > 0)
                        {
                            Toast.makeText(MainActivity.this, "Object is far...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Object is near...", Toast.LENGTH_SHORT).show();
                        }
                        String prox = Float.toString(val);
                        child4.setValue(prox);
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            }, s1, SensorManager.SENSOR_DELAY_NORMAL);

            sm1.registerListener(new SensorEventListener()
            {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) // x, y, z will be in values[] array in this method
                {
                    if(sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE)
                    {
                        float pe = sensorEvent.values[0];
                        @SuppressLint("DefaultLocale") String pre = String.format("%3f mbar", pe);
                        b.setText(pre);
                        String press = Float.toString(pe);
                        child4.setValue(press);
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            }, s2, SensorManager.SENSOR_DELAY_NORMAL);

            sm1.registerListener(new SensorEventListener()
            {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) // x, y, z will be in values[] array in this method
                {
                    if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
                    {
                        float li = sensorEvent.values[0];
                        l.setText("Luminosity : " + li);
                        String Lumi = Float.toString(li);
                        child5.setValue(Lumi);
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {}
            }, s3, SensorManager.SENSOR_DELAY_NORMAL);

        }

    }
}
