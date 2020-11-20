package com.smartiecards.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
//import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AnaadIT on 3/30/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "MyFirebaseMsgService";
    MediaPlayer mp;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        //Displaying data in log
        //It is optional

        Log.e(TAG, "From: " +"========================"+remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body:================= " + remoteMessage.getNotification().getBody().toString());


/*
       JSONObject json = new JSONObject(remoteMessage.getData());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody().toString());
*/


/*
        JSONObject json1= null;
        try
        {
            json1 = json.getJSONObject("message");
            Log.e(TAG, "Notification Message Body123456: " + json1.toString());

            String title=json1.getString("title").toString();
            String body=json1.getString("body").toString();
            sendNotification(title,body);
           // String jstring=remoteMessage.getData().get("data");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
*/

//        if (!remoteMessage.getData().get("data").toString().equalsIgnoreCase(""))
//        {
//            try
//            {
//                JSONObject json = new JSONObject(remoteMessage.getData().get("data"));
//
//                String title=json.getString("title").toString();
//                String body=json.getString("body").toString();
//
//                Log.e(TAG, "title Message========123456: " + title);
//                Log.e(TAG, "body  Message========1234567: " + body);
//
//                sendNotification(title,body);
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG , "onDestroy");
    }


    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG , "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG , "onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
    }
}