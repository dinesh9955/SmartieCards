package com.smartiecards.fcm;

import android.os.AsyncTask;
import android.util.Log;
/**
 * Created by AnaadIT on 9/25/2017.
 */

public class NotificationTask extends AsyncTask<String, String, String> {

    private static final String TAG = "NotificationTask";

    @Override
    protected String doInBackground(String... params) {
        //return WSConnector.getStringHTTPnHTTPSResponse(WSContants.REGISTER_DEVICE_ID+"email="+params[0]+"&device_id="+params[1]);

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "SSSDDDD "+s);
    }
}
