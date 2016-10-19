package ch.ethz.inf.vs.a2.solution.sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.ethz.inf.vs.a2.sensor.AbstractSensor;


public class TextSensor extends AbstractSensor {

    private String host = "vslab.inf.ethz.ch";
    private String path = "/sunspots/Spot1/sensors/temperature/";
    private int port = 8081;

    @Override
    public String executeRequest() throws Exception {

        //Set up the httpConnection
        URL url = new URL("http", host, port, path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "text/plain");

        //retrieve input stream
        InputStream input_stream = connection.getInputStream();

        //convert the input_stream to String
        return convertStreamToString(input_stream);


    }

    @Override
    public double parseResponse(String response) {
        return Double.valueOf(response);
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}
