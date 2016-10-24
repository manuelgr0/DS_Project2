package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Alessandro on 20.10.2016.
 */

public class SocketInterface {

    // HTML shown on the root page.
    private String index =
            "<html>" +
               "<body>" +
                  "<h1>Welcome to our Webservices</h1>" +
                    "<a href=\"/0\">Light Sensor</a></br>" +
                    "<a href=\"/1\">Pressure Sensor</a></br>" +
                    "<a href=\"/2\">Vibrator</a></br>" +
                    "<a href=\"/3\">Play Sound</a></br>" +
               "</body>" +
            "</html>";

    public SocketInterface() {

    }

    public void handleRequest(Socket socket) throws IOException {
        byte[] b = new byte[socket.getInputStream().available()];
        socket.getInputStream().read(b); // read raw data from input stream
        String stream = new String(b, "UTF-8"); // convert raw data into readable format
        Log.d("input stream", stream);
        if (stream.length() < 7) { // request is to short and might get an OOB exception when calling substring
            socket.close();
            return;
        }
        String site = stream.substring(5,7);
        // Lightweight parsing begins here
        if (!stream.substring(0,3).equals("GET")) // wrong format, we only consider GET requests
            sendResponse(socket, "HTTP/1.1 404 Not Found\n\n<html><head><title>404 Not Found</title></head><body><h1>Not Found</h1></body></html>\n");
        else if (site.equals(" H")) { // root site
            String str = "HTTP/1.1 200 OK\n\n" + index + "\n";
            sendResponse(socket, str);
        }
        else if (site.equals("0 ")) {
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Light Sensor</h1>";
            str += Server.sensorVals[0] + " lx";
            str += "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("1 ")) {
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Pressure Sensor</h1>";
            str += Server.sensorVals[1] + " hPa";
            str += "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("2 ")) {
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Vibrator</h1>" +
                    "<a href=\"/21\">click to vibrate for 2 secs</a></br>" +
                    "<a href=\"/22\">click to vibrate for 5 secs</a></br>" +
                    "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("21")) {
            REST_server.vib.vibrate(2000);
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Vibrator</h1>Vibrating for 2 secs.</br>" +
                    "<a href=\"/21\">click to vibrate for 2 secs</a></br>" +
                    "<a href=\"/22\">click to vibrate for 5 secs</a></br>" +
                    "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("22")) {
            REST_server.vib.vibrate(5000);
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Vibrator</h1>Vibrating for 5 secs.</br>" +
                    "<a href=\"/21\">click to vibrate for 2 secs</a></br>" +
                    "<a href=\"/22\">click to vibrate for 5 secs</a></br>" +
                    "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("3 ")) {
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Play sound</h1>" +
                    "<a href=\"/31\">click to play sound</a></br>" +
                    "</body></html>\n";
            sendResponse(socket, str);
        }
        else if (site.equals("31")) {
            REST_server.mp.start();
            String str = "HTTP/1.1 200 OK\n\n<html><body><a href=\"/\">root</a><h1>Play sound</h1>Playing sound.</br>" +
                    "<a href=\"/31\">click to play sound</a></br>" +
                    "</body></html>\n";
            sendResponse(socket, str);
        }
        else { // requested site is not in scope
            sendResponse(socket, "HTTP/1.1 404 Not Found\n\n<html><head><title>404 Not Found</title></head><body><h1>Not Found</h1></body></html>\n");
        }
    }

    // Send response and immediately close socket -> Connectionless communication.
    public void sendResponse(Socket socket, String msg) throws IOException {
        socket.getOutputStream().write(msg.getBytes("UTF-8"));
        socket.close();
    }

}
