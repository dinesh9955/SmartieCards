package com.smartiecards.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;
import com.smartiecards.BaseFragment;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 1/24/2018.
 */

public class LoginFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "LoginFragment";

    ImageView imageViewFB, imageViewGP;

    Button buttonLogin;
    TextView textViewForgotPassword;
   // SavePref pref = new SavePref();

    TextView textViewSignup;

    EditText editTextUserName , editTextPassword;

    CallbackManager callbackManager;

    public static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);


        textViewSignup = (TextView) view.findViewById(R.id.textView2353453);
        buttonLogin = (Button) view.findViewById(R.id.button6546);
        textViewForgotPassword = (TextView) view.findViewById(R.id.textView23423545);

        editTextUserName = (EditText) view.findViewById(R.id.login_username);
        editTextPassword = (EditText) view.findViewById(R.id.login_password);

        imageViewFB = (ImageView) view.findViewById(R.id.button656456746);
        imageViewGP = (ImageView) view.findViewById(R.id.button6654654);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

     //   pref.SavePref(getActivity());




        callbackManager = CallbackManager.Factory.create();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextUserName.getText().toString().length() == 0 || editTextPassword.getText().toString().length() == 0){
//                    new ShowMsg().createDialog(getActivity() , "All field is required.");
                    new ShowMsg().createSnackbar(getActivity() , "All field is required.");
                }else{
                    boolean b = WSConnector.checkAvail(getActivity());
                    if(b == true){
                        //new LoginTask().execute(editTextUserName.getText().toString(), editTextPassword.getText().toString());
                        callApiMethod();
                    }else{
                        new ShowMsg().createSnackbar(getActivity() , "No internet connection.");
                     //   new ShowMsg().createDialog(getActivity() , "No internet connection.");
                    }
                }
            }
        });




        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPassword.class);
                startActivity(intent);
            }
        });


        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });




        imageViewFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKeyHash();

                LoginManager.getInstance().logOut();

                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("basic_info", "email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>(){
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // new ShowMsg().createDialog(Login.this  , loginResult.getAccessToken() +" AAA "+ AccessToken.getCurrentAccessToken());
                        Log.e(TAG, "onSuccess");
                        RequestData();
                    }
                    @Override
                    public void onCancel() {
                        Log.e(TAG, "onCancel");
                        if(AccessToken.getCurrentAccessToken() != null){
                            //RequestData();
                        }
                    }
                    @Override
                    public void onError(FacebookException error) {
                    }
                });
            }
        });

        imageViewGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        return view;
    }


//4aawyAB9vmqN3uQ7FjRGTy

    private void callApiMethod() {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.show();

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        arrayListMash.add(MultipartBody.Part.createFormData("email", editTextUserName.getText().toString()));
//        arrayListMash.add(MultipartBody.Part.createFormData("password", editTextPassword.getText().toString()));


        call = apiInterface.login(editTextUserName.getText().toString(), editTextPassword.getText().toString(), Utility.getLocalIpAddress());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        Log.e(TAG , "XXXXXFFFFAAAA "+responseCode);


                        try{
                            final JSONObject jsonObject = new JSONObject(responseCode);
                            String status = jsonObject.getString("status");
                            if(status.equals("1")) {
                                String message = jsonObject.getString("message");
                                new ShowMsg().createToast(getActivity() ,""+message);

                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                String id = jsonObject1.getString("id");

                                Log.e(TAG , "XXXXXFFFF "+id);

                                pref.setId(id);

                                Intent intent = new Intent(getActivity() , MainActivity.class);
                                startActivity(intent);
                                getActivity().finishAffinity();
                                getActivity().finish();


                            }else {

                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(getActivity(), message);
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










    class LoginTask extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog= new ShowMsg().createProgressDialog(getActivity());
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"login.php?email="+params[0]+"&password="+params[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.d(TAG, "ssss "+s);

            try {

                final JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                if(status.equals("1")) {
                    String message = jsonObject.getString("message");
                    new ShowMsg().createToast(getActivity() ,""+message);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    String id = jsonObject1.getString("id");

                    pref.setId(id);

//                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//                    new NotificationTask().execute(pref.getId(), refreshedToken);


                    Intent intent = new Intent(getActivity() , MainActivity.class);
                    startActivity(intent);
                    getActivity().finishAffinity();
                    getActivity().finish();


                }else {

                    String message = jsonObject.getString("message");
                    new ShowMsg().createSnackbar(getActivity(), message);
                }


            }catch (Exception e){

            }

        }
    }







    private void getKeyHash() {
        PackageInfo info;
        try {
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName() , PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found111", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm111", e.toString());
        } catch (Exception e) {
            Log.e("exception111", e.toString());
        }
    }



    public void RequestData(){

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        System.out.println("Json getToken :"+accessToken.getToken());


        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                System.out.println("Json data :"+json);


                //   new ShowMsg().createDialog(getActivity(),  "data = "+json);


                try{
                    String id = json.getString("id");
                    String name = json.getString("name");
                    String email = json.getString("email");
//                    JSONObject picture = json.getJSONObject("picture");
//                    JSONObject data = picture.getJSONObject("data");
//                    String url = data.getString("url");

                    String url = "http://graph.facebook.com/"+id+"/picture?type=large";


                    if(name.contains(" ")){
                        String nameSplit [] = name.split(" ");
                        myIds(id, nameSplit[0], nameSplit[1], email, url, "fb");
                    }else{
                        myIds(id, name, "", email, url, "fb");
                    }

                }catch (Exception e){

                }
            }
        });


        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,email,picture,birthday,gender,hometown");
        parameters.putString("fields", "id,name,email,gender,birthday,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void signOut() {
        if(mGoogleApiClient != null){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // updateUI(false);
                    }
                });
        }

    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // updateUI(false);
                    }
                });
    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 64206){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

            //  new ShowMsg().createDialog(getActivity() , ""+result.isSuccess());
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        //new ShowMsg().createDialog(getActivity(),  "handleSignInResult = "+result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();


//            Gson gson = new Gson();
//            String json = gson.toJson(acct);
//            Log.d(TAG, "onPostExecuteitemAddProperty "+json);
            //new ShowMsg().createDialog(Interessi.this , "zzzzzzz "+json);


            // new ShowMsg().createDialog(getActivity(),  "handleSignInResult = "+acct.getPhotoUrl());

//            Log.e(TAG, "display name: " + acct.getDisplayName());

            String id = "";
            String personName = "";
            String personPhotoUrl = "";
            String email = "";
//
//            Log.e(TAG, "id::: "+id+" Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);


            if(acct.getId() != null){
                id = acct.getId();
            }

            if(acct.getDisplayName() != null){
                personName = acct.getDisplayName();
            }

            if(acct.getPhotoUrl() != null){
                personPhotoUrl = acct.getPhotoUrl().toString();
            }

            if(acct.getEmail() != null){
                email = acct.getEmail();
            }


            if(personName.contains(" ")){
                String string []= personName.split(" ");
                myIds(String.valueOf(id), string[0], string[1], email, personPhotoUrl, "google");
            }else{
                myIds(String.valueOf(id), personName, "", email, personPhotoUrl, "google");
            }



//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);
//
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    private void myIds(

        final String id,
        final String first_name,
        final String last_name,
        final String email2 ,
        final String imageUrl,
        final String type) {


            Log.d(TAG, "id:: " + id);
            Log.d(TAG, "first_name:: " + first_name);
            Log.d(TAG, "last_name:: " + last_name);
            Log.d(TAG, "email2:: " + email2);
            Log.d(TAG, "imageUrl:: " + imageUrl);
            Log.d(TAG, "type:: " + type);

//        if(email2.contains("@")){
            //String emailEE[] = email2.split("@");
//            new SocialTask().execute(first_name, last_name, first_name+"_"+last_name, email2, imageUrl);
//        }

            callSocialApiMethod(first_name, last_name, first_name+"_"+last_name, email2, imageUrl);


    }

    private void callSocialApiMethod(String first_name, String last_name, String s, String email2, String imageUrl) {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.show();

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        arrayListMash.add(MultipartBody.Part.createFormData("first_name", first_name));
//        arrayListMash.add(MultipartBody.Part.createFormData("last_name", last_name));
//        arrayListMash.add(MultipartBody.Part.createFormData("username", s));
//        arrayListMash.add(MultipartBody.Part.createFormData("email", email2));
//        arrayListMash.add(MultipartBody.Part.createFormData("profilepic", imageUrl));


        call = apiInterface.fbRegister(first_name, last_name, s, email2, imageUrl);
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

                            JSONObject jsonObjectResponse = jsonObject.getJSONObject("data");

                            String status = jsonObject.getString("status");
                            if(status.equals("1")){

                                String id = jsonObjectResponse.getString("id");
                                final String email = jsonObjectResponse.getString("email");
                                pref.SavePref(getActivity());

                                pref.setId(id);
                                pref.setEmail(email);

                                String message = jsonObject.getString("message");
                                new ShowMsg().createToast(getActivity(), ""+message);

//                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                        Log.e(TAG, "Refreshed token: " + refreshedToken);

                                showActivity();

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











    class SocialTask extends AsyncTask<String, String, String>{
        ProgressDialog progressDialog= new ShowMsg().createProgressDialog(getActivity());

        @Override
        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE+
//                    "firstname="+params[0]+
//                    "&lastname="+params[1]+
//                    "&email="+params[2]+
//                    "&profilepic="+params[3]+
//                    "&type="+params[4]);

//            if(params[4].equalsIgnoreCase("fb")){
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"fb_register.php?first_name="+params[0]+"&last_name="+params[1]+"&username="+params[2]+"&email="+params[3]+"&profilepic="+params[4]);
//                        "firstname="+params[0]+
//                        "&lastname="+params[1]+
//                        "&email="+params[2]+
//                        "&profilepic="+params[3]+
//                        "&type="+params[4]);
//            }
//            if(params[4].equalsIgnoreCase("google")){
//                return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE+
//                        "firstname="+params[0]+
//                        "&lastname="+params[1]+
//                        "&email="+params[2]+
//                        "&profilepic="+params[3]+
//                        "&type="+params[4]);
//            }

//            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "SocialLoginTask "+result);


                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONObject jsonObjectResponse = jsonObject.getJSONObject("data");

                    String status = jsonObject.getString("status");
                    if(status.equals("1")){

                        String id = jsonObjectResponse.getString("id");
                        final String email = jsonObjectResponse.getString("email");
                        pref.SavePref(getActivity());

                        pref.setId(id);
                        pref.setEmail(email);

                        String message = jsonObject.getString("message");
                        new ShowMsg().createToast(getActivity(), ""+message);

//                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                        Log.e(TAG, "Refreshed token: " + refreshedToken);

                        showActivity();

                    }else{
                        String message = jsonObject.getString("message");
                        new ShowMsg().createSnackbar(getActivity(), ""+message);
                    }
                } catch (Exception e) {

                }
        }
    }




    private void showActivity() {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//        new NotificationTask().execute(pref.getId(), refreshedToken);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // pref.clear();

                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);
                getActivity().finishAffinity();
                getActivity().finish();
            }
        }, 100);
    }



    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
