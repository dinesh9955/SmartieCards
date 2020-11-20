package com.smartiecards.game;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.ImageZoom;
import com.smartiecards.flipview.AnimationFactory;
import com.smartiecards.home.FlashCardFlip;
import com.smartiecards.home.ItemCardFlip;
import com.smartiecards.home.ItemSubjectTopic;
import com.smartiecards.home.MyAdapter;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 3/8/2018.
 */

public class GameShuffleFlip extends BaseAppCompactActivity {

    //Button buttonShuffle;
    boolean alreadyFlip = false;

    ViewAnimator viewAnimator;

    private static final String TAG = "GameCardShuffle";
    ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();

    String gameSubjectId = "" , gameSubjectTopicId ="", colorKey = "#ffffff", subjectKey2 ="";

    ArrayList<ItemSubjectTopic> arrayListMain = new ArrayList<ItemSubjectTopic>();
    int positionMain = 0;


    TextView textViewSeekValue;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

//    GameCardShuffleTask subjectHomeTask = null;
//    final Handler handler = new Handler();

//    SavePref pref = new SavePref();

//    String keyId = "" , keyType ="";


    SeekBar seekBar;
    private static ViewPager mPager;


    boolean isScroll = false;

    GameShuffleAdapter adapter;



    ImageView buttonPrev, buttonNext, imageViewStar;

    Button buttonAddStar, buttonViewStarCard, buttonUnStar;

    int nextPrevCount = 0;
    int nextPrevCountPlus = 0;

    int nextPrevCountPlusIndicate = 0;

    boolean dialogOpen = false;

    boolean isShuffle = false;

    boolean isViewStarCard = false;

    RelativeLayout relativeLayoutBottomStar;

    @Override
    protected int getLayoutResource() {
        return R.layout.game_shuffle_flip;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        subjectKey2 = getString(R.string.app_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Game Shuffle");

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);


        seekBar = (SeekBar) findViewById(R.id.seekBar235435);
        mPager = (ViewPager) findViewById(R.id.pager);

        textViewSeekValue = (TextView) findViewById(R.id.textView25345);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

//        buttonShuffle = (Button) findViewById(R.id.button414234);


        buttonPrev = (ImageView) findViewById(R.id.button276878);
        buttonNext = (ImageView) findViewById(R.id.button3657);

        buttonAddStar = (Button) findViewById(R.id.button25345646);
        buttonViewStarCard = (Button) findViewById(R.id.button253456546);
        buttonUnStar = (Button) findViewById(R.id.button65465);

        imageViewStar = (ImageView) findViewById(R.id.button2534565);

        relativeLayoutBottomStar = (RelativeLayout) findViewById(R.id.relative23534564);

        relativeLayoutBottomStar.setVisibility(View.GONE);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        //    pref.SavePref(GameShuffleFlip.this);


        Bundle bundle = getIntent().getExtras();
//        colorKey = bundle.getString("getColor");
//        gameSubjectId = bundle.getString("gameSubjectId");
//        gameSubjectTopicId = bundle.getString("gameSubjectTopicId");

        arrayListMain = (ArrayList<ItemSubjectTopic>) bundle.getSerializable("arrayList");
        positionMain = bundle.getInt("position");

        textViewTitleBar.setText(subjectKey2);

        Log.e(TAG, "colorKey: "+colorKey);

        adapter = new GameShuffleAdapter(GameShuffleFlip.this, arrayList, colorKey);
        mPager.setAdapter(adapter);

//        mPager.setPageTransformer(true, new FlipHorizontalTransformer());



        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG, "page selected " + position);
//                textViewSeekValue.setText(arrayList.get(position).getNumber()+"/"+(arrayList.size()));
//                seekBar.setProgress(position);
                alreadyFlip = false;
                nextPrevCount = position;

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

                if(arrayList.size() != 0){
                    if(nextPrevCount == arrayList.size() -1){
                    }else{
                        nextPrevCount ++;
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

                if(arrayList.size() != 0){
                    if(nextPrevCount == 0){
                    }else{
                        nextPrevCount --;
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
                    ItemGameShuffle cardFlip = arrayList.get(nextPrevCount);
                    callStarApiMethod(cardFlip);
                    // new AddStarTask(cardFlip).execute();
                }
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

                callRefreshMethod();

            }
        });



        buttonUnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrayList.size() > 0){
                    ItemGameShuffle cardFlip = arrayList.get(nextPrevCount);
                    callUnStarApiMethod(cardFlip);
                    //  new UnStarTask(cardFlip).execute(pref.getId() , cardFlip.getSubject_id(), cardFlip.getTopic_id(), cardFlip.getId(), "games");
                }
            }
        });






        imageViewStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG , "imageViewStar");

                if(arrayList.size() > 0){

                    ItemGameShuffle cardFlip = arrayList.get(nextPrevCount);

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


//        buttonShuffle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callRefreshMethod();
//            }
//        });


    }






//    private void buttonGrayed(int nextPrevCount) {
//        Log.e(TAG , "buttonGrayed "+nextPrevCount);
//
//        if(nextPrevCount == 0){
//            buttonPrev.setImageResource(R.drawable.backgray4);
//        }else{
//            buttonPrev.setImageResource(R.drawable.back4);
//        }
//    }




    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(GameShuffleFlip.this);
        if (b == true) {
            callFlashCardFlipApiMethod();
            //handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }









//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new GameCardShuffleTask();
//            subjectHomeTask.execute(pref.getId() , "games");
//        }
//    };






    private void noCardDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GameShuffleFlip.this);
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




    private void callFlashCardFlipApiMethod() {
        nextPrevCountPlusIndicate = 0;

        if((positionMain > arrayListMain.size() -1)){
            //new ShowMsg().createToast(FlashCardFlip.this , "Empty");
            noCardDialog();
        }else{
            ItemSubjectTopic itemSubjectTopic = arrayListMain.get(positionMain);

            colorKey = itemSubjectTopic.getColor();
            subjectKey2 = itemSubjectTopic.getFtopic();
            gameSubjectId = itemSubjectTopic.getSubjectid();
            gameSubjectTopicId = itemSubjectTopic.getId();

            textViewTitleBar.setText(subjectKey2);

            progressBar.setVisibility(View.VISIBLE);

            call = apiInterface.gameShuffle(gameSubjectId, gameSubjectTopicId, pref.getId() , "games");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    progressBar.setVisibility(View.GONE);
                    String responseCode = "";
                    try {
                        if(response.body() != null) {
                            responseCode = response.body().string();

                            //  nextPrevCount = 0;

                            if(isViewStarCard == true){
                                arrayList = new ParsingHelper().getItemGameShuffleFlipOnlyStar(responseCode);
                            }else{
                                arrayList = new ParsingHelper().getItemGameShuffleFlip(responseCode);
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


                            if(isShuffle == true){
                                Collections.shuffle(arrayList);
                            }


                            adapter = new GameShuffleAdapter(GameShuffleFlip.this, arrayList, colorKey);
                            mPager.setAdapter(adapter);

                            adapter.updateData(arrayList);

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



    }





//
//    class GameCardShuffleTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "game-shuffle.php?subject_id="+gameSubjectId+"&topic_id="+gameSubjectTopicId+"&userid=" +params[0]+ "&typ="+ params[1]);
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
//            progressBar.setVisibility(View.GONE);
////            swipeRefreshLayout.setRefreshing(false);
//            Log.e(TAG, "ssss " + s);
//
//
//            if(isViewStarCard == true){
//                arrayList = new ParsingHelper().getItemGameShuffleFlipOnlyStar(s);
//            }else{
//                arrayList = new ParsingHelper().getItemGameShuffleFlip(s);
//            }
//
//
//
//            if (arrayList.size() == 0) {
//                textViewMsg.setVisibility(View.VISIBLE);
//
//                if(isViewStarCard == true){
//                    textViewMsg.setText("No Cards have been Starred as yet.");
//                }else{
//                    textViewMsg.setText(".No Question Found");
//                }
//                relativeLayoutBottomStar.setVisibility(View.GONE);
//            } else {
//                textViewMsg.setVisibility(View.GONE);
//                seekBar.setMax(arrayList.size() - 1);
//                relativeLayoutBottomStar.setVisibility(View.VISIBLE);
//
//
//                if(isViewStarCard == true){
//                    buttonAddStar.setVisibility(View.GONE);
//                    buttonViewStarCard.setVisibility(View.GONE);
//                    buttonUnStar.setVisibility(View.VISIBLE);
//                }else{
//                    buttonAddStar.setVisibility(View.VISIBLE);
//                    buttonViewStarCard.setVisibility(View.VISIBLE);
//                    buttonUnStar.setVisibility(View.GONE);
//                }
//            }
//
//
//
//            Log.e(TAG, "ssss arrayList " + arrayList.size());
//
//            if(isShuffle == true){
//                Collections.shuffle(arrayList);
//            }
//
//
//
////            if (arrayList.size() == 0) {
////               // textViewMsg.setText("No question found.");
////                textViewMsg.setVisibility(View.VISIBLE);
////            } else {
////                textViewMsg.setVisibility(View.GONE);
////                seekBar.setMax(arrayList.size() - 1);
////            }
//
//
//
//            adapter = new GameShuffleAdapter(GameShuffleFlip.this, arrayList, colorKey);
//            mPager.setAdapter(adapter);
//
//            adapter.updateData(arrayList);
//
//
//
//            if(arrayList.size() != 0){
//                setStar(arrayList.get(nextPrevCount));
//                mPager.setVisibility(View.VISIBLE);
//            }else{
//                mPager.setVisibility(View.GONE);
//                textViewSeekValue.setText("0/"+(arrayList.size()));
//                seekBar.setMax(arrayList.size());
//            }
//
//
//
//
//
//            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    Log.e(TAG, "page selectedAAAA " + position);
//                    nextPrevCount = position;
//
//                    if(arrayList.size() != 0){
//                        textViewSeekValue.setText(arrayList.get(position).getNumber()+"/"+(arrayList.size()));
//                        seekBar.setProgress(position);
//
//                        if(arrayList.size() != 0){
//                            setStar(arrayList.get(nextPrevCount));
//                        }
//                    }else{
//                        textViewSeekValue.setText("0/"+(arrayList.size()));
//                    }
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    Log.i(TAG, "onPageSelected " + position);
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//
//
//        }
//    }
//





//
//    public class GameCardShuffleAdapter extends RecyclerView.Adapter<GameCardShuffleAdapter.ViewHolder> {
//
//        private static final String TAG = "GameCardShuffleAdapter";
//        ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
//        Activity context;
//
//        ImageLoader imageLoader;
//        DisplayImageOptions options;
//
//        ImageLoader imageLoader1;
//        DisplayImageOptions options1;
//
//        public GameCardShuffleAdapter(Activity context11, ArrayList<ItemGameShuffle> arrayList111) {
//            super();
//            this.context = context11;
//            arrayList = arrayList111;
//            // FacebookSdk.sdkInitialize(context);
//
//            try{
//                imageLoader = ImageLoader.getInstance();
//
//                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
//                options = new DisplayImageOptions.Builder()
//                        .showImageOnLoading(R.drawable.picture_default)
//                        .showImageForEmptyUri(R.drawable.picture_default)
//                        .showImageOnFail(R.drawable.picture_default)
//                        .cacheInMemory(true)
//                        .cacheOnDisk(true)
//                        .considerExifParams(true)
//                        .bitmapConfig(Bitmap.Config.RGB_565)
////			.displayer(new RoundedBitmapDisplayer(20))
////                        .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
//                        .build();
//            }catch(Exception e){
//                Log.d(TAG, "myError11: "+e.getMessage());
//            }
//
//
//        }
//
//        @Override
//        public GameCardShuffleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
//            final View v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.card_game_item, viewGroup, false);
//            GameCardShuffleAdapter.ViewHolder viewHolder = new GameCardShuffleAdapter.ViewHolder(v);
//            return viewHolder;
//        }
//
//
//        @Override
//        public void onBindViewHolder(final GameCardShuffleAdapter.ViewHolder viewHolder, final int i) {
//
//            viewHolder.textViewQuestion.setText(""+arrayList.get(i).getQuestion());
//            viewHolder.textViewAnswer.setText(""+arrayList.get(i).getAnswer());
//
//
//            //   imageLoader.displayImage(WSContants.BASE_MAIN__CARD_IMAGE_URL+arrayList.get(i).getImage(), viewHolder.imageViewImage, options);
//
//
//
////            viewHolder.cardViewClick.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent intent = new Intent(GameCardShuffle.this , GameCardShuffle.class);
////                    intent.putExtra("getType" , arrayList.get(i).getType());
////                    startActivity(intent);
////                }
////            });
//
//
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return arrayList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//            View view11 = null;
//            //  public ImageView imageViewImage;
//            public TextView textViewQuestion, textViewAnswer;
//
//            //  Button buttonBuyNow;
////            public RelativeLayout layout;
//
//            //     CardView cardViewClick;
//
//            private ItemClickListener clickListener;
//            View view = null;
//            public ViewHolder(View itemView) {
//                super(itemView);
//                view11 = itemView;
//
//                textViewQuestion = (TextView) itemView.findViewById(R.id.textView25345);
//                textViewAnswer = (TextView) itemView.findViewById(R.id.textView2786867);
//
//
//            }
//
//            public void setClickListener(ItemClickListener itemClickListener) {
//                this.clickListener = itemClickListener;
//            }
//
//            @Override
//            public void onClick(View view) {
//                clickListener.onClick(view, getPosition(), false);
//            }
//
//            @Override
//            public boolean onLongClick(View view) {
//                clickListener.onClick(view, getPosition(), true);
//                return true;
//            }
//        }
//
//
//        public void updateData(ArrayList<ItemGameShuffle> arrayList2) {
//            // TODO Auto-generated method stub
//            arrayList.clear();
//            arrayList.addAll(arrayList2);
//            notifyDataSetChanged();
//        }
//    }
//
////pref.getId() , cardFlip.getSubject_id(), cardFlip.getTopic_id(), cardFlip.getId(), "games"







    private void callStarApiMethod(ItemGameShuffle cardFlip11 ) {

        progressBar.setVisibility(View.VISIBLE);
//        userid=" + pref.getId() + "&subid=" +cardFlip.getSubject_id()+ "&cardid=" +cardFlip.getTopic_id()+"&topicid="+cardFlip.getId()+"&typ="+"games"

        call = apiInterface.addStarCard(pref.getId(), cardFlip11.getSubject_id() , cardFlip11.getTopic_id(), cardFlip11.getId(), "games");

        ItemGameShuffle cardFlip = cardFlip11;


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
                            new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);

                            cardFlip.setStar_status("1");
                            if(arrayList.size() > 0){
                                setStar(arrayList.get(nextPrevCount));
                            }

                        }else{
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);
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




//
//    class AddStarTask extends AsyncTask<String, String, String> {
//
//        ItemGameShuffle cardFlip = new ItemGameShuffle();
//        public AddStarTask(ItemGameShuffle cardFlip11) {
//            cardFlip = cardFlip11;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "add-star-card.php?userid=" + pref.getId() + "&subid=" +cardFlip.getSubject_id()+ "&cardid=" +cardFlip.getTopic_id()+"&topicid="+cardFlip.getId()+"&typ="+"games");
//
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
//            progressBar.setVisibility(View.GONE);
//
//            Log.d(TAG, "ssss " + s);
//
//            try{
//                JSONObject jsonObject = new JSONObject(s);
//                String status = jsonObject.getString("status");
//                if(status.equalsIgnoreCase("1")){
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);
//
//                    cardFlip.setStar_status("1");
//                    if(arrayList.size() > 0){
//                        setStar(arrayList.get(nextPrevCount));
//                    }
//
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);
//                }
//            }catch (Exception e){
//
//            }
//
//        }
//    }


//
//    class UnStarTask extends AsyncTask<String, String, String> {
//
//        ItemGameShuffle cardFlip = new ItemGameShuffle();
//
//        public UnStarTask(ItemGameShuffle cardFlip11) {
//            cardFlip = cardFlip11;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "unstar-card.php?userid=" + pref.getId() + "&subid=" +cardFlip.getSubject_id()+ "&cardid=" +cardFlip.getTopic_id()+"&topicid="+cardFlip.getId()+"&typ="+"games");
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
//            progressBar.setVisibility(View.GONE);
//
//            Log.d(TAG, "ssss " + s);
//
//            try{
//                JSONObject jsonObject = new JSONObject(s);
//                String status = jsonObject.getString("status");
//                if(status.equalsIgnoreCase("1")){
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);
//
//                    cardFlip.setStar_status("0");
//                    if(arrayList.size() != 0){
//                        setStar(arrayList.get(nextPrevCount));
//                    }
//
//                }else{
//                    String message = jsonObject.getString("message");
//                    new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);
//
//                }
//                callRefreshMethod();
//
//            }catch (Exception e){
//
//            }
//
//        }
//    }
//



    private void callUnStarApiMethod(ItemGameShuffle cardFlip11 ) {

        progressBar.setVisibility(View.VISIBLE);

        call = apiInterface.unstarCard(pref.getId(), cardFlip11.getSubject_id() , cardFlip11.getTopic_id(), cardFlip11.getId(), "games");

        ItemGameShuffle cardFlip = cardFlip11;

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
                            new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);

                            cardFlip.setStar_status("0");
                            if(arrayList.size() != 0){
                                setStar(arrayList.get(nextPrevCount));
                            }
                        }else{
                            String message = jsonObject.getString("message");
                            new ShowMsg().createSnackbar(GameShuffleFlip.this , ""+message);

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





    private void setStar(ItemGameShuffle itemCardFlip) {
        Log.e(TAG, "setStar " + itemCardFlip.getId());
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
        // Log.e(TAG , nextPrevCount+ " buttonGrayed "+nextPrevCountPlus);

        if(nextPrevCount == 0){
            buttonPrev.setImageResource(R.drawable.backgray4);
        }else{
            buttonPrev.setImageResource(R.drawable.back4);
        }





        if((arrayList.size() - 1) == nextPrevCount){
            if(nextPrevCountPlus > nextPrevCount){
                Log.e(TAG , nextPrevCount+" OOOOOOOOOOO "+nextPrevCountPlus);
                if(nextPrevCountPlusIndicate == 1){
                    Log.e(TAG , nextPrevCount+" OOOOOOOOOOOnextPrevCountPlusIndicate "+nextPrevCountPlus);
                    if(dialogOpen == false){
                        Log.e(TAG , nextPrevCount+" OOOOOOOOOOOopenDialogLast "+nextPrevCountPlus);
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

    }




    private void openDialogLast() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GameShuffleFlip.this);
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
                        Intent ii = new Intent(GameShuffleFlip.this, MainActivity.class);
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
        //alert.setCanceledOnTouchOutside(false);
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogOpen = false;
            }
        });
        alert.show();



    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.shuffle_menu, menu);
//        //  setMenuBackground();
//        return true;
//        //  return super.onCreateOptionsMenu(menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
////        if(isViewStarCard == true){
////            isViewStarCard = false;
////            callRefreshMethod();
////        }
////        if(item.getItemId() == android.R.id.home){
////            finish();
////        }
//        if(item.getItemId() == android.R.id.home){
//            if(isViewStarCard == true){
//                isViewStarCard = false;
//                callRefreshMethod();
//            }else{
//                finish();
//            }
//        }
//        if(item.getItemId() == R.id.shuffle_refresh){
//            isShuffle = true;
//            callRefreshMethod();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//





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
        inflater.inflate(R.menu.game_bar_menu_context, popup.getMenu());
        popup.show();


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

//                if(item.getItemId() == R.id.help){
//                    Log.e(TAG , "onMenuItemClick_help");
//                }

                if(item.getItemId() == R.id.shuffle_card){
                    Log.e(TAG , "onMenuItemClick_shuffle_card");

                    isShuffle = true;
                    callRefreshMethod();
                }

                if(item.getItemId() == R.id.view_starred_card){
                    Log.e(TAG , "onMenuItemClick_view_starred_card");

                    isViewStarCard = true;
                    nextPrevCount = 0;
                    callRefreshMethod();
                }
                return false;
            }
        });

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
        if(arrayList.size() != 0){
            if(nextPrevCount == arrayList.size() -1){
            }else{
                nextPrevCount ++;
                mPager.setCurrentItem(nextPrevCount);
            }

            if(arrayList.size() != 0){
                setStar(arrayList.get(nextPrevCount));
            }
        }
    }













    public class GameShuffleAdapter extends PagerAdapter {

        private static final String TAG = "GameShuffleAdapter";

        boolean alreadyFlip = false;

        private ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
        private LayoutInflater inflater;
        private Context context;


        ImageLoader imageLoader;
        DisplayImageOptions options;

        String colorK = "#00000000";


        public GameShuffleAdapter(Context context, ArrayList<ItemGameShuffle> images, String col) {
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

//        final RelativeLayout relativeLayoutClickToFLip = (RelativeLayout) myImageLayout.findViewById(R.id.second_image);
//
//        if(arrayList.size() > 0){
//            if(position == 0){
//                relativeLayoutClickToFLip.setVisibility(View.VISIBLE);
//            }else{
//                relativeLayoutClickToFLip.setVisibility(View.GONE);
//            }
//        }




            final ViewAnimator viewAnimator = (ViewAnimator)myImageLayout.findViewById(R.id.viewFlipper);


            final ViewFlipper viewFlipper = (ViewFlipper)myImageLayout.findViewById(R.id.viewFlipper);

            Log.e(TAG, "arrayList "+position);


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
                    // relativeLayoutClickToFLip.setVisibility(View.GONE);
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
                    //relativeLayoutClickToFLip.setVisibility(View.GONE);
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






            TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
            TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);

            textViewQues.setText(arrayList.get(position).getQuestion());
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


            Log.e(TAG , "getQuestionimage "+arrayList.get(position).getQuestionimage());

//        if(position == 0){
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getQuestionimage(), imageViewQuestion);
//        }
//

//        if(!arrayList.get(position).getAnswerimage2().equalsIgnoreCase("")){
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage2(), imageViewAnswer);
//        }else{
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage(), imageViewAnswer);
//            imageViewAnswer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context , ImageZoom.class);
//                    intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(position).getAnswerimage());
//                    context.startActivity(intent);
//                }
//            });
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


            ImageView imageViewQuestionIdi = (ImageView) myImageLayout.findViewById(R.id.imageView54356);
            ImageView imageViewAnswerIdi = (ImageView) myImageLayout.findViewById(R.id.imageView75667);


//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestionIdi.setVisibility(View.VISIBLE);
//                imageViewAnswerIdi.setVisibility(View.VISIBLE);
//            }else{
            imageViewQuestionIdi.setVisibility(View.GONE);
            imageViewAnswerIdi.setVisibility(View.GONE);
//            }
//        }


//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestion.setVisibility(View.VISIBLE);
//            }else{
//                imageViewQuestion.setVisibility(View.GONE);
//            }
//        }



//
//        TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
//        textViewQues.setVisibility(View.VISIBLE);
//        TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);
//        textViewAns.setVisibility(View.VISIBLE);
//
//        textViewQues.setText(arrayList.get(position).getQuestion());
//        textViewAns.setText(arrayList.get(position).getAnswer());




//
//        final WebView webViewQue = (WebView) myImageLayout.findViewById( R.id.webView1354);;
//        webViewQue.setVisibility(View.VISIBLE);
//        webViewQue.getSettings().setLoadWithOverviewMode(true);
//        webViewQue.getSettings().setUseWideViewPort(true);
//        webViewQue.getSettings().setJavaScriptEnabled(true);
//        webViewQue.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//
//
//        webViewQue.getSettings().setTextZoom((int) (webViewQue.getSettings().getTextZoom() * 2.1));
//
//        webViewQue.loadData("<b>"+arrayList.get(position).getQuestion()+"</b>", "text/html; charset=utf-8", "utf-8");
//        webViewQue.setBackgroundColor(0x00000000);
//        webViewQue.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//
//        webViewQue.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                webViewQue.loadUrl( "javascript:document.body.style.setProperty(\"color\", \"white\");" );
//                webViewQue.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
//            }
//        });
//
//
//        final WebView  webViewAns = (WebView) myImageLayout.findViewById( R.id.webView5466);;
//        webViewAns.setVisibility(View.VISIBLE);
//        webViewAns.getSettings().setLoadWithOverviewMode(true);
//        webViewAns.getSettings().setUseWideViewPort(true);
//        webViewAns.getSettings().setJavaScriptEnabled(true);
//        webViewAns.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//        webViewAns.getSettings().setTextZoom((int) (webViewAns.getSettings().getTextZoom() * 2.1));
//        webViewAns.loadData(arrayList.get(position).getAnswer(), "text/html; charset=utf-8", "utf-8");
//        webViewAns.setBackgroundColor(0x00000000);
//        webViewAns.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//
//        webViewAns.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                webViewAns.loadUrl( "javascript:document.body.style.setProperty(\"color\", \"white\");" );
////                webViewAns.loadUrl("javascript:(document.body.style.fontSize ='20pt');");
//                webViewAns.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
//            }
//        });
//
//
//
//
//
//        webViewQue.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_MOVE){
//                    return false;
//                }
//
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                }
//
//                return false;
//            }
//        });
//
//        webViewAns.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_MOVE){
//                    return false;
//                }
//
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                }
//
//                return false;
//            }
//        });
//





            view.addView(myImageLayout);

            // ((GameShuffleFlip)context).showView(position);

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








        public void updateData(ArrayList<ItemGameShuffle> arrayList2) {
            // TODO Auto-generated method stub
            arrayList = arrayList2;
//        arrayList.clear();
//        arrayList.addAll(arrayList2);
            notifyDataSetChanged();
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
