package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class Server extends Service {
    public Server() {
    }

    private Enumeration<NetworkInterface> enumeration;
    private NetworkInterface ni;
    private String interfaceType;
    private ServerSocket serverSocket;
    private Socket socket;
    private int port = 8088;
    private InetAddress inet;
    private boolean closed = true;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "started");

        interfaceType = (String) intent.getExtras().get("interface");

        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (enumeration.hasMoreElements()) {
            ni = enumeration.nextElement();
            if (ni.getName().equals(interfaceType))
                break;
        }
        Log.d("Interface chosen", ni.getName());
        Enumeration<InetAddress> en = ni.getInetAddresses();
        while (en.hasMoreElements()) {
            InetAddress temp = en.nextElement();
            Log.d("address", temp.toString());
            if (temp.getAddress().length == 4)
                inet = temp;
        }

        try {
            serverSocket = new ServerSocket(port, 50, inet);
            closed = false;
            Log.d("server", String.valueOf(serverSocket.getLocalSocketAddress()));
                Thread t = new Thread(new Runnable() { // create thread handling network logistics
                    @Override
                    public void run() {
                        Log.d("waiting", inet.getHostAddress());
                        try {
                            socket = serverSocket.accept(); // wait for connection establishment
                            byte[] b = new byte[socket.getInputStream().available()];
                            socket.getInputStream().read(b, 0, socket.getInputStream().available()); // read raw data from input stream
                            String stream = new String(b, "UTF-8"); // convert raw data into readable format
                            Log.d("input stream", stream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("finished", "accept");
                    }
                });
                t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service", "stopped");
        try {
            if (!closed) {
                closed = true;
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
