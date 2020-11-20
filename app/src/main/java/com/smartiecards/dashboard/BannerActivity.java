package com.smartiecards.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;
import com.smartiecards.account.ForgotPassword;
import com.smartiecards.home.FlashCardLists;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.util.ShowMsg;

import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/17/2018.
 */

public class BannerActivity extends BaseAppCompactActivity {

    final Timer t = new Timer();

    boolean booleanTimerStart = false;

    private static final String TAG =  "BannerActivity";
    int timeCounter = 0;

    ProgressBar progressBar;

    boolean booleanNext = false;

    String subjectId = "" , subjectName ="", slink = "",  classKey = "";


    ImageLoader imageLoader;
    DisplayImageOptions options;

    TextView textViewMessage, textViewTime;
    ImageView photoView, imageViewCross;

    RelativeLayout relativeLayoutBanner;

    @Override
    protected int getLayoutResource() {
        return R.layout.banner_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        photoView = findViewById(R.id.photo_view);

        imageViewCross = (ImageView) findViewById(R.id.cross);

        textViewMessage = (TextView) findViewById(R.id.textView67d57567);
        textViewTime = (TextView) findViewById(R.id.textView23534);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

        relativeLayoutBanner = (RelativeLayout) findViewById(R.id.relativeLayout_banner);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        try{
            imageLoader = ImageLoader.getInstance();

            imageLoader.init(ImageLoaderConfiguration.createDefault(BannerActivity.this));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.picture_default)
                    .showImageForEmptyUri(R.drawable.picture_default)
                    .showImageOnFail(R.drawable.picture_default)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
                    //  .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 2))
                    .build();
        }catch(Exception e){
            // Log.d(TAG, "myError11: "+e.getMessage());
        }



        Bundle bundle = getIntent().getExtras();

        subjectId = bundle.getString("key");
        classKey = bundle.getString("classKey");
        subjectName = bundle.getString("key2");





        boolean b = WSConnector.checkAvail(BannerActivity.this);
        if(b == true){
           // new BannerTask().execute(subjectId);

            callBannerApiMethod(subjectId);
        }
        if(b == false){
            new ShowMsg().createSnackbar(BannerActivity.this , "No network available");
        }



        relativeLayoutBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(booleanNext == false){
                    if(!slink.equalsIgnoreCase("")){
//                        if(!slink.startsWith("http") && !slink.startsWith("https")){
////                            Intent i = new Intent(Intent.ACTION_VIEW,
////                                    Uri.parse("http"+slink));
////                            startActivity(i);
//                        }

                        if (!slink.startsWith("https://") && !slink.startsWith("http://")){
                            slink = "http://" + slink;
                        }
                        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slink));
                        startActivity(openUrlIntent);
                        finish();
                        t.cancel();
                    }
                }else{
                }
                Log.e(TAG , "slinkslink "+slink);
            }
        });
        imageViewCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                t.cancel();
            }
        });


    }
    private void callBannerApiMethod(String key) {

        progressBar.setVisibility(View.VISIBLE);
//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        arrayListMash.add(MultipartBody.Part.createFormData("id", pref.getId()));

        call = apiInterface.ads(key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        JSONObject jsonObject = new JSONObject(responseCode);
                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                            String message = jsonObject.getString("message");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(jsonArray.length() > 0){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String photo = jsonObject1.getString("photo");
                                String comments = jsonObject1.getString("comments");
                                slink = jsonObject1.getString("slink");
                                String stime = jsonObject1.getString("stime");

                                imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+photo , photoView, options);
                                textViewMessage.setText(comments);

                                try{
                                    timeCounter = Integer.parseInt(stime);
                                }catch (Exception e){

                                }

                                LocationDD();

//


                            }

                        }else{
                            String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(BannerActivity.this , ""+message);

                            LocationDD();

//                    Intent intent = new Intent(BannerActivity.this , FlashCardLists.class);
//                    intent.putExtra("key", subjectId);
//                    intent.putExtra("classKey", classKey);
//                    intent.putExtra("key2", subjectName);
//                    startActivity(intent);
//                    finish();
                        }
                    }else{
                        new ShowMsg().createSnackbar(BannerActivity.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(BannerActivity.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                new ShowMsg().createSnackbar(BannerActivity.this, ""+t.getMessage());
            }
        });




    }


//
//
//    class BannerTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"ads.php?id="+params[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            progressBar.setVisibility(View.GONE);
//
//            try{
//                JSONObject jsonObject = new JSONObject(result);
//                String status = jsonObject.getString("status");
//                if(status.equals("1")){
//                    String message = jsonObject.getString("message");
//
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    if(jsonArray.length() > 0){
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                        String photo = jsonObject1.getString("photo");
//                        String comments = jsonObject1.getString("comments");
//                        slink = jsonObject1.getString("slink");
//                        String stime = jsonObject1.getString("stime");
//
//                        imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+photo , photoView, options);
//                        textViewMessage.setText(comments);
//
//                        try{
//                            timeCounter = Integer.parseInt(stime);
//                        }catch (Exception e){
//
//                        }
//
//                                LocationDD();
//
////
//
//
//                    }
//
//                }else{
//                    String message = jsonObject.getString("message");
////                    new ShowMsg().createSnackbar(BannerActivity.this , ""+message);
//
//                    LocationDD();
//
////                    Intent intent = new Intent(BannerActivity.this , FlashCardLists.class);
////                    intent.putExtra("key", subjectId);
////                    intent.putExtra("classKey", classKey);
////                    intent.putExtra("key2", subjectName);
////                    startActivity(intent);
////                    finish();
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//


    private void LocationDD() {

        booleanTimerStart = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewTime.setText(timeCounter+" seconds...");
            }
        });

        // TODO Auto-generated method stub
        //Declare the timer

        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      if(timeCounter != 0){
                                          timeCounter --;
                                          Log.e(TAG, "LocationDD1111 "+timeCounter);
                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                textViewTime.setText(timeCounter+" seconds...");
                                              }
                                          });
                                      }else{
                                          Log.e(TAG, "LocationDD2222 "+timeCounter);
                                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  textViewTime.setText(timeCounter+" seconds...");
                                              }
                                          });
                                          Log.e(TAG, "subjectId "+subjectId);
                                          Log.e(TAG, "classKey "+classKey);
                                          Log.e(TAG, "subjectName "+subjectName);
                                            Intent intent = new Intent(BannerActivity.this , FlashCardLists.class);
                                            intent.putExtra("key", subjectId);
                                            intent.putExtra("classKey", classKey);
                                            intent.putExtra("key2", subjectName);
                                            startActivity(intent);
                                            finish();
                                          booleanNext = true;
                                          t.cancel();
                                      }
                                  }
                              },
                0,
                1000 );


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(booleanTimerStart == true){
            t.cancel();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
