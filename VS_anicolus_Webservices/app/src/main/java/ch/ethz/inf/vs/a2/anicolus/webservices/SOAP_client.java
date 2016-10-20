package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.SoapSensor;
import ch.ethz.inf.vs.a2.solution.sensor.XmlSensor;

public class SOAP_client extends AppCompatActivity implements SensorListener {

    TextView text;
    XmlSensor xml = new XmlSensor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap_client);

        text = (TextView)findViewById(R.id.temp);

        //register this activity as listener for SOAP sensors
        xml.registerListener(this);


        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                xml.getTemperature();
                Toast.makeText(getApplicationContext(), "Temperature updated using manual SOAP", Toast.LENGTH_SHORT).show();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                xml.getTemperature();
                Toast.makeText(getApplicationContext(), "Temperature updated using manual SOAP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onReceiveSensorValue(double value) {
        text.setText("Temperature is " + String.valueOf(value)+"°");
    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
