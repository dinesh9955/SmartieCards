package com.smartiecards.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SquareRelativeLayout extends RelativeLayout
{
    public SquareRelativeLayout(Context context)
    {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      //  setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
//        setMinimumHeight(getMeasuredWidth());
//        setMinimumWidth(getMeasuredWidth());
    }
}

//<TextView
//android:id="@+id/textView4"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_below="@+id/relativeLayout1"
//android:layout_centerHorizontal="true"
//android:layout_marginTop="5dp"
//android:textSize="20dp"
//android:text="$0.00" />
//<TextView
//android:id="@+id/textView5"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_below="@+id/textView4"
//android:layout_centerHorizontal="true"
//android:layout_marginTop="5dp"
//android:text="as of 3.40 pm" 
//  android:textColor="#A4A3A2"/>
//<TextView
//android:id="@+id/textView6"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//
//android:layout_below="@+id/textView5"
// android:layout_centerHorizontal="true"
//             android:layout_marginTop="5dp"
//android:text="My Card(7424)"
//android:textColor="#A4A3A2"/>