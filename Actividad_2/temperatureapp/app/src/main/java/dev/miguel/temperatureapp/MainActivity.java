package dev.miguel.temperatureapp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textViewTemperature;
    private TextView textViewMessage;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTemperatureSensorAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textViewTemperature = findViewById(R.id.textview_temperature);
        textViewMessage = findViewById(R.id.textview_message);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        } else {
            textViewTemperature.setText("Temperature sensor is not available.");
            isTemperatureSensorAvailable = false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        textViewTemperature.setText(sensorEvent.values[0] + " °C");
        if(sensorEvent.values[0] < -15){
            textViewTemperature.setTextColor(Color.CYAN);
            textViewMessage.setTextColor(Color.CYAN);
            textViewMessage.setText("Too cold, it's dangerous 🥶😵");
        }
        if(sensorEvent.values[0] > -15 && sensorEvent.values[0] < 0){
            textViewTemperature.setTextColor(Color.BLUE);
            textViewMessage.setTextColor(Color.BLUE);
            textViewMessage.setText("It's cold, bundle up 🧣🧤");
        }
        if(sensorEvent.values[0] > 0 && sensorEvent.values[0] < 15){
            textViewTemperature.setTextColor(Color.GREEN);
            textViewMessage.setTextColor(Color.GREEN);
            textViewMessage.setText("Cool weather 🍃");
        }
        if(sensorEvent.values[0] > 15 && sensorEvent.values[0] < 25){
            textViewTemperature.setTextColor(Color.YELLOW);
            textViewMessage.setTextColor(Color.YELLOW);
            textViewMessage.setText("Pleasant weather 😎");
        }
        if(sensorEvent.values[0] > 25 && sensorEvent.values[0] < 35){
            textViewTemperature.setTextColor(Color.parseColor("#FF9300"));
            textViewMessage.setTextColor(Color.parseColor("#FF9300"));
            textViewMessage.setText("It's hot 🕶️🩳🩴");
        }
        if(sensorEvent.values[0] > 35){
            textViewTemperature.setTextColor(Color.RED);
            textViewMessage.setTextColor(Color.RED);
            textViewMessage.setText("Too hot, it's dangerous 🥵😵");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(isTemperatureSensorAvailable){
            sensorManager
                    .registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(isTemperatureSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}