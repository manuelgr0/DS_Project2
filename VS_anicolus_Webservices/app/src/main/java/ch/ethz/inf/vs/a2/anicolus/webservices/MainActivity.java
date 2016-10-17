package ch.ethz.inf.vs.a2.anicolus.webservices;

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

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });


    }
}
