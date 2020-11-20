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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;

/**
 * Created by AnaadIT on 2/16/2018.
 */

public class GameShuffle extends BaseAppCompactActivity {

    Button buttonShuffle;

    private static final String TAG = "GameCardShuffle";
    ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GameCardShuffleAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
    GameCardShuffleTask subjectHomeTask = null;
    final Handler handler = new Handler();

    SavePref pref = new SavePref();

//    String keyId = "" , keyType ="";


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
        textViewTitleBar.setText("Game Shuffle");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);

        buttonShuffle = (Button) findViewById(R.id.button414234);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        pref.SavePref(GameShuffle.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(GameShuffle.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
        mAdapter = new GameCardShuffleAdapter(GameShuffle.this, arrayList);
        mRecyclerView.setAdapter(mAdapter);



//        Bundle bundle = getIntent().getExtras();
//
//        keyId = bundle.getString("keyId");
//        keyType = bundle.getString("keyType");
//
//        // textViewTitleBar.setText(subjectName+"/FlashCard");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                callRefreshMethod();
            }
        });

        //callRefreshMethod();




        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRefreshMethod();
            }
        });


    }










    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(GameShuffle.this);
        if (b == true) {
            handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }






    private Runnable runnableAbout = new Runnable() {
        @Override
        public void run() {
            subjectHomeTask = new GameCardShuffleTask();
            subjectHomeTask.execute();
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

    }






    class GameCardShuffleTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "game-shuffle.php?");
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


            arrayList = new ParsingHelper().getItemGameShuffle(s);

            if (arrayList.size() == 0) {
                textViewMsg.setText("No subject found.");
                textViewMsg.setVisibility(View.VISIBLE);
            } else {
                textViewMsg.setVisibility(View.GONE);
            }


            mAdapter.updateData(arrayList);



        }
    }







    public class GameCardShuffleAdapter extends RecyclerView.Adapter<GameCardShuffleAdapter.ViewHolder> {

        private static final String TAG = "GameCardShuffleAdapter";
        ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GameCardShuffleAdapter(Activity context11, ArrayList<ItemGameShuffle> arrayList111) {
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
        public GameCardShuffleAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_game_item, viewGroup, false);
            GameCardShuffleAdapter.ViewHolder viewHolder = new GameCardShuffleAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GameCardShuffleAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.textViewQuestion.setText(""+arrayList.get(i).getQuestion());
            viewHolder.textViewAnswer.setText(""+arrayList.get(i).getAnswer());


         //   imageLoader.displayImage(WSContants.BASE_MAIN__CARD_IMAGE_URL+arrayList.get(i).getImage(), viewHolder.imageViewImage, options);



//            viewHolder.cardViewClick.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(GameCardShuffle.this , GameCardShuffle.class);
//                    intent.putExtra("getType" , arrayList.get(i).getType());
//                    startActivity(intent);
//                }
//            });


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

            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                textViewQuestion = (TextView) itemView.findViewById(R.id.textView25345);
                textViewAnswer = (TextView) itemView.findViewById(R.id.textView2786867);


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


        public void updateData(ArrayList<ItemGameShuffle> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
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
