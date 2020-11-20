package com.smartiecards.account;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//import com.google.firebase.FirebaseApp;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessaging;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Splash extends BaseAppCompactActivity{
	private static final String TAG = "Splash";

	public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1999;

	@Override
	protected int getLayoutResource() {
		return R.layout.splash;
	}

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.drawable.launch);
//		setContentView(R.layout.splash);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	     .detectAll()
	     .penaltyLog()
	     .build();
	    StrictMode.setThreadPolicy(policy);
//		FirebaseMessaging.getInstance().subscribeToTopic("topic");
//		String token = FirebaseInstanceId.getInstance().getToken();
//		Log.e("TOKENA", token);

//
//		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//		//Displaying token on logcat
//		Log.e(TAG, "Refreshed token: " + refreshedToken);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(Utility.checkAndRequestPermissions(Splash.this, REQUEST_ID_MULTIPLE_PERMISSIONS)) {
					getLogin();
				}else{
				}
			}
		}, 1000);



//		Dialog progDailog = new ShowMsg().createCustomProgressbarDialog(Splash.this);
//		progDailog.setCancelable(false);
//		progDailog.show();



	}

	private void getLogin() {
//		SavePref pref = new SavePref();
//		pref.SavePref(Splash.this);
//		if(!pref.getId().equals("")){
			startActivity(new Intent(Splash.this, MainActivity.class));
			Splash.this.finish();
//		}else {
////			startActivity(new Intent(Splash.this, Login.class));
////			Splash.this.finish();
//		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		Log.d(TAG, "Permission callback called-------");
		switch (requestCode) {
			case REQUEST_ID_MULTIPLE_PERMISSIONS: {
				if(Utility.checkAdditionPermissionsCheck(Splash.this, permissions, grantResults, REQUEST_ID_MULTIPLE_PERMISSIONS)) {
					getLogin();
				}
			}
		}

	}

}
