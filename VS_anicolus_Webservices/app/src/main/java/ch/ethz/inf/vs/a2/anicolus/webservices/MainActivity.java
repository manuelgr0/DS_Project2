package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        final Intent intent1 = new Intent(this, REST_client.class);
        final Intent intent2 = new Intent(this, SOAP_client.class);
        final Intent intent3 = new Intent(this, REST_server.class);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }
}
