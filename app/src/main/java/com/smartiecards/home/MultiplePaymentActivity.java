package com.smartiecards.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/23/2018.
 */

public class MultiplePaymentActivity extends BaseAppCompactActivity {

    private static final int PAYPAL_BACK_REQUEST_CODE = 576;

    String payble_amt = "";

    private static final String TAG = "MultiplePaymentActivity";

//    SavePref pref = new SavePref();

    TextView textViewSubjects, textViewAmount, textViewDiscount, textViewPayableAmount;

    Button buttonAddCoupon, buttonBuyNormal;

    ImageView imageViewBuyNow;
    LinearLayout linearLayoutEnterCoupon, linearLayoutDiscount, linearLayoutPayableAmount, linearLayoutAddCouponButton;

    EditText editTextAddCoupon;


    boolean booleanIsPayAble = false;


    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    int allAmounts = 0;
    int allSubjects = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.multiple_payment;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Payment");

        textViewSubjects = (TextView) findViewById(R.id.textView25346);
                textViewAmount = (TextView) findViewById(R.id.textView26457);
        textViewDiscount = (TextView) findViewById(R.id.textView9789678);
        textViewPayableAmount = (TextView) findViewById(R.id.textView86797890);



        editTextAddCoupon = (EditText) findViewById(R.id.login_old_password);

        buttonAddCoupon = (Button) findViewById(R.id.button6546);
        buttonBuyNormal = (Button) findViewById(R.id.button654656);

        imageViewBuyNow = (ImageView) findViewById(R.id.button235345);

        linearLayoutEnterCoupon = (LinearLayout) findViewById(R.id.linear346546);
        linearLayoutDiscount = (LinearLayout) findViewById(R.id.linear346456547);
        linearLayoutPayableAmount = (LinearLayout) findViewById(R.id.linear12123234);
        linearLayoutAddCouponButton = (LinearLayout) findViewById(R.id.linear34534);




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


       // pref.SavePref(MultiplePaymentActivity.this);


        final Bundle bundle = getIntent().getExtras();

        if(bundle.getString("pos").equalsIgnoreCase("1")){                // top
            arrayList = (ArrayList<ItemSubject>) bundle.getSerializable("key");

            if(arrayList.size() > 0){
                allSubjects = getAllSubjects(arrayList);
                textViewSubjects.setText(""+allSubjects);
            }


            if(arrayList.size() > 0){
               allAmounts = getAllAmounts(arrayList);
                payble_amt = allAmounts+"";
                textViewAmount.setText(WSContants.CURRENCY+allAmounts+WSContants.TERM);
            }
        }else{                                                                                  // subjects
            arrayList = (ArrayList<ItemSubject>) bundle.getSerializable("key");

            if(arrayList.size() > 0){
                allSubjects = getAllSubjects(arrayList);
                textViewSubjects.setText(""+allSubjects);
            }


            if(arrayList.size() > 0){
                allAmounts = getAllAmounts(arrayList);
                payble_amt = allAmounts+"";
                textViewAmount.setText(WSContants.CURRENCY+allAmounts+WSContants.TERM);
            }

        }





        buttonAddCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //new CouponTask().execute(""+allAmounts, editTextAddCoupon.getText().toString());
                callCouponMethod(""+allAmounts, editTextAddCoupon.getText().toString());
            }
        });


        buttonBuyNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dd = 0;
                try{
                    dd = Integer.parseInt(payble_amt);
                }catch (Exception e){
                }

                if(dd == 0){
                    Gson gson = new Gson();
                    ArrayList<ItemSubject> itemSubjects = getAllMY(arrayList);
                    String json = gson.toJson(itemSubjects);
                    Log.e(TAG, "jsonQQQ "+json);

                    callPaymentMethod(pref.getId() , json.toString(), "0", editTextAddCoupon.getText().toString(), "");

                   // new PaymentTask().execute(pref.getId() , json.toString(), "0", editTextAddCoupon.getText().toString(), "");
                }else{

                }
            }
        });



        imageViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment payment = null;

                int dd = 0;
                    try{
                        dd = Integer.parseInt(payble_amt);
                    }catch (Exception e){
                    }

                    if(dd == 0) {
                        Log.e(TAG , "IIIIIIIFFFFFFFFFF");

                        ArrayList<ItemSubject> itemSubjects = getAllMY(arrayList);

                        Gson gson = new Gson();
                        String json = gson.toJson(itemSubjects);
                        Log.e(TAG, "jsonQQQW "+json);


                        callPaymentMethod(pref.getId() , json.toString(), "0", editTextAddCoupon.getText().toString(), "");

                    }else{
                        Log.e(TAG , "EEEELLLLSSSSEEEEE");
                        payment = new PayPalPayment(new BigDecimal(payble_amt), "USD", "All Subjects",
                                PayPalPayment.PAYMENT_INTENT_SALE);
                    }


                    if(payment != null){
                            Intent intent = new Intent(MultiplePaymentActivity.this, PaymentActivity.class);
                            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
                            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                            startActivityForResult(intent, PAYPAL_BACK_REQUEST_CODE);
                    }


//                callPaymentMethod(pref.getId() , "", "0", editTextAddCoupon.getText().toString(), "");


                }
        });


    }

    private ArrayList<ItemSubject> getAllMY(ArrayList<ItemSubject> arrayList) {
        ArrayList<ItemSubject> itemSubjects = new ArrayList<>();
        if(arrayList.size() > 0){
            for(int i = 0 ; i < arrayList.size() ; i++){
                ItemSubject subject = new ItemSubject();
                subject.setId(arrayList.get(i).getId());
                subject.setAmount(arrayList.get(i).getAmount());
                subject.setPurchase_status(arrayList.get(i).getPurchase_status());
                subject.setSubjectname(arrayList.get(i).getSubjectname());
                itemSubjects.add(subject);
            }
        }
        return itemSubjects;
    }


    private void callCouponMethod(String amount, String toString) {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(MultiplePaymentActivity.this);
        dialog.setCancelable(false);
        dialog.show();

        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();

        arrayListMash.add(MultipartBody.Part.createFormData("amt", amount));
        arrayListMash.add(MultipartBody.Part.createFormData("coupon", toString));

        call = apiInterface.coupanCode(arrayListMash);
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
                                String discount = jsonObject.getString("discount");
                                payble_amt = jsonObject.getString("payble_amt");
                                //     new ShowMsg().createSnackbar(AddCoupon.this , ""+message);

                                //  itemSubject.setPayable_amount(""+payble_amt);
                                textViewDiscount.setText(""+discount);
                                textViewPayableAmount.setText(WSContants.CURRENCY+""+payble_amt+"/Year");

                                linearLayoutEnterCoupon.setVisibility(View.GONE);
                                linearLayoutDiscount.setVisibility(View.VISIBLE);
                                linearLayoutPayableAmount.setVisibility(View.VISIBLE);
                                linearLayoutAddCouponButton.setVisibility(View.GONE);
                                booleanIsPayAble = true;

                                int dd = 0;
                                try{
                                    dd = Integer.parseInt(payble_amt);
                                }catch (Exception e){
                                }

                                if(dd == 0){
                                    imageViewBuyNow.setVisibility(View.GONE);
                                    buttonBuyNormal.setVisibility(View.VISIBLE);
                                    //new PaymentTask().execute(pref.getId() , itemSubject.getId(), "Purchase Subject", itemSubject.getPayable_amount(), editTextAddCoupon.getText().toString());
                                }else{
                                    imageViewBuyNow.setVisibility(View.VISIBLE);
                                    buttonBuyNormal.setVisibility(View.GONE);
                                }

                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(MultiplePaymentActivity.this , ""+message);
                                booleanIsPayAble = false;
                            }

                        }catch (Exception e){

                        }

                    }else{
                       // new ShowMsg().createSnackbar(AddCoupon.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // new ShowMsg().createSnackbar(AddCoupon.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                //new ShowMsg().createSnackbar(AddCoupon.this, ""+t.getMessage());
            }
        });


    }





//
//    class CouponTask extends AsyncTask<String, String, String> {
//        ProgressDialog dialog = new ShowMsg().createProgressDialog(MultiplePaymentActivity.this);
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"coupan-code.php?amt="+params[0]+"&coupon="+params[1]);
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
//                String status = jsonObject.getString("status");
//
//                if(status.equals("1")){
//                    String discount = jsonObject.getString("discount");
//                    payble_amt = jsonObject.getString("payble_amt");
//                    //     new ShowMsg().createSnackbar(AddCoupon.this , ""+message);
//
//                  //  itemSubject.setPayable_amount(""+payble_amt);
//                    textViewDiscount.setText(""+discount);
//                    textViewPayableAmount.setText(WSContants.CURRENCY+""+payble_amt+"/Year");
//
//                    linearLayoutEnterCoupon.setVisibility(View.GONE);
//                    linearLayoutDiscount.setVisibility(View.VISIBLE);
//                    linearLayoutPayableAmount.setVisibility(View.VISIBLE);
//                    linearLayoutAddCouponButton.setVisibility(View.GONE);
//                    booleanIsPayAble = true;
//
//                    int dd = 0;
//                    try{
//                        dd = Integer.parseInt(payble_amt);
//                    }catch (Exception e){
//                    }
//
//                    if(dd == 0){
//                        imageViewBuyNow.setVisibility(View.GONE);
//                        buttonBuyNormal.setVisibility(View.VISIBLE);
//                        //new PaymentTask().execute(pref.getId() , itemSubject.getId(), "Purchase Subject", itemSubject.getPayable_amount(), editTextAddCoupon.getText().toString());
//                    }else{
//                        imageViewBuyNow.setVisibility(View.VISIBLE);
//                        buttonBuyNormal.setVisibility(View.GONE);
//                    }
//
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(MultiplePaymentActivity.this , ""+message);
//                    booleanIsPayAble = false;
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//
//




    private int getAllAmounts(ArrayList<ItemSubject> arrayList) {
        int totalAmount = 0;
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("0")){
                try{
                    totalAmount = totalAmount + Integer.parseInt(arrayList.get(i).getAmount());
                    allSubjects = allSubjects ++;
                }catch (Exception e){
                }
            }
        }
        return totalAmount;
    }




    private int getAllSubjects(ArrayList<ItemSubject> arrayList) {
        int totalAmount = 0;
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("0")){
                try{
                    totalAmount = totalAmount + 1;
                }catch (Exception e){
                }
            }
        }
        return totalAmount;
    }

















    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PAYPAL_BACK_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject()
                                .toString(4));

                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                        JSONObject response = jsonObject.getJSONObject("response");
                        String txid = response.getString("id");
                        String state = response.getString("state");

                        //if(state.equals("approved")){
                        JSONObject jsonObject2 = new JSONObject(confirm.getPayment().toJSONObject()
                                .toString(4));
                        String amount = jsonObject2.getString("amount");
                        Gson gson = new Gson();
                        ArrayList<ItemSubject> itemSubjects = getAllMY(arrayList);
                        String json = gson.toJson(itemSubjects);
                        Log.d(TAG, "onPostExecute "+json);
                        if(state.equalsIgnoreCase("approved")){
                           // callPaymentMethod(pref.getId() , json.toString(), amount, txid, editTextAddCoupon.getText().toString());

                            callPaymentMethod(pref.getId() , ""+json, txid,  amount, editTextAddCoupon.getText().toString());

//                            private void callPaymentMethod(String prefId, String jsonData, String txid, String amount, String coupon) {


                            }
                    } catch (JSONException e) {
                        Log.e("paymentExample111", "an extremely unlikely failure occurred: ", e);
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample111", "The user canceled.");
            }
            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample111", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }


    }










//
//    class PaymentTask extends AsyncTask<String, String, String> {
//        ProgressDialog dialog = new ShowMsg().createProgressDialog(MultiplePaymentActivity.this);
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            String responseStr = "";
//            try {
//                HttpClient client = WSConnector.getNewHttpClient();
//                HttpPost postMethod = new HttpPost(WSContants.BASE_MAIN_URL_ANDROID + "multiplepayments.php?");
//
//                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//                entity.addPart("userid", new StringBody(""+params[0]));
//                entity.addPart("data_result", new StringBody(""+params[1]));
//                entity.addPart("payable_amount", new StringBody(""+params[2]));
//                entity.addPart("coupon", new StringBody(""+params[3]));
//                entity.addPart("txn_id", new StringBody(""+params[4]));
//
//                postMethod.setEntity(entity);
//
//                HttpResponse response = client.execute(postMethod);
//                HttpEntity resEntity = response.getEntity();
//
//                if (resEntity != null) {
//                    responseStr = EntityUtils.toString(resEntity).trim();
//                    Log.v(TAG, "ResponseCCCCCCCCCCC: " + responseStr);
//                }
//            } catch (Exception e) {
//
//            }
//            return responseStr;
//            //  return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"multiplepayments.php?userid="+params[0]+"&data_result="+params[1]);
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
//
//            Log.e(TAG , "onPostExecuteAAA "+result);
//
//          //    new ShowMsg().createDialog(MultiplePaymentActivity.this , ""+result);
//
//            try{
//                JSONObject jsonObject = new JSONObject(result);
//                String status = jsonObject.getString("status");
//
//                if(status.equals("1")){
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createDialogFinishClass(MultiplePaymentActivity.this , ""+message);
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(MultiplePaymentActivity.this , ""+message);
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//
//



    private void callPaymentMethod(String prefId, String jsonData, String txid, String amount, String coupon) {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(MultiplePaymentActivity.this);
        dialog.setCancelable(false);
        dialog.show();

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        arrayListMash.add(MultipartBody.Part.createFormData("uid", prefId));
//        arrayListMash.add(MultipartBody.Part.createFormData("subid", itemSubjectId));
//        arrayListMash.add(MultipartBody.Part.createFormData("txn_id", id));
//        arrayListMash.add(MultipartBody.Part.createFormData("amt", amount));
//        arrayListMash.add(MultipartBody.Part.createFormData("coupon", toString));

       // callPaymentMethod(pref.getId() , json.toString(), amount, txid, editTextAddCoupon.getText().toString());


        call = apiInterface.multiplePayments(prefId, jsonData, txid, amount, coupon);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();
                        Log.e(TAG , "responseCodeVVVV "+responseCode);
                        try{
                            JSONObject jsonObject = new JSONObject(responseCode);
                            String status = jsonObject.getString("status");

                            if(status.equals("1")){
                                String message = jsonObject.getString("message");
                                new ShowMsg().createDialogFinishClass(MultiplePaymentActivity.this , ""+message);
                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(MultiplePaymentActivity.this , ""+message);
                            }
                        }catch (Exception e){
                        }
                    }else{
                        Log.e(TAG , "responseCodeFAS "+responseCode);
                       // new ShowMsg().createSnackbar(AddCoupon.this, "Something went wrong!");
                    }

                    Log.e(TAG , "responseCodeLAST "+responseCode);


                } catch (Exception e) {
                    e.printStackTrace();
                  //  new ShowMsg().createSnackbar(AddCoupon.this, ""+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                //new ShowMsg().createSnackbar(AddCoupon.this, ""+t.getMessage());
            }
        });


    }










    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MultiplePaymentActivity.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
        startService(intent);
    }





    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(MultiplePaymentActivity.this, PayPalService.class));
    }




}
