package com.smartiecards.fcm;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.regex.Pattern;

/**
 * Created by AnaadIT on 3/30/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static SharedPreferences.Editor editor;
    public static SharedPreferences prefs;
    public  int i=0;

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh()
    {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//
//        //Displaying token on logcat
//        Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//        if ((refreshedToken != null || refreshedToken != "") && i == 0)
//            sendRegistrationToServer(refreshedToken);

    }

    private void sendRegistrationToServer(String token)
    {
        prefs = getApplicationContext().getSharedPreferences("regid",0);
        editor = prefs.edit();

        editor.putString("regid",token);
        editor.commit();

        String userId =prefs.getString("regid","");

        if(userId !="")
        {
           // Utils.toastTxt(String.valueOf(userId),Utils.context);
        }
        i=1;
    }
}
