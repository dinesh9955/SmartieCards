package com.smartiecards.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelativeLayoutHalf extends RelativeLayout
{
    public SquareRelativeLayoutHalf(Context context)
    {
        super(context);
    }

    public SquareRelativeLayoutHalf(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareRelativeLayoutHalf(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      //  setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        
//        int i = width * (40/100);
        
      //  int ii = width - i;
        
        setMeasuredDimension(width-20, width);
        
//        setMinimumHeight(getMeasuredWidth());
//        setMinimumWidth(getMeasuredWidth());
    }
}
