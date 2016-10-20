package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alessandro on 20.10.2016.
 */

public class SocketInterface {

    private String index =
            "<html>" +
               "<body>" +
                  "<h1>Welcome to our Webservices</h1>" +
                    "<a href=\"/0\">Light Sensor</a></br>" +
                    "<a href=\"/1\">Pressure Sensor</a></br>" +
                    "<a href=\"/2\">Actuator 1</a></br>" +
                    "<a href=\"/3\">Actuator 2</a></br>" +
               "</body>" +
            "</html>";

    public SocketInterface() {

    }

    public void handleRequest(Socket socket) throws IOException {
        byte[] b = new byte[socket.getInputStream().available()];
        socket.getInputStream().read(b); // read raw data from input stream
        String stream = new String(b, "UTF-8"); // convert raw data into readable format
        Log.d("input stream", stream);
        String site = stream.substring(5,7);
        if (!stream.substring(0,3).equals("GET")) // wrong format
            sendResponse(socket, "HTTP/1.1 404 Not Found\n\n\n\n\n<html><head><title>404 Not Found</title></head><body><h1>Not Found</h1></body></html>\n\n");
        else if (site.equals(" H")) { // root site
            String str = "HTTP/1.1 200 OK\n\n\n\n\n" + index + "\n\n";
            sendResponse(socket, str);
        }
        else if (site.equals("0 ")) {
            String str = "HTTP/1.1 200 OK\n\n\n\n\n<html><body><a href=\"/\">root</a><h1>Light Sensor</h1>";
            str += Server.sensorVals[0] + " lx";
            str += "</body></html>\n\n";
            sendResponse(socket, str);
        }
        else if (site.equals("1 ")) {
            String str = "HTTP/1.1 200 OK\n\n\n\n\n<html><body><a href=\"/\">root</a><h1>Pressure Sensor</h1>";
            str += Server.sensorVals[1] + " hPa";
            str += "</body></html>\n\n";
            sendResponse(socket, str);
        }
        else if (site.equals("2 ")) {
            String str = "HTTP/1.1 200 OK\n\n\n\n\n<html><body><a href=\"/\">root</a><h1>Actuator 1</h1></body></html>\n\n";
            sendResponse(socket, str);
        }
        else if (site.equals("3 ")) {
            String str = "HTTP/1.1 200 OK\n\n\n\n\n<html><body><a href=\"/\">root</a><h1>Actuator 2</h1></body></html>\n\n";
            sendResponse(socket, str);
        }
        else {
            sendResponse(socket, "HTTP/1.1 404 Not Found\n\n\n\n\n<html><head><title>404 Not Found</title></head><body><h1>Not Found</h1></body></html>\n\n");
        }
    }

    public void sendResponse(Socket socket, String msg) throws IOException {
        socket.getOutputStream().write(msg.getBytes("UTF-8"));
        socket.close();
    }

}
