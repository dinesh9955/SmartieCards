package com.smartiecards;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.smartiecards.home.FlashCardFlip;

public class PagerDemo2 extends AppCompatActivity {

    private static final String TAG = "PagerDemo2";
    public static Activity activity;

    public static ViewPager mPager;

    ViewPagerAdapter pagerAdapter;

    int  nextPrevCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pager_demo2);

        activity = this;


        mPager = (ViewPager) findViewById(R.id.pager);

       // ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new FirstFragment(), "First");
        pagerAdapter.addFragment(new FirstFragment(), "Second");
        pagerAdapter.addFragment(new FirstFragment(), "Third");



        mPager.setAdapter(pagerAdapter);


        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                nextPrevCount = position;
                Log.e(TAG, "nextPrevCount "+nextPrevCount);
                //pagerAdapter.set
               // pagerAdapter.update(nextPrevCount);
//                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }



}
