package com.smartiecards.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by AnaadIT on 2/19/2018.
 */

public class GameTimed extends BaseAppCompactActivity {

    private static final String TAG = "GameTimed";
    private static final int GET_BACK_REFRESH_REQUEST = 643 ;
    Button buttonNext;
//
//    private static final String TAG = "GameTimed";
    ArrayList<ItemAnswers> itemAnswers = new ArrayList<ItemAnswers>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GameTimedAdapter mAdapter;
//
    TextView textViewTime, textViewMessage, textViewQue, textViewTotalQues;
    ProgressBar progressBar;
//
    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
    GameTimedTask subjectHomeTask = null;
    final Handler handler = new Handler();

    SavePref pref = new SavePref();

//    String keyId = "" , keyType ="";

    private static CountDownTimer countDownTimer;


    String isCorrectValue = "";
    String isAnswer = "";


    String gameSubjectId = "" , gameSubjectTopicId ="";

    int total_questions = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.game_card_timed;
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
//
        textViewTime = (TextView) findViewById(R.id.textView23535356);
        textViewMessage = (TextView) findViewById(R.id.textView123124);
        textViewQue = (TextView) findViewById(R.id.textView567456467);
        textViewTotalQues = (TextView) findViewById(R.id.textView96786878);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);
//
        buttonNext = (Button) findViewById(R.id.button414234);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        pref.SavePref(GameTimed.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameTimed.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
        mAdapter = new GameTimedAdapter(GameTimed.this, itemAnswers);
        mRecyclerView.setAdapter(mAdapter);


        Bundle bundle = getIntent().getExtras();

        gameSubjectId = bundle.getString("gameSubjectId");
        gameSubjectTopicId = bundle.getString("gameSubjectTopicId");


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
//                callRefreshMethod();
            }
        });
//
        callRefreshMethod();

//
//

        //startTime();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callRefreshMethod();

                stopCountdown();

             //  callRefreshMethod();


//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startTime();
//                    }
//                }, 50);

              //  startTimer(textViewTime, 120);




                if(isCorrectValue.equalsIgnoreCase("1")){
                    new ShowMsg().createSnackbar(GameTimed.this , "Your answer is correct.");
                }

                if(isCorrectValue.equalsIgnoreCase("0")){
                    new ShowMsg().createSnackbar(GameTimed.this , "Opps! your answer is wrong.");
                }


                Intent intent = new Intent(GameTimed.this , ResultGameActivity.class);
                intent.putExtra("que" , textViewQue.getText().toString());
                intent.putExtra("ans" , isAnswer);
                intent.putExtra("ansCorrect" , isCorrectValue);
                intent.putExtra("totalQuestions" , total_questions);
                startActivityForResult(intent, GET_BACK_REFRESH_REQUEST);

            }
        });


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
            String getMinutes = "2";//Get minutes from edittexf
            //Check validation over edittext
            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                startTimer(noOfMinutes);//start countdown
                //startTimer.setText(getString(R.string.stop_timer));//Change Text

            } else
                Toast.makeText(GameTimed.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
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

        boolean b = WSConnector.checkAvail(GameTimed.this);
        if (b == true) {
            handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMessage.setText("No internet connection.");
            textViewMessage.setVisibility(View.VISIBLE);
        }

    }






    private Runnable runnableAbout = new Runnable() {
        @Override
        public void run() {
            subjectHomeTask = new GameTimedTask();
            subjectHomeTask.execute(gameSubjectId, gameSubjectTopicId);
        }
    };









    @Override
    public void onDestroy()
    {
        super.onDestroy();
        handler.removeCallbacks(runnableAbout);
        if(subjectHomeTask != null){
            if (subjectHomeTask.getStatus() != AsyncTask.Status.FINISHED) {
                subjectHomeTask.cancel(false);
            }
        }


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
            swipeRefreshLayout.setRefreshing(false);
            Log.d(TAG, "ssss " + s);


            try{
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");
                if(status.equalsIgnoreCase("1")){
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String question = jsonObject1.getString("question");
                    textViewQue.setText(question);

                    String time = jsonObject1.getString("time");
                    textViewTime.setText("TIMED "+time);
                    startTime();

                    total_questions = jsonObject1.getInt("total_questions");
                    textViewTotalQues.setText("Total "+total_questions);


                    JSONArray jsonArray = jsonObject1.getJSONArray("result");

                    itemAnswers = new ParsingHelper().getItemAnswers(jsonArray);

                    Log.d(TAG, "itemAnswersssss " + itemAnswers.size());


                    if (itemAnswers.size() == 0) {
                        textViewMessage.setText("No subject found.");
                        textViewMessage.setVisibility(View.VISIBLE);
                    } else {
                        textViewMessage.setVisibility(View.GONE);
                    }


                    mAdapter.updateData(itemAnswers);


                }
            }catch (Exception e){

            }






        }
    }







    public class GameTimedAdapter extends RecyclerView.Adapter<GameTimedAdapter.ViewHolder> {
        private RadioButton lastCheckedRB = null;

        int lastPos =  - 1;

        private static final String TAG = "GameTimedAdapter";
        ArrayList<ItemAnswers> arrayList = new ArrayList<ItemAnswers>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GameTimedAdapter(Activity context11, ArrayList<ItemAnswers> arrayList111) {
            super();
            this.context = context11;
            arrayList = arrayList111;
            // FacebookSdk.sdkInitialize(context);

            try{
                imageLoader = ImageLoader.getInstance();

                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.picture_default)
                        .showImageForEmptyUri(R.drawable.picture_default)
                        .showImageOnFail(R.drawable.picture_default)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
//                        .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
                        .build();
            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }


        }

        @Override
        public GameTimedAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_game_time_item, viewGroup, false);
            GameTimedAdapter.ViewHolder viewHolder = new GameTimedAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GameTimedAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.radioButton.setText(""+arrayList.get(i).getAnswer());

            viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e(TAG , "radioButton111 " +i);

                    if(lastCheckedRB != null){
                        lastCheckedRB.setChecked(false);
                    }
                    lastCheckedRB = viewHolder.radioButton;
                    
                    
                    setAnswerForCheck(arrayList.get(i).getIs_correct(), arrayList.get(i).getAnswer());
                    
                }
            });


        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;
            //  public ImageView imageViewImage;
            public TextView textViewQuestion, textViewAnswer;

            //  Button buttonBuyNow;
//            public RelativeLayout layout;

            //     CardView cardViewClick;

            RadioButton radioButton;

            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

//                textViewQuestion = (TextView) itemView.findViewById(R.id.textView25345);
//                textViewAnswer = (TextView) itemView.findViewById(R.id.textView2786867);
                radioButton = (RadioButton) itemView.findViewById(R.id.offer_select);


            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }
        }


        public void updateData(ArrayList<ItemAnswers> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }
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
