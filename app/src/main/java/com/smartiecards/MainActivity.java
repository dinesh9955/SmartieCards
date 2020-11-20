package com.smartiecards;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.paypal.android.sdk.payments.PayPalService;
import com.smartiecards.account.LoginFragment;
import com.smartiecards.account.ProfileActivity;
import com.smartiecards.dashboard.AccountFragment;
import com.smartiecards.home.ContactFragment;
import com.smartiecards.game.GamesFragment;
import com.smartiecards.home.SubjectFragment;
import com.smartiecards.home.TopSubjectFragment;
import com.smartiecards.network.WSConnector;
import com.smartiecards.network.WSContants;
import com.smartiecards.parsing.ParsingHelper;
import com.smartiecards.settings.SettingsAdapter;
import com.smartiecards.settings.SettingsFragment;
import com.smartiecards.settings.WebFragment;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.ShowMsg;
import com.smartiecards.util.Utility;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAppCompactActivity {

    private static final String TAG = "MainActivity";

    Toolbar toolbar;
    public TextView textViewTitleBar;
    DrawerLayout drawerLayout;


    ArrayList<ItemLeftMenu> contacts = new ArrayList<ItemLeftMenu>();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LeftMenuAdapter mAdapter;


    RelativeLayout relativeLayoutProfile;

//    SavePref pref = new SavePref();


    ImageLoader imageLoader;
    DisplayImageOptions options;



    TextView textViewProfileName;
    ImageView imageViewProfilePic;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

//        pref.SavePref(MainActivity.this);


        relativeLayoutProfile = (RelativeLayout) findViewById(R.id.view_container);

        textViewProfileName = (TextView) findViewById(R.id.textView2_23);
        imageViewProfilePic = (ImageView) findViewById(R.id.profile_image);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textViewTitleBar = (TextView) findViewById(R.id.textView_title);
        textViewTitleBar.setText(getString(R.string.dashboard));
//        textViewTitleBar.setTypeface(EasyFonts.robotoLight(this));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

//        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                menuSetUp();
//            }
//        });




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

       // new ShowMsg().createSnackbarWithButton(MainActivity.this ,"Message");



        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new LeftMenuAdapter(MainActivity.this, contacts);
        mRecyclerView.setAdapter(mAdapter);


        try{
            imageLoader = ImageLoader.getInstance();

            imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.malecostume_64)
                    .showImageForEmptyUri(R.drawable.malecostume_64)
                    .showImageOnFail(R.drawable.malecostume_64)
                    .resetViewBeforeLoading(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
                    //.displayer(new CircleBitmapDisplayer(Color.parseColor("#ff0000"), 2))
//                    .displayer(new Utility.OvaledBitmapDisplayer())
                    .build();

            //options.imageOnLoadingScaleType




        }catch(Exception e){
            Log.d(TAG, "myError11: "+e.getMessage());
        }


//        contacts = new Utility().getItemLeftMenuWithoutLogin();
//
//        if(pref.getId().equalsIgnoreCase("")){
//            relativeLayoutProfile.setVisibility(View.GONE);
//            contacts = new Utility().getItemLeftMenuWithoutLogin();
//        }else{
//            relativeLayoutProfile.setVisibility(View.VISIBLE);
//            contacts = new Utility().getItemLeftMenuLogin();
//        }
//
//        mAdapter.updateData(contacts);




        relativeLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this , ProfileActivity.class);
               startActivityForResult(intent , SettingsAdapter.EDIT_PROFILE_REFRESH_REQUEST_CODE);
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            switchPosition(bundle.getInt("keyPosition"));
        }else{
            switchPosition(0);
        }


      //  calldeviceCheckApiMethod();


        updateProfile();

    }

    public void switchPosition(int i) {
        menuSetUp();

        Fragment fragment = null;
//not login
        if (pref.getId().equalsIgnoreCase("")){
            if(i == 0){
                fragment = new TopSubjectFragment();
                textViewTitleBar.setText("Top Subjects");
                //switchFragment(fragment);
            }else if(i == 1){
                fragment = new LoginFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
                //switchFragment2(fragment);
            }else if(i == 2){
//                fragment = new SubjectFragment();
                 fragment = new SubjectFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 3){
//                fragment = new OtherProductsFragment();
//                textViewTitleBar.setText(contacts.get(i).getName());
//            }else if(i == 4){
                fragment = new GamesFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 4){
                fragment = new ContactFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 5){
                fragment = new WebFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", WSContants.ABOUTUS);

               // bundle.putString("url","https://www.absolute-interpreting.co.uk/Portal/ClientsPortal/web_services/booking.php?client_id=89&InterpreterID=89");

                fragment.setArguments(bundle);
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 6){
                sharePost();
            }
        }else{
            if(i == 0){
                fragment = new TopSubjectFragment();
                textViewTitleBar.setText("Top Subjects");
            }else if(i == 1){
                fragment = new SubjectFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 2){
                fragment = new GamesFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 3){
                fragment = new ContactFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 4){
                fragment = new AccountFragment();
                textViewTitleBar.setText(contacts.get(i).getName());
            }else if(i == 5){
                sharePost();
            }else if(i == 6){
                showLogoutDialog();
            }
        }



        if(fragment != null) {
            switchFragment(fragment);
        }


//        calldeviceCheckApiMethod();

    }






    private void sharePost() {

        try {

//            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
//                    BitmapFactory.decodeResource(getResources(), R.drawable.logo_icon), null, null));

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
           // i.setType("image/*");
          //  i.putExtra(Intent.EXTRA_STREAM, imageUri);
            //i.putExtra(Intent.EXTRA_SUBJECT, "My application name");

            String sAux = "\n"+"Hey,\n\n Smartie Cards is a fun and simple app. You won't believe how much it helps with your exams! Click to install."+"\n\n";
//            sAux = sAux + getString(R.string.app_name)+"\n";
            sAux = sAux + "Android: " + "https://play.google.com/store/apps/details?id=com.smartiecards"+"\n\n";
            sAux = sAux + "iOS: " + "https://itunes.apple.com/us/app/smartie-cards/id1405595122?ls=1&mt=8"+"\n\n";
            sAux = sAux + "Thanks";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
            //e.toString();
        }

    }
    public void switchFragment(final Fragment home) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_relative2, home);
                // String backStateName = home.getClass().getName();
                // ft.addToBackStack(null);
                ft.commit();
            }
        }, 50);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }



    }

    public void switchFragment2(final Fragment home) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_relative2, home);
                String backStateName = home.getClass().getName();
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }, 50);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }



    private void showLogoutDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle(MainActivity.this.getString(R.string.app_name));
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                        pref.SavePref(MainActivity.this);
                        pref.clear();

                        onLogoutClick();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressLint("NewApi")
    public void onLogoutClick() {
//        LoginManager.getInstance().logOut();

        Intent i = new Intent(MainActivity.this, MainActivity.class);
        i.putExtra("keyPosition" , 1);
        startActivity(i);
        finishAffinity();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }


        menuSetUp();

        Fragment currentFrag =  getSupportFragmentManager().findFragmentById(R.id.main_relative2);
        String fragName = "NONE";

        Log.e(TAG, "currentFrag : "+currentFrag);

        if(currentFrag != null){
            fragName = currentFrag.getClass().getSimpleName();

//            if(fragName.equalsIgnoreCase("TopSubjectFragment"))
//            {
//                finish();
//            }else{
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction trans = manager.beginTransaction();
//                trans.remove(currentFrag);
//                trans.commit();
//                manager.popBackStack();
//            }



            if(fragName.equalsIgnoreCase("TopSubjectFragment"))
            {
                finish();
            }
            else
            {
                if(!fragName.equalsIgnoreCase("NONE"))
                {
                    //textViewTitleBar.setText(getString(R.string.dashboard));
                    switchPosition(0);
                }else{
                    finish();
                }
            }
        }else{
            finish();
        }

        Log.e(TAG, "fragName : "+fragName);
    }

    private void menuSetUp() {
        if(pref.getId().equalsIgnoreCase("")){
            relativeLayoutProfile.setVisibility(View.GONE);
            contacts = new Utility().getItemLeftMenuWithoutLogin();
        }else{
            relativeLayoutProfile.setVisibility(View.VISIBLE);
            contacts = new Utility().getItemLeftMenuLogin();
        }
        mAdapter.updateData(contacts);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        menuSetUp();

        if (requestCode == SettingsAdapter.EDIT_PROFILE_REFRESH_REQUEST_CODE) {
            updateProfile();
        }



        //  new ShowMsg().createDialog(MainActivity.this, requestCode+" : "+resultCode);


        if(requestCode == 64206){
            //  new ShowMsg().createDialog(MainActivity.this, requestCode+" : "+resultCode);
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_relative2);
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        if(requestCode == LoginFragment.RC_SIGN_IN){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_relative2);
            fragment.onActivityResult(requestCode, resultCode, data);
        }




        if(requestCode == TopSubjectFragment.COUPON_REQUEST_CODE){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_relative2);
            fragment.onActivityResult(requestCode, resultCode, data);
        }


        if(requestCode == SubjectFragment.COUPON_REQUEST_CODE){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_relative2);
            fragment.onActivityResult(requestCode, resultCode, data);
        }


        if(requestCode == GamesFragment.PAYPAL_REQUEST_CODE){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_relative2);
            fragment.onActivityResult(requestCode, resultCode, data);
        }



    }




    private void updateProfile() {
        boolean b = WSConnector.checkAvail(MainActivity.this);
        if(b == true){
            if(!pref.getId().equals("")){
                callProfileApiMethod();
//                new ProfileTask().execute(pref.getId());
            }
        }else{
            // new ShowMsg().createDialog(MainActivity.this , "No internet connection.");
        }
    }




    private void callProfileApiMethod() {

        call = apiInterface.profileinfo(pref.getId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseCode = "";
                try {
                    if(response.body() != null) {
                        responseCode = response.body().string();

                        JSONObject jsonObject = new JSONObject(responseCode);
                        //JSONObject response = jsonObject.getJSONObject("response");

                        String status = jsonObject.getString("status");
                        if(status.equals("1")){
                            JSONObject data = jsonObject.getJSONObject("data");
                            String id = data.getString("id");
                            String username = data.getString("username");

                            String profilepic = data.getString("profilepic");

                            // Log.e(TAG , "profilepic "+WSContants.BASE_MAIN__PROFILE_URL+profilepic);


                            textViewProfileName.setText(""+username.toString());


                            if(profilepic.equals("")){
                                String imageUri = "drawable://" + R.drawable.malecostume_128;
                                imageLoader.displayImage(imageUri, imageViewProfilePic, options);
                            }else{
                                imageLoader.displayImage(profilepic, imageViewProfilePic, options);
                            }

                        }else{

                        }

                    }else{
                       // new ShowMsg().createSnackbar(getActivity(), "Something went wrong!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // new ShowMsg().createSnackbar(getActivity(), ""+e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // progressDialog.dismiss();
//                progressBar.setVisibility(View.GONE);
//                new ShowMsg().createSnackbar(getActivity(), ""+t.getMessage());
            }
        });




    }





    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, WSContants.config);
        startService(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(MainActivity.this, PayPalService.class));
    }






//
//    private void calldeviceCheckApiMethod() {
//        call = apiInterface.deviceCheck(pref.getId() , ""+Utility.getLocalIpAddress());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String responseCode = "";
//                try {
//                    if(response.body() != null) {
//                        responseCode = response.body().string();
//                        Log.e(TAG , "responseCodeXX "+responseCode);
//
//                        JSONObject jsonObject = new JSONObject(responseCode);
//                        String status = jsonObject.getString("status");
//                        if(status.equals("1")){
//                             //pref.clear();
////                                Intent i = new Intent(BaseAppCompactActivity.this , MainActivity.class);
////                                i.putExtra("keyPosition" , 1);
////                                startActivity(i);
////                                finishAffinity();
////                                finish();
//
//                            //MainActivity
//
//                            menuSetUp();
//
//                            textViewTitleBar.setText("Login");
//                            switchFragment(new LoginFragment());
//
//
//
//
//                        }else{
//                        }
//                    }else{
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//            }
//        });
//
//
//
//
//    }
//



}
