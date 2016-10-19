package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ch.ethz.inf.vs.a2.sensor.SensorListener;
import ch.ethz.inf.vs.a2.solution.sensor.JsonSensor;
import ch.ethz.inf.vs.a2.solution.sensor.RawHttpSensor;
import ch.ethz.inf.vs.a2.solution.sensor.TextSensor;

import static android.R.attr.data;

public class REST_client extends AppCompatActivity implements SensorListener {

    private TextView temperature;
    private Spinner spinner;
    private Button button;

    private RawHttpSensor http = new RawHttpSensor();
    private TextSensor text = new TextSensor();
    private JsonSensor json = new JsonSensor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);

        //find textView, spinner & button on activity
        temperature = (TextView) findViewById(R.id.temp);
        spinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);

        //adding activity to list of sensors as listener
        http.registerListener(this);
        text.registerListener(this);
        json.registerListener(this);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayTemperature();
            }
        });


    }

    public void displayTemperature() {
        switch (spinner.getSelectedItem().toString()) {
            case "HTML":
                http.getTemperature();
                Toast.makeText(this, "Temperature updated using HTML",
                        Toast.LENGTH_SHORT).show();
                break;
            case "Plain text":
                text.getTemperature();
                Toast.makeText(this, "Temperature updated using plain text",
                        Toast.LENGTH_SHORT).show();
                break;
            case "JSON":
                json.getTemperature();
                Toast.makeText(this, "Temperature updated using JSON Object",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Temperature not updated",
                        Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onReceiveSensorValue(double value) {

        temperature.setText("Temperature is " + String.valueOf(value)+"°");

    }

    @Override
    public void onReceiveMessage(String message) {

    }
}
