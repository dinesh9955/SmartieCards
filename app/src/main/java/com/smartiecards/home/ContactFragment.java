package com.smartiecards.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.smartiecards.BaseFragment;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.settings.ChangePassword;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class ContactFragment extends BaseFragment {

    EditText editTextFullName, editTextEmail, editTextSubject, editTextMessage;
    Button buttonSubmit;

  //  SavePref pref = new SavePref();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);



        editTextFullName = (EditText) view.findViewById(R.id.login_fullname);
        editTextEmail = (EditText) view.findViewById(R.id.login_email);
        editTextSubject = (EditText) view.findViewById(R.id.login_subject);
        editTextMessage = (EditText) view.findViewById(R.id.login_address);

        buttonSubmit = (Button) view.findViewById(R.id.button6546);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextFullName.getText().toString().length() == 0){
                    new ShowMsg().createSnackbar(getActivity() , "Enter fullname.");
                }else{
                    if(editTextEmail.getText().toString().length() == 0){
                        new ShowMsg().createSnackbar(getActivity() , "Enter Email.");
                    }else{
                        if(editTextSubject.getText().toString().length() == 0){
                            new ShowMsg().createSnackbar(getActivity() , "Enter Subject.");
                        }else{
                            if(editTextMessage.getText().toString().toString().length() == 0){
                                new ShowMsg().createSnackbar(getActivity() , "Enter Message.");
                            }else{
                                boolean b = WSConnector.checkAvail(getActivity());
                                if(b == true){
//                                    new ContactTask().execute(
//                                            editTextFullName.getText().toString(),
//                                            editTextEmail.getText().toString(),
//                                            editTextSubject.getText().toString(),
//                                            editTextMessage.getText().toString());

                                    callApiMethod();
                                }
                                if(b == false){
                                    new ShowMsg().createSnackbar(getActivity() , "No network available");
                                }
                            }
                        }
                    }
                }
            }
        });






        return view;
    }




    private void callApiMethod() {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.show();

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        MultipartBody.Part userid = MultipartBody.Part.createFormData("userid", pref.getId());
//        arrayListMash.add(userid);
//
//        MultipartBody.Part name = MultipartBody.Part.createFormData("name", editTextFullName.getText().toString());
//        arrayListMash.add(name);
//
//        MultipartBody.Part email = MultipartBody.Part.createFormData("email", editTextEmail.getText().toString());
//        arrayListMash.add(email);
//
//        MultipartBody.Part subject = MultipartBody.Part.createFormData("subject", editTextSubject.getText().toString());
//        arrayListMash.add(subject);
//
//        MultipartBody.Part message = MultipartBody.Part.createFormData("message", editTextMessage.getText().toString());
//        arrayListMash.add(message);

        call = apiInterface.contactus(pref.getId(),
                editTextFullName.getText().toString(),
                editTextEmail.getText().toString(),
                editTextSubject.getText().toString(),
                editTextMessage.getText().toString());

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
                                new ShowMsg().createSnackbar(getActivity() , ""+message);

                                editTextFullName.setText("");
                                editTextEmail.setText("");
                                editTextSubject.setText("");
                                editTextMessage.setText("");
                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(getActivity(), ""+message);
                            }
                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(getActivity(), "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(getActivity(), ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });
    }
    class ContactTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(getActivity());
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"contactus.php?name="+params[0]+"&email="+params[1]+"&subject="+params[2]+"&message="+params[3]);
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
                    new ShowMsg().createSnackbar(getActivity() , ""+message);

                    editTextFullName.setText("");
                    editTextEmail.setText("");
                    editTextSubject.setText("");
                    editTextMessage.setText("");
                }else{
                    String message = jsonObject.getString("message");
                    new ShowMsg().createSnackbar(getActivity(), ""+message);
                }
            }catch (Exception e){

            }
        }
    }



}
