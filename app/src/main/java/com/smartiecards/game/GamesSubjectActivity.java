package com.smartiecards.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.ImageZoom;
import com.smartiecards.account.LoginFragment;
import com.smartiecards.dashboard.BannerActivity;
import com.smartiecards.home.AddCoupon;
import com.smartiecards.home.FullTextActivity;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.home.SubjectFragment;
import com.smartiecards.home.TopSubjectFragment;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.settings.SettingsAdapter;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.EasyFontsCustom;
import com.smartiecards.util.ResizableCustomView;
import com.smartiecards.util.ShowMsg;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/15/2018.
 */

public class GamesSubjectActivity extends BaseAppCompactActivity {
    public static final int PAYPAL_REQUEST_CODE = 195;

    private static final String TAG = "GamesSubjectActivity";

    private static final int GAME_COUPON_REQUEST_CODE = 334;
    Button buttonLogin, buttonSignUp;

    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GamesSubjectAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    GamesSubjectTask subjectHomeTask = null;
//    final Handler handler = new Handler();

//    SavePref pref = new SavePref();

    String gameType = "", keySubject = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.subject_account;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
            textViewTitleBar.setText("Game Subjects");


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

        pref.SavePref(GamesSubjectActivity.this);


        Bundle bundle = getIntent().getExtras();
        gameType = bundle.getString("gameType");

        if(gameType.equalsIgnoreCase("shuffle")){
            textViewTitleBar.setText("Shuffle Subjects");
        }else if(gameType.equalsIgnoreCase("match")){
            textViewTitleBar.setText("Match Subjects");
        }else if(gameType.equalsIgnoreCase("timed")){
            textViewTitleBar.setText("Timed Subjects");
        }


        mRecyclerView.setLayoutManager(new GridLayoutManager(GamesSubjectActivity.this, 2));
        mAdapter = new GamesSubjectAdapter(GamesSubjectActivity.this, arrayList);
        mRecyclerView.setAdapter(mAdapter);







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

        boolean b = WSConnector.checkAvail(GamesSubjectActivity.this);
        if (b == true) {
            callApiMethod();
            //handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }





//
//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            subjectHomeTask = new GamesSubjectTask();
//            subjectHomeTask.execute(pref.getId(), gameType);
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

        call = apiInterface.gameSubject(pref.getId(), gameType);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "ssssAA " + responseCode);

                        arrayList = new ParsingHelper().getItemSubjectCategory(responseCode);

                        if (arrayList.size() == 0) {
                            textViewMsg.setText("No subject category.");
                            textViewMsg.setVisibility(View.VISIBLE);
                        } else {
                            textViewMsg.setVisibility(View.GONE);
                        }

                        mAdapter.updateData(arrayList);

                    }else{
                       // new ShowMsg().createSnackbar(getActivity(), "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //new ShowMsg().createSnackbar(getActivity(), ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
//                new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });




    }









    class GamesSubjectTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "gamesubject.php?userid="+params[0]+"&type="+params[1]);
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
            Log.d(TAG, "ssssAA " + s);

            arrayList = new ParsingHelper().getItemSubjectCategory(s);

            if (arrayList.size() == 0) {
                textViewMsg.setText("No subject category.");
                textViewMsg.setVisibility(View.VISIBLE);
            } else {
                textViewMsg.setVisibility(View.GONE);
            }

            mAdapter.updateData(arrayList);

        }
    }






    public class GamesSubjectAdapter extends RecyclerView.Adapter<GamesSubjectAdapter.ViewHolder> {

        private static final String TAG = "GamesSubjectAdapter";
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GamesSubjectAdapter(Activity context11, ArrayList<ItemSubject> arrayList111) {
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
                        // .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
                        .build();
            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }



        }

        @Override
        public GamesSubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_item, viewGroup, false);
            GamesSubjectAdapter.ViewHolder viewHolder = new GamesSubjectAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GamesSubjectAdapter.ViewHolder viewHolder, final int i) {




            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
                viewHolder.layoutShade.setVisibility(View.GONE);
                viewHolder.buttonBuyNow.setText("See Topics");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname());
                viewHolder.textViewDesc.setVisibility(View.GONE);
            }else{
                viewHolder.layoutShade.setVisibility(View.GONE);
                viewHolder.buttonBuyNow.setText("See Topics");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname()+"\n"+WSContants.CURRENCY+arrayList.get(i).getAmount()+""+WSContants.TERM);

                //String text = "Welcome to Android Masterd tutorials which are commonly used in development now days.";
                viewHolder.textViewDesc.setText(arrayList.get(i).getDiscription());

                if(!arrayList.get(i).getDiscription().equalsIgnoreCase("")){
                    if(arrayList.get(i).getDiscription().length() > 50){
                        ResizableCustomView.addReadMore(arrayList.get(i).getDiscription(),viewHolder.textViewDesc);
                    }else{

                    }
                }
//                ResizableCustomView.addReadMore(arrayList.get(i).getDiscription(),viewHolder.textViewDesc );
                viewHolder.textViewDesc.setVisibility(View.VISIBLE);
                viewHolder.textViewDesc.setTypeface(EasyFontsCustom.avenirnext_TLPro_Medium(context));
            }

            viewHolder.textViewDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(GamesSubjectActivity.this, FullTextActivity.class);
                    intent.putExtra("key" , arrayList.get(i).getDiscription());
                    startActivity(intent);
                }
            });

//            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
//                viewHolder.layoutShade.setVisibility(View.GONE);
//                viewHolder.buttonBuyNow.setText("View Detail");
//                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
//                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname());
//            }else{
//                viewHolder.layoutShade.setVisibility(View.VISIBLE);
//                viewHolder.buttonBuyNow.setText("Buy Now");
//                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.black_round_selector);
//                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname()+"\n"+WSContants.CURRENCY+arrayList.get(i).getAmount()+"/Year");
//            }


//            viewHolder.imageViewTopicImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(GamesSubjectActivity.this , ImageZoom.class);
//                    intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(i).getPhoto());
//                    startActivity(intent);
//                }
//            });

            viewHolder.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!pref.getId().equalsIgnoreCase("")){
                        String buttonTxt = viewHolder.buttonBuyNow.getText().toString();
                        if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
                            Intent intent = new Intent(GamesSubjectActivity.this , BannerGameActivity.class);
                           // Intent intent = new Intent(GamesSubjectActivity.this , GameSubjectTopic.class);

                            //new ShowMsg().createDialog(GamesSubjectActivity.this, arrayList.get(i).getSubjectname());

                            intent.putExtra("keySubject", arrayList.get(i).getSubjectname());
                            intent.putExtra("keyId", arrayList.get(i).getId());
                            intent.putExtra("keyType", arrayList.get(i).getType());
                            intent.putExtra("keyTime", arrayList.get(i).getSubjecttime());
                            startActivity(intent);

//                            Intent intent = new Intent(getActivity() , BannerActivity.class);
//                            intent.putExtra("key", arrayList.get(i).getId());
//                            intent.putExtra("classKey", "Subjects");
//                            intent.putExtra("key2", arrayList.get(i).getSubjectname());
//                            startActivity(intent);

//                            Intent intent = new Intent(GamesSubjectActivity.this , BannerActivity.class);
//                            intent.putExtra("key", arrayList.get(i).getId());
//                            intent.putExtra("key2", arrayList.get(i).getSubjectname());
//                            startActivity(intent);

                        }else{
                            Intent intent = new Intent(GamesSubjectActivity.this, AddCoupon.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pos", "1");
                            bundle.putSerializable("key", arrayList.get(i));
                            intent.putExtras(bundle);
                            startActivityForResult(intent, GAME_COUPON_REQUEST_CODE);
                        }
                    }else{
                        Intent i = new Intent(GamesSubjectActivity.this, MainActivity.class);
                        i.putExtra("keyPosition" , 1);
                        startActivity(i);
                        finishAffinity();
                        finish();
                    }
                }
            });


            imageLoader.displayImage(WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(i).getPhoto(), viewHolder.imageViewTopicImage, options);

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;
            public ImageView imageViewTopicImage, imageViewPostImage, imageViewLike, imageViewComment, imageViewDelete, imageViewShare;
            public TextView textViewTitle, textViewDesc, textViewNews_post, textViewLikeCount, textViewCommnentCount, textViewReportPost;

            Button buttonBuyNow;
            public RelativeLayout layout, layoutShade;

            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                buttonBuyNow = (Button) itemView.findViewById(R.id.button414234);
                layoutShade = (RelativeLayout) itemView.findViewById(R.id.layout234353);
                textViewTitle = (TextView) itemView.findViewById(R.id.textView46456);
                textViewDesc = (TextView) itemView.findViewById(R.id.textView4645667);

                imageViewTopicImage = (ImageView) itemView.findViewById(R.id.imageView6456);
//                imageViewPostImage = (ImageView) itemView.findViewById(R.id.imageView1111);


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


        public void updateData(ArrayList<ItemSubject> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GAME_COUPON_REQUEST_CODE) {
            callRefreshMethod();
        }
    }




}
