package com.smartiecards;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;
//import com.smartiecards.fcm.OnClearFromRecentService;



public class Controller extends Application
{
     String stringData;

 	public static final String TAG = Controller.class
 			.getSimpleName();


 	private static Controller mInstance;

 	@Override
 	public void onCreate() {
 		super.onCreate();
 		mInstance = this;

		FirebaseApp.initializeApp(this);
		FacebookSdk.sdkInitialize(getApplicationContext());

		//startService(new Intent(this, OnClearFromRecentService.class));

		Log.e(TAG , "FirebaseApp");

 	}

 	public static synchronized Controller getInstance() {
 		return mInstance;
 	}


 	public String getStringData() {
 		return stringData;
 	}

 	public void setStringData(String stringData) {
 		this.stringData = stringData;
 	}



	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}



}