package com.smartiecards.account;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.util.ShowMsg;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Demo3 extends BaseAppCompactActivity {

    private static final String TAG = "Demo3";
    Button button;

    @Override
    protected int getLayoutResource() {
        return R.layout.demo3;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setBackgroundDrawableResource(R.drawable.bg);

        button = (Button) findViewById(R.id.button4);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "setOnClickListener ");
//                callCountryApiMethod();
            }
        });

    }
    private void callCountryApiMethod() {

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        MultipartBody.Part email = MultipartBody.Part.createFormData("demo", "");
//        arrayListMash.add(email);
//
//        MultipartBody.Part subject = MultipartBody.Part.createFormData("password", editTextPassword.getText().toString());
//        arrayListMash.add(subject);


        call = apiInterface.getCountries();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();
//

                        Log.e(TAG, "responseCode "+responseCode);

//                        try{
//                            itemCountries = new ParsingHelper().getItemCountry(responseCode);
//                            spinnerCountry.setAdapter(new ArrayAdapter<String>(RegisterActivity.this , R.layout.item_list_regione_drop_down,
//                                    getWishIdArrayListtoStringArrayState(itemCountries)));
//
//                        }catch (Exception e){
//
//                        }

                    }else{
                        new ShowMsg().createSnackbar(Demo3.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(Demo3.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressBarCountry.setVisibility(View.GONE);
                Log.e(TAG, "responseCodeAAA "+t.getMessage());
                new ShowMsg().createSnackbar(Demo3.this, ""+t.getMessage());
            }
        });

    }
}
