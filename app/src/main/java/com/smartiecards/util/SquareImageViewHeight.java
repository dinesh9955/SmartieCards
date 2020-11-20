package com.smartiecards.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
@SuppressLint("AppCompatCustomView")
public class SquareImageViewHeight extends ImageView
{
    public SquareImageViewHeight(Context context)
    {
        super(context);
    }

    public SquareImageViewHeight(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareImageViewHeight(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight()); //Snap to width
    }
}