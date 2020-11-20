package com.smartiecards.home;

/**
 * Created by AnaadIT on 11/3/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.smartiecards.R;
import com.smartiecards.account.ImageZoom;
import com.smartiecards.flipview.AnimationFactory;
import com.smartiecards.home.FlashCardFlip;
import com.smartiecards.home.ItemCardFlip;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.network.WSContants;
import com.smartiecards.util.Utility;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Typeface.*;

public class MyAdapter extends PagerAdapter {

    private static final String TAG = "MyAdapter";

    ViewAnimator viewAnimator;
    boolean isFlip = false;

    private ArrayList<ItemCardFlip> arrayList = new ArrayList<ItemCardFlip>();
    private LayoutInflater inflater;
    private Activity context;


    ImageLoader imageLoader;
    DisplayImageOptions options;

    String colorK = "#00000000";


    boolean isStarShow = false;

    int starPosition = 0;


    public MyAdapter(Activity context, ArrayList<ItemCardFlip> images, String col) {
        this.context = context;
        this.arrayList=images;
        colorK = col;

        inflater = LayoutInflater.from(context);


        try{
            imageLoader = ImageLoader.getInstance();

            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.picture_default)
                    .showImageForEmptyUri(R.drawable.picture_default)
                    .showImageOnFail(R.drawable.picture_default)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
//                    .displayer(new CircleBitmapDisplayer(Color.parseColor("#efddb4"), 3))
                    .build();

        }catch(Exception e){
            Log.d(TAG, "myError11: "+e.getMessage());
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }



    @Override
    public Object instantiateItem(ViewGroup view,final int position) {

        Log.e(TAG , position+ " instantiateItem "+view.getId());

        View myImageLayout = inflater.inflate(R.layout.home_slide_item, view, false);

        ScrollView scrollView1 = (ScrollView) myImageLayout.findViewById(R.id.firstScroll);
        ScrollView scrollView2 = (ScrollView) myImageLayout.findViewById(R.id.secondScroll);

        LinearLayout linearLayout1 = (LinearLayout) myImageLayout.findViewById(R.id.firstLinear);
        LinearLayout linearLayout2 = (LinearLayout) myImageLayout.findViewById(R.id.secondLinear);

        RelativeLayout relativeLayout1 = (RelativeLayout) myImageLayout.findViewById(R.id.first);
        RelativeLayout relativeLayout2 = (RelativeLayout) myImageLayout.findViewById(R.id.second);



//
       // LongShadowsWrapper longShadowsWrapper1 = (LongShadowsWrapper) myImageLayout.findViewById(R.id.long111);
//        LongShadowsWrapper longShadowsWrapper2 = (LongShadowsWrapper) myImageLayout.findViewById(R.id.long222);
//
//        longShadowsWrapper1.setShouldAnimateShadow(true);
//        longShadowsWrapper1.setAnimationDuration(10);
//
//        longShadowsWrapper2.setShouldAnimateShadow(true);
//        longShadowsWrapper2.setAnimationDuration(10);

        //  ImageView imageViewStar = (ImageView) myImageLayout.findViewById(R.id.button2534565);


//        if(isStarShow == true){
//            imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
//        }else{
//            imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
//        }


//        Log.e(TAG , "getStar_status "+arrayList.get(position).getStar_status());
//
//        if(arrayList.get(position).getStar_status().equalsIgnoreCase("1")){
//            imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
//        }else{
//            imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
//        }
//
//        imageViewStar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG , "imageViewStar");
////                ItemCardFlip cardFlip = arrayList.get(position);
////                if(isStarShow == true){
////                    imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
////                   // callUnStarApiMethod(cardFlip);
////
////                    ((FlashCardFlip)context).callUnStarApiMethod(cardFlip);
////
////                }else{
////                    imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
////                   // callStarApiMethod(cardFlip);
////                    ((FlashCardFlip)context).callStarApiMethod(cardFlip);
////                }
//                ItemCardFlip cardFlip = arrayList.get(position);
//                if(arrayList.get(position).getStar_status().equalsIgnoreCase("1")){
//                    imageViewStar.setImageResource(R.drawable.icn_rating_star_yellow);
//                    cardFlip.setStar_status("1");
//                    arrayList.set(position, cardFlip);
//                    ((FlashCardFlip)context).callUnStarApiMethod(cardFlip);
//                }else{
//                    imageViewStar.setImageResource(R.drawable.icn_rating_start_fade_yellow);
//                    cardFlip.setStar_status("0");
//                    arrayList.set(position, cardFlip);
//                    ((FlashCardFlip)context).callStarApiMethod(cardFlip);
//                }
//
////                instantiateItem(position);
////                notifyDataSetChanged();
//              //  ((FlashCardFlip)context).updateData(arrayList);
//
//
//            }
//        });



//
//        imageViewStar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        final RelativeLayout relativeLayoutClickToFLip = (RelativeLayout) myImageLayout.findViewById(R.id.second_image);
//
//        if(arrayList.size() > 0){
//            if(position == 0){
//                relativeLayoutClickToFLip.setVisibility(View.VISIBLE);
//            }else{
//                relativeLayoutClickToFLip.setVisibility(View.GONE);
//            }
//        }




        ViewAnimator viewAnimator = (ViewAnimator)myImageLayout.findViewById(R.id.viewFlipper);
        final ViewFlipper viewFlipper = (ViewFlipper)myImageLayout.findViewById(R.id.viewFlipper);

        Log.e(TAG, "arrayList "+colorK);




//        viewFlipper.setInAnimation(context , R.anim.left_1);
//            viewFlipper.setFlipInterval(5000);
//            viewFlipper.setAutoStart(true);
//
//            viewFlipper.startFlipping();




        if(Utility.isColor(colorK) == true){
            int color = Utility.replaceColor(colorK);

            relativeLayout1.setBackgroundColor(color);
            relativeLayout2.setBackgroundColor(color);
        }



        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListAAA "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
            }
        });


        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListBBB "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showPrevious();
              //  relativeLayoutClickToFLip.setVisibility(View.GONE);
            }
        });










        scrollView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListAAA "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
            }
        });



        scrollView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListAAA "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showNext();
              //  relativeLayoutClickToFLip.setVisibility(View.GONE);
            }
        });






        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListAAA "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                    viewFlipper.showNext();
            }
        });



        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e(TAG, "arrayListAAA "+position);
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                    viewFlipper.showNext();
               // relativeLayoutClickToFLip.setVisibility(View.GONE);
            }
        });



//        if(isFlip == true){
//            isFlip = false;
////            viewAnimator.setDuration(700);
////            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//            Log.e(TAG , "isFlip "+isFlip);
////            viewFlipper.setInAnimation(context, android.R.anim.slide_out_right);
////            viewFlipper.setFlipInterval(2000);
////            viewFlipper.startFlipping();
////            viewFlipper.showNext();
////            viewFlipper.setAutoStart(true);
//            //viewAnimator.setAnimateFirstView(true);
//
////            Animation imgAnimationOut = AnimationUtils.
////                    loadAnimation(context, android.R.anim.slide_out_right);
////            imgAnimationOut.setDuration(700);
////            viewAnimator.setOutAnimation(imgAnimationOut);
//
//        }



        TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
        TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);

        textViewQues.setText(arrayList.get(position).getFormula());
        textViewAns.setText(arrayList.get(position).getAnswer());

        String colorAA = arrayList.get(position).getFontsize().replace("\u00a0"  ,"").replace(" "  ,"").trim();

        Log.e(TAG , "getFontsize111 "+colorAA);

        if(Utility.isColor(colorAA) == true){
            Log.e(TAG , "getFontsize "+colorAA);
            //int color = Utility.replaceColor(arrayList.get(position).getFontsize());
            textViewQues.setTextColor(Color.parseColor(colorAA));
            textViewAns.setTextColor(Color.parseColor(colorAA));
        }


        textViewQues.setVisibility(View.VISIBLE);
        textViewAns.setVisibility(View.VISIBLE);


        ImageView imageViewQuestion = (ImageView) myImageLayout.findViewById(R.id.imageView6456);
        ImageView imageViewAnswer = (ImageView) myImageLayout.findViewById(R.id.imageView64564325);



//        if(position == 0){
//            //imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getFormulaimage(), imageViewQuestion);
//        }
            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage(), imageViewAnswer);
        imageViewAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ImageZoom.class);
                    intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(position).getAnswerimage());
                    context.startActivity(intent);
                }
            });

//        if(!arrayList.get(position).getAnswerimage2().equalsIgnoreCase("")){
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage2(), imageViewAnswer);
//        }else{
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage(), imageViewAnswer);
//            imageViewAnswer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context , ImageZoom.class);
//                    intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(position).getAnswerimage());
//                    context.startActivity(intent);
//                }
//            });
//        }




//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestion.setVisibility(View.VISIBLE);
//            }else{
//                imageViewQuestion.setVisibility(View.GONE);
//            }
//        }


//        ImageView imageViewQuestionIdi = (ImageView) myImageLayout.findViewById(R.id.imageView54356);
//        ImageView imageViewAnswerIdi = (ImageView) myImageLayout.findViewById(R.id.imageView75667);
//
//
//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestionIdi.setVisibility(View.VISIBLE);
//                imageViewAnswerIdi.setVisibility(View.VISIBLE);
//            }else{
//                imageViewQuestionIdi.setVisibility(View.GONE);
//                imageViewAnswerIdi.setVisibility(View.GONE);
//            }
//        }

//        Display display = context.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x - 20;
//        int height = size.y;


//        imageViewQuestion.getLayoutParams().height = width;
//        imageViewQuestion.getLayoutParams().width = width;


//        imageViewAnswer.getLayoutParams().height = width;
//        imageViewAnswer.getLayoutParams().width = width;





//        final WebView  webViewQue = (WebView) myImageLayout.findViewById( R.id.webView1354);;
//        webViewQue.setVisibility(View.VISIBLE);
//        webViewQue.getSettings().setLoadWithOverviewMode(true);
//        webViewQue.getSettings().setUseWideViewPort(true);
//        webViewQue.getSettings().setJavaScriptEnabled(true);
//        webViewQue.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//
//
//        webViewQue.getSettings().setTextZoom((int) (webViewQue.getSettings().getTextZoom() * 2.1));
//
//        webViewQue.loadData("<b>"+arrayList.get(position).getFormula()+"</b>", "text/html; charset=utf-8", "utf-8");
//        webViewQue.setBackgroundColor(0x00000000);
//        webViewQue.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//
//        webViewQue.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                if(Utility.isColor(arrayList.get(position).getFontsize()) == true){
//                    //int color = Utility.replaceColor(arrayList.get(position).getFontsize());
//                    webViewQue.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
//                }
//
//            }
//        });
//
//
//
//
//
//        final WebView  webViewAns = (WebView) myImageLayout.findViewById( R.id.webView5466);;
//        webViewAns.setVisibility(View.VISIBLE);
//        webViewAns.getSettings().setLoadWithOverviewMode(true);
//        webViewAns.getSettings().setUseWideViewPort(true);
//        webViewAns.getSettings().setJavaScriptEnabled(true);
//        webViewAns.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//        webViewAns.getSettings().setTextZoom((int) (webViewAns.getSettings().getTextZoom() * 2.1));
//        webViewAns.loadData(arrayList.get(position).getAnswer(), "text/html; charset=utf-8", "utf-8");
//        webViewAns.setBackgroundColor(0x00000000);
//        webViewAns.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//
//        webViewAns.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                webViewAns.loadUrl( "javascript:document.body.style.setProperty(\"color\", \"white\");" );
////                webViewAns.loadUrl("javascript:(document.body.style.fontSize ='20pt');");
//                if(Utility.isColor(arrayList.get(position).getFontsize()) == true){
//                   // int color = Utility.replaceColor(arrayList.get(position).getFontsize());
//                    webViewAns.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
//                }
//            }
//        });
//
//
//
//
//
//        webViewQue.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_MOVE){
//                    return false;
//                }
//
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//                }
//
//                return false;
//            }
//        });
//
//        webViewAns.setOnTouchListener(new View.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction()==MotionEvent.ACTION_MOVE){
//                    return false;
//                }
//
//                if (event.getAction()==MotionEvent.ACTION_UP){
//                    AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//                }
//
//                return false;
//            }
//        });



       // flipDD(viewAnimator);


        view.addView(myImageLayout, 0);


//        try {
//            Thread.sleep(3000);
//            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////
////        ((FlashCardFlip)context).showView(position);

        return myImageLayout;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }







    public void updateData(ArrayList<ItemCardFlip> arrayList2) {
        // TODO Auto-generated method stub
        arrayList = arrayList2;
//        arrayList.clear();
//        arrayList.addAll(arrayList2);
        notifyDataSetChanged();
    }

    public void darkStar(boolean star) {
        isStarShow = star;
       // notifyDataSetChanged();
    }


    public void fadeStar(boolean star) {
        isStarShow = star;
        //notifyDataSetChanged();
    }


    public void statPosition(int pos) {
        starPosition = pos;
       // notifyDataSetChanged();
    }





    private void flipDD(ViewAnimator viewAnimator11) {
        viewAnimator = viewAnimator11;

        Log.e(TAG , "BBBBBBBBBBBBBBBBBBBB");
//        if(isFlip == true) {
//            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.RIGHT_LEFT);
//            isFlip = false;
//        }
//        AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
    }


    public void upDateFlip(boolean b) {


        isFlip = b;

        if(isFlip == true) {
            isFlip = false;
            Log.e(TAG , "AAAAAAAAAAAAAAAAA");
            AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
        }
        //notifyDataSetChanged();

    }
}