package ch.ethz.inf.vs.a2.anicolus.webservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Server extends Service {
    public Server() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service", "stopped");
    }
}
