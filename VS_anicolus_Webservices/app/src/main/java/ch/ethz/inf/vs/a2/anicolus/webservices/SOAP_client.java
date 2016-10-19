package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.XmlSensor;

public class SOAP_client extends AppCompatActivity implements SensorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap_client);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                XmlSensor sensor1 = new XmlSensor();
                sensor1.getTemperature();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
    }

    @Override
    public void onReceiveSensorValue(double value) {

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
