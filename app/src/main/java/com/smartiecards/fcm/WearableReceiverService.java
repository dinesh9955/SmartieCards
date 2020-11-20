package com.smartiecards.fcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class WearableReceiverService extends IntentService {
String TAG = "WearableReceiverService";

    public WearableReceiverService(){
        super("WearableReceiverService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        msgReqAction(intent.getIntExtra(MyConstants.BROADCAST_DATA_REQ, 0));
//        WearableReceiver.completeWakefulIntent(intent);
        Log.e(TAG, "AAAXX");
        Intent serviceIntent = new Intent(getApplicationContext(), WearableReceiverService.class);
      //  startWastartWa

    }
}