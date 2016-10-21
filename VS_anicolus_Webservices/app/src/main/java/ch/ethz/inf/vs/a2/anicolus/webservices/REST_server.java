package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class REST_server extends AppCompatActivity {

    private String interfaceType = "eth0";
    private boolean serviceRunning;
    private Intent server;
    private TextView text;
    public static TextView address;
    public static Vibrator vib;
    public static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_server);

        address = (TextView) findViewById(R.id.address);

        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.sound);
        mp.setVolume(1.0f, 1.0f);

        text = (TextView) findViewById(R.id.serviceState);
        text.setText("Service Inactive.");
        server = new Intent(this, Server.class);
        server.putExtra("interface", interfaceType);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setText("Start Service");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceRunning) {
                    serviceRunning = true;
                    text.setText("Service Running.");
                    startService(server);
                }
            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setText("Stop Service");
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceRunning) {
                    serviceRunning = false;
                    text.setText("Service Stopped.");
                    stopService(server);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "called");
        if (serviceRunning) {
            serviceRunning = false;
            stopService(server);
        }
    }

}
