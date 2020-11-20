package com.smartiecards.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

//import com.github.florent37.longshadow.LongShadow;
import com.eftimoff.viewpagertransformers.FlipHorizontalTransformer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.ImageZoom;
import com.smartiecards.flipview.AnimationFactory;
import com.smartiecards.flipview.FlipAnimation;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/1/2018.
 */

public class FlashCardFlip extends BaseAppCompactActivity{

//    boolean screenFlip = false;

    ViewAnimator viewAnimator;

//    private final Handler mHandlerFlip = new Handler(Looper.getMainLooper());

    boolean alreadyFlip = false;

    private static final String TAG = "FlashCardFlip";

    TextView textViewSeekValue;

    private ArrayList<ItemCardFlip> arrayList = new ArrayList<ItemCardFlip>();
    //    private ArrayList<ItemCardFlip> arrayListSuffle = new ArrayList<ItemCardFlip>();
    private static ViewPager mPager;


    boolean isScroll = false;

//    Button buttonPlay, buttonSuffle;

    SeekBar seekBar;

    MyAdapter adapter;



    //    FlashCardFlipTask subjectHomeTask = null;
    //final Handler handler = new Handler();

//    SavePref pref = new SavePref();

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    String subjectKey = "" , subjectKey2 ="", subjectName = "", classKey ="", colorKey = "#00000000";

    ImageView buttonPrev, buttonNext, imageViewStar;

    int nextPrevCount = 0;
    int nextPrevCountPlus = 0;

    int nextPrevCountPlusIndicate = 0;

    boolean dialogOpen = false;

    Button buttonAddStar, buttonViewStarCard, buttonUnStar;


    boolean isViewStarCard = false;


    RelativeLayout relativeLayoutBottomStar, relativeLayoutCenter;

  //  LongShadow longShadow;

    ArrayList<ItemSubjectTopic> arrayListMain = new ArrayList<ItemSubjectTopic>();
    int positionMain = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.flash_card_flip;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        subjectKey2 = getString(R.string.app_name);

        textViewSeekValue = (TextView) findViewById(R.id.textView25345);

//        buttonPlay = (Button) findViewById(R.id.button543452);
//        buttonSuffle = (Button) findViewById(R.id.button3547567);

        seekBar = (SeekBar) findViewById(R.id.seekBar235435);
        mPager = (ViewPager) findViewById(R.id.pager);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

        buttonPrev = (ImageView) findViewById(R.id.button276878);
        buttonNext = (ImageView) findViewById(R.id.button3657);

        buttonAddStar = (Button) findViewById(R.id.button25345646);
        buttonViewStarCard = (Button) findViewById(R.id.button253456546);
        buttonUnStar = (Button) findViewById(R.id.button65465);

        imageViewStar = (ImageView) findViewById(R.id.button2534565);

        relativeLayoutBottomStar = (RelativeLayout) findViewById(R.id.relative23534564);

        relativeLayoutBottomStar.setVisibility(View.GONE);

//        relativeLayoutCenter = (RelativeLayout) findViewById(R.id.relative2534554);


//        longShadow = (LongShadow) findViewById(R.id.rl34244);
////        longShadow.setShadowAngle(45);
////        longShadow.setShadowColor(Color.RED);
//        longShadow.setBackgroundColor(Color.TRANSPARENT);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
//        textViewTitleBar.setText("English/CLASSIFICATION OF LIVING THINGS");



//        pref.SavePref(FlashCardFlip.this);


        Bundle bundle = getIntent().getExtras();

        arrayListMain = (ArrayList<ItemSubjectTopic>) bundle.getSerializable("arrayList");
        positionMain = bundle.getInt("position");


//        itemSubjectTopic = arrayListMain.get(positionMain);
//
//        subjectKey = getCallingPackage()

//        subjectKey = bundle.getString("key");
//        subjectKey2 = bundle.getString("key2");
//        colorKey = bundle.getString("color");

//        subjectName = bundle.getString("subjectName");
//        classKey = bundle.getString("classKey");
        // textViewTitleBar.setText(classKey+"/"+subjectName+" Topics/"+subjectKey2);
        textViewTitleBar.setText(subjectKey2);





        adapter = new MyAdapter(FlashCardFlip.this, arrayList, colorKey);
        mPager.setAdapter(adapter);
           // mPager.setPageTransformer(true, new FlipHorizontalTransformer());

//        mPager.setId(nextPrevCount);


        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "page selected " + position);
                alreadyFlip = false;
                nextPrevCount = position;
               // nextPrevCountPlus = position;

                if(arrayList.size() != 0){
                    textViewSeekValue.setText(arrayList.get(position).getNumber()+"/"+(arrayList.size()));
                    seekBar.setProgress(position);

                    if(arrayList.size() != 0){
                        setStar(arrayList.get(nextPrevCount));
                    }

                }else{
                    textViewSeekValue.setText("0/"+(arrayList.size()));
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e(TAG, fromUser + " onProgressChanged "+progress);
                if(fromUser == true){
                    mPager.setCurrentItem(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e(TAG, "onStartTrackingTouch "+seekBar.getId());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }



        });

//
//        seekBar.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });




        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyFlip = false;
                nextPrevCount = mPager.getCurrentItem();
               // nextPrevCountPlus = mPager.getCurrentItem();

                if(arrayList.size() != 0){
                    if(nextPrevCount == arrayList.size() -1){
                    }else{
                        nextPrevCount ++;
                       // nextPrevCountPlus ++;

                        mPager.setCurrentItem(nextPrevCount);
                    }


                    if(arrayList.size() != 0){
                        setStar(arrayList.get(nextPrevCount));
                    }
                }
            }
        });


        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyFlip = false;
                nextPrevCount = mPager.getCurrentItem();
             //   nextPrevCountPlus = mPager.getCurrentItem();

                if(arrayList.size() != 0){
                    if(nextPrevCount == 0){
                    }else{
                        nextPrevCount --;
                     //   nextPrevCountPlus --;
                        mPager.setCurrentItem(nextPrevCount);
                    }


                    if(arrayList.size() != 0){
                        setStar(arrayList.get(nextPrevCount));
                    }
                }
            }
        });






        buttonAddStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayList.size() > 0){
                    ItemCardFlip cardFlip = arrayList.get(nextPrevCount);
//                    new AddStarTask(cardFlip).execute();

                    callStarApiMethod(cardFlip);
                }
//                ItemCardFlip cardFlip = arrayList.get(nextPrevCount);
//                cardFlip.setStar_status("1");

//                new AddStarTask().execute(pref.getId() , cardFlip.getSubjectid(), cardFlip.getCardid(), cardFlip.getId(), "cards");
//
//                if(arrayList.size() != 0){
//                    setStar(arrayList.get(nextPrevCount));
//                }

            }
        });


        buttonViewStarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonAddStar.setVisibility(View.GONE);
                buttonViewStarCard.setVisibility(View.GONE);
                buttonUnStar.setVisibility(View.VISIBLE);

                isViewStarCard = true;

                nextPrevCount = 0;
                //nextPrevCountPlus = 0;
                callRefreshMethod();

            }
        });



        buttonUnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size() > 0){
                    ItemCardFlip cardFlip = arrayList.get(nextPrevCount);
//                    new UnStarTask(cardFlip).execute();

                    callUnStarApiMethod(cardFlip);
                }
            }
        });




        imageViewStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG , "imageViewStar");

                if(arrayList.size() > 0){

                    ItemCardFlip cardFlip = arrayList.get(nextPrevCount);

                    if(cardFlip.getStar_status().equalsIgnoreCase("1")){
                        imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
                        callUnStarApiMethod(cardFlip);
                    }else{
                        imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
                        callStarApiMethod(cardFlip);
                    }


                    imageViewStar.setVisibility(View.VISIBLE);


                }

            }
        });


        callRefreshMethod();




    }






    public void showView(int position) {
        Log.e(TAG , "showView:: "+position);
//        textViewSeekValue.setText(position+"/"+(arrayList.size()));
    }








    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(FlashCardFlip.this);
        if (b == true) {
            callFlashCardFlipApiMethod();
            // handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }







    private void callFlashCardFlipApiMethod() {

        nextPrevCountPlusIndicate = 0;

        if((positionMain > arrayListMain.size() -1)){
            //new ShowMsg().createToast(FlashCardFlip.this , "Empty");
            noCardDialog();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            ItemSubjectTopic itemSubjectTopic = arrayListMain.get(positionMain);

            colorKey = itemSubjectTopic.getColor();
            subjectKey2 = itemSubjectTopic.getFtopic();

            textViewTitleBar.setText(subjectKey2);

            call = apiInterface.topicQueAns(itemSubjectTopic.getId(), pref.getId() , "cards");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressBar.setVisibility(View.GONE);
                    String responseCode = "";
                    try {
                        if(response.body() != null) {
                            responseCode = response.body().string();


                            nextPrevCount = 0;
                            //nextPrevCountPlus = 0;

                            if(isViewStarCard == true){
                                arrayList = new ParsingHelper().getItemTopicQueAnsOnlyStar(responseCode);
                            }else{
                                arrayList = new ParsingHelper().getItemTopicQueAns(responseCode);
                            }






                            if (arrayList.size() == 0) {
                                Log.e(TAG , "ifffffffffff0000000");
                                textViewMsg.setVisibility(View.VISIBLE);

                                if(isViewStarCard == true){
                                    textViewMsg.setText("No Cards have been Starred as yet.");
                                }else{
                                    textViewMsg.setText("No Question Found.");
                                }
                                relativeLayoutBottomStar.setVisibility(View.GONE);
                            } else {
                                textViewMsg.setVisibility(View.GONE);
                                seekBar.setMax(arrayList.size() - 1);
                                relativeLayoutBottomStar.setVisibility(View.VISIBLE);

                                if(isViewStarCard == true){
                                    buttonAddStar.setVisibility(View.GONE);
                                    buttonViewStarCard.setVisibility(View.GONE);
                                    buttonUnStar.setVisibility(View.VISIBLE);
                                }else{
                                    buttonAddStar.setVisibility(View.VISIBLE);
                                    buttonViewStarCard.setVisibility(View.VISIBLE);
                                    buttonUnStar.setVisibility(View.GONE);
                                }
                            }


                            adapter = new MyAdapter(FlashCardFlip.this, arrayList, colorKey);
                            mPager.setAdapter(adapter);

                            adapter.updateData(arrayList);

//                            mPager.setId(nextPrevCount);

                            //Log.e(TAG , "SCROLLING_RUNNABLE_ADS "+SCROLLING_RUNNABLE_ADS);

//                            if(isScroll == false){
//                                isScroll = true;
//                                mHandler.postDelayed(SCROLLING_RUNNABLE_ADS, 10000);
//                            }




                            if(arrayList.size() != 0){
                                setStar(arrayList.get(nextPrevCount));
                                mPager.setVisibility(View.VISIBLE);
                                Log.e(TAG , "ifffffffffff11111 "+arrayList.size());
                                //seekBar.setMax(arrayList.size());
                                textViewSeekValue.setText("0/"+(arrayList.size()));
                            }else{
                                Log.e(TAG , "ifffffffffff2222222");
                                mPager.setVisibility(View.GONE);
                                textViewSeekValue.setText("0/"+(arrayList.size()));
                                seekBar.setMax(arrayList.size());
                            }





                            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    Log.e(TAG, "page selectedAAAA " + position);
                                    alreadyFlip = false;
                                    nextPrevCount = position;
                                    //nextPrevCountPlus = position;

                                    if(arrayList.size() != 0){
                                        textViewSeekValue.setText(arrayList.get(position).getNumber()+"/"+(arrayList.size()));
                                        seekBar.setProgress(position);

                                        if(arrayList.size() != 0){
                                            setStar(arrayList.get(nextPrevCount));
                                        }

                                        nextPrevCountPlusIndicate = 1;
                                    }else{
                                        textViewSeekValue.setText("0/"+(arrayList.size()));
                                    }
                                }

                                @Override
                                public void onPageSelected(int position) {
                                    Log.i(TAG, "onPageSelected " + position);
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });

                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);

                }
            });


        }




    }

    private void noCardDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(FlashCardFlip.this);
        builder.setTitle(getString(R.string.app_name));
                builder.setMessage("You have no Subject Topic.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//                        dialogOpen = false;
//					}
//				});



        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new FlashCardFlipTask();
//            subjectHomeTask.execute(""+subjectKey, pref.getId() , "cards");
//        }
//    };



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        isScroll = false;
        if(SCROLLING_RUNNABLE_ADS != null){
            mHandler.removeCallbacks(SCROLLING_RUNNABLE_ADS);
        }

        if(SCROLLING_RUNNABLE_ADSCard != null){
            mHandlerCard.removeCallbacks(SCROLLING_RUNNABLE_ADSCard);
        }

//        handler.removeCallbacks(runnableAbout);
//        if(subjectHomeTask != null){
//            if (subjectHomeTask.getStatus() != AsyncTask.Status.FINISHED) {
//                subjectHomeTask.cancel(false);
//            }
//        }

    }






    private void callStarApiMethod(ItemCardFlip cardFlip11 ) {

        progressBar.setVisibility(View.VISIBLE);

        call = apiInterface.addStarCard(pref.getId(), cardFlip11.getSubjectid() , cardFlip11.getCardid(), cardFlip11.getId(), "cards");

        ItemCardFlip cardFlip = cardFlip11;


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        JSONObject jsonObject = new JSONObject(responseCode);
                        String status = jsonObject.getString("status");
                        if(status.equalsIgnoreCase("1")){
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(FlashCardFlip.this , ""+message);

                            cardFlip.setStar_status("1");
                            if(arrayList.size() > 0){
                                setStar(arrayList.get(nextPrevCount));
                            }

                        }else{
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(FlashCardFlip.this , ""+message);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

            }
        });




    }









    private void callUnStarApiMethod(ItemCardFlip cardFlip11 ) {

        progressBar.setVisibility(View.VISIBLE);

        call = apiInterface.unstarCard(pref.getId(), cardFlip11.getSubjectid() , cardFlip11.getCardid(), cardFlip11.getId(), "cards");

        ItemCardFlip cardFlip = cardFlip11;


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        JSONObject jsonObject = new JSONObject(responseCode);
                        String status = jsonObject.getString("status");
                        if(status.equalsIgnoreCase("1")){
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(FlashCardFlip.this , ""+message);
                            cardFlip.setStar_status("0");
                            if(arrayList.size() != 0){
                                setStar(arrayList.get(nextPrevCount));
                            }
                        }else{
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(FlashCardFlip.this , ""+message);
                        }
                        callRefreshMethod();

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

            }
        });

    }


    private void setStar(ItemCardFlip itemCardFlip) {
       // Log.e(TAG, "setStar " + itemCardFlip.getId());
        if(itemCardFlip.getStar_status().equalsIgnoreCase("1")){
            imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
            imageViewStar.setVisibility(View.VISIBLE);
        }else{
            imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
            imageViewStar.setVisibility(View.VISIBLE);
        }


        buttonGrayed(nextPrevCount);

    }



    private void buttonGrayed(int nextPrevCount) {

        Log.e(TAG , nextPrevCount+ " buttonGrayed "+nextPrevCountPlus);

        if(nextPrevCount == 0){
            buttonPrev.setImageResource(R.drawable.backgray4);
        }else{
            buttonPrev.setImageResource(R.drawable.back4);
        }





        if((arrayList.size() - 1) == nextPrevCount){
            if(nextPrevCountPlus > nextPrevCount){
                Log.e(TAG , nextPrevCount+" OOOOOOOOOOO "+nextPrevCountPlus);
                if(nextPrevCountPlusIndicate == 1){
                    if(dialogOpen == false){
                        openDialogLast();
                        dialogOpen = true;
                    }
                }
            }else{
                Log.e(TAG , nextPrevCount+" DDDDDDDDDDDD "+nextPrevCountPlus);
                nextPrevCountPlus = nextPrevCountPlus + 5;
            }
        }else{
            nextPrevCountPlus = nextPrevCount;
        }



//
//
//        adapter.upDateFlip(alreadyFlip);


      //  mPager.DDD();



//        mPager.setId(nextPrevCount);
//
//        mHandler.removeCallbacks(SCROLLING_RUNNABLE_FLIP);
//        mHandler.postDelayed(SCROLLING_RUNNABLE_FLIP, 3000);
//        mHandlerFlip.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG , "11111111");
//            }
//        },1000);



    }


//
//    private final Runnable SCROLLING_RUNNABLE_FLIP = new Runnable() {
//        @Override
//        public void run() {
//           if (alreadyFlip == false){
//               alreadyFlip = true;
//               Log.e(TAG , "11111111111");
//               adapter.upDateFlip(true);
//           }
//        }
//    };

    private void openDialogLast() {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(FlashCardFlip.this);
                builder.setTitle(getString(R.string.app_name));

        final CharSequence[] items = { "Redo", "Next Topic", "Subject Topic", "Top Subjects" };
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                switch (item) {
                    case 0:
                        callRefreshMethod();
                        break;
                    case 1:
                        positionMain++;
                        callRefreshMethod();
                        break;
                    case 2:
//                        Intent i = new Intent(FlashCardFlip.this, MainActivity.class);
//                        i.putExtra("keyPosition" , 0);
//                        startActivity(i);
//                        finishAffinity();
                        finish();
                        break;
                    case 3:
                        Intent ii = new Intent(FlashCardFlip.this, MainActivity.class);
                        ii.putExtra("keyPosition" , 0);
                        startActivity(ii);
                        finishAffinity();
                        finish();
                        break;
                    case 4:
                        break;
                }

                dialogOpen = false;
            }
        });
        
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int item) {
//
//                // will toast your selection
//               // showToast("Name: " + items[item]);
//                dialog.dismiss();
//
//            }
//        });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.setCancelable(true);
      //  alert.setCanceledOnTouchOutside(true);
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogOpen = false;
            }
        });
        alert.show();



    }




    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(isViewStarCard == true){
            isViewStarCard = false;
            callRefreshMethod();
        }else{
            finish();
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);//Menu Resource, Menu
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(isViewStarCard == true){
                isViewStarCard = false;
                callRefreshMethod();
            }else{
                finish();
            }
        }

        if(item.getItemId() == R.id.bar_icon_id){
            View view = findViewById(R.id.bar_icon_id);
//            registerForContextMenu(view);
            showPopup(view);
        }

        return super.onOptionsItemSelected(item);
    }




    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.bar_menu_context, popup.getMenu());
        popup.show();


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

//                if(item.getItemId() == R.id.help){
//                    Log.e(TAG , "onMenuItemClickhelp");
//                }

                if(item.getItemId() == R.id.view_starred_card){
                    Log.e(TAG , "onMenuItemClickview_starred_card");

                    isViewStarCard = true;
                    nextPrevCount = 0;
                    //nextPrevCountPlus = 0;
                    callRefreshMethod();
                }
                return false;
            }
        });

    }





    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE_ADS = new Runnable() {
        @Override
        public void run() {
//            mPager.smoothScrollBy(600, 0);
            mPager.setCurrentItem(nextPrevCount, true);
            mHandler.postDelayed(this, 15000);

            setItemByPosition();
        }
    };

    private void setItemByPosition() {
        nextPrevCount = mPager.getCurrentItem();
      //  nextPrevCountPlus = mPager.getCurrentItem();
        if(arrayList.size() != 0){
            if(nextPrevCount == arrayList.size() -1){
            }else{
                nextPrevCount ++;
                //nextPrevCountPlus ++;
                mPager.setCurrentItem(nextPrevCount);
            }

            if(arrayList.size() != 0){
                setStar(arrayList.get(nextPrevCount));
            }
        }
    }



















    public class MyAdapter extends PagerAdapter {

        private static final String TAG = "MyAdapter";

//        ViewAnimator viewAnimator;
        boolean alreadyFlip = false;

        private ArrayList<ItemCardFlip> arrayList = new ArrayList<ItemCardFlip>();
        private LayoutInflater inflater;
        private Activity context;


        ImageLoader imageLoader;
        DisplayImageOptions options;

        String colorK = "#00000000";


        boolean isStarShow = false;

        int starPosition = 0;


        public MyAdapter(Activity context, ArrayList<ItemCardFlip> images, String col) {
            this.context = context;
            this.arrayList=images;
            colorK = col;

            inflater = LayoutInflater.from(context);


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
//                    .displayer(new CircleBitmapDisplayer(Color.parseColor("#efddb4"), 3))
                        .build();

            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }



        @Override
        public Object instantiateItem(ViewGroup view,final int position) {
            View myImageLayout = inflater.inflate(R.layout.home_slide_item, view, false);

            ScrollView scrollView1 = (ScrollView) myImageLayout.findViewById(R.id.firstScroll);
            ScrollView scrollView2 = (ScrollView) myImageLayout.findViewById(R.id.secondScroll);

            LinearLayout linearLayout1 = (LinearLayout) myImageLayout.findViewById(R.id.firstLinear);
            LinearLayout linearLayout2 = (LinearLayout) myImageLayout.findViewById(R.id.secondLinear);

            RelativeLayout relativeLayout1 = (RelativeLayout) myImageLayout.findViewById(R.id.first);
            RelativeLayout relativeLayout2 = (RelativeLayout) myImageLayout.findViewById(R.id.second);



            ViewAnimator viewAnimator = (ViewAnimator)myImageLayout.findViewById(R.id.viewFlipper);
            final ViewFlipper viewFlipper = (ViewFlipper)myImageLayout.findViewById(R.id.viewFlipper);

            Log.e(TAG, "arrayList "+colorK);









            if(Utility.isColor(colorK) == true){
                int color = Utility.replaceColor(colorK);

                relativeLayout1.setBackgroundColor(color);
                relativeLayout2.setBackgroundColor(color);
            }



            relativeLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListAAA "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
                }
            });


            relativeLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListBBB "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showPrevious();
                    //  relativeLayoutClickToFLip.setVisibility(View.GONE);
                }
            });










            scrollView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListAAA "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
                }
            });



            scrollView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListAAA "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showNext();
                    //  relativeLayoutClickToFLip.setVisibility(View.GONE);
                }
            });






            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListAAA "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
                }
            });



            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "arrayListAAA "+position);
                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showNext();
                    // relativeLayoutClickToFLip.setVisibility(View.GONE);
                }
            });



//        if(isFlip == true){
//            isFlip = false;
////            viewAnimator.setDuration(700);
////            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//            Log.e(TAG , "isFlip "+isFlip);
////            viewFlipper.setInAnimation(context, android.R.anim.slide_out_right);
////            viewFlipper.setFlipInterval(2000);
////            viewFlipper.startFlipping();
////            viewFlipper.showNext();
////            viewFlipper.setAutoStart(true);
//            //viewAnimator.setAnimateFirstView(true);
//
////            Animation imgAnimationOut = AnimationUtils.
////                    loadAnimation(context, android.R.anim.slide_out_right);
////            imgAnimationOut.setDuration(700);
////            viewAnimator.setOutAnimation(imgAnimationOut);
//
//        }




            TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
            TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);

            textViewQues.setText(arrayList.get(position).getFormula());
            textViewAns.setText(arrayList.get(position).getAnswer());

            String colorAA = arrayList.get(position).getFontsize().replace("\u00a0"  ,"").replace(" "  ,"").trim();

            Log.e(TAG , "getFontsize111 "+colorAA);

            if(Utility.isColor(colorAA) == true){
                Log.e(TAG , "getFontsize "+colorAA);
                //int color = Utility.replaceColor(arrayList.get(position).getFontsize());
                textViewQues.setTextColor(Color.parseColor(colorAA));
                textViewAns.setTextColor(Color.parseColor(colorAA));
            }


            textViewQues.setVisibility(View.VISIBLE);
            textViewAns.setVisibility(View.VISIBLE);


            ImageView imageViewQuestion = (ImageView) myImageLayout.findViewById(R.id.imageView6456);
            ImageView imageViewAnswer = (ImageView) myImageLayout.findViewById(R.id.imageView64564325);



//        if(position == 0){
//            //imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getFormulaimage(), imageViewQuestion);
//        }
            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage(), imageViewAnswer);
            imageViewAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ImageZoom.class);
                    intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(position).getAnswerimage());
                    context.startActivity(intent);
                }
            });



//            if(nextPrevCount == position){
//                Log.e(TAG , nextPrevCount+ " XXXXXXXXXX "+view.getId());
//            }

          //  Log.e(TAG , nextPrevCount+ " XXXXXXXXXX "+view.getId());
           // Log.e(TAG , position+ " instantiateItem "+view.getId());

           // view.addView(myImageLayout, 0);



//            viewFlipper.setFlipInterval(3000);
//            viewFlipper.setAutoStart(true);
//            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
////            viewFlipper.setInAnimation();
////            viewAnimator.setInAnimation(getApplicationContext(), R.anim.flip);
//          //  viewFlipper.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
//            viewFlipper.startFlipping();
//
//
////            Animation[] result = new Animation[2];
//            float centerY;
//
//            centerY = viewFlipper.getHeight() / 2.0f;
//
//            Animation outFlip = new FlipAnimation(AnimationFactory.FlipDirection.RIGHT_LEFT.getStartDegreeForFirstView(), AnimationFactory.FlipDirection.RIGHT_LEFT.getEndDegreeForFirstView(), false, centerY, AnimationFactory.FlipDirection.RIGHT_LEFT, viewFlipper.getWidth());
//            outFlip.setDuration(5000);
//            outFlip.setFillAfter(true);
//            outFlip.setInterpolator(new AccelerateInterpolator());
//
//            AnimationSet outAnimation = new AnimationSet(true);
//            outAnimation.addAnimation(outFlip);
////            result[0] = outAnimation;
//
//
//            viewFlipper.setInAnimation(outAnimation);

            view.addView(myImageLayout);


            //Log.e(TAG , position+ " instantiateItem "+view.getId());
            Log.e(TAG , "getItemPosition0000 "+nextPrevCount);






            return myImageLayout;
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.e(TAG , "getCurrentItem1111 "+mPager.getCurrentItem());
         //   Log.e(TAG ,  " isViewFromObject "+view.getId());

            //alreadyFlip = true;

            ViewAnimator viewAnimator11 = (ViewAnimator)view.findViewById(R.id.viewFlipper);
            final ViewFlipper viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper);





           // alreadyFlip = false;

           // Log.e(TAG , nextPrevCount+" alreadyFlip1111 "+alreadyFlip);



           // viewFlipper.setFlipInterval(10000);
//            viewFlipper.setAutoStart(true);
//            viewFlipper.setInAnimation(FlashCardFlip.this, R.anim.left_1);
//            viewFlipper.startFlipping();
            //viewFlipper.showNext();
//            Boolean isAutoStart = viewFlipper.isAutoStart();
//
//            if(isAutoStart == false){
//                viewFlipper.startFlipping();
//            }else{
//                viewFlipper.stopFlipping();
//            }


            //AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);




            if(alreadyFlip == false){
                //Log.e(TAG , nextPrevCount+" alreadyFlip2222 "+alreadyFlip);

                 alreadyFlip = true;
                Log.e(TAG , nextPrevCount+" getCurrentItem2222 "+mPager.getCurrentItem());
                Log.e(TAG , nextPrevCount+" viewAnimator2222 "+viewAnimator);

                viewAnimator = viewAnimator11;

//                viewFlipper.setFlipInterval(4000);
//                viewFlipper.setAutoStart(true);
////                AnimationFactory.flipTransition(viewAnimator11, AnimationFactory.FlipDirection.RIGHT_LEFT);
////            viewFlipper.setInAnimation();
//            viewAnimator.setInAnimation(getApplicationContext(), R.anim.left_1);
//                viewFlipper.startFlipping();


//                viewFlipper.setFlipInterval(3000);
//                viewFlipper.setAutoStart(true);
//                viewFlipper.startFlipping();
                startThred();


            }

            return view.equals(object);
        }




        public void updateData(ArrayList<ItemCardFlip> arrayList2) {
            // TODO Auto-generated method stub
            arrayList = arrayList2;
//        arrayList.clear();
//        arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }

//        public void darkStar(boolean star) {
//            isStarShow = star;
//            // notifyDataSetChanged();
//        }
//
//
//        public void fadeStar(boolean star) {
//            isStarShow = star;
//            //notifyDataSetChanged();
//        }
//
//
//        public void statPosition(int pos) {
//            starPosition = pos;
//            // notifyDataSetChanged();
//        }


        public void upDateFlip(boolean b) {
            alreadyFlip = b;
           // notifyDataSetChanged();
        }
    }

    private void startThred() {
        Log.e(TAG , nextPrevCount+" BBBBBBBBBBBBBBBBBBBB "+viewAnimator);

        Log.e(TAG , "SCROLLING_RUNNABLE_ADSCard Before "+SCROLLING_RUNNABLE_ADSCard);


//        viewAnimator.setFlipInterval(1000);
//        viewAnimator.setAutoStart(true);


        mHandlerCard.removeCallbacks(SCROLLING_RUNNABLE_ADSCard);
//
//        Log.e(TAG , "SCROLLING_RUNNABLE_ADSCard After "+SCROLLING_RUNNABLE_ADSCard);
//
//
        mHandlerCard.postDelayed(SCROLLING_RUNNABLE_ADSCard, 15000);
//        mHandlerCard.postAtTime(SCROLLING_RUNNABLE_ADSCard, 5000);

    }












    private final Handler mHandlerCard = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE_ADSCard = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG , "SCROLLING_RUNNABLE_ADSCardrun");
            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);

            if(isScroll == false){
               isScroll = true;
               mHandler.postDelayed(SCROLLING_RUNNABLE_ADS, 15000);
            }
        }
    };


}