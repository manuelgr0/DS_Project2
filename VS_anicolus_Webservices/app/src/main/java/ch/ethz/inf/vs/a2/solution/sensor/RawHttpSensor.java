package ch.ethz.inf.vs.a2.solution.sensor;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import ch.ethz.inf.vs.a2.sensor.AbstractSensor;
import ch.ethz.inf.vs.a2.solution.http.HttpRawRequestImpl;

import static java.lang.System.in;


public class RawHttpSensor extends AbstractSensor {

    private String host = "vslab.inf.ethz.ch";
    private String path = "/sunspots/Spot1/sensors/temperature/";
    private int port = 80;


    @Override
    public String executeRequest() throws Exception {

        //create socket and bind to it
        Socket socket = new Socket(host, port);

        //create a request using our HttpRawRequest implementation
        String request = (new HttpRawRequestImpl()).generateRequest(host, port, path);

        //send request to server
        PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        writer.print(request);

        //read response from server
        InputStream input_stream = socket.getInputStream();

        return input_stream.toString();

    }

    @Override
    public double parseResponse(String response) {
        //parse string filter out temperature
        int i = response.indexOf("getterValue");
        String temp = response.substring(i+13, i+18);
        return Double.valueOf(temp);
    }
}
