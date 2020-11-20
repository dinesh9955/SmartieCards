package com.smartiecards.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import static java.lang.Math.abs;

/**
 * Created by AnaadIT on 9/27/2017.
 */

public class SquareRelativeLayout4 extends RelativeLayout {

    public SquareRelativeLayout4(Context context) {
        super(context);
    }

    public SquareRelativeLayout4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SquareRelativeLayout4(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Measure the view and its content to determine the measured width and the measured height.
     * @param widthMeasureSpec :  horizontal space requirements as imposed by the parent
     * @param heightMeasureSpec :  vertical space requirements as imposed by the parent
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        //provide a square Preview in xml (not possible in certain cases)
        if(isInEditMode()){
            // if height < width : call super method with heightSpec for all sides
            if (abs(heightMeasureSpec) < abs(widthMeasureSpec)) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            }

            // if width <= height : call super method with widthSpec for all sides
            else {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            }
        }
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec * 80/100); //Snap to width

        init();
    }

    /**
     * Callback method to be invoked when the view tree is about to be drawn.
     * At this point, all views in the tree have been measured and given a frame.
     * So calculate the bounds before rendering the layout.
     */
    private void init() {
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (getWidth() != getHeight()) {
                    //Get the smaller dimension of height and width
                    int squareSize = Math.min(getWidth(), getHeight());

                    //Set the dimensions to a Square
                    ViewGroup.LayoutParams lp = getLayoutParams();
                    lp.width = squareSize;
                    lp.height = squareSize;
                    requestLayout();
                    return false;
                }
                return true;

            }
        });
    }

}
