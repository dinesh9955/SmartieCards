package com.smartiecards.game;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseFragment;
import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.game.GamesSubjectActivity;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.EasyFontsCustom;
import com.smartiecards.util.Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class GamesFragment extends BaseFragment {

    View viewBuyNowAllLayout;

    public static final int PAYPAL_REQUEST_CODE = 115;

    private static final String TAG = "GamesFragment";
    Button buttonLogin, buttonSignUp;


    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    GameAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;


    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
    GameTask subjectHomeTask = null;
    final Handler handler = new Handler();

   // SavePref pref = new SavePref();



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);

        viewBuyNowAllLayout = view.findViewById(R.id.include_1);
        viewBuyNowAllLayout.setVisibility(View.GONE);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) view.findViewById(R.id.textView123124);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1444);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

       // pref.SavePref(getActivity());



        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new GameAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);


        arrayList = Utility.getGameItemStatic();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // swipeRefreshLayout.setRefreshing(true);
//                callRefreshMethod();
                mAdapter.updateData(arrayList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        callRefreshMethod();



        mAdapter.updateData(arrayList);


        return view;
    }

    private void callRefreshMethod() {

        boolean b = WSConnector.checkAvail(getActivity());
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
            subjectHomeTask = new GameTask();
            subjectHomeTask.execute(pref.getId());
        }
    };



//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//        handler.removeCallbacks(runnableAbout);
//        if(subjectHomeTask != null){
//            if (subjectHomeTask.getStatus() != AsyncTask.Status.FINISHED) {
//                subjectHomeTask.cancel(false);
//            }
//        }
//
//    }
//







    class GameTask extends AsyncTask<String, String, String> {
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
    public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
        private static final String TAG = "HomeAdapter";
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public GameAdapter(Activity context11, ArrayList<ItemSubject> arrayList111) {
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
                    //    .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
                        .build();
            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }



        }

        @Override
        public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.game_cat_itm, viewGroup, false);
            GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final GameAdapter.ViewHolder viewHolder, final int i) {


           viewHolder.textViewName.setText(arrayList.get(i).getSubjectname());
            viewHolder.textViewDescription.setText(arrayList.get(i).getGameDescription());


                String imageUri = "drawable://" + arrayList.get(i).getImage();
                imageLoader.displayImage(imageUri, viewHolder.imageViewImage, options);


            viewHolder.cardViewClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity() , GamesSubjectActivity.class);
                    intent.putExtra("gameType", arrayList.get(i).getType());
                    getActivity().startActivity(intent);
                }
            });



        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


            public ImageView imageViewImage;
            public TextView textViewName, textViewDescription;
            CardView cardViewClick;


            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;

                cardViewClick = (CardView) itemView.findViewById(R.id.card_view);
                imageViewImage = (ImageView) itemView.findViewById(R.id.imageView6456);
                textViewName = (TextView) itemView.findViewById(R.id.textView46456);
                textViewDescription = (TextView) itemView.findViewById(R.id.textView4645667);
                textViewDescription.setTypeface(EasyFontsCustom.avenirnext_TLPro_Medium(context));

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





}
