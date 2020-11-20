package com.smartiecards.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.home.TopSubjectFragment;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 2/26/2018.
 */

public class ResultCheckActivity extends BaseAppCompactActivity {

    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ResultCheckAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;


    @Override
    protected int getLayoutResource() {
        return R.layout.result_check_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) findViewById(R.id.textView123124);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1444);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText("Check Answers");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        Bundle bundle = getIntent().getExtras();

        final ArrayList<ItemTimedGame> arrayList = ( ArrayList<ItemTimedGame>) bundle.getSerializable("arrKey");


//        final String que = bundle.getString("que");
//        final String ans = bundle.getString("ans");
//        final String ansCorrect = bundle.getString("ansCorrect");



        mRecyclerView.setLayoutManager(new LinearLayoutManager(ResultCheckActivity.this, LinearLayoutManager.VERTICAL, false));

        //mRecyclerView.setLayoutManager(new GridLayoutManager(FlashCardLists.this, 2));
        mAdapter = new ResultCheckAdapter(ResultCheckActivity.this, arrayList);
        mRecyclerView.setAdapter(mAdapter);





    }








    public class ResultCheckAdapter extends RecyclerView.Adapter<ResultCheckAdapter.ViewHolder> {
        private static final String TAG = "GameTimedAdapter";
        ArrayList<ItemTimedGame> arrayList = new ArrayList<ItemTimedGame>();
        Activity context;

        public ResultCheckAdapter(Activity context11, ArrayList<ItemTimedGame> arrayList111) {
            super();
            this.context = context11;
            arrayList = arrayList111;

        }

        @Override
        public ResultCheckAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.result_check_item, viewGroup, false);

            ResultCheckAdapter.ViewHolder viewHolder = new ResultCheckAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final ResultCheckAdapter.ViewHolder viewHolder, final int i) {

            viewHolder.textViewQuestion.setText((i+1)+") "+arrayList.get(i).getText());

            String styledTextItems = "<font color='#000000'><b> You answered:&nbsp; </b></font><font color='#000000'>" + arrayList.get(i).getGivenAnswer() + "</font>";
            viewHolder.textViewAnswer.setText(Html.fromHtml(styledTextItems), TextView.BufferType.SPANNABLE);


            if(arrayList.get(i).answerCorrect == true){
                viewHolder.textViewGivenAnswer.setText("Right Answer!!!");
                viewHolder.textViewGivenAnswer.setTextColor(Color.GREEN);
            }else{
                viewHolder.textViewGivenAnswer.setText("Wrong Answer!!!");
                viewHolder.textViewGivenAnswer.setTextColor(Color.RED);
            }

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;

            public TextView textViewQuestion, textViewAnswer, textViewGivenAnswer;

            private ItemClickListener clickListener;
            View view = null;
            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                textViewQuestion = (TextView) itemView.findViewById(R.id.textView46456);
                textViewAnswer = (TextView) itemView.findViewById(R.id.textView5345466);
                textViewGivenAnswer = (TextView) itemView.findViewById(R.id.textView65465467);


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


        public void updateData(ArrayList<ItemTimedGame> arrayList2) {
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
