package com.smartiecards.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;


import com.smartiecards.MainActivity;
import com.smartiecards.R;

import org.json.JSONObject;

public class MyReciever extends WakefulBroadcastReceiver
{

    private static final String TAG = "MyReciever";

    Context context = null;

    private static final String ACTION_REGISTRATION
            = "com.google.android.c2dm.intent.REGISTRATION";
    private static final String ACTION_RECEIVE
            = "com.google.android.c2dm.intent.RECEIVE";


    @Override
    public void onReceive(Context context11,Intent intent)
    {

        context = context11;



        String action = intent.getAction();
        for (String key : intent.getExtras().keySet()) {
            Log.d(
                    "TAG",
                    "{UniversalFCM}->onReceive: key->"
                            + key + ", value->" + intent.getExtras().get(key)
            );
        }


        String data = intent.getStringExtra("data");

                Log.d(
                "TAGGGString ",
                "{UniversalFCMQQQ}->"+data
        );

        String body11 = intent.getStringExtra("gcm.notification.body");

        Log.d(
                "TAGGGString ",
                "{UniversalFCMQQQ body11}->"+body11
        );


            try
            {
                JSONObject json = new JSONObject(data);

                String title=json.getString("title").toString();
                String body=json.getString("body").toString();

                Log.e(TAG, "title Message========123456: " + title);
                Log.e(TAG, "body  Message========1234567: " + body);

               // sendNotification(title,body);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


       // sendNotificationReward("Mango");
//
//        try{
//            JSONObject jsonObject = new JSONObject(data);
//            String code = jsonObject.getString("code");
//
//            if(code.equals("1")){
//                String data1 = jsonObject.getString("data1");
//
//                try{
//                    JSONObject jsonObject2 = new JSONObject(data);
//
//                    String status = jsonObject2.getString("status");
//
//                    if(status.equals("offer")){
//
//                        SavePref pref = new SavePref();
//                        pref.SavePref(context);
//
//                        //generateNotificationReward(context, data1);
//                        sendNotificationReward(data1);
//                    }
//
//                }catch(Exception e){
//
//                }
//
//            }else if(code.equals("2")){
//                String data1 = jsonObject.getString("data");
////                generateNotificationInbox(context, data1);
//                sendNotificationInbox(data1);
//            }
//            else if(code.equals("3")){
//                String data1 = jsonObject.getString("data");
//
//              //  generateNotificationRewardStar(context, data1);
//                sendNotificationRewardStar(data1);
//
//            }else{
//
//            }
//
//        }catch(Exception e){
//
//        }



        abortBroadcast();




    }




    //
//    private void sendNotification(String title,String body)
//    {
//        // Log.e(TAG, " Message Title123456: " +title);
//        // Log.e(TAG, " Message Body123456: " +body);
//
//
//        Intent intent = new Intent(context,Splash.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText(body);
//
//
//
//
//        // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        android.support.v7.app.NotificationCompat.Builder notificationBuilder=(android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.logo_noti)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setContentTitle(title)
//                .setAutoCancel(true)
//              //  .setSound(Uri.parse(url))
//                .setContentIntent(pendingIntent)
//                .setContentText(body);
//
//        Notification notification = notificationBuilder.build();
//
//
////        if (!loop.equalsIgnoreCase("") && !loop.equalsIgnoreCase("null"))
////        {
////            if (Integer.parseInt(loop)==2)
////            {
////                notification.flags = Notification.FLAG_INSISTENT;
////                notification.flags |= Notification.FLAG_AUTO_CANCEL;
////            }
////            else
////            {
////                notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
////                notification.flags |= Notification.FLAG_AUTO_CANCEL;
////            }
////        }
////        else
////        {
//            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
//            notification.flags |= Notification.FLAG_AUTO_CANCEL;
////        }
//
//        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,notification);
//    }
//



    private void sendNotificationReward(String messageBody) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo_icon)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }



//
//    private void sendNotificationInbox(String messageBody) {
//        Intent intent = new Intent(context, InboxNotificationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.logo_noti)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }


//    private void sendNotificationRewardStar(String messageBody) {
//        Intent intent = new Intent(context, OffersAndRewardsPager.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.logo_noti)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//    }


//    public static String getActionReceive() {
//
//        return ACTION_RECEIVE;
//    }



}