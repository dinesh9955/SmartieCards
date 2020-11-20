package com.smartiecards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.smartiecards.network.APIClient;
import com.smartiecards.network.APIInterface;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.EasyFontsCustom;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by AnaadIT on 10/5/2017.
 */

public abstract class BaseAppCompactActivity extends AppCompatActivity {

    private static final String TAG = "BaseAppCompactActivity";


    public APIInterface apiInterface  = APIClient.getClient().create(APIInterface.class);
    public Call<ResponseBody> call = null;


    public SavePref pref = new SavePref();

    AppBarLayout appBarLayout;

    public TextView textViewTitleBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         pref.SavePref(BaseAppCompactActivity.this);


        setTheme(R.style.AppThemeNoTitleBar);




//        if(pref.getSwitch().equals("landlord")){
//            setTheme(R.style.AppThemeNoTitleBar);
//        }else{
//            setTheme(R.style.AppThemeNoTitleBarBlue);
//        }

        setContentView(getLayoutResource());



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        if(getLayoutResource() == R.layout.splash ||
                getLayoutResource() == R.layout.banner_activity ||
                getLayoutResource() == R.layout.image_zoom){

        }else{
            textViewTitleBar = (TextView) findViewById(R.id.textView_title);
//            textViewTitleBar.setSelected(true);
           // textViewTitleBar.setTypeface(EasyFontsCustom.avenirnext_TLPro_Demi(BaseAppCompactActivity.this));
        }


//        if(Utility.LOGIN == false){
            calldeviceCheckApiMethod();
//        }else{
//
//        }





    }


    protected abstract int getLayoutResource();






    private void calldeviceCheckApiMethod() {
        call = apiInterface.deviceCheck(pref.getId() , ""+Utility.getLocalIpAddress());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();
                        Log.e(TAG , "responseCodeXX "+responseCode);

                        JSONObject jsonObject = new JSONObject(responseCode);
                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                                pref.clear();
//                                Intent i = new Intent(BaseAppCompactActivity.this , MainActivity.class);
//                                i.putExtra("keyPosition" , 1);
//                                startActivity(i);
//                                finishAffinity();
//                                finish();

                           //MainActivity



                            //switchPosition




                        }else{
                        }
                    }else{
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });




    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(call != null){
            call.cancel();
            call = null;
        }
    }




}
