package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server extends Service implements SensorEventListener {
    public Server() {
    }

    private Enumeration<NetworkInterface> enumeration;
    private NetworkInterface ni;
    private String interfaceType;
    private ServerSocket serverSocket;
    private int port = 8088;
    private InetAddress inet;
    private boolean closed = true;
    private BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);
    private ThreadPoolExecutor tpe = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, workQueue);
    public static volatile float[] sensorVals = new float[2];
    private SensorManager sMgr;
    private Sensor light;
    private Sensor pressure;

    @Override
    public IBinder onBind(Intent intent) {
        // Do nothing here
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "started");

        // Get the required sensors.
        sMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        pressure = sMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);

        // Register listeners for particular sensor events.
        sMgr.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        sMgr.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);

        // check which network interface to use
        interfaceType = (String) intent.getExtras().get("interface");

        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // scan for desired network interface
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
            if (temp.getAddress().length == 4) // be sure to get the IPv4 address of the desired network interface
                inet = temp;
        }

        // show address to connect to on screen
        REST_server.address.setText("Please enter " + inet.getHostAddress() + ":" + port + " in browser.");

        try {
            serverSocket = new ServerSocket(port, 50, inet); // Instantiate a new server socket
            closed = false;
            Log.d("server", String.valueOf(serverSocket.getLocalSocketAddress()));
                Thread t = new Thread(new Runnable() { // create thread handling network logistics
                    @Override
                    public void run() {
                        Log.d("waiting", inet.getHostAddress());
                        try {
                            while (!closed) {
                                final Socket socket = serverSocket.accept(); // wait for connection establishment (blocking)
                                Runnable r = new Runnable() { // each client (in particular request) handled separately in an individual thread
                                    @Override
                                    public void run() {
                                        SocketInterface si = new SocketInterface();
                                        try {
                                            si.handleRequest(socket);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                tpe.execute(r); // pass the thread handling the request to our executor service
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("finished", "accept");
                    }
                });
                t.start(); // start thread handling network logistics
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

    // Handling sensor events
    // Write them to a volatile field in order to avoid data races
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_LIGHT)
            sensorVals[0] = event.values[0];
        else
            sensorVals[1] = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing here
    }
}
