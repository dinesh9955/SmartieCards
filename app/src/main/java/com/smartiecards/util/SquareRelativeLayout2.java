package com.smartiecards.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

	public class SquareRelativeLayout2 extends RelativeLayout
	{
	    public SquareRelativeLayout2(Context context)
	    {
	        super(context);
	    }

	    public SquareRelativeLayout2(Context context, AttributeSet attrs)
	    {
	        super(context, attrs);
	    }

	    public SquareRelativeLayout2(Context context, AttributeSet attrs, int defStyle)
	    {
	        super(context, attrs, defStyle);
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	    {
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	      //  setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
	        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()*70/100);
//	        setMinimumHeight(getMeasuredWidth());
//	        setMinimumWidth(getMeasuredWidth());
	    }
	}
