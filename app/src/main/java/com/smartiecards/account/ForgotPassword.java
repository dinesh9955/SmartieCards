package com.smartiecards.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.util.ShowMsg;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 10/9/2017.
 */

public class ForgotPassword extends BaseAppCompactActivity {

    EditText editTextEmail;
    Button buttonSubmit;

    @Override
    protected int getLayoutResource() {
        return R.layout.forgot_password;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

      //  setContentView(R.layout.forgot_password);


        editTextEmail = (EditText) findViewById(R.id.login_username);
        buttonSubmit = (Button) findViewById(R.id.button6546);
        editTextEmail = (EditText) findViewById(R.id.login_username);
        buttonSubmit = (Button) findViewById(R.id.button6546);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Reset Password");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextEmail.getText().toString().length() == 0){
                    new ShowMsg().createSnackbar(ForgotPassword.this , "Enter your email address.");
                }else{
                    boolean b = WSConnector.checkAvail(ForgotPassword.this);
                    if(b == true){
                        //new ResetTask().execute(editTextEmail.getText().toString());
                        callApiMethod();
                    }
                    if(b == false){
                        new ShowMsg().createSnackbar(ForgotPassword.this , "No network available");
                    }
                }
            }
        });



    }

    private void callApiMethod() {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(ForgotPassword.this);
        dialog.setCancelable(false);
        dialog.show();
//
//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        MultipartBody.Part email = MultipartBody.Part.createFormData("email", editTextEmail.getText().toString());
//        arrayListMash.add(email);


        call = apiInterface.forgotPassword(editTextEmail.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        try{
                            JSONObject jsonObject = new JSONObject(responseCode);
                            //  JSONObject response = jsonObject.getJSONObject("response");
                            String status = jsonObject.getString("status");

                            if(status.equals("1")){
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(ForgotPassword.this , ""+message);

                                editTextEmail.setText("");
                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(ForgotPassword.this , ""+message);
                            }

                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(ForgotPassword.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(ForgotPassword.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                new ShowMsg().createSnackbar(ForgotPassword.this, ""+t.getMessage());
            }
        });

    }
    class ResetTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(ForgotPassword.this);
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"forgot_password.php?email="+params[0]);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            try{
                JSONObject jsonObject = new JSONObject(result);
              //  JSONObject response = jsonObject.getJSONObject("response");
                String status = jsonObject.getString("status");

                if(status.equals("1")){
                    String message = jsonObject.getString("message");
                    new ShowMsg().createSnackbar(ForgotPassword.this , ""+message);

                    editTextEmail.setText("");
                }else{
                    String message = jsonObject.getString("message");
                    new ShowMsg().createSnackbar(ForgotPassword.this , ""+message);
                }
            }catch (Exception e){

            }
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
