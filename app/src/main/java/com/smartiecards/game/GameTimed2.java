package com.smartiecards.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameTimed2 extends BaseAppCompactActivity {

    private static final String TAG = "GameTimed";
    private static final int GET_BACK_REFRESH_REQUEST = 643 ;
    Button buttonNext;

    ArrayList<ItemTimedGame> arrayList = new ArrayList<ItemTimedGame>();

//    RecyclerView mRecyclerView;
//    RecyclerView.LayoutManager mLayoutManager;
//    GameTimedAdapter mAdapter;

    TextView textViewTime, textViewMessage, textViewQue, textViewTotalQues;
    ProgressBar progressBar;
    //
//    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    GameTimedTask subjectHomeTask = null;
//    final Handler handler = new Handler();

//    SavePref pref = new SavePref();

//    String keyId = "" , keyType ="";

    private static CountDownTimer countDownTimer;


    String isCorrectValue = "";
    String isAnswer = "";


    String gameSubjectId = "" , gameSubjectTopicId ="", timeAA = "0:0";

  //  int timeA = 0;

    int total_questions = 0;

    int questionCounter = 0;

    RadioButton radioButton1, radioButton2;

    RelativeLayout relativeLayout;


    String stringGiveAnswer = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.game_card_timed2;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Game Timed");

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
////
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//
        textViewTime = (TextView) findViewById(R.id.textView23535356);
        textViewMessage = (TextView) findViewById(R.id.textView123124);
        textViewQue = (TextView) findViewById(R.id.textView567456467);
        textViewTotalQues = (TextView) findViewById(R.id.textView96786878);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);
//
        buttonNext = (Button) findViewById(R.id.button414234);

        radioButton1 = (RadioButton) findViewById(R.id.offer_select1);
        radioButton2 = (RadioButton) findViewById(R.id.offer_select2);

        relativeLayout = (RelativeLayout) findViewById(R.id.rela1654667);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

//        pref.SavePref(GameTimed2.this);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameTimed2.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
//        mAdapter = new GameTimedAdapter(GameTimed2.this, itemAnswers);
//        mRecyclerView.setAdapter(mAdapter);


        Bundle bundle = getIntent().getExtras();

        gameSubjectId = bundle.getString("gameSubjectId");
        gameSubjectTopicId = bundle.getString("gameSubjectTopicId");

        timeAA = bundle.getString("keyTime");

       // Log.e(TAG, "timeAAA "+timeAA);

        callRefreshMethod();



        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
                stringGiveAnswer = ""+arrayList.get(questionCounter).getOp1();
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(false);
                radioButton2.setChecked(true);
                stringGiveAnswer = ""+arrayList.get(questionCounter).getOp2();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  stopCountdown();
                if(stringGiveAnswer.equalsIgnoreCase("")){
                    new ShowMsg().createSnackbar(GameTimed2.this , "Select anyone option.");
                }else{
                    loadQuestion();
                    stringGiveAnswer = "";
                }
            }
        });

    }

    private void loadQuestion() {
        total_questions = arrayList.size() - 1;

        Log.e(TAG , "questionCounter111: "+questionCounter);
        if(questionCounter != total_questions){
            ItemTimedGame timedGameOLD = arrayList.get(questionCounter);
            timedGameOLD.setGivenAnswer(stringGiveAnswer);
            if(stringGiveAnswer.equalsIgnoreCase(timedGameOLD.getCorrect())){
                timedGameOLD.setAnswerCorrect(true);
            }else{
                timedGameOLD.setAnswerCorrect(false);
            }


            questionCounter ++;
            setQuestionAns();
        }else{
//                    questionCounter = 0;
//                    setQuestionAns();


            ItemTimedGame timedGameOLD = arrayList.get(questionCounter);
            timedGameOLD.setGivenAnswer(stringGiveAnswer);
            if(stringGiveAnswer.equalsIgnoreCase(timedGameOLD.getCorrect())){
                timedGameOLD.setAnswerCorrect(true);
            }else{
                timedGameOLD.setAnswerCorrect(false);
            }


            Log.e(TAG , "questionCounter222: "+questionCounter);
            //new ShowMsg().createToast(GameTimed2.this , "Ok");


            for(int i = 0 ; i < arrayList.size() ; i++){
                Log.e(TAG , "arrayListSSS "+arrayList.get(i).getGivenAnswer());
            }


            stopCountdown();

            goToNext();


        }

    }

    private void goToNext() {

        stopCountdown();
        stringGiveAnswer = "";
        questionCounter = 0;

        Intent intent = new Intent(GameTimed2.this , ResultGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("arrKey", arrayList);
        intent.putExtras(bundle);
        startActivityForResult(intent, GET_BACK_REFRESH_REQUEST);
    }


    private static int sec = 60*2;  // this is for 2 for min TODO replace your sec

    public void startTimer(final TextView textView ,int sec) {

        CountDownTimer downTimer= new CountDownTimer(1000 * sec, 1000) {

            public void onTick(long millisUntilFinished) {


                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                textView.setText(v + ":" + String.format("%02d", va));
            }

            public void onFinish() {
                textView.setText("done!");
            }
        };
        downTimer.start();

    }




    private void startTime() {

        if (countDownTimer == null) {
            String getMinutes = timeAA;//Get minutes from edittexf
            //Check validation over edittext
            if (!getMinutes.equals("") || getMinutes.length() > 0 || getMinutes.contains(":")) {

                Log.e(TAG, "timeAA "+timeAA);

                int noOfMinutes = 0;
                try{
                    String timeArr[] = getMinutes.split(":");

                    String timeHH = timeArr[0];
                    String timeMM = timeArr[1];

                    int hhh = Integer.parseInt(timeHH);
                    int mmm = Integer.parseInt(timeMM);

                    noOfMinutes = ((hhh * 60) + mmm) * 1000;


                }catch (Exception e){

                }

                //int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                startTimer(noOfMinutes);//start countdown
                //startTimer.setText(getString(R.string.stop_timer));//Change Text

            } else
                Toast.makeText(GameTimed2.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
        } else {
            //Else stop timer and change text

            stopCountdown();
            //startTimer.setText(getString(R.string.start_timer));
        }


    }






    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d",  TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                textViewTime.setText("TIMED "+hms);//set text
            }

            public void onFinish() {

                textViewTime.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                //startTimer.setText(getString(R.string.start_timer));//Change button text


                goToNext();

            }
        }.start();

    }





    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }





    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(GameTimed2.this);
        if (b == true) {
//            handler.postDelayed(runnableAbout, 50);
            callApiMethod();
        } else {
            textViewMessage.setText("No internet connection.");
            textViewMessage.setVisibility(View.VISIBLE);
        }

    }






//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new GameTimedTask();
//            subjectHomeTask.execute(gameSubjectId, gameSubjectTopicId);
//        }
//    };





    private void callApiMethod() {

        progressBar.setVisibility(View.VISIBLE);
        call = apiInterface.gamesTimed(gameSubjectId, gameSubjectTopicId);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        arrayList = new ParsingHelper().getItemTimedGame(responseCode);


                        if(arrayList.size() != 0){
                            setQuestionAns();

                            startTime();
                        }else{

                        }

                    }else{
                        //     new ShowMsg().createSnackbar(getActivity(), "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // new ShowMsg().createSnackbar(getActivity(), ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                // new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });




    }









    @Override
    public void onDestroy()
    {
        super.onDestroy();
//        handler.removeCallbacks(runnableAbout);
//        if(subjectHomeTask != null){
//            if (subjectHomeTask.getStatus() != AsyncTask.Status.FINISHED) {
//                subjectHomeTask.cancel(false);
//            }
//        }


        stopCountdown();

    }






    class GameTimedTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "games-timed.php?subjectid="+params[0]+"&topicid="+params[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
            Log.d(TAG, "ssss " + s);


            arrayList = new ParsingHelper().getItemTimedGame(s);


            if(arrayList.size() != 0){
                setQuestionAns();

                startTime();
            }else{

            }

        }
    }

    private void setQuestionAns() {
        ItemTimedGame timedGame = arrayList.get(questionCounter);
        textViewQue.setText(Html.fromHtml(timedGame.getText()));

        Log.e(TAG , "textViewQue:: "+timedGame.getText());



        radioButton1.setText(""+timedGame.getOp1());
        radioButton2.setText(""+timedGame.getOp2());

        if(Utility.isColor(timedGame.getBackcolor()) == true){
            int color = Utility.replaceColor(timedGame.getBackcolor());
            relativeLayout.setBackgroundColor(color);
        }



        if(Utility.isColor(timedGame.getFontsize()) == true){
            int color = Utility.replaceColor(timedGame.getFontsize());
            textViewQue.setTextColor(color);
            radioButton1.setTextColor(color);
            radioButton2.setTextColor(color);
        }




        int xx = questionCounter + 1;

        textViewTotalQues.setText("Total: "+xx+"/"+arrayList.size());

        radioButton1.setChecked(false);
        radioButton2.setChecked(false);

        radioButton1.setVisibility(View.VISIBLE);
        radioButton2.setVisibility(View.VISIBLE);

        textViewQue.setVisibility(View.VISIBLE);
    }



    private void setAnswerForCheck(String is_correct, String ans) {
        isCorrectValue = is_correct;
        isAnswer = ans;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_BACK_REFRESH_REQUEST){
            if(resultCode == ResultGameActivity.BACK_TRY_AGAIN_RESULT){
                callRefreshMethod();
            }else{

            }
        }else{

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
