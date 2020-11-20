package com.smartiecards.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

/**
 * Created by AnaadIT on 9/28/2017.
 */

public class OnClearFromRecentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");



//        Intent service = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);

        // Start the service, keeping the device awake while it is launching.
        //Log.i("SimpleWakefulReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
//        star
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();

//        Intent serviceIntent = new Intent(getApplicationContext(), WearableReceiverService.class);
//        startService(serviceIntent);

        //sendBroadcast(new Intent(this, WearableReceiverService.class));

        stopSelf();

    }
}