package com.smartiecards.account;

import android.os.AsyncTask;
import android.util.Log;

import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;

/**
 * Created by AnaadIT on 9/25/2017.
 */

public class NotificationTask extends AsyncTask<String, String, String> {

    private static final String TAG = "NotificationTask";

    @Override
    protected String doInBackground(String... params) {
        return WSConnector.getStringHTTPnHTTPSResponse(WSContants.REGISTER_DEVICE_ID+"user_id="+params[0]+"&token="+params[1]+"&type=android");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "SSSDDDD "+s);
    }
}
