package com.smartiecards;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smartiecards.network.APIClient;
import com.smartiecards.network.APIInterface;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment  extends Fragment {
    private static final String TAG = "BaseFragment";
    public APIInterface apiInterface  = APIClient.getClient().create(APIInterface.class);
    public Call<ResponseBody> call = null;

    public SavePref pref = new SavePref();

    public BaseAppCompactActivity baseAppCompactActivity ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        pref.SavePref(getActivity());
        baseAppCompactActivity = (BaseAppCompactActivity) getActivity();



        calldeviceCheckApiMethod();



//        if(pref.getLogin() == false){
//            Log.e(TAG, "false");
////            Intent i = new Intent(baseAppCompactActivity , MainActivity.class);
////            i.putExtra("keyPosition" , 1);
////            startActivity(i);
////            baseAppCompactActivity.finishAffinity();
////            baseAppCompactActivity.finish();
//        }else{
//            Log.e(TAG, "true");
//        }



    }








    private void calldeviceCheckApiMethod() {
        call = apiInterface.deviceCheck(pref.getId() , ""+Utility.getLocalIpAddress());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        Log.e(TAG , "responseCodeQQ "+responseCode);

                        JSONObject jsonObject = new JSONObject(responseCode);
                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                                pref.clear();
//                                Intent i = new Intent(baseAppCompactActivity , MainActivity.class);
//                                i.putExtra("keyPosition" , 1);
//                                startActivity(i);
//                                baseAppCompactActivity.finishAffinity();
//                                baseAppCompactActivity.finish();
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
    public void onDestroyView() {
        super.onDestroyView();
        if(call != null){
            call.cancel();
            call = null;
        }
    }
}
