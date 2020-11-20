package com.smartiecards.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
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

public class ProfileActivity extends BaseAppCompactActivity{

    private static final int REFRESH_PROFILE_CODE = 1120;

    private static final String TAG = "ProfileActivity";

   // SavePref pref = new SavePref();

    ProgressBar progressBar;


    ImageView imageViewProfilePic;

    EditText editTextFName, editTextLName, editTextUserName, editTextEmail, editTextAddress, editTextCity, editTextCountry;

    Button buttonDOB;


    ImageLoader imageLoader;
    DisplayImageOptions options;

    String profilepic = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.profile_activity;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        imageViewProfilePic = (ImageView) findViewById(R.id.profile_image);

        editTextFName = (EditText) findViewById(R.id.login_fname);
        editTextLName = (EditText) findViewById(R.id.login_lname);
        editTextUserName = (EditText) findViewById(R.id.login_username);
        editTextEmail = (EditText) findViewById(R.id.login_email);
        buttonDOB = (Button) findViewById(R.id.button678789789);
        editTextAddress = (EditText) findViewById(R.id.login_address);
        editTextCity = (EditText) findViewById(R.id.login_city);
        editTextCountry = (EditText) findViewById(R.id.login_country);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText(getString(R.string.profile));


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        //pref.SavePref(ProfileActivity.this);



        try{
            imageLoader = ImageLoader.getInstance();

            imageLoader.init(ImageLoaderConfiguration.createDefault(ProfileActivity.this));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.malecostume_64)
                    .showImageForEmptyUri(R.drawable.malecostume_64)
                    .showImageOnFail(R.drawable.malecostume_64)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
                    // .displayer(new CircleBitmapDisplayer(Color.parseColor("#ff0000"), 2))
                    .build();
        }catch(Exception e){
            Log.d(TAG, "myError11: "+e.getMessage());
        }



        imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this , ImageZoom.class);
                intent.putExtra("imageLink" , profilepic);
                startActivity(intent);
            }
        });


        updateProfile();



    }

    protected void setMenuBackground(){
        // Log.d(TAG, "Enterting setMenuBackGround");
        getLayoutInflater().setFactory( new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context,
                                     AttributeSet attrs) {
                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        /* The background gets refreshed each time a new item is added the options menu.
                         * So each time Android applies the default background we need to set our own
                         * background. This is done using a thread giving the background change as runnable
                         * object */
                        new Handler().post(new Runnable() {
                            public void run () {
                                // sets the background color
                                view.setBackgroundResource(R.color.white);
                                // sets the text color
                                ((TextView) view).setTextColor(Color.WHITE);
                                // sets the text size
                                ((TextView) view).setTextSize(18);
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
            }});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        //  setMenuBackground();
        return true;
        //  return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit){
            Intent intent = new Intent(ProfileActivity.this , EditProfileActivity.class);
            startActivityForResult(intent, REFRESH_PROFILE_CODE);
        }
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (requestCode == REFRESH_PROFILE_CODE ) {
            updateProfile();
        }

        if (requestCode == REFRESH_PROFILE_CODE && resultCode == Activity.RESULT_CANCELED) {

        }


////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }






    private void updateProfile() {
        boolean b = WSConnector.checkAvail(ProfileActivity.this);
        if(b == true){
            if(!pref.getId().equalsIgnoreCase("")){
//                new ProfileTask().execute(pref.getId());
                callProfileMethod();
            }
        }else{
            // new ShowMsg().createDialog(MainActivity.this , "No internet connection.");
        }
    }



    private void callProfileMethod() {

        progressBar.setVisibility(View.VISIBLE);
//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        MultipartBody.Part userid = MultipartBody.Part.createFormData("userid", pref.getId());
//        arrayListMash.add(userid);

        call = apiInterface.profileinfo(pref.getId());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseCode);
                        //JSONObject response = jsonObject.getJSONObject("response");

                        String status = jsonObject.getString("status");
                        if(status.equals("1")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String id = data.getString("id");
                            String first_name = data.getString("first_name");
                            String last_name = data.getString("last_name");
                            String username = data.getString("username");
                            String email = data.getString("email");
                            String address = data.getString("address");
                            String dob = data.getString("dob");
                            String city = data.getString("city");
                            String country = data.getString("country");

                            profilepic = data.getString("profilepic");

                            Log.e(TAG , "profilepic "+WSContants.BASE_MAIN__PROFILE_URL+profilepic);

                            editTextFName.setText(""+first_name.toString());
                            editTextLName.setText(""+last_name.toString());
                            editTextUserName.setText(""+username.toString());
                            editTextEmail.setText(""+email.toString());
                            editTextAddress.setText(""+address.toString());
                            buttonDOB.setText(""+dob.toString());
                            editTextCity.setText(""+city.toString());
                            editTextCountry.setText(""+country.toString());

                            if (profilepic.equals("")) {
                                String imageUri = "drawable://" + R.drawable.malecostume_128;
                                imageLoader.displayImage(imageUri, imageViewProfilePic, options);
                            } else {

                                imageLoader.displayImage(profilepic, imageViewProfilePic, options);
                            }

                        }


                        }else{
                        new ShowMsg().createSnackbar(ProfileActivity.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(ProfileActivity.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                new ShowMsg().createSnackbar(ProfileActivity.this, ""+t.getMessage());
            }
        });




    }



//
//    class ProfileTask extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"profileinfo.php?userid="+params[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            progressBar.setVisibility(View.GONE);
//
//            try{
//                JSONObject jsonObject = new JSONObject(s);
//                //JSONObject response = jsonObject.getJSONObject("response");
//
//                String status = jsonObject.getString("status");
//                if(status.equals("1")){
//                    JSONObject data = jsonObject.getJSONObject("data");
//                    String id = data.getString("id");
//                    String first_name = data.getString("first_name");
//                    String last_name = data.getString("last_name");
//                    String username = data.getString("username");
//                    String email = data.getString("email");
//                    String address = data.getString("address");
//                    String dob = data.getString("dob");
//                    String city = data.getString("city");
//                    String country = data.getString("country");
//
//                    profilepic = data.getString("profilepic");
//
//                    Log.e(TAG , "profilepic "+WSContants.BASE_MAIN__PROFILE_URL+profilepic);
//
//                    editTextFName.setText(""+first_name.toString());
//                    editTextLName.setText(""+last_name.toString());
//                    editTextUserName.setText(""+username.toString());
//                    editTextEmail.setText(""+email.toString());
//                    editTextAddress.setText(""+address.toString());
//                    buttonDOB.setText(""+dob.toString());
//                    editTextCity.setText(""+city.toString());
//                    editTextCountry.setText(""+country.toString());
//
//                    if(profilepic.equals("")){
//                        String imageUri = "drawable://" + R.drawable.malecostume_128;
//                        imageLoader.displayImage(imageUri, imageViewProfilePic, options);
//                    }else{
//
//                        imageLoader.displayImage(profilepic, imageViewProfilePic, options);
//                    }
//
//
//
//
//                }else{
//
//                }
//
//            }catch (Exception e){
//
//            }
//            Log.e(TAG , "onPostExecute "+s);
//        }
//    }
//




}
