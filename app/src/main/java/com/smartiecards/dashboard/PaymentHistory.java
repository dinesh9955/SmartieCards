package com.smartiecards.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.home.FlashCardFlip;
import com.smartiecards.home.FlashCardLists;
import com.smartiecards.home.ItemSubject;
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
 * Created by AnaadIT on 2/2/2018.
 */

public class PaymentHistory extends BaseAppCompactActivity{

    private static final String TAG = "PaymentHistory";
    ArrayList<ItemPaymentHistory> arrayList = new ArrayList<ItemPaymentHistory>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PaymentHistoryAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    PaymentHistoryTask paymentHistoryTask = null;
    final Handler handler = new Handler();

    SavePref pref = new SavePref();



    @Override
    protected int getLayoutResource() {
        return R.layout.payment_history;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Payment History");



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


        pref.SavePref(PaymentHistory.this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(PaymentHistory.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
        mAdapter = new PaymentHistoryAdapter(PaymentHistory.this, arrayList);
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

        boolean b = WSConnector.checkAvail(PaymentHistory.this);
        if (b == true) {
           // handler.postDelayed(runnableAbout, 50);
            callApiMethod();
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }

    }






//    private Runnable runnableAbout = new Runnable() {
//        @Override
//        public void run() {
//            paymentHistoryTask = new PaymentHistoryTask();
//            paymentHistoryTask.execute(pref.getId());
//        }
//    };
//


    @Override
    public void onDestroy()
    {
        super.onDestroy();
//        handler.removeCallbacks(runnableAbout);
//        if(paymentHistoryTask != null){
//            if (paymentHistoryTask.getStatus() != AsyncTask.Status.FINISHED) {
//                paymentHistoryTask.cancel(false);
//            }
//        }

    }





    private void callApiMethod() {

        progressBar.setVisibility(View.VISIBLE);
        call = apiInterface.purchasedSubjects(pref.getId());

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
                        arrayList = new ParsingHelper().getPaymentHistory(responseCode);

                        if (arrayList.size() == 0) {
                            textViewMsg.setText("No subject payment history.");
                            textViewMsg.setVisibility(View.VISIBLE);
                        } else {
                            textViewMsg.setVisibility(View.GONE);
                        }


                        mAdapter.updateData(arrayList);




                    }else{
                        new ShowMsg().createSnackbar(PaymentHistory.this, "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // new ShowMsg().createSnackbar(MySubject.this, ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                //new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });




    }








//
//
//    class PaymentHistoryTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "purchased_subjects.php?userid=" + params[0]);
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
//            arrayList = new ParsingHelper().getPaymentHistory(s);
//
//
//                if (arrayList.size() == 0) {
//                        textViewMsg.setText("No subject payment history.");
//                        textViewMsg.setVisibility(View.VISIBLE);
//                    } else {
//                        textViewMsg.setVisibility(View.GONE);
//                    }
//
//
//            mAdapter.updateData(arrayList);
//
//
//
//        }
//    }
//






    public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

        private static final String TAG = "HomeAdapter";
        ArrayList<ItemPaymentHistory> arrayList = new ArrayList<ItemPaymentHistory>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public PaymentHistoryAdapter(Activity context11, ArrayList<ItemPaymentHistory> arrayList111) {
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
                        .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
                        .build();
            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }


        }

        @Override
        public PaymentHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.payment_history_item, viewGroup, false);
            PaymentHistoryAdapter.ViewHolder viewHolder = new PaymentHistoryAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final PaymentHistoryAdapter.ViewHolder viewHolder, final int i) {

//            String styledTextName = "<font color='#10447a'><b>" + "Posted by:" + "</b></font><font color='#10447a'>&nbsp;" + arrayList.get(i).getName() + "</font>";
//            viewHolder.textViewName.setText(Html.fromHtml(styledTextName), TextView.BufferType.SPANNABLE);
//
//            //viewHolder.textViewName.setText("Posted by: "+arrayList.get(i).getName());
//            viewHolder.textViewPosted_ago.setText(""+arrayList.get(i).getPosted_ago());
//            viewHolder.textViewNews_post.setText(""+arrayList.get(i).getNews_post());
//
//
//            viewHolder.textViewLikeCount.setText(""+arrayList.get(i).getLikes());
//            viewHolder.textViewCommnentCount.setText(""+arrayList.get(i).getComments());
//
//            imageLoader.displayImage(WSContants.BASE_URL_IMAGE+arrayList.get(i).getProfile_pic(), viewHolder.imageViewProfile, options);
//



            viewHolder.textViewSNo.setText(""+(i + 1));
            viewHolder.textViewSubject.setText(""+arrayList.get(i).getSname());
            viewHolder.textViewAmount.setText(WSContants.CURRENCY+arrayList.get(i).getAmount());
//            if(arrayList.get(i).getSname().contains(" ")){
//                //String spiltDate[] = arrayList.get(i).getDat().split(" ");
//                viewHolder.textViewDate.setText(""+arrayList.get(i).getDat().split(" ")[0]);
//            }else{
                viewHolder.textViewDate.setText(""+arrayList.get(i).getDate());
//            }

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;
            public ImageView imageViewProfile, imageViewPostImage, imageViewLike, imageViewComment, imageViewDelete, imageViewShare;
            public TextView textViewSNo, textViewSubject, textViewAmount, textViewDate;

            //  Button buttonBuyNow;
//            public RelativeLayout layout;



            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                //   buttonBuyNow = (Button) itemView.findViewById(R.id.button414234);



//                imageViewProfile = (ImageView) itemView.findViewById(R.id.imageView65465);
//                imageViewPostImage = (ImageView) itemView.findViewById(R.id.imageView1111);
//
//                imageViewLike = (ImageView) itemView.findViewById(R.id.imageView36546546);
//                imageViewComment = (ImageView) itemView.findViewById(R.id.imageView67574575346);
//                imageViewDelete = (ImageView) itemView.findViewById(R.id.imageView2234535);
//                imageViewShare = (ImageView) itemView.findViewById(R.id.imageView346546546);
//
//
//
                textViewSNo = (TextView) itemView.findViewById(R.id.textView54235);
                textViewSubject = (TextView) itemView.findViewById(R.id.textView75677);
                textViewAmount = (TextView) itemView.findViewById(R.id.textView7567568);
                textViewDate = (TextView) itemView.findViewById(R.id.textView675757);
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


        public void updateData(ArrayList<ItemPaymentHistory> arrayList2) {
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
