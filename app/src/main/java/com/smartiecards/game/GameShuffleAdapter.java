package com.smartiecards.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.smartiecards.network.WSContants;
import com.smartiecards.util.Utility;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 3/8/2018.
 */

public class GameShuffleAdapter extends PagerAdapter {

    private static final String TAG = "GameShuffleAdapter";

    private ArrayList<ItemGameShuffle> arrayList = new ArrayList<ItemGameShuffle>();
    private LayoutInflater inflater;
    private Context context;


    ImageLoader imageLoader;
    DisplayImageOptions options;

    String colorK = "#00000000";


    public GameShuffleAdapter(Context context, ArrayList<ItemGameShuffle> images, String col) {
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
        View myImageLayout = inflater.inflate(R.layout.home_slide_item, view, false);




        ScrollView scrollView1 = (ScrollView) myImageLayout.findViewById(R.id.firstScroll);
        ScrollView scrollView2 = (ScrollView) myImageLayout.findViewById(R.id.secondScroll);

        LinearLayout linearLayout1 = (LinearLayout) myImageLayout.findViewById(R.id.firstLinear);
        LinearLayout linearLayout2 = (LinearLayout) myImageLayout.findViewById(R.id.secondLinear);

        RelativeLayout relativeLayout1 = (RelativeLayout) myImageLayout.findViewById(R.id.first);
        RelativeLayout relativeLayout2 = (RelativeLayout) myImageLayout.findViewById(R.id.second);

//        final RelativeLayout relativeLayoutClickToFLip = (RelativeLayout) myImageLayout.findViewById(R.id.second_image);
//
//        if(arrayList.size() > 0){
//            if(position == 0){
//                relativeLayoutClickToFLip.setVisibility(View.VISIBLE);
//            }else{
//                relativeLayoutClickToFLip.setVisibility(View.GONE);
//            }
//        }




        final ViewAnimator viewAnimator = (ViewAnimator)myImageLayout.findViewById(R.id.viewFlipper);


        final ViewFlipper viewFlipper = (ViewFlipper)myImageLayout.findViewById(R.id.viewFlipper);

        Log.e(TAG, "arrayList "+position);


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
               // relativeLayoutClickToFLip.setVisibility(View.GONE);
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
                //relativeLayoutClickToFLip.setVisibility(View.GONE);
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






        TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
        TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);

        textViewQues.setText(arrayList.get(position).getQuestion());
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


        Log.e(TAG , "getQuestionimage "+arrayList.get(position).getQuestionimage());

//        if(position == 0){
//            imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getQuestionimage(), imageViewQuestion);
//        }
//

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

        imageLoader.displayImage(WSContants.BASE_MAIN__ADDS_IMAGE_URL+arrayList.get(position).getAnswerimage(), imageViewAnswer);
        imageViewAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ImageZoom.class);
                intent.putExtra("imageLink" , WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(position).getAnswerimage());
                context.startActivity(intent);
            }
        });


        ImageView imageViewQuestionIdi = (ImageView) myImageLayout.findViewById(R.id.imageView54356);
        ImageView imageViewAnswerIdi = (ImageView) myImageLayout.findViewById(R.id.imageView75667);


//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestionIdi.setVisibility(View.VISIBLE);
//                imageViewAnswerIdi.setVisibility(View.VISIBLE);
//            }else{
                imageViewQuestionIdi.setVisibility(View.GONE);
                imageViewAnswerIdi.setVisibility(View.GONE);
//            }
//        }


//        if(arrayList.size() > 0){
//            if(position == 0){
//                imageViewQuestion.setVisibility(View.VISIBLE);
//            }else{
//                imageViewQuestion.setVisibility(View.GONE);
//            }
//        }



//
//        TextView textViewQues = (TextView) myImageLayout.findViewById(R.id.textView345646);
//        textViewQues.setVisibility(View.VISIBLE);
//        TextView textViewAns = (TextView) myImageLayout.findViewById(R.id.textView345645435345);
//        textViewAns.setVisibility(View.VISIBLE);
//
//        textViewQues.setText(arrayList.get(position).getQuestion());
//        textViewAns.setText(arrayList.get(position).getAnswer());




//
//        final WebView webViewQue = (WebView) myImageLayout.findViewById( R.id.webView1354);;
//        webViewQue.setVisibility(View.VISIBLE);
//        webViewQue.getSettings().setLoadWithOverviewMode(true);
//        webViewQue.getSettings().setUseWideViewPort(true);
//        webViewQue.getSettings().setJavaScriptEnabled(true);
//        webViewQue.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
//
//
//        webViewQue.getSettings().setTextZoom((int) (webViewQue.getSettings().getTextZoom() * 2.1));
//
//        webViewQue.loadData("<b>"+arrayList.get(position).getQuestion()+"</b>", "text/html; charset=utf-8", "utf-8");
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
////                webViewQue.loadUrl( "javascript:document.body.style.setProperty(\"color\", \"white\");" );
//                webViewQue.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
//            }
//        });
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
//                webViewAns.loadUrl( "javascript:document.body.style.setProperty(\"color\", \""+arrayList.get(position).getFontsize()+"\");" );
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
//





        view.addView(myImageLayout, 0);

       // ((GameShuffleFlip)context).showView(position);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }







    public void updateData(ArrayList<ItemGameShuffle> arrayList2) {
        // TODO Auto-generated method stub
        arrayList = arrayList2;
//        arrayList.clear();
//        arrayList.addAll(arrayList2);
        notifyDataSetChanged();
    }
}