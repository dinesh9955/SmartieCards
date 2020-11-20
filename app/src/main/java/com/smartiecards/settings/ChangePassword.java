package com.smartiecards.settings;

import android.app.ProgressDialog;
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
import com.smartiecards.R;
import com.smartiecards.account.ForgotPassword;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 1/30/2018.
 */

public class ChangePassword extends BaseAppCompactActivity {

    EditText editTextOldPassword, editTextNewPassword, editTextConPassword;
    Button buttonSubmit;

    //SavePref pref = new SavePref();

    @Override
    protected int getLayoutResource() {
        return R.layout.change_password;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        //  setContentView(R.layout.forgot_password);


        editTextOldPassword = (EditText) findViewById(R.id.login_old_password);
        editTextNewPassword = (EditText) findViewById(R.id.login_new_password);
        editTextConPassword = (EditText) findViewById(R.id.login_con_password);

        buttonSubmit = (Button) findViewById(R.id.button6546);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Change Password");

      //  pref.SavePref(ChangePassword.this);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextOldPassword.getText().toString().length() == 0){
                    new ShowMsg().createSnackbar(ChangePassword.this , "Enter old password.");
                }else{
                    if(editTextNewPassword.getText().toString().length() == 0){
                        new ShowMsg().createSnackbar(ChangePassword.this , "Enter new password.");
                    }else{
                        if(editTextConPassword.getText().toString().length() == 0){
                            new ShowMsg().createSnackbar(ChangePassword.this , "Enter new confirm password.");
                        }else{
                            if(editTextNewPassword.getText().toString().equalsIgnoreCase(editTextConPassword.getText().toString())){
                                boolean b = WSConnector.checkAvail(ChangePassword.this);
                                if(b == true){
                                    callApiMethod();
//                                    new ChangePasswordTask().execute(
//                                            pref.getId() ,
//                                            editTextOldPassword.getText().toString(),
//                                            editTextNewPassword.getText().toString());
                                }
                                if(b == false){
                                    new ShowMsg().createSnackbar(ChangePassword.this , "No network available");
                                }
                            }else{
                                new ShowMsg().createSnackbar(ChangePassword.this , "Confirm password does not match.");
                            }
                        }
                    }
                }
            }
        });
    }

    private void callApiMethod() {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(ChangePassword.this);
        dialog.setCancelable(false);
        dialog.show();

        call = apiInterface.changePassword(pref.getId(),
                editTextOldPassword.getText().toString(),
                editTextNewPassword.getText().toString()
                );
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
                            String status = jsonObject.getString("status");

                            if(status.equals("1")){
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(ChangePassword.this , ""+message);

                                editTextOldPassword.setText("");
                                editTextNewPassword.setText("");
                                editTextConPassword.setText("");
                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(ChangePassword.this , ""+message);
                            }
                        }catch (Exception e){

                        }

                    }else{
                        //new ShowMsg().createSnackbar(ForgotPassword.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // new ShowMsg().createSnackbar(ForgotPassword.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
               // new ShowMsg().createSnackbar(ForgotPassword.this, ""+t.getMessage());
            }
        });
    }




//
//
//    class ChangePasswordTask extends AsyncTask<String, String, String> {
//        ProgressDialog dialog = new ShowMsg().createProgressDialog(ChangePassword.this);
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"change_password.php?id="+params[0]+"&oldpassword="+params[1]+"&newpassword="+params[2]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            dialog.dismiss();
//            try{
//                JSONObject jsonObject = new JSONObject(result);
//
//              //  JSONObject response = jsonObject.getJSONObject("response");
//                String status = jsonObject.getString("status");
//
//                if(status.equals("1")){
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(ChangePassword.this , ""+message);
//
//                    editTextOldPassword.setText("");
//                    editTextNewPassword.setText("");
//                    editTextConPassword.setText("");
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(ChangePassword.this , ""+message);
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
