package ch.ethz.inf.vs.a2.solution.sensor;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import ch.ethz.inf.vs.a2.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.solution.http.HttpRawRequestImpl;

import static java.lang.System.in;


public class RawHttpSensor extends AbstractSensor {

    private String host = "vslab.inf.ethz.ch";
    private String path = "/sunspots/Spot1/sensors/temperature/";
    private int port = 8081;


    @Override
    public String executeRequest() throws Exception {


       System.out.println("executeRequest in");
        //create socket and bind to it
        Socket socket = new Socket(host, port);

        //create a request using our HttpRawRequest implementation
        String request = (new HttpRawRequestImpl()).generateRequest(host, port, path);

        //send request to server
        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        writer.print(request);
        writer.flush();

        //read response from server
        InputStream input_stream = socket.getInputStream();

        //convert InputStream to String
        String result = convertStreamToString(input_stream);

        System.out.println("lenght of response is " + result.length());

        input_stream.close();

        return result;

    }

    @Override
    public double parseResponse(String response) {
        //parse string filter out temperature
        int i = response.indexOf("getterValue");
        System.out.println(i);
        String temp = response.substring(i+13, i+18);
        System.out.println("temp is " + temp);
        return Double.valueOf(temp);
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
