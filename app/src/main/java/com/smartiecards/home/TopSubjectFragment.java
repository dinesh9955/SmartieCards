package com.smartiecards.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.smartiecards.BaseFragment;
import com.smartiecards.ItemClickListener;
import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.account.ImageZoom;
import com.smartiecards.dashboard.BannerActivity;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.EasyFontsCustom;
import com.smartiecards.util.RecyclerItemClickListener;
import com.smartiecards.util.ResizableCustomView;
import com.smartiecards.util.ShowMsg;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AnaadIT on 2/6/2018.
 */

public class TopSubjectFragment extends BaseFragment {

    //public static final int PAYPAL_REQUEST_CODE = 153;
    public static final int COUPON_REQUEST_CODE = 153;

    private static final String TAG = "TopSubjectFragment";
    Button buttonLogin, buttonSignUp;

    ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TopSubjectAdapter mAdapter;

    TextView textViewMsg, textViewPathPostImage;
    ProgressBar progressBar;

    private SwipeRefreshLayout swipeRefreshLayout;

    //    ProfileTask profileTask = null;
//    TopSubjectTask subjectHomeTask = null;
    final Handler handler = new Handler();

   // SavePref pref = new SavePref();

    Button buttonBuyNowAll;
   // RelativeLayout relativeLayoutTop;
   View viewBuyNowAllLayout;


    private boolean isMultiSelect = false;
    //i created List of int type to store id of data, you can create custom class type data according to your need.
    private List<Integer> selectedIds = new ArrayList<>();

    private boolean buyNowAll = false;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        textViewMsg = (TextView) view.findViewById(R.id.textView123124);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1444);


        buttonBuyNowAll = (Button) view.findViewById(R.id.button414234);

        viewBuyNowAllLayout = view.findViewById(R.id.include_1);
//        viewBuyNowAllLayout.setVisibility(View.GONE);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        if(pref.getId().equalsIgnoreCase("")){
            viewBuyNowAllLayout.setVisibility(View.GONE);
        }else{
            viewBuyNowAllLayout.setVisibility(View.VISIBLE);
        }


        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new TopSubjectAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                callRefreshMethod();
            }
        });

        callRefreshMethod();


        viewBuyNowAllLayout.setVisibility(View.GONE);

        buttonBuyNowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pref.getId().equalsIgnoreCase("")){
                    if(buyNowAll == true && selectedIds.size() > 0){
                        Log.e(TAG , "GGGGGGGGGGGG");

                        if(arrayList.size() > 0){
                            ArrayList<ItemSubject> subjects = new ArrayList<>();
                            for (ItemSubject data : arrayList) {
                                if (selectedIds.contains(data.getId_count())){
                                    subjects.add(data);
                                }
                                // stringBuilder.append("\n").append(data.getTitle());
                            }

                            if (subjects.size() > 0){
                                Intent intent = new Intent(getActivity(), MultiplePaymentActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("pos", "1");
                                bundle.putSerializable("key", subjects);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, COUPON_REQUEST_CODE);
                            }

                            //Toast.makeText(getActivity(), "Selected items are :" + subjects.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Log.e(TAG , "HHHHHHHHHHHHH");
                        if(arrayList.size() > 0){
                            Intent intent = new Intent(getActivity(), MultiplePaymentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pos", "1");
                            bundle.putSerializable("key", arrayList);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, COUPON_REQUEST_CODE);
                        }
                    }
                }else{
                    MainActivity activity = (MainActivity) getActivity();
                    activity.switchPosition(1);
                }
            }
        });



//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (isMultiSelect){
//                    //if multiple selection is enabled then select item on single click else perform normal click on item.
//                    multiSelect(position);
//                    Log.e(TAG ,"ZZZZZZZZZZz");
//                }
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                if (!isMultiSelect){
//                    selectedIds = new ArrayList<>();
//                    isMultiSelect = true;
//
////                if (actionMode == null){
////                    actionMode = startActionMode(MainActivity.this); //show ActionMode.
////                }
//                    Log.e(TAG ,"QQQQQQQQQQQ");
//                }
//                Log.e(TAG ,"EEEEEEEE");
//                multiSelect(position);
//            }
//        }));




        return view;
    }






    private void multiSelect(int position) {
        ItemSubject data = mAdapter.getItem(position);
        if (data != null){
            Log.e(TAG ,"000000000");
            if(data.getPurchase_status().equalsIgnoreCase("0")) {
                if (selectedIds.contains(data.getId_count())) {
                    selectedIds.remove(Integer.valueOf(data.getId_count()));
                    Log.e(TAG, "AAAAAAAAAa");
                } else {
                    selectedIds.add(data.getId_count());
                    Log.e(TAG, "BBBBBBBBbb");
                }

                mAdapter.setSelectedIds(selectedIds);
            }else{
                new ShowMsg().createSnackbar(getActivity() , "Subject already purchased.");
            }

        }

        if(selectedIds.size() > 0){
            buyNowAll = true;
            buttonBuyNowAll.setText("Buy selected subjects now");
        }else{
            buyNowAll = false;
            buttonBuyNowAll.setText("Buy all subjects now");
        }



        Log.e(TAG, "selectedIds:: "+selectedIds);


    }






    private void callRefreshMethod() {
        boolean b = WSConnector.checkAvail(getActivity());
        if (b == true) {
           callApiMethod();
        } else {
            textViewMsg.setText("No internet connection.");
            textViewMsg.setVisibility(View.VISIBLE);
        }




    }


    private void callApiMethod() {

        progressBar.setVisibility(View.VISIBLE);
//        ArrayList<MultipartBody.Part> arrayListMash = new ArrayList<MultipartBody.Part>();
//
//        MultipartBody.Part userid = MultipartBody.Part.createFormData("userid", pref.getId());
//        arrayListMash.add(userid);

        call = apiInterface.topSubjects(pref.getId());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "ssss " + responseCode);

                        selectedIds.clear();
                        isMultiSelect = false;
                        buyNowAll = false;
                        buttonBuyNowAll.setText("Buy all subjects now");

                        if(!pref.getId().equalsIgnoreCase("")){
                            arrayList = new ParsingHelper().getItemTopSubject(responseCode);
                        }else{
                            arrayList = new ParsingHelper().getItemTopSubjectWithoutId(responseCode);
                        }




                        if (arrayList.size() == 0) {
                            textViewMsg.setText("No subject category.");
                            textViewMsg.setVisibility(View.VISIBLE);
                        } else {
                            textViewMsg.setVisibility(View.GONE);

                            int allAmounts = getAllAmounts(arrayList);

                            if(allAmounts > 0){
                                viewBuyNowAllLayout.setVisibility(View.VISIBLE);
                            }else{
                                viewBuyNowAllLayout.setVisibility(View.GONE);
                            }

                        }
                        mAdapter.updateData(arrayList);


                    }else{
                        new ShowMsg().createSnackbar(getActivity(), "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new ShowMsg().createSnackbar(getActivity(), ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });




    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
//        handler.removeCallbacks(runnableAbout);
//        if(subjectHomeTask != null){
//            if (subjectHomeTask.getStatus() != AsyncTask.Status.FINISHED) {
//                subjectHomeTask.cancel(false);
//            }
//        }

    }

//
//
//    class TopSubjectTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            return WSConnector.getStringHTTPnHTTPSResponse(WSContants.BASE_MAIN_URL_ANDROID + "top_subjects.php?userid="+params[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            progressBar.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
//            Log.d(TAG, "ssss " + s);
//
//            selectedIds.clear();
//            isMultiSelect = false;
//            buyNowAll = false;
//            buttonBuyNowAll.setText("Buy all subjects now");
//
//            if(!pref.getId().equalsIgnoreCase("")){
//                arrayList = new ParsingHelper().getItemTopSubject(s);
//            }else{
//                arrayList = new ParsingHelper().getItemTopSubjectWithoutId(s);
//            }
//
//
//
//
//
//
//            if (arrayList.size() == 0) {
//                textViewMsg.setText("No subject category.");
//                textViewMsg.setVisibility(View.VISIBLE);
//            } else {
//                textViewMsg.setVisibility(View.GONE);
//
//               int allAmounts = getAllAmounts(arrayList);
//
//               if(allAmounts > 0){
//                   viewBuyNowAllLayout.setVisibility(View.VISIBLE);
//               }else{
//                   viewBuyNowAllLayout.setVisibility(View.GONE);
//               }
//
//            }
//            mAdapter.updateData(arrayList);
//        }
//    }




    private int getAllAmounts(ArrayList<ItemSubject> arrayList) {
        int totalAmount = 0;
        for(int i = 0 ; i < arrayList.size() ; i++){
            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("0")){
                try{
                    totalAmount = totalAmount + Integer.parseInt(arrayList.get(i).getAmount());
                }catch (Exception e){
                }
            }
        }
        return totalAmount;
    }


    private boolean getAaA(ArrayList<ItemSubject> arrayList, ItemSubject subject) {
        boolean b = false;

        Log.e(TAG, subject.getId()+" arrayList2222 ");


        for(int i = 0 ; i < arrayList.size() ; i++){
            Log.e(TAG, subject.getId()+" arrayList2222 "+arrayList.get(i).getId());
            if(!subject.getId().equalsIgnoreCase(arrayList.get(i).getId())){
                Log.e(TAG, subject.getId()+" arrayList111 "+arrayList.get(i).getId());
                b = true;
            }
        }
        return b;
    }


//    public boolean compareLists(ArrayList<ItemSubject> prevList, List<MyData> modelList) {
//        if (prevList.size() == modelList.size()) {
//            for (MyData modelListdata : modelList) {
//                for (MyData prevListdata : prevList) {
//                    if (prevListdata.getName().equals(modelListdata.getName())
//                            && prevListdata.isCheck() != modelListdata.isCheck()) {
//                        return  true;
//
//                    }
//                }
//
//            }
//        }
//        else{
//            return true;
//        }
//        return false;
//
//    }








    public class TopSubjectAdapter extends RecyclerView.Adapter<TopSubjectAdapter.ViewHolder> {

        private List<Integer> selectedIds = new ArrayList<>();

        private static final String TAG = "TopSubjectAdapter";
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();
        ArrayList<String> arrayListColor = new ArrayList<String>();
        Activity context;

        ImageLoader imageLoader;
        DisplayImageOptions options;

        ImageLoader imageLoader1;
        DisplayImageOptions options1;


        public TopSubjectAdapter(Activity context11, ArrayList<ItemSubject> arrayList111) {
            super();
            this.context = context11;
            arrayList = arrayList111;
            // FacebookSdk.sdkInitialize(context);

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
                        // .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 1))
                        .build();
            }catch(Exception e){
                Log.d(TAG, "myError11: "+e.getMessage());
            }



        }

        @Override
        public TopSubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            final View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.home_item, viewGroup, false);
            TopSubjectAdapter.ViewHolder viewHolder = new TopSubjectAdapter.ViewHolder(v);
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final TopSubjectAdapter.ViewHolder viewHolder, final int i) {

            int id = arrayList.get(i).getId_count();

            if (selectedIds.contains(id)){
                //if item is selected then,set foreground color of FrameLayout.
                viewHolder.cardView.setCardBackgroundColor(Color.GRAY);
            }
            else {
                //else remove selected item color.
                viewHolder.cardView.setCardBackgroundColor(Color.WHITE);
            }

            if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
               // viewHolder.layoutShade.setVisibility(View.GONE);
                viewHolder.buttonBuyNow.setText("See Topics");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname());
                viewHolder.textViewDesc.setVisibility(View.GONE);
            }else{
               // viewHolder.layoutShade.setVisibility(View.VISIBLE);
                viewHolder.buttonBuyNow.setText("See Topics");
                viewHolder.buttonBuyNow.setBackgroundResource(R.drawable.green_selector);
                viewHolder.textViewTitle.setText(arrayList.get(i).getSubjectname()+"\n"+WSContants.CURRENCY+arrayList.get(i).getAmount()+""+WSContants.TERM);

               // String text = "Welcome to Android Masterd tutorials which are commonly used in development now days.";

                Log.e(TAG , "arrayList.get(i).getDiscription() "+arrayList.get(i).getDiscription());

                viewHolder.textViewDesc.setText(arrayList.get(i).getDiscription());

                if(!arrayList.get(i).getDiscription().equalsIgnoreCase("")){
                    if(arrayList.get(i).getDiscription().length() > 50){
                        ResizableCustomView.addReadMore(arrayList.get(i).getDiscription(),viewHolder.textViewDesc);
                    }else{

                    }
                }

                viewHolder.textViewDesc.setVisibility(View.VISIBLE);
                viewHolder.textViewDesc.setTypeface(EasyFontsCustom.avenirnext_TLPro_Medium(context));
            }

            viewHolder.textViewDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity() , FullTextActivity.class);
                    intent.putExtra("key" , arrayList.get(i).getDiscription());
                    startActivity(intent);
                }
            });


            viewHolder.buttonBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!pref.getId().equalsIgnoreCase("")){
                        String buttonTxt = viewHolder.buttonBuyNow.getText().toString();
                        if(arrayList.get(i).getPurchase_status().equalsIgnoreCase("1")){
                       // if(buttonTxt.equalsIgnoreCase("View Detail")){
//                            Intent intent = new Intent(getActivity() , FlashCardLists.class);
                            Intent intent = new Intent(getActivity() , BannerActivity.class);
                            intent.putExtra("key", arrayList.get(i).getId());
                            intent.putExtra("classKey", "Top Subjects");
                            intent.putExtra("key2", arrayList.get(i).getSubjectname());
                            startActivity(intent);



//                            Intent intent = new Intent(getActivity() , FlashCardLists.class);
//                            intent.putExtra("key", arrayList.get(i).getId());
//                            intent.putExtra("classKey", "Top Subjects");
//                            intent.putExtra("key2", arrayList.get(i).getSubjectname());
//                            startActivity(intent);


                        }else{

                            Intent intent = new Intent(getActivity(), AddCoupon.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pos", "1");
                            bundle.putSerializable("key", arrayList.get(i));
                            intent.putExtras(bundle);
                            startActivityForResult(intent, COUPON_REQUEST_CODE);

                        }
                    }else{
//                        MainActivity activity = (MainActivity) getActivity();
//                        activity.switchFragment(new LoginFragment());
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("keyPosition" , 1);
                        getActivity().startActivity(i);
                        getActivity().finishAffinity();
                        getActivity().finish();
                    }


                }
            });


            imageLoader.displayImage(WSContants.BASE_MAIN__SUBJECT_IMAGE_URL+arrayList.get(i).getPhoto(), viewHolder.imageViewTopicImage, options);

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public ItemSubject getItem(int position){
            return arrayList.get(position);
        }

        public void setSelectedIds(List<Integer> selectedIds) {
            this.selectedIds = selectedIds;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            View view11 = null;
            public ImageView imageViewTopicImage, imageViewPostImage, imageViewLike, imageViewComment, imageViewDelete, imageViewShare;
            public TextView textViewTitle, textViewDesc, textViewNews_post, textViewLikeCount, textViewCommnentCount, textViewReportPost;

            Button buttonBuyNow;
            public RelativeLayout layout, layoutShade;

            private ItemClickListener clickListener;
            View view = null;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                view11 = itemView;

                buttonBuyNow = (Button) itemView.findViewById(R.id.button414234);
                layoutShade = (RelativeLayout) itemView.findViewById(R.id.layout234353);
                textViewTitle = (TextView) itemView.findViewById(R.id.textView46456);
                textViewDesc = (TextView) itemView.findViewById(R.id.textView4645667);

                imageViewTopicImage = (ImageView) itemView.findViewById(R.id.imageView6456);
//                imageViewPostImage = (ImageView) itemView.findViewById(R.id.imageView1111);
                cardView = (CardView) itemView.findViewById(R.id.card_view);

            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }
        }


        public void updateData(ArrayList<ItemSubject> arrayList2) {
            // TODO Auto-generated method stub
            arrayList.clear();
            arrayList.addAll(arrayList2);
            notifyDataSetChanged();
        }
    }









    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        if(requestCode == PAYPAL_REQUEST_CODE){
//            if (resultCode == Activity.RESULT_OK) {
//                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if (confirm != null) {
//                    try {
//                        Log.i("paymentExample111", confirm.toJSONObject().toString(4));
//                        Log.i("paymentExample222", confirm.getPayment().toJSONObject()
//                                .toString(4));
//
//                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
//                        JSONObject response = jsonObject.getJSONObject("response");
//                        String id = response.getString("id");
//                        String state = response.getString("state");
//
//                        //if(state.equals("approved")){
//                        JSONObject jsonObject2 = new JSONObject(confirm.getPayment().toJSONObject()
//                                .toString(4));
//                        String amount = jsonObject2.getString("amount");
//
//
//                    } catch (JSONException e) {
//                        Log.e("paymentExample111", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            }
//            else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("paymentExample111", "The user canceled.");
//            }
//            else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i("paymentExample111", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//            }
//        }





        if(requestCode == COUPON_REQUEST_CODE){
            callRefreshMethod();
        }
    }






    private String convertValueStringToDouble(String valueFirst) {
        try{
            double time = Double.parseDouble(valueFirst);
            DecimalFormat df = new DecimalFormat("0.00");
            //DecimalFormat df = new DecimalFormat("##.##");
            return ""+df.format(time);
        }catch (Exception e){

        }
        return "0.0";
    }




}
