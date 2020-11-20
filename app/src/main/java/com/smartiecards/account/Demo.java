package com.smartiecards.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.smartiecards.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by AnaadIT on 2/8/2018.
 */

public class Demo extends AppCompatActivity{

    private static final String TAG = "Demo";
    Button button;


    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final String CONFIG_CLIENT_ID = "Adm6TFnlaupWzFEB85Tcly2kaTmc8Ei4gCKLMSslRopUr6z69kFs4gUh420yFVke-3J5mlzHCz0hs-r1";


    public static final int PAYPAL_REQUEST_CODE = 123;


    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
//	.acceptCreditCards(true)
            .merchantName("Flash Card").languageOrLocale("en")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.demo11);
//
//        button = (Button) findViewById(R.id.button2124234);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PayPalPayment payment = new PayPalPayment(new BigDecimal(""+convertValueStringToDouble("12.45")), "USD", "Premimum Plan Fee",
////
//////                        PayPalPayment payment = new PayPalPayment(new BigDecimal("1"), "USD", "Premimum Plan Fee",
////                        PayPalPayment.PAYMENT_INTENT_SALE);
////                Intent intent = new Intent(Demo.this, PaymentActivity.class);
////                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
////                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
////                startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//
//
//
//
//
//
//
//
//            }
//        });
//
//
//
//
//
//
//        final TextView tx = (TextView) findViewById(R.id.textViewName);
//        tx.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        tx.setSelected(true);
//        tx.setSingleLine(true);
//        tx.setText("Marquee he layout at all, as though it were not there.");
//
////        TranslateAnimation tanim = new TranslateAnimation(
////                TranslateAnimation.ABSOLUTE, 1.0f * 400,
////                TranslateAnimation.ABSOLUTE, -1.0f * 500,
////                TranslateAnimation.ABSOLUTE, 0.0f,
////                TranslateAnimation.ABSOLUTE, 0.0f);
////        tanim.setDuration(1000);
////        tanim.setInterpolator(new LinearInterpolator());
////        tanim.setRepeatCount(Animation.INFINITE);
////        tanim.setRepeatMode(Animation.ABSOLUTE);
////
////        tx.startAnimation(tanim);



//        setContentView(R.layout.android_marquee_example);
//
//        TextView marque = (TextView) this.findViewById(R.id.marque_scrolling_text);
//        marque.setSelected(true);
//
//        TextView marque1 = (TextView) this.findViewById(R.id.sliding_text_marquee);
//        marque1.setSelected(true);
//
//        marque1.setText(Html.fromHtml("<b><marquee behavior='scroll' direction='left'>dfff</marquee><br/>"));




        String cc = "\u00a0#ff5c33";

        Log.e(TAG, "ccCCC "+cc.replace("\u00a0", ""));







    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(Demo.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }





    @Override
    public void onDestroy()
    {
       stopService(new Intent(Demo.this, PayPalService.class));
        super.onDestroy();
    }









    private String convertValueStringToDouble(String valueFirst) {
        try{
            double time = Double.parseDouble(valueFirst);
            DecimalFormat df = new DecimalFormat("0.00");
            //DecimalFormat df = new DecimalFormat("##.##");
            return ""+df.format(time);
        }catch (Exception e){

        }
        return "0.0";
    }













    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PAYPAL_REQUEST_CODE){
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




}
