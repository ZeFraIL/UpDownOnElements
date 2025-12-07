package zeev.fraiman.updownonelements;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private TextView tvNumber;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int number = 0;
    private static final float SHAKE_THRESHOLD_POSITIVE = 2.5f; // Threshold for positive tilt (forward)
    private static final float SHAKE_THRESHOLD_NEGATIVE = -2.5f; // Threshold for negative tilt (backward)
    private boolean isCountingUp = false; // Flag to avoid multiple increments in one tilt
    private boolean isCountingDown = false; // Flag to avoid multiple decrements in one tilt

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = findViewById(R.id.tvNumber);
        tvNumber.setText("0");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            Toast.makeText(this, "Accelerometer not available", Toast.LENGTH_SHORT).show();
            return;
        }
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];

            // Increase the number if the forward tilt exceeds the threshold
            if (x > SHAKE_THRESHOLD_POSITIVE) {
                if (!isCountingUp) { // Increase only once per "event" of forward tilt
                    number++;
                    tvNumber.setText(String.valueOf(number));
                    isCountingUp = true;
                    isCountingDown = false; // Reset the flag for the other direction
                }
            }
            // Decrease the number if the backward tilt exceeds the threshold
            else if (x < SHAKE_THRESHOLD_NEGATIVE) {
                if (!isCountingDown) { // Decrease only once per "event" of backward tilt
                    number--;
                    tvNumber.setText(String.valueOf(number));
                    isCountingDown = true;
                    isCountingUp = false; // Reset the flag for the other direction
                }
            }
            // If the x value has returned to the "neutral" zone between the thresholds
            else if (x < (SHAKE_THRESHOLD_POSITIVE / 2) && x > (SHAKE_THRESHOLD_NEGATIVE / 2)) {
                isCountingUp = false;   // Reset the flags to be ready for the next tilt
                isCountingDown = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
