package com.smartiecards.account;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.settings.WebActivity;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 1/24/2018.
 */

public class RegisterActivity extends BaseAppCompactActivity {


    private static final String TAG = "RegisterActivity";
    TextView textViewAggree;

    EditText editTextFirstName, editTextLastName, editTextUserName,  editTextEmail, editTextPassword, editTextConPassword, editTextAddress, editTextCity;
    Button buttonDOB;
    CheckBox checkBoxAccept;
    Button buttonRegister;


    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialogOne;

    SavePref pref = new SavePref();

    Spinner spinnerCountry;
    ProgressBar progressBarCountry;


    ArrayList<ItemCountry> itemCountries = new ArrayList<ItemCountry>();

    String stringCountry = "";

    String sdfsdf[] = {"" , ""};

    @Override
    protected int getLayoutResource() {
        return R.layout.register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        textViewAggree = (TextView) findViewById(R.id.textView12sdd);


        editTextFirstName = (EditText) findViewById(R.id.login_fname);
        editTextLastName = (EditText) findViewById(R.id.login_lname);
        editTextUserName = (EditText) findViewById(R.id.login_username);
        editTextEmail = (EditText) findViewById(R.id.login_email);
        editTextPassword = (EditText) findViewById(R.id.login_password);
        editTextConPassword = (EditText) findViewById(R.id.login_con_password);
        editTextAddress = (EditText) findViewById(R.id.login_address);
        editTextCity = (EditText) findViewById(R.id.login_city);

        spinnerCountry = (Spinner) findViewById(R.id.spinner645657);
        progressBarCountry = (ProgressBar) findViewById(R.id.progressBar3657475);

        buttonDOB = (Button) findViewById(R.id.button678789789);



        checkBoxAccept = (CheckBox) findViewById(R.id.checkBox111);

        buttonRegister = (Button) findViewById(R.id.button6546);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText(getString(R.string.login_signup));


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        pref.SavePref(RegisterActivity.this);


        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        setDateTimeFieldOne();

       // registerForContextMenu(buttonRegister);

        LinkBuilder.on(textViewAggree)
                .addLinks(getExampleLinks())
                .build();

        callCountryApiMethod();


     //   new CountryTask().execute();



        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                stringCountry = itemCountries.get(position).getId();
           // new ShowMsg().createToast(RegisterActivity.this,""+stringCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        buttonDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialogOne.show();
            }
        });




        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(   editTextUserName.getText().toString().length() == 0 ||
                        editTextEmail.getText().toString().length() == 0 ||
                        editTextPassword.getText().toString().length() == 0 ||
                        editTextConPassword.getText().toString().length() == 0 ||
                        buttonDOB.getText().toString().equalsIgnoreCase("Date Of Birth") ||
                                editTextAddress.getText().toString().length() == 0 ||
                                editTextCity.getText().toString().length() == 0 ||
                                stringCountry.length() == 0 ||
                                editTextFirstName.getText().toString().length() == 0 ||
                                editTextLastName.getText().toString().length() == 0
                        ){
//                    new ShowMsg().createDialog(getActivity() , "All field is required.");
                    new ShowMsg().createSnackbar(RegisterActivity.this , "All field is required.");
                }else{
                    if(!editTextPassword.getText().toString().equalsIgnoreCase(editTextConPassword.getText().toString())) {
                        new ShowMsg().createSnackbar(RegisterActivity.this , "Confirm password does not match.");
                    }else{
                        boolean b = WSConnector.checkAvail(RegisterActivity.this);
                        if(b == true){

                            if (checkBoxAccept.isChecked() == true) {
//                                new RegisterTask().execute(
//                                        editTextUserName.getText().toString(),
//                                                editTextEmail.getText().toString(),
//                                        editTextPassword.getText().toString(),
//                                        buttonDOB.getText().toString(),
//                                        editTextAddress.getText().toString(),
//                                        editTextCity.getText().toString(),
//                                        stringCountry,
//                                                editTextFirstName.getText().toString(),
//                                                editTextLastName.getText().toString()
//                                );

                                callRegisterApiMethod();


                            }else{
                                new ShowMsg().createSnackbar(RegisterActivity.this, "I agree to the Terms of Service and Privacy Policy” checkbox before clicking on the “SIGN UP” button.");
                            }
                        }else{
                            new ShowMsg().createSnackbar(RegisterActivity.this , "No internet connection.");
                            //   new ShowMsg().createDialog(getActivity() , "No internet connection.");
                        }
                    }
                }
            }
        });



    }





    private void setDateTimeFieldOne() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialogOne = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                buttonDOB.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }







    private void callCountryApiMethod() {
        progressBarCountry.setVisibility(View.VISIBLE);
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
                progressBarCountry.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        try{
                            itemCountries = new ParsingHelper().getItemCountry(responseCode);
                            spinnerCountry.setAdapter(new ArrayAdapter<String>(RegisterActivity.this , R.layout.item_list_regione_drop_down,
                                    getWishIdArrayListtoStringArrayState(itemCountries)));

                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(RegisterActivity.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(RegisterActivity.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarCountry.setVisibility(View.GONE);
                new ShowMsg().createSnackbar(RegisterActivity.this, ""+t.getMessage());
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

            spinnerCountry.setAdapter(new ArrayAdapter<String>(RegisterActivity.this , R.layout.item_list_regione_drop_down,
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




    private void callRegisterApiMethod() {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.show();


        call = apiInterface.register(editTextUserName.getText().toString(),
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                buttonDOB.getText().toString(),
                editTextAddress.getText().toString(),
                editTextCity.getText().toString(),
                stringCountry,
                editTextFirstName.getText().toString(),
                editTextLastName.getText().toString(),
                Utility.getLocalIpAddress()
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
                            final JSONObject jsonObject = new JSONObject(responseCode);
                            String status = jsonObject.getString("status");
                            if(status.equals("1")) {
                                String message = jsonObject.getString("message");
                                new ShowMsg().createToast(RegisterActivity.this ,""+message);

                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                                String id = jsonObject1.getString("id");

                                pref.setId(id);
//
//                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//                    new NotificationTask().execute(pref.getId(), refreshedToken);


                                Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                finish();


                            }else {
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(RegisterActivity.this, message);
                            }
                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(RegisterActivity.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(RegisterActivity.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                new ShowMsg().createSnackbar(RegisterActivity.this, ""+t.getMessage());
            }
        });




    }




    class RegisterTask extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog= new ShowMsg().createProgressDialog(RegisterActivity.this);
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"register.php?username="+params[0]+"&email="+params[1]+"&password="+params[2]+"&dob="+params[3]+"&address="+params[4]+"&city="+params[5]+"&country="+params[6]+"&first_name="+params[7]+"&last_name="+params[8]);
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
            Log.d(TAG, "ssssXX "+s);

            try {
                final JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                if(status.equals("1")) {
                    String message = jsonObject.getString("message");
                    new ShowMsg().createToast(RegisterActivity.this ,""+message);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    String id = jsonObject1.getString("id");

                    pref.setId(id);
//
//                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//                    Log.e(TAG, "Refreshed token: " + refreshedToken);
//
//                    new NotificationTask().execute(pref.getId(), refreshedToken);


                    Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    finish();


                }else {
                    String message = jsonObject.getString("message");
                    new ShowMsg().createSnackbar(RegisterActivity.this, message);
                }


            }catch (Exception e){

            }

        }
    }





    private List<Link> getExampleLinks() {
        List<Link> links = new ArrayList<>();

        Link linkTerms = new Link("Terms of Service")
                .setTextColor(Color.parseColor("#ffffff"))                  // optional, defaults to holo blue
                .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
//                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(false)                                       // optional, defaults to true
                .setBold(true)                                              // optional, defaults to false
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        Intent intent = new Intent(RegisterActivity.this, WebActivity.class);
                        intent.putExtra("key", "Terms of Service");
                        intent.putExtra("url", WSContants.TERMS);
                        startActivity(intent);
                        Log.d(TAG , "Terms of Service");
                    }
                });
        links.add(linkTerms);


        Link linkPolicy = new Link("Privacy Policy")
                .setTextColor(Color.parseColor("#ffffff"))                  // optional, defaults to holo blue
                .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
//                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                .setUnderlined(false)                                       // optional, defaults to true
                .setBold(true)                                              // optional, defaults to false
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        Intent intent = new Intent(RegisterActivity.this, WebActivity.class);
                        intent.putExtra("key", "Privacy Policy");
                        intent.putExtra("url", WSContants.PRIVACY);
                        startActivity(intent);
                        Log.d(TAG , "Privacy Policy");
                    }
                });
        links.add(linkPolicy);

        return links;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
