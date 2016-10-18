package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.RawHttpSensor;

public class REST_client extends AppCompatActivity implements SensorListener{

    private TextView temperature;
    private RawHttpSensor http = new RawHttpSensor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);

        //find textView on activity
       temperature = (TextView) findViewById(R.id.temp);

        //adding to list of HttpSensor listeners
        http.registerListener(this);



    }

    @Override
    public void onReceiveSensorValue(double value) {

        temperature.setText(String.valueOf(value));
        Log.d("teeasfsdf","sdfsdfsdk");

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
