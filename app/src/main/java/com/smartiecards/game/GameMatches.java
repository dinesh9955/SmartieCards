package com.smartiecards.game;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/19/2018.
 */

public class GameMatches extends BaseAppCompactActivity {

    Button buttonShuffle;

    private static final String TAG = "GameMatches";
    ArrayList<ItemGameMatches> arrayList = new ArrayList<ItemGameMatches>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GameMatchesAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    GameMatchesTask subjectHomeTask = null;
//    final Handler handler = new Handler();

//    SavePref pref = new SavePref();

    String gameSubjectId = "" , gameSubjectTopicId ="", colorKey = "#00000000";



    @Override
    protected int getLayoutResource() {
        return R.layout.game_card_list;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Game Matches");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

        buttonShuffle = (Button) findViewById(R.id.button414234);


        buttonShuffle.setVisibility(View.GONE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

//        pref.SavePref(GameMatches.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameMatches.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));

        Bundle bundle = getIntent().getExtras();

        gameSubjectId = bundle.getString("gameSubjectId");
        gameSubjectTopicId = bundle.getString("gameSubjectTopicId");
        colorKey = bundle.getString("getColor");


        mAdapter = new GameMatchesAdapter(GameMatches.this, arrayList , colorKey);
        mRecyclerView.setAdapter(mAdapter);





//        // textViewTitleBar.setText(subjectName+"/FlashCard");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                callRefreshMethod();
            }
        });

        callRefreshMethod();




        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRefreshMethod();
            }
        });


    }










    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(GameMatches.this);
        if (b == true) {
//            handler.postDelayed(runnableAbout, 50);
            callApiMethod();
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }




    private void callApiMethod() {

        progressBar.setVisibility(View.VISIBLE);

        call = apiInterface.gameMatches( gameSubjectId, gameSubjectTopicId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "ssss " + responseCode);


                        arrayList = new ParsingHelper().getItemGameMatches(responseCode);

                        if (arrayList.size() == 0) {
                            textViewMsg.setText("No subject found.");
                            textViewMsg.setVisibility(View.VISIBLE);
                        } else {
                            textViewMsg.setVisibility(View.GONE);
                        }


                        //Collections.shuffle(arrayList);
                        mAdapter.updateData(arrayList);

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










//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new GameMatchesTask();
//            subjectHomeTask.execute(gameSubjectId, gameSubjectTopicId);
//        }
//    };



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

    }





//
//    class GameMatchesTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "games-matches.php?subjectid="+params[0]+"&topicid="+params[1]);
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
//            swipeRefreshLayout.setRefreshing(false);
//            Log.d(TAG, "ssss " + s);
//
//
//            arrayList = new ParsingHelper().getItemGameMatches(s);
//
//            if (arrayList.size() == 0) {
//                textViewMsg.setText("No subject found.");
//                textViewMsg.setVisibility(View.VISIBLE);
//            } else {
//                textViewMsg.setVisibility(View.GONE);
//            }
//
//
//            //Collections.shuffle(arrayList);
//            mAdapter.updateData(arrayList);
//
//
//
//        }
//    }
//
//
//




    public class GameMatchesAdapter extends RecyclerView.Adapter<GameMatchesAdapter.ViewHolder> {

        String colorK = "#00000000";

        int intQuestion = -1;
        int intAnswer = -1;


        int questionHideID = -1;
        int answerHideID = -1;

        String stringQuestionId = "";
        String stringAnswerId = "";

        private static final String TAG = "GameMatchesAdapter";
        ArrayList<ItemGameMatches> arrayList = new ArrayList<ItemGameMatches>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GameMatchesAdapter(Activity context11, ArrayList<ItemGameMatches> arrayList111 , String col) {
            super();
            this.context = context11;
            arrayList = arrayList111;

            colorK = col;
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
        public GameMatchesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_game_item, viewGroup, false);
            GameMatchesAdapter.ViewHolder viewHolder = new GameMatchesAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GameMatchesAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.textViewQuestion.setText(""+arrayList.get(i).getFormula());
            //viewHolder.textViewAnswer.setText(""+arrayList.get(i).getAnswer());


            if(arrayList.get(i).getQuestionType().equalsIgnoreCase("1")){
                viewHolder.textViewQuestion.setTypeface(null, Typeface.BOLD);
            }
            if(arrayList.get(i).getQuestionType().equalsIgnoreCase("2")){
                viewHolder.textViewQuestion.setTypeface(null, Typeface.NORMAL);
            }


         //   viewHolder.layoutQuestion.setBackgroundResource(R.drawable.white_border_corner_round);


//            if(Utility.isColor(colorK) == true){
//                int color = Utility.replaceColor(colorK);
//                Log.e(TAG, "color::: "+color);
//                viewHolder.layoutQuestion.setBackgroundColor(color);
//
////               String colorString  = Utility.replaceColorString(colorK);
////                Log.e(TAG, "colorString::: "+colorString);
//            }

//            if(intQuestion == 1){
                if(Utility.isColor(colorK) == true){
                    int color = Utility.replaceColor(colorK);
                    arrayList.get(i).setIsColor(color);

                    viewHolder.layoutQuestion.setBackgroundColor(arrayList.get(i).getIsColor());
                }
//
//            }else{
//                if(Utility.isColor(colorK) == true){
//                    int color = Utility.replaceColor(colorK);
//                    arrayList.get(i).setIsColor(color);
//
//                    viewHolder.layoutQuestion.setBackgroundColor(arrayList.get(i).getIsColor());
//                }
//
//            }

//            if(Utility.isColor(colorK) == true){
//                    int color = Utility.replaceColor(colorK);
//                    arrayList.get(i).setIsColor(color);
//
//                    viewHolder.layoutQuestion.setBackgroundColor(arrayList.get(i).getIsColor());
//                }


            viewHolder.layoutQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "layoutQuestion111 "+arrayList.get(i).getId());
                    if(intQuestion == -1){
                        intQuestion = 1;

                        questionHideID = i;

                       // viewHolder.layoutQuestion.setBackgroundColor(Color.RED);

                        arrayList.get(i).setIsColor(Color.RED);
                        viewHolder.layoutQuestion.setBackgroundColor(arrayList.get(i).getIsColor());




                        stringQuestionId = arrayList.get(i).getId();
                    }else{

                        answerHideID = i;

                        arrayList.get(i).setIsColor(Color.GREEN);
                        viewHolder.layoutQuestion.setBackgroundColor(arrayList.get(i).getIsColor());

                        stringAnswerId = arrayList.get(i).getId();

                        if(stringQuestionId.equalsIgnoreCase(stringAnswerId)){
                            Log.e(TAG, "yes");

                            if(questionHideID == answerHideID){
                                Log.e(TAG, "same");
                                refreshOnly();
                            }else{
                                Log.e(TAG, "notsame");
                                if(answerHideID > questionHideID){
                                    refreshForHide(answerHideID);
                                    refreshForHide(questionHideID);
                                }else{
                                    refreshForHide(questionHideID);
                                    refreshForHide(answerHideID);
                                }
                            }

                        }else{
                            Log.e(TAG, "No");
                            createDialogBox();
                        }
                    }
                }
            });




        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;

            public TextView textViewQuestion, textViewAnswer1;

            public RelativeLayout layoutQuestion, layoutAnswer1;


            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                textViewQuestion = (TextView) itemView.findViewById(R.id.textView25345);
              //  textViewAnswer = (TextView) itemView.findViewById(R.id.textView2786867);

                layoutQuestion = (RelativeLayout) itemView.findViewById(R.id.relative23244);
               // layoutAnswer = (RelativeLayout) itemView.findViewById(R.id.relative547756);



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


        public void updateData(ArrayList<ItemGameMatches> arrayList2) {
            // TODO Auto-generated method stub
            intQuestion = -1;
            intAnswer = -1;

            questionHideID = -1;
            answerHideID = -1;


            stringQuestionId = "";
            stringAnswerId = "";
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }
    }

    private void createDialogBox() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GameMatches.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Oops, try again")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        refreshOnly();
                    }
                });
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


    private void refreshForHide(int i) {
        arrayList.remove(i);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.updateData(arrayList);
            }
        },50);

        if (arrayList.size() == 0) {
            textViewMsg.setText("No question found.");
            textViewMsg.setVisibility(View.VISIBLE);
        } else {
            textViewMsg.setVisibility(View.GONE);
        }
    }

    private void refreshOnly() {
        mAdapter.updateData(arrayList);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
