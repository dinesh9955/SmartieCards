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

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.ForgotPassword;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/17/2018.
 */

public class AddCoupon extends BaseAppCompactActivity {
    private static final int PAYPAL_BACK_REQUEST_CODE = 546;
    boolean booleanIsPayAble = false;

TextView textViewSubjects, textViewAmount, textViewDiscount, textViewPayableAmount;

EditText editTextAddCoupon;

Button buttonAddCoupon, buttonBuyNormal;
ImageView imageViewBuyNow;

LinearLayout linearLayoutEnterCoupon, linearLayoutDiscount, linearLayoutPayableAmount, linearLayoutAddCouponButton;

ItemSubject itemSubject = new ItemSubject();
ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    String allSubjects = "";
    int allAmounts = 0;

//    SavePref pref = new SavePref();

    @Override
    protected int getLayoutResource() {
        return R.layout.add_coupon;
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


       // pref.SavePref(AddCoupon.this);

        final Bundle bundle = getIntent().getExtras();

        if(bundle.getString("pos").equalsIgnoreCase("1")){
            itemSubject = (ItemSubject) bundle.getSerializable("key");
            textViewSubjects.setText(""+itemSubject.getSubjectname());
            textViewAmount.setText(WSContants.CURRENCY+itemSubject.getAmount());
        }else{
            arrayList = (ArrayList<ItemSubject>) bundle.getSerializable("key");

            if(arrayList.size() > 0){
                allSubjects = getAllSubjects(arrayList);
                textViewSubjects.setText(""+allSubjects);
            }


            if(arrayList.size() > 0){
                allAmounts = getAllAmounts(arrayList);
                textViewAmount.setText(WSContants.CURRENCY+allAmounts+WSContants.TERM);
            }

        }

        buttonAddCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.getString("pos").equalsIgnoreCase("1")){
                    //new CouponTask().execute(itemSubject.getAmount(), editTextAddCoupon.getText().toString());
                    callCouponMethod(itemSubject.getAmount(), editTextAddCoupon.getText().toString());
                }else{
                   // new CouponTask().execute(allAmounts+"", editTextAddCoupon.getText().toString());
                    callCouponMethod(allAmounts+"", editTextAddCoupon.getText().toString());
                }
            }
        });


        buttonBuyNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dd = 0;
                try{
                    dd = Integer.parseInt(itemSubject.getPayable_amount());
                }catch (Exception e){
                }

                if(dd == 0){
//                    new PaymentTask().execute(pref.getId() , itemSubject.getId(), "", itemSubject.getPayable_amount(), editTextAddCoupon.getText().toString());
                    callPaymentMethod(pref.getId() , itemSubject.getId(), "", itemSubject.getPayable_amount(), editTextAddCoupon.getText().toString());

                }else{

                }
            }
        });




        imageViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment payment = null;
                if(bundle.getString("pos").equalsIgnoreCase("1")){
                   if(booleanIsPayAble == true){
                            int dd = 0;
                            try{
                                dd = Integer.parseInt(itemSubject.getPayable_amount());
                            }catch (Exception e){
                            }

                            if(dd == 0){
                                //new PaymentTask().execute(pref.getId() , itemSubject.getId(), "Purchase Subject", itemSubject.getPayable_amount(), editTextAddCoupon.getText().toString());
                            }else{
                                payment = new PayPalPayment(new BigDecimal(""+ Utility.convertValueStringToDouble(""+itemSubject.getPayable_amount())), "USD", ""+itemSubject.getSubjectname(),
                                        PayPalPayment.PAYMENT_INTENT_SALE);
                            }
                   }else{
                       payment = new PayPalPayment(new BigDecimal(""+ Utility.convertValueStringToDouble(""+itemSubject.getAmount())), "USD", ""+itemSubject.getSubjectname(),
                               PayPalPayment.PAYMENT_INTENT_SALE);
                   }
                }else{

                }
                if(payment != null){
                    Intent intent = new Intent(AddCoupon.this, PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                    startActivityForResult(intent, PAYPAL_BACK_REQUEST_CODE);
                }
            }
        });



    }





    private int getAllAmounts(ArrayList<ItemSubject> arrayList) {
        int totalAmount = 0;
        for(int i = 0 ; i < arrayList.size() ; i++){
            try{
                totalAmount = totalAmount + Integer.parseInt(arrayList.get(i).getAmount());
            }catch (Exception e){
            }
        }
        return totalAmount;
    }

    private String getAllSubjects(ArrayList<ItemSubject> arrayList) {
        StringBuilder builder = new StringBuilder();
        String stringSubjects = "";
        for(int i = 0 ; i < arrayList.size() ; i++){
            builder.append(arrayList.get(i).getSubjectname()).append(",");
        }
        return String.valueOf(builder.deleteCharAt(builder.lastIndexOf(",")));
    }



    private void callCouponMethod(String amount, String toString) {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(AddCoupon.this);
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
                                String payble_amt = jsonObject.getString("payble_amt");
                                //new ShowMsg().createSnackbar(AddCoupon.this , ""+payble_amt);

                                itemSubject.setPayable_amount(""+payble_amt);
                                textViewDiscount.setText(""+discount);
                                textViewPayableAmount.setText(WSContants.CURRENCY+""+itemSubject.getPayable_amount()+"/Year");

                                linearLayoutEnterCoupon.setVisibility(View.GONE);
                                linearLayoutDiscount.setVisibility(View.VISIBLE);
                                linearLayoutPayableAmount.setVisibility(View.VISIBLE);
                                linearLayoutAddCouponButton.setVisibility(View.GONE);
                                booleanIsPayAble = true;

                                int dd = 0;
                                try{
                                    dd = Integer.parseInt(itemSubject.getPayable_amount());
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
                                new ShowMsg().createSnackbar(AddCoupon.this , ""+message);
                                booleanIsPayAble = false;
                            }
                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(AddCoupon.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(AddCoupon.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                new ShowMsg().createSnackbar(AddCoupon.this, ""+t.getMessage());
            }
        });


    }





    class CouponTask extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(AddCoupon.this);
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"coupan-code.php?amt="+params[0]+"&coupon="+params[1]);
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
                String status = jsonObject.getString("status");

                if(status.equals("1")){
                    String discount = jsonObject.getString("discount");
                    String payble_amt = jsonObject.getString("payble_amt");
                    //new ShowMsg().createSnackbar(AddCoupon.this , ""+payble_amt);

                    itemSubject.setPayable_amount(""+payble_amt);
                    textViewDiscount.setText(""+discount);
                    textViewPayableAmount.setText(WSContants.CURRENCY+""+itemSubject.getPayable_amount()+"/Year");

                    linearLayoutEnterCoupon.setVisibility(View.GONE);
                    linearLayoutDiscount.setVisibility(View.VISIBLE);
                    linearLayoutPayableAmount.setVisibility(View.VISIBLE);
                    linearLayoutAddCouponButton.setVisibility(View.GONE);
                    booleanIsPayAble = true;

                    int dd = 0;
                    try{
                        dd = Integer.parseInt(itemSubject.getPayable_amount());
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
                    new ShowMsg().createSnackbar(AddCoupon.this , ""+message);
                    booleanIsPayAble = false;
                }
            }catch (Exception e){

            }
        }
    }






    private void callPaymentMethod(String prefId, String itemSubjectId, String id, String amount, String toString) {
        ProgressDialog dialog = new ShowMsg().createProgressDialog(AddCoupon.this);
        dialog.setCancelable(false);
        dialog.show();

//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        arrayListMash.add(MultipartBody.Part.createFormData("uid", prefId));
//        arrayListMash.add(MultipartBody.Part.createFormData("subid", itemSubjectId));
//        arrayListMash.add(MultipartBody.Part.createFormData("txn_id", id));
//        arrayListMash.add(MultipartBody.Part.createFormData("amt", amount));
//        arrayListMash.add(MultipartBody.Part.createFormData("coupon", toString));




        call = apiInterface.subjectPayment(prefId, itemSubjectId, id, amount, toString);
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
                                new ShowMsg().createDialogFinishClass(AddCoupon.this , ""+message);
                            }else{
                                String message = jsonObject.getString("message");
                                new ShowMsg().createSnackbar(AddCoupon.this , ""+message);
                            }
                        }catch (Exception e){

                        }

                    }else{
                        new ShowMsg().createSnackbar(AddCoupon.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(AddCoupon.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                new ShowMsg().createSnackbar(AddCoupon.this, ""+t.getMessage());
            }
        });


    }




//
//    class PaymentTask extends AsyncTask<String, String, String> {
//        ProgressDialog dialog = new ShowMsg().createProgressDialog(AddCoupon.this);
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"subject-payment.php?uid="+params[0]+"&subid="+params[1]+"&txn_id="+params[2]+"&amnt="+params[3]+"&coupon="+params[4]);
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
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createDialogFinishClass(AddCoupon.this , ""+message);
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(AddCoupon.this , ""+message);
//                }
//            }catch (Exception e){
//
//            }
//        }
//    }
//







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PAYPAL_BACK_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i("paymentExample111", confirm.toJSONObject().toString(4));
                        Log.i("paymentExample222", confirm.getPayment().toJSONObject()
                                .toString(4));

                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                        JSONObject response = jsonObject.getJSONObject("response");
                        String id = response.getString("id");
                        String state = response.getString("state");

                        //if(state.equals("approved")){
                        JSONObject jsonObject2 = new JSONObject(confirm.getPayment().toJSONObject()
                                .toString(4));
                        String amount = jsonObject2.getString("amount");

                        if(state.equalsIgnoreCase("approved")){
                            callPaymentMethod(pref.getId() , itemSubject.getId(), id, amount, editTextAddCoupon.getText().toString());
                           // new PaymentTask().execute(pref.getId() , itemSubject.getId(), id, amount, editTextAddCoupon.getText().toString());
                        }
                    //    return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID+"subject-payment.php?uid="+params[0]+"&subid="+params[1]+"&txn_id="+params[2]+"&amnt="+params[3]+"&coupon="+params[4]);


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
        Intent intent = new Intent(AddCoupon.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
        startService(intent);
    }





    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(AddCoupon.this, PayPalService.class));
    }


}
