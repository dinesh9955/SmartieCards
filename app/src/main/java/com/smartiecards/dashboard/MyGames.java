package com.smartiecards.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.home.FlashCardLists;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by AnaadIT on 2/13/2018.
 */

public class MyGames extends BaseAppCompactActivity {
    public static final int PAYPAL_REQUEST_CODE = 155;

    private static final String TAG = "MyGames";
    final Handler handler = new Handler();
    Button buttonLogin, buttonSignUp;
    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MyGameAdapter mAdapter;
    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;
    //    ProfileTask profileTask = null;
    MyGameTask subjectHomeTask = null;
    SavePref pref = new SavePref();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Runnable runnableAbout = new Runnable() {
        @Override
        public void run() {
            subjectHomeTask = new MyGameTask();
            subjectHomeTask.execute(pref.getId());
        }
    };

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
        textViewTitleBar.setText("My Games");


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

        pref.SavePref(MyGames.this);



        mRecyclerView.setLayoutManager(new GridLayoutManager(MyGames.this, 2));
        mAdapter = new MyGameAdapter(MyGames.this, arrayList);
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

        boolean b = WSConnector.checkAvail(MyGames.this);
        if (b == true) {
            handler.postDelayed(runnableAbout, 50);
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }

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

    class MyGameTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "purchased_subjects.php?userid="+params[0]);
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

    public class MyGameAdapter extends RecyclerView.Adapter<MyGameAdapter.ViewHolder> {

        private static final String TAG = "MySubjectAdapter";
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public MyGameAdapter(Activity context11, ArrayList<ItemSubject> arrayList111) {
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
        public MyGameAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_item, viewGroup, false);
            MyGameAdapter.ViewHolder viewHolder = new MyGameAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final MyGameAdapter.ViewHolder viewHolder, final int i) {


            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
                viewHolder.layoutShade.setVisibility(View.GONE);
                viewHolder.buttonBuyNow.setText("View Detail");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
            }else{
                viewHolder.layoutShade.setVisibility(View.VISIBLE);
                viewHolder.buttonBuyNow.setText("Buy Now");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.black_round_selector);
            }


            viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname()+"\n"+WSContants.CURRENCY+arrayList.get(i).getAmount()+""+WSContants.TERM);


//            viewHolder.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(!pref.getId().equalsIgnoreCase("")){
//                        String buttonTxt = viewHolder.buttonBuyNow.getText().toString();
//                        if(buttonTxt.equalsIgnoreCase("View Detail")){
//                            Intent intent = new Intent(MyGames.this , FlashCardLists.class);
//                            intent.putExtra("key", arrayList.get(i).getId());
//                            intent.putExtra("key2", arrayList.get(i).getSubjectname());
//                            startActivity(intent);
//                        }else{
////                            Intent intent = new Intent(getActivity() , PayNowScreen.class);
////                            startActivity(intent);
//                            PayPalPayment payment = new PayPalPayment(new BigDecimal(""+convertValueStringToDouble("12.45")), "USD", "Premimum Plan Fee",
//
////                        PayPalPayment payment = new PayPalPayment(new BigDecimal("1"), "USD", "Premimum Plan Fee",
//                                    PayPalPayment.PAYMENT_INTENT_SALE);
//                            Intent intent = new Intent( MyGames.this , PaymentActivity.class);
//                            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
//                            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//                            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//                        }
//                    }else{
////                        MainActivity activity = (MainActivity) getActivity();
////                        activity.switchFragment(new LoginFragment());
//                        Intent i = new Intent(MyGames.this, MainActivity.class);
//                        i.putExtra("keyPosition" , 1);
//                        startActivity(i);
//                        finishAffinity();
//                        finish();
//                    }
//                }
//            });


            imageLoader.displayImage(WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(i).getPhoto(), viewHolder.imageViewTopicImage, options);

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public void updateData(ArrayList<ItemSubject> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            public ImageView imageViewTopicImage, imageViewPostImage, imageViewLike, imageViewComment, imageViewDelete, imageViewShare;
            public TextView textViewTitle, textViewPosted_ago, textViewNews_post, textViewLikeCount, textViewCommnentCount, textViewReportPost;
            public RelativeLayout layout, layoutShade;
            View view11 = null;
            Button buttonBuyNow;
            View view = null;
            private ItemClickListener clickListener;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                buttonBuyNow = (Button) itemView.findViewById(R.id.button414234);
                layoutShade = (RelativeLayout) itemView.findViewById(R.id.layout234353);
                textViewTitle = (TextView) itemView.findViewById(R.id.textView46456);

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
    }
}
