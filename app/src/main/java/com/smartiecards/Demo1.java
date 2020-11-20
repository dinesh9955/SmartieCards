package com.smartiecards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eftimoff.viewpagertransformers.FlipHorizontalTransformer;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by AnaadIT on 1/30/2018.
 */

public class Demo1 extends AppCompatActivity{
    private static final int TWO_SPACES = 2;
    private static final String TAG = "Demo1";
    TextView textView;

    int MAX_LINES = 3;

    private ArrayList<String> arrayList = new ArrayList<String>();

    private static ViewPager mPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demo1);

        mPager = (ViewPager) findViewById(R.id.pager);


        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");

//        mPager.setAdapter(new MyAdapter(Demo1.this, arrayList));
        mPager.setPageTransformer(true, new FlipHorizontalTransformer());
      //  textView = (TextView) findViewById(R.id.tvReviewDescription);

//        String text = "I tend to shy away from restaurant chains, but wherever I go, PF Chang&apos;s has solidly good food and, like Starbucks, they&apos;re reliable. We were staying in Boston for a week and after a long day and blah blah blah blah...";
//
//        if (text.length()>20) {
//            text=text.substring(0,20)+"...";
//            textView.setText(Html.fromHtml(text+"<font color='red'> <u>View More</u></font>"));
//
//        }

      //  makeTextViewResizable(textView, 3, "See More", true);





       // final String myReallyLongText = "Bacon ipsum dolor amet porchetta venison ham fatback alcatra tri-tip, turducken strip steak sausage rump burgdoggen pork loin. Spare ribs filet mignon salami, strip steak ball tip shank frankfurter corned beef venison. Pig pork belly pork chop andouille. Porchetta pork belly ground round, filet mignon bresaola chuck swine shoulder leberkas jerky boudin. Landjaeger pork chop corned beef, tri-tip brisket rump pastrami flank.";





     //   ImageView imageView = (ImageView) findViewById(R.id.imageView23435);


//        Glide.with(Demo1.this)
//                .load(R.drawable.dance)
////                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                  //.crossFade(500)
////                .placeholder(R.drawable.loading_spinner)
//                .into(imageView);

       // GlideApp.with(this).load("http://goo.gl/gEgYUd").into(imageView);

//        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
//                    BitmapFactory.decodeResource(getResources(), R.drawable.dance), null, null));


//        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dance, null);
//        Bitmap myLogo = ((BitmapDrawable) vectorDrawable).getBitmap();

//        Drawable myDrawable = getResources().getDrawable(R.drawable.dance);
//
//                Glide.with(Demo1.this).load(myDrawable).into(imageView);



        ImageView imgView = (ImageView) findViewById(R.id.imageView23435);
//        imgView.setImageResource(R.drawable.dance);
////        imgView.setDrawingCacheEnabled(true);
////        Bitmap bitmap = Bitmap.createBitmap(imgView.getDrawingCache());
////
////        Glide.with(Demo1.this).load(bitmap).into(imgView);
////
////        imgView.setFreezesAnimation(false);




//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgView);
//
//        Glide.with(Demo1.this).load(R.drawable.dance1).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade(00).into(imageViewTarget);


      //  GifDrawable gifFromAssets = new GifDrawable( getAssets(), "anim.gif" );

//        try {
//            GifDrawable gifFromAssets = new GifDrawable( getAssets(), "dance.gif" );
//            //GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.dance );
//            imgView.setImageDrawable(gifFromAssets);
//            long tt = gifFromAssets.getDuration();
//            Log.e(TAG , "tttttt "+tt);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }







    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#1b76d3"));
        }

        @Override
        public void onClick(View widget) {


        }
    }


}
