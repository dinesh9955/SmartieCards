package com.smartiecards.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
@SuppressLint("AppCompatCustomView")
public class SquareImageViewGrid3 extends ImageView
{
    public SquareImageViewGrid3(Context context)
    {
        super(context);
    }

    public SquareImageViewGrid3(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
    }

    public SquareImageViewGrid3(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
      //  int gg = getMeasuredWidth()*25/100;
        
     //   setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
        
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()*60/100); //Snap to width
    }
}
