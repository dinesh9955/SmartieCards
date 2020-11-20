package com.smartiecards.game;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.util.ShowMsg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AnaadIT on 2/26/2018.
 */

public class ResultGameActivity extends BaseAppCompactActivity {

    Button buttonCheckYourAnswers, buttonTryAgain;

   public static int BACK_TRY_AGAIN_RESULT = 654;


   TextView textViewQuestionCount , textViewPercent;


   int total_questions = 0 ;

    @Override
    protected int getLayoutResource() {
        return R.layout.result_game_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        buttonCheckYourAnswers  = (Button) findViewById(R.id.button5657568);
        buttonTryAgain  = (Button) findViewById(R.id.button6546);

        textViewQuestionCount = (TextView) findViewById(R.id.textView654);
        textViewPercent = (TextView) findViewById(R.id.textView46546);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Result");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        Bundle bundle = getIntent().getExtras();

        final ArrayList<ItemTimedGame> arrayList = ( ArrayList<ItemTimedGame>) bundle.getSerializable("arrKey");


        total_questions = arrayList.size();


        int rightQuestions = getRightQuestions(arrayList);

        textViewQuestionCount.setText(rightQuestions+" of "+total_questions);

//        final String ans = bundle.getString("ans");
//        final String ansCorrect = bundle.getString("ansCorrect");
//        int totalQuestions = bundle.getInt("totalQuestions");


//        textViewQuestionCount.setText(totalQuestions+" of "+totalQuestions);
        double perce = 0;
        try{
            perce = (rightQuestions * 100) / total_questions;

        }catch (Exception e){

        }

        textViewPercent.setText(perce+" %");


        buttonCheckYourAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultGameActivity.this , ResultCheckActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("arrKey", arrayList);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent();
            setResult(BACK_TRY_AGAIN_RESULT, intent );
            finish();
            }
        });

    }

    private int getRightQuestions(ArrayList<ItemTimedGame> arrayList) {
        int aa = 0;
        for(int i = 0; i < arrayList.size() ; i++){
            if(arrayList.get(i).answerCorrect == true){
                aa = aa + 1;
            }
        }
        return aa;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent();
            setResult(BACK_TRY_AGAIN_RESULT, intent );
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        setResult(BACK_TRY_AGAIN_RESULT, intent );
        finish();
    }
}
