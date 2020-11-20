package com.smartiecards.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.home.FlashCardFlip;
import com.smartiecards.home.FlashCardLists;
import com.smartiecards.home.ItemSubjectTopic;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/16/2018.
 */

public class GameSubjectTopic extends BaseAppCompactActivity {


    private static final String TAG = "GameSubjectTopic";
    ArrayList<ItemSubjectTopic> arrayList = new ArrayList<ItemSubjectTopic>();

    ArrayList<String> arrayListString = new ArrayList<String>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GameSubjectTopicAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    GameSubjectTopicTask subjectHomeTask = null;
//    final Handler handler = new Handler();

//    SavePref pref = new SavePref();


    String keyId = "" , keyType ="" , keyTime = "", keySubject = "";


    @Override
    protected int getLayoutResource() {
        return R.layout.flash_card_list;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
//        textViewTitleBar.setText("Game Subject Topic");


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

//        pref.SavePref(GameSubjectTopic.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameSubjectTopic.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
        mAdapter = new GameSubjectTopicAdapter(GameSubjectTopic.this, arrayList);
        mRecyclerView.setAdapter(mAdapter);



        Bundle bundle = getIntent().getExtras();

        keySubject = bundle.getString("keySubject");
        keyId = bundle.getString("keyId");
        keyType = bundle.getString("keyType");
        keyTime = bundle.getString("keyTime");

        textViewTitleBar.setText(keySubject+" "+keyType);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                callRefreshMethod();
            }
        });

        callRefreshMethod();




    }










    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(GameSubjectTopic.this);
        if (b == true) {
            callApiMethod();
           // handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }



//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new GameSubjectTopicTask();
//            subjectHomeTask.execute(keyId, keyType);
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






    private void callApiMethod() {

        progressBar.setVisibility(View.VISIBLE);


        if(keyType.equalsIgnoreCase("timed")){
            call = apiInterface.gameSubjectTopic(keyId, keyType);
        }else{
            call = apiInterface.subjectTopic(keyId, keyType);
        }


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


                        arrayList = new ParsingHelper().getItemSubjectTopic(responseCode);

                        arrayListString = new ParsingHelper().getItemSubjectTopicDescription(responseCode);

                        if (arrayList.size() == 0) {
                            textViewMsg.setText("No subject found.");
                            textViewMsg.setVisibility(View.VISIBLE);
                        } else {
                            textViewMsg.setVisibility(View.GONE);
                        }

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







//
//
//    class GameSubjectTopicTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            if(params[1].equalsIgnoreCase("timed")){
//                return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "game_subject_topic.php?id=" + params[0]+"&type="+params[1]);
//            }else{
//                return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "subject_topic.php?id=" + params[0]+"&type="+params[1]);
//            }
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
//            arrayList = new ParsingHelper().getItemSubjectTopic(s);
//
//            arrayListString = new ParsingHelper().getItemSubjectTopicDescription(s);
//
//            if (arrayList.size() == 0) {
//                textViewMsg.setText("No subject found.");
//                textViewMsg.setVisibility(View.VISIBLE);
//            } else {
//                textViewMsg.setVisibility(View.GONE);
//            }
//
//            mAdapter.updateData(arrayList);
//
//        }
//    }
//






    public class GameSubjectTopicAdapter extends RecyclerView.Adapter<GameSubjectTopicAdapter.ViewHolder> {

        private static final String TAG = "GameSubjectTopicAdapter";
        ArrayList<ItemSubjectTopic> arrayList = new ArrayList<ItemSubjectTopic>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GameSubjectTopicAdapter(Activity context11, ArrayList<ItemSubjectTopic> arrayList111) {
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
        public GameSubjectTopicAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_cat_itm, viewGroup, false);
            GameSubjectTopicAdapter.ViewHolder viewHolder = new GameSubjectTopicAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GameSubjectTopicAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.textViewName.setText(""+arrayList.get(i).getFtopic());

            imageLoader.displayImage(WSContants.BASE_MAIN__CARD_IMAGE_URL+arrayList.get(i).getImage(), viewHolder.imageViewImage, options);



            viewHolder.cardViewClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(keyType.equalsIgnoreCase("shuffle")){
                        Intent intent = new Intent(GameSubjectTopic.this , GameShuffleFlip.class);
//                        intent.putExtra("gameSubjectId" , arrayList.get(i).getSubjectid());
//                        intent.putExtra("gameSubjectTopicId" , arrayList.get(i).getId());
//                        intent.putExtra("getColor" , arrayList.get(i).getColor());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("arrayList" , arrayList);
                        bundle.putInt("position" , i);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else if(keyType.equalsIgnoreCase("match")){
                        Intent intent = new Intent(GameSubjectTopic.this , GameMatches.class);
                        intent.putExtra("gameSubjectId" , arrayList.get(i).getSubjectid());
                        intent.putExtra("gameSubjectTopicId" , arrayList.get(i).getId());
                        intent.putExtra("getColor" , arrayList.get(i).getColor());
                        startActivity(intent);
                    }else if(keyType.equalsIgnoreCase("timed")){
                        Intent intent = new Intent(GameSubjectTopic.this , GameTimed2.class);
                        intent.putExtra("gameSubjectId" , arrayList.get(i).getSubjectid());
                        intent.putExtra("gameSubjectTopicId" , arrayList.get(i).getId());
                        intent.putExtra("getColor" , arrayList.get(i).getColor());
                        intent.putExtra("keyTime", keyTime);
                        startActivity(intent);
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
            public ImageView imageViewImage;
            public TextView textViewName;

            //  Button buttonBuyNow;
//            public RelativeLayout layout;

            CardView cardViewClick;

            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                //   buttonBuyNow = (Button) itemView.findViewById(R.id.button414234);

                cardViewClick = (CardView) itemView.findViewById(R.id.card_view);

                imageViewImage = (ImageView) itemView.findViewById(R.id.imageView6456);
//                imageViewPostImage = (ImageView) itemView.findViewById(R.id.imageView1111);
//
//                imageViewLike = (ImageView) itemView.findViewById(R.id.imageView36546546);
//                imageViewComment = (ImageView) itemView.findViewById(R.id.imageView67574575346);
//                imageViewDelete = (ImageView) itemView.findViewById(R.id.imageView2234535);
//                imageViewShare = (ImageView) itemView.findViewById(R.id.imageView346546546);
//
//

                textViewName = (TextView) itemView.findViewById(R.id.textView5345346);
//                textViewPosted_ago = (TextView) itemView.findViewById(R.id.textView2w35w345);
//                textViewNews_post = (TextView) itemView.findViewById(R.id.textView34534);
//                textViewReportPost = (TextView) itemView.findViewById(R.id.imageView546547);
//
//                textViewLikeCount = (TextView) itemView.findViewById(R.id.imageView6757457);
//                textViewCommnentCount = (TextView) itemView.findViewById(R.id.imageView6757457654);


//            layout = (RelativeLayout) itemView.findViewById(R.id.top_layout);

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


        public void updateData(ArrayList<ItemSubjectTopic> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if (item.getItemId() == R.id.action_newItem) {
            if(arrayListString.size() > 0){
//                if(keyType.equalsIgnoreCase("shuffle")){
                    new ShowMsg().createDialogNewTitleANDDescription(GameSubjectTopic.this, arrayListString.get(0),arrayListString.get(1));
//                }else if(keyType.equalsIgnoreCase("match")){
//                    new ShowMsg().createDialogNewTitleANDDescription(GameSubjectTopic.this, WSContants.MATCHES);
//                }else if(keyType.equalsIgnoreCase("timed")){
//                    new ShowMsg().createDialogNewTitleANDDescription(GameSubjectTopic.this, WSContants.TIMED);
//                }
            }

        }
        return super.onOptionsItemSelected(item);
    }




}
