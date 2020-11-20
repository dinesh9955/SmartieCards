package com.smartiecards.account;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class EditProfileActivity extends BaseAppCompactActivity{

  //  public static final int REFRESH_PROFILE_CODE_BACK = 1121;
  private static final String TAG = "EditProfileActivity";

//  SavePref pref = new SavePref();




  ProgressBar progressBar;


  ImageView imageViewProfilePic;

  EditText editTextFName, editTextLName, editTextUserName, editTextEmail, editTextAddress , editTextCity;

  Button buttonDOB, buttonUpdate, buttonUploadPicture;

  private SimpleDateFormat dateFormatter;
  private DatePickerDialog fromDatePickerDialogOne;

  String birth_date = "";




  String anotherCarUrl = "";

  DisplayImageOptions options;
  ImageLoader imageLoader;

  private static final int IMAGE_GALLERY_11 = 110;
  private static final int IMAGE_CAMERA_11 = 210;

  boolean realGalleryCamera = false;



  Spinner spinnerCountry;
  ProgressBar progressBarCountry;


  ArrayList<ItemCountry> itemCountries = new ArrayList<ItemCountry>();

  String stringCountry = "";


  @Override
  protected int getLayoutResource() {
    return R.layout.edit_profile_activity;
  }


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setBackgroundDrawableResource(R.drawable.bg);



    imageViewProfilePic = (ImageView) findViewById(R.id.profile_image);
    buttonUploadPicture = (Button) findViewById(R.id.button6546324235);

    editTextFName = (EditText) findViewById(R.id.login_fname);
    editTextLName = (EditText) findViewById(R.id.login_lname);
    editTextUserName = (EditText) findViewById(R.id.login_username);
    editTextEmail = (EditText) findViewById(R.id.login_email);
    buttonDOB = (Button) findViewById(R.id.button678789789);
    editTextAddress = (EditText) findViewById(R.id.login_address);

    progressBar = (ProgressBar) findViewById(R.id.progressBar1444);


    editTextCity = (EditText) findViewById(R.id.login_city);

    spinnerCountry = (Spinner) findViewById(R.id.spinner645657);
    progressBarCountry = (ProgressBar) findViewById(R.id.progressBar3657475);


    buttonUpdate = (Button) findViewById(R.id.button6546);



    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
    textViewTitleBar.setText(getString(R.string.edit_profile));


    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build();
    StrictMode.setThreadPolicy(policy);

//    pref.SavePref(EditProfileActivity.this);



    try{
      imageLoader = ImageLoader.getInstance();

      imageLoader.init(ImageLoaderConfiguration.createDefault(EditProfileActivity.this));
      options = new DisplayImageOptions.Builder()
              .showImageOnLoading(R.drawable.malecostume_64)
              .showImageForEmptyUri(R.drawable.malecostume_64)
              .showImageOnFail(R.drawable.malecostume_64)
              .cacheInMemory(true)
              .cacheOnDisk(true)
              .considerExifParams(true)
              .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
//                    .displayer(new CircleBitmapDisplayer(Color.parseColor("#ff0000"), 2))
              .build();
    }catch(Exception e){
      Log.d(TAG, "myError11: "+e.getMessage());
    }


    dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

    setDateTimeFieldOne();


    buttonUploadPicture.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDialogBOX(IMAGE_GALLERY_11, IMAGE_CAMERA_11);
      }
    });



    buttonDOB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fromDatePickerDialogOne.show();
      }
    });


    callCountryApiMethod();


    //new CountryTask().execute();



    spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        stringCountry = itemCountries.get(position).getId();
        //new ShowMsg().createToast(EditProfileActivity.this,""+stringCountry);
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });





    buttonUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (
                editTextFName.getText().toString().length() == 0 ||
                        editTextLName.getText().toString().length() == 0 ||
                editTextUserName.getText().toString().length() == 0 ||
                        editTextEmail.getText().toString().length() == 0 ||
                        buttonDOB.getText().toString().equalsIgnoreCase("Date Of Birth") ||
                        editTextAddress.getText().toString().length() == 0 ||
                        editTextCity.getText().toString().length() == 0 ||
                        stringCountry.length() == 0
                ) {
          new ShowMsg().createSnackbar(EditProfileActivity.this, "All field is required.");
        } else {
          boolean b = WSConnector.checkAvail(EditProfileActivity.this);
          if (b == true) {
//            new UpdateTask().execute(
//                    pref.getId(),
//                    editTextUserName.getText().toString(),
//                    editTextEmail.getText().toString(),
//                    buttonDOB.getText().toString(),
//                    editTextAddress.getText().toString(),
//                    anotherCarUrl,
//                    editTextCity.getText().toString(),
//                    stringCountry,
//                    editTextFName.getText().toString(),
//                    editTextLName.getText().toString()
//            );

            uploadFile(
                    pref.getId(),
                    editTextUserName.getText().toString(),
                    editTextEmail.getText().toString(),
                    buttonDOB.getText().toString(),
                    editTextAddress.getText().toString(),
                    anotherCarUrl,
                    editTextCity.getText().toString(),
                    stringCountry,
                    editTextFName.getText().toString(),
                    editTextLName.getText().toString()
            );

          }else{
            new ShowMsg().createSnackbar(EditProfileActivity.this, "No internet connection.");
          }
        }
      }
    });


    updateProfile();

  }







// pref.getId(),
//         editTextUserName.getText().toString(),
//                    editTextEmail.getText().toString(),
//                    buttonDOB.getText().toString(),
//                    editTextAddress.getText().toString(),
//  anotherCarUrl,
//          editTextCity.getText().toString(),
//  stringCountry,
//          editTextFName.getText().toString(),
//                    editTextLName.getText().toString()



  private void uploadFile(String prefId,
                          String userName,
                          String email,
                          String dob,
                          String addr,
                          String anotherCarUrl,
                          String stringCity,
                          String stringCountry,
                          String fname,
                          String lname) {
    progressBar.setVisibility(View.VISIBLE);

    MultipartBody.Part body = null;
    if(anotherCarUrl != ""){
      File fileUri = new File(anotherCarUrl);
      RequestBody reqFile = RequestBody.create(MediaType.parse("application/octet-stream"), fileUri);
      body = MultipartBody.Part.createFormData("image", fileUri.getName(), reqFile);
    }

    RequestBody id1 = RequestBody.create( okhttp3.MultipartBody.FORM, prefId);
    RequestBody name1 = RequestBody.create( okhttp3.MultipartBody.FORM, userName);
    RequestBody email1 = RequestBody.create( okhttp3.MultipartBody.FORM, email);
    RequestBody dob1 = RequestBody.create( okhttp3.MultipartBody.FORM, dob);
    RequestBody addr1 = RequestBody.create( okhttp3.MultipartBody.FORM, addr);
    RequestBody stringCity1 = RequestBody.create( okhttp3.MultipartBody.FORM, stringCity);
    RequestBody stringCountry1 = RequestBody.create( okhttp3.MultipartBody.FORM, stringCountry);
    RequestBody fname1 = RequestBody.create( okhttp3.MultipartBody.FORM, fname);
    RequestBody lname1 = RequestBody.create( okhttp3.MultipartBody.FORM, lname);


    Log.e(TAG , "body:: "+body);

    // finally, execute the request
    Call<ResponseBody> call = apiInterface.editProfile(id1,name1,email1, dob1,addr1, stringCity1, stringCountry1, fname1, lname1, body);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call,
                             Response<ResponseBody> response) {
        progressBar.setVisibility(View.GONE);
        Log.e("UploadAAAA", "success");

        String responseCode = null;
        try {
          responseCode = response.body().string();

          final JSONObject jsonObject = new JSONObject(responseCode);
          String status = jsonObject.getString("status");
          if(status.equals("1")) {
            String message = jsonObject.getString("message");
            new ShowMsg().createToast(EditProfileActivity.this ,""+message);

            updateProfile();

          }else {
            String message = jsonObject.getString("message");
            new ShowMsg().createSnackbar(EditProfileActivity.this, message);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        Log.e(TAG , "responseCode "+responseCode);
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
        Log.e("UploadAAAA error:", t.getMessage());
      }
    });
  }








  private void setDateTimeFieldOne() {

//        Calendar newCalendar = Calendar.getInstance();
//        fromDatePickerDialogOne = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                buttonDOB.setText(dateFormatter.format(newDate.getTime()));
//            }
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    // Log.e(TAG , "birth_date "+birth_date);

    Date date = null;
    try {
      date = dateFormatter.parse(birth_date);
    } catch (Exception ex) {
// do something for invalid dateformat
    }

    Log.e(TAG , "birth_datedate "+date);

    final Calendar newCalendar = Calendar.getInstance();
    if(date != null){
      newCalendar.setTime(date);
    }else{

    }

    try{
      fromDatePickerDialogOne = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
          Calendar newDate = Calendar.getInstance();
          newDate.set(year, month, dayOfMonth);
          buttonDOB.setText(dateFormatter.format(newDate.getTime()));
        }
      }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }catch (Exception e){

    }

  }





  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == android.R.id.home){
      finish();
    }
    return super.onOptionsItemSelected(item);
  }









  private void updateProfile() {
    boolean b = WSConnector.checkAvail(EditProfileActivity.this);
    if(b == true){
      if(!pref.getId().equals("")){
        //new ProfileTask().execute(pref.getId());
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
            if(status.equals("1")){
              JSONObject data = jsonObject.getJSONObject("data");
              String id = data.getString("id");
              String first_name = data.getString("first_name");
              String last_name = data.getString("last_name");
              String username = data.getString("username");
              String email = data.getString("email");
              String address = data.getString("address");
              birth_date = data.getString("dob");
              String city = data.getString("city");
              String country = data.getString("country");

              String profilepic = data.getString("profilepic");

              Log.e(TAG , "profilepic "+WSContants.BASE_MAIN__PROFILE_URL+profilepic);

              editTextFName.setText(""+first_name.toString());
              editTextLName.setText(""+last_name.toString());
              editTextUserName.setText(""+username.toString());
              editTextEmail.setText(""+email.toString());
              editTextAddress.setText(""+address.toString());
              editTextCity.setText(""+city.toString());

              setSpinRegionText(spinnerCountry, country);

              if(!birth_date.equalsIgnoreCase("")){
                buttonDOB.setText(""+birth_date.toString());
              }



              setDateTimeFieldOne();


              if(profilepic.equals("")){
                String imageUri = "drawable://" + R.drawable.malecostume_128;
                imageLoader.displayImage(imageUri, imageViewProfilePic, options);
              }else{
                imageLoader.displayImage(profilepic, imageViewProfilePic, options);
              }
            }else{

            }


          }
        } catch (Exception e) {
          e.printStackTrace();
         // new ShowMsg().createSnackbar(ProfileActivity.this, ""+e.getMessage());
        }

      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        // progressDialog.dismiss();
        progressBar.setVisibility(View.GONE);
        //new ShowMsg().createSnackbar(ProfileActivity.this, ""+t.getMessage());
      }
    });




  }






//
//  class ProfileTask extends AsyncTask<String, String, String> {
//
//    @Override
//    protected String doInBackground(String... params) {
//      return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"profileinfo.php?userid="+params[0]);
//    }
//
//    @Override
//    protected void onPreExecute() {
//      super.onPreExecute();
//      progressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//      super.onPostExecute(s);
//
//      progressBar.setVisibility(View.GONE);
//
//      try{
//        JSONObject jsonObject = new JSONObject(s);
//        //JSONObject response = jsonObject.getJSONObject("response");
//
//        String status = jsonObject.getString("status");
//        if(status.equals("1")){
//          JSONObject data = jsonObject.getJSONObject("data");
//          String id = data.getString("id");
//          String first_name = data.getString("first_name");
//          String last_name = data.getString("last_name");
//          String username = data.getString("username");
//          String email = data.getString("email");
//          String address = data.getString("address");
//          birth_date = data.getString("dob");
//          String city = data.getString("city");
//          String country = data.getString("country");
//
//          String profilepic = data.getString("profilepic");
//
//          Log.e(TAG , "profilepic "+WSContants.BASE_MAIN__PROFILE_URL+profilepic);
//
//          editTextFName.setText(""+first_name.toString());
//          editTextLName.setText(""+last_name.toString());
//          editTextUserName.setText(""+username.toString());
//          editTextEmail.setText(""+email.toString());
//          editTextAddress.setText(""+address.toString());
//          editTextCity.setText(""+city.toString());
//
//          setSpinRegionText(spinnerCountry, country);
//
//          if(!birth_date.equalsIgnoreCase("")){
//            buttonDOB.setText(""+birth_date.toString());
//          }
//
//
//
//          setDateTimeFieldOne();
//
//
//          if(profilepic.equals("")){
//            String imageUri = "drawable://" + R.drawable.malecostume_128;
//            imageLoader.displayImage(imageUri, imageViewProfilePic, options);
//          }else{
//            imageLoader.displayImage(profilepic, imageViewProfilePic, options);
//          }
//        }else{
//
//        }
//
//      }catch (Exception e){
//
//      }
//      Log.e(TAG , "onPostExecute "+s);
//    }
//  }
//
//





  public void setSpinRegionText(Spinner spin, String text) {
    for (int i = 0; i < spin.getAdapter().getCount(); i++) {
      if (spin.getAdapter().getItem(i).toString().contains(text)) {
        spin.setSelection(i);
      }
    }
  }







//
//  class UpdateTask extends AsyncTask<String,String,String> {
//    ProgressDialog progressDialog= new ShowMsg().createProgressDialog(EditProfileActivity.this);
//    @Override
//    protected String doInBackground(String... params) {
//
//      String responseStr = "";
//      try {
//        HttpClient client = WSConnector.getNewHttpClient();
//        HttpPost postMethod = new HttpPost(WSContants.BASE_MAIN_URL_ANDROID + "editprofile.php?");
//
//        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//        entity.addPart("userid", new StringBody(""+params[0]));
//        entity.addPart("username", new StringBody(""+params[1]));
//        entity.addPart("email", new StringBody(params[2]));
//        entity.addPart("dob", new StringBody(params[3]));
//        entity.addPart("address", new StringBody(params[4]));
//        entity.addPart("city", new StringBody(params[6]));
//        entity.addPart("country", new StringBody(params[7]));
//        entity.addPart("first_name", new StringBody(params[8]));
//        entity.addPart("last_name", new StringBody(params[9]));
//
//
//
//
//        if(!params[5].equalsIgnoreCase("")){
//          File file = new File(params[5]);
//          FileBody fileBody = new FileBody(file);
//          entity.addPart("image", fileBody);
//        }
//
//        postMethod.setEntity(entity);
//
//        HttpResponse response = client.execute(postMethod);
//        HttpEntity resEntity = response.getEntity();
//
//        if (resEntity != null) {
//          responseStr = EntityUtils.toString(resEntity).trim();
//          Log.v(TAG, "ResponseCCCCCCCCCCC: " + responseStr);
//        }
//      } catch (Exception e) {
//
//      }
//      return responseStr;
//      // return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"editprofile.php?userid="+params[0]+"&username="+params[1]+"&email="+params[2]+"&dob="+params[3]+"&address="+params[4]);
//    }
//
//    @Override
//    protected void onPreExecute() {
//      super.onPreExecute();
//      progressDialog.setCancelable(false);
//      progressDialog.show();
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//      super.onPostExecute(s);
//      progressDialog.dismiss();
//      Log.d(TAG, "ssssXX "+s);
//
//
//      //new ShowMsg().createDialog(EditProfileActivity.this ,""+s);
//
//      try {
//        final JSONObject jsonObject = new JSONObject(s);
//        String status = jsonObject.getString("status");
//        if(status.equals("1")) {
//          String message = jsonObject.getString("message");
//          new ShowMsg().createToast(EditProfileActivity.this ,""+message);
//
//          updateProfile();
//
//        }else {
//          String message = jsonObject.getString("message");
//          new ShowMsg().createSnackbar(EditProfileActivity.this, message);
//        }
//
//      }catch (Exception e){
//
//      }
//
//    }
//  }
//
//








  private void showDialogBOX(final int fffff, final int sssss) {
    // TODO Auto-generated method stub
    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
    builder.setTitle("Choose Image");
    AlertDialogImageAdapter adapter = new AlertDialogImageAdapter();
    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which)
      {
        // TODO Auto-generated method stub
        if (which == 0) {
          realGalleryCamera = false;
          imageChooseFromGallery1(fffff);
        }
        if (which == 1) {
          realGalleryCamera = true;
          imageChooseFromCamera1(sssss);
        }
      }
    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void imageChooseFromGallery1(int fffffff) {
    Intent i = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(i, fffffff);
  }

  public void imageChooseFromCamera1(int sssssss) {
    // TODO Auto-generated method stub
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, sssssss);
  }

  class AlertDialogImageAdapter extends BaseAdapter {
    final CharSequence[] items = {"Choose from Gallery", "Take a Pic"};
    final int[] img = {R.drawable.gallery_64, R.drawable.camera_64};
    @Override
    public int getCount() {
      // TODO Auto-generated method stub
      return img.length;
    }
    @Override
    public Object getItem(int position) {
      // TODO Auto-generated method stub
      return position;
    }
    @Override
    public long getItemId(int position) {
      // TODO Auto-generated method stub
      return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // TODO Auto-generated method stub
      View view = convertView;

      LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.alert_row, null);

      ImageView imageView = (ImageView) view.findViewById(R.id.imageView35345451);
      TextView textView = (TextView) view.findViewById(R.id.textView112354345);

      imageView.setImageResource(img[position]);
      textView.setText(items[position]);

      return view;
    }

  }









  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (requestCode == IMAGE_GALLERY_11 && resultCode == Activity.RESULT_OK && null != data) {
      Uri selectedImage = data.getData();

      String imagePath1 = getImagePath(selectedImage);
      anotherCarUrl = imagePath1;

      Log.e(TAG , "imagePath1 "+imagePath1);

      imageLoader.displayImage("file:///" + anotherCarUrl, imageViewProfilePic, options);
    }

    if (requestCode == IMAGE_GALLERY_11 && resultCode == Activity.RESULT_CANCELED && null != data) {

    }

    if (requestCode == IMAGE_CAMERA_11 && resultCode == Activity.RESULT_OK && null != data) {
      // Uri selectedImage = data.getData();

      String imagePath1 = getImagePathCamera(data);

      anotherCarUrl = imagePath1;

      imageLoader.displayImage("file:///" + anotherCarUrl, imageViewProfilePic, options);

    }

    if (requestCode == IMAGE_CAMERA_11 && resultCode == Activity.RESULT_CANCELED && null != data) {

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////

  }








  private String getImagePath(Uri selectedImage) {
    // TODO Auto-generated method stub
    String[] filePathColumn = {MediaStore.Images.Media.DATA};

    Cursor cursor = getContentResolver().query(selectedImage,
            filePathColumn, null, null, null);
    cursor.moveToFirst();

    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
    String picturePath = cursor.getString(columnIndex);
    cursor.close();

    Log.d(TAG, "path is :: " + picturePath);
    return picturePath;
  }


  private String getImagePathCamera(Intent data) {
    // TODO Auto-generated method stub
    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
    File destination = new File(Environment.getExternalStorageDirectory(),
            System.currentTimeMillis() + ".jpg");
    FileOutputStream fo;
    try {
      destination.createNewFile();
      fo = new FileOutputStream(destination);
      fo.write(bytes.toByteArray());

      String imagePath1 = destination.toString();

      fo.close();

      return imagePath1;

      //imageLoader.displayImage("file:///"+imagePath1, imageViewDriverProfile, options);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }







  private void callCountryApiMethod() {
    progressBarCountry.setVisibility(View.VISIBLE);
    call = apiInterface.getCountries();
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        progressBarCountry.setVisibility(View.GONE);
        String responseCode = "";
        try {
          if(response.body() != null) {
            responseCode = response.body().string();

            try{
              itemCountries = new ParsingHelper().getItemCountry(responseCode);
              //   new ShowMsg().createDialog(getActivity(),""+itemTargetLanguages.size());

              spinnerCountry.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this , R.layout.item_list_regione_drop_down,
                      getWishIdArrayListtoStringArrayState(itemCountries)));


              updateProfile();

            }catch (Exception e){

            }

          }else{
            new ShowMsg().createSnackbar(EditProfileActivity.this, "Something went wrong!");
          }
        } catch (Exception e) {
          e.printStackTrace();
          new ShowMsg().createSnackbar(EditProfileActivity.this, ""+e.getMessage());
        }

      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        progressBarCountry.setVisibility(View.GONE);
        new ShowMsg().createSnackbar(EditProfileActivity.this, ""+t.getMessage());
      }
    });




  }








  class CountryTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... string) {
      return  WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"get_countries.php?");
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      progressBarCountry.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
      super.onPostExecute(s);
//            new ShowMsg().createDialog(Registeration.this,""+itemNationaltas.size());
      progressBarCountry.setVisibility(View.GONE);

      itemCountries = new ParsingHelper().getItemCountry(s);
      //   new ShowMsg().createDialog(getActivity(),""+itemTargetLanguages.size());

      spinnerCountry.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this , R.layout.item_list_regione_drop_down,
              getWishIdArrayListtoStringArrayState(itemCountries)));

      //setSpinRegionText(nazionality ,"ITALY");

      //new RegioneTask().execute();

    }


  }



  private String[] getWishIdArrayListtoStringArrayState(ArrayList<ItemCountry> itemNationaltas) {
    String[] orderArray= new String[itemNationaltas.size()];

    int i=0;
    for (ItemCountry currentOrder : itemNationaltas){
      orderArray[i]=currentOrder.getCountry_name();
      i++;
    }
    return  orderArray;
  }

}
