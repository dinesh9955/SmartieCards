package com.smartiecards;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.bumptech.glide.load.engine.Resource;
import com.smartiecards.flipview.AnimationFactory;

public class FirstFragment extends Fragment implements UpdateableFragmentListener{

boolean ffff = false;

    private final Handler mHandlerCardAA = new Handler(Looper.getMainLooper());
    private Handler mHandlerCard = new Handler(Looper.getMainLooper());


    private static final String TAG = "FirstFragment";
    ViewAnimator viewAnimator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);


        viewAnimator = (ViewAnimator)view.findViewById(R.id.viewFlipper);
        final ViewFlipper viewFlipper = (ViewFlipper)view.findViewById(R.id.viewFlipper);






//
//        viewAnimator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//            }
//        });





       // AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);

//        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(viewFlipper, "scaleX", 1f, 0f);
//        oa1.setInterpolator(new AccelerateDecelerateInterpolator());


       // AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);


//
//        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(
//                getActivity(), R.animator.flip);
//        anim.setTarget(view);
//        anim.setDuration(1000);
//        anim.end();
//        anim.start();

//        oa1.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//
//
//            }
//        });
        //oa1.start();



        //AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//       viewFlipper.setInAnimation(getActivity(),R.anim.left_1);

       // viewFlipper.setInAnimation(inFromLeftAnimation());


//        Animation animationFlipIn  = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left);
//        viewFlipper.setInAnimation(animationFlipIn);

//            viewFlipper.setFlipInterval(5000);
//            viewFlipper.setAutoStart(true);
//
//            viewFlipper.startFlipping();


//        mHandlerCard.removeCallbacks(SCROLLING_RUNNABLE_ADSCard);
//
//        //Log.e(TAG , "SCROLLING_RUNNABLE_ADSCard After "+SCROLLING_RUNNABLE_ADSCard);
//
//        if(ffff == false){
//            mHandlerCard.postDelayed(SCROLLING_RUNNABLE_ADSCard, 5000);
//            ffff = true;
//        }





//        viewFlipper.animate().withLayer()
//                .rotationY(90)
//                .setDuration(300)
//                .withEndAction(
//                        new Runnable() {
//                            @Override public void run() {
//
//
//                                // second quarter turn
//                                viewFlipper.setRotationY(-90);
//                                viewFlipper.animate().withLayer()
//                                        .rotationY(0)
//                                        .setDuration(300)
//                                        .start();
//                            }
//                        }
//                ).start();


//        ObjectAnimator animation = ObjectAnimator.ofFloat(viewFlipper, "rotationY", 0.0f, 360f);  // HERE 360 IS THE ANGLE OF ROTATE, YOU CAN USE 90, 180 IN PLACE OF IT,  ACCORDING TO YOURS REQUIREMENT
//
//        animation.setDuration(500); // HERE 500 IS THE DURATION OF THE ANIMATION, YOU CAN INCREASE OR DECREASE ACCORDING TO YOURS REQUIREMENT
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
//        animation.start();

        mHandlerCard = mHandlerCardAA;

        Log.e(TAG , "mHandlerqqq "+mHandlerCard);

        Log.e(TAG, "onCreateView");

        return view;
    }




    private final Runnable SCROLLING_RUNNABLE_ADSCard = new Runnable() {
        @Override
        public void run() {
          //  Log.e(TAG , "SCROLLING_RUNNABLE_ADSCardrun");
            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
        }
    };



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            Log.e(TAG, "setUserVisibleHint");
//
//            if(mHandlerCard == mHandlerCardAA){
//                mHandlerCard.postDelayed(SCROLLING_RUNNABLE_ADSCard, 5000);
//            }else{
//                mHandlerCard.removeCallbacks(SCROLLING_RUNNABLE_ADSCard);
//
//            }





//
        }
    }




    public void update(int currentItem) {
        Log.e(TAG, "update "+currentItem);
    }

    @Override
    public void update(String string) {
        Log.e(TAG, "pagerPosition "+string);
    }
}
