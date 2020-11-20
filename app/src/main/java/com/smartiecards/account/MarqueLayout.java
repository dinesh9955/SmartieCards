package com.smartiecards.account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * Created by AnaadIT on 2/28/2018.
 */

public class MarqueLayout extends FrameLayout {

    Animation animation ;

    public MarqueLayout(@NonNull Context context) {
        super(context);
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, +1f,
                Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
                );
        animation.setRepeatCount(Animation.RESTART);
//        animation.setRepeatCount(Animation.RESTART);
    }



    public void setDuration(int durationMillis) {
        animation.setDuration(2000);
//        animation.setStartOffset(200);
      //  animation.set
    }


    public void startAnimation() {
        startAnimation(animation);
    }
}
