package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.RawHttpSensor;

public class REST_client extends AppCompatActivity implements SensorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);

        RawHttpSensor sensor = new RawHttpSensor();
        try {
            Log.d("temperature", String.valueOf(sensor.parseResponse(sensor.executeRequest())));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onReceiveSensorValue(double value) {

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
