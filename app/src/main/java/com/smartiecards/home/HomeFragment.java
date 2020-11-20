package com.smartiecards.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.smartiecards.ItemClickListener;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.LoginActivity;
import com.smartiecards.account.LoginFragment;
import com.smartiecards.account.RegisterActivity;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    Button buttonLogin, buttonSignUp;

    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    HomeAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

//    ProfileTask profileTask = null;
    SubjectHomeTask subjectHomeTask = null;
    final Handler handler = new Handler();

    SavePref pref = new SavePref();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);


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

        pref.SavePref(getActivity());



        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new HomeAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                callRefreshMethod();
            }
        });

        callRefreshMethod();


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
            subjectHomeTask = new SubjectHomeTask();
            subjectHomeTask.execute(pref.getId());
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










    class SubjectHomeTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "top_subjects.php?userid="+params[0]);
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





    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

        private static final String TAG = "HomeAdapter";
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;

        public HomeAdapter(Activity context11, ArrayList<ItemSubject> arrayList111) {
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
        public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_item, viewGroup, false);
            HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final HomeAdapter.ViewHolder viewHolder, final int i) {

//            String styledTextName = "<font color='#10447a'><b>" + "Posted by:" + "</b></font><font color='#10447a'>&nbsp;" + arrayList.get(i).getName() + "</font>";
//            viewHolder.textViewName.setText(Html.fromHtml(styledTextName), TextView.BufferType.SPANNABLE);
//
//            //viewHolder.textViewName.setText("Posted by: "+arrayList.get(i).getName());
//            viewHolder.textViewPosted_ago.setText(""+arrayList.get(i).getPosted_ago());
//            viewHolder.textViewNews_post.setText(""+arrayList.get(i).getNews_post());
//
//


//            if(i == 0){
//                viewHolder.layoutShade.setVisibility(View.GONE);
//                viewHolder.buttonBuyNow.setText("View Detail");
//                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
//            }else{
//                viewHolder.layoutShade.setVisibility(View.VISIBLE);
//                viewHolder.buttonBuyNow.setText("Buy Now");
//                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.black_round_selector);
//            }



            viewHolder.layoutShade.setVisibility(View.GONE);
            viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.black_round_selector);
            viewHolder.buttonBuyNow.setVisibility(View.VISIBLE);


//            viewHolder.textViewTitle.setText(arrayList.get(i).getSname()+"\n"+WSContants.CURRENCY+arrayList.get(i).getAmount()+"/Year");


            viewHolder.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!pref.getId().equalsIgnoreCase("")){
                        String buttonTxt = viewHolder.buttonBuyNow.getText().toString();
                        if(buttonTxt.equalsIgnoreCase("View Detail")){
                            Intent intent = new Intent(getActivity() , FlashCardLists.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity() , PayNowScreen.class);
                            startActivity(intent);
                        }
                    }else{
//                        MainActivity activity = (MainActivity) getActivity();
//                        activity.switchFragment(new LoginFragment());
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("keyPosition" , 1);
                        getActivity().startActivity(i);
                        getActivity().finishAffinity();
                        getActivity().finish();
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
            public TextView textViewTitle, textViewPosted_ago, textViewNews_post, textViewLikeCount, textViewCommnentCount, textViewReportPost;

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





}
