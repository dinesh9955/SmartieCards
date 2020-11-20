package com.smartiecards.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.Log;


import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.smartiecards.ItemLeftMenu;
import com.smartiecards.R;
import com.smartiecards.home.ItemSubject;
import com.smartiecards.settings.ItemSettings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AnaadIT on 3/9/2017.
 */

public class Utility {
    public static final int MY_PERMISSIONS_REQUEST = 123;
    public static final boolean LOGIN = false;
    private static final String TAG = "Utility";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.GET_ACCOUNTS}, MY_PERMISSIONS_REQUEST);
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.GET_ACCOUNTS}, MY_PERMISSIONS_REQUEST);
                    }
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean checkAndRequestPermissions(Activity context, int request) {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int writeExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readExternalStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int coarseLocartion = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocartion = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int callPhone = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        int readContacts = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (coarseLocartion != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (fineLocartion != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (callPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (readContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), request);
            return false;
        }
        return true;
    }


    public static boolean checkPermissionsCheck(Activity splashScreen) {
        if (ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(splashScreen, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ) {
            return true;
        }
        return false;
    }


    public static boolean checkAdditionPermissionsCheck(final Activity splashScreen, String permissions[], int[] grantResults, final int REQUEST_ID_MULTIPLE_PERMISSIONS) {

        Map<String, Integer> perms = new HashMap<>();

        perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);

        if (grantResults.length > 0) {
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for both permissions
            if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                    perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    ) {
                //   Log.d(TAG, "Please allow access to all asked permissions. Due to the nature of the friendlywagon app, access to these areas on your mobile devices are necessary  for the app to function properly.");
                // process the normal flow
                //else any one or both the permissions are not granted
//                Toast.makeText(splashScreen, "CCCCCCCCCc", Toast.LENGTH_LONG)
//                        .show();
                return true;
            } else {
                //  Log.d(TAG, "Please allow access to all asked permissions. Due to the nature of the friendlywagon app, access to these areas on your mobile devices are necessary  for the app to function properly.");
                if (ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.CAMERA) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.CALL_PHONE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(splashScreen, Manifest.permission.READ_CONTACTS)
                        ) {
//                    showDialogOK(splashScreen, "Please allow access to all asked permissions, access to your mobile devices are necessary for the app to function properly.",
                    showDialogOK(splashScreen, "Please allow access to all asked permissions.",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermissions(splashScreen, REQUEST_ID_MULTIPLE_PERMISSIONS);
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            });
                } else {
                    return true;
//                    Toast.makeText(splashScreen, "Go to settings and enable permissions", Toast.LENGTH_LONG)
//                            .show();
                }
            }
        } else {

        }


        return false;
    }






    private static void showDialogOK(Activity splashScreen, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(splashScreen)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }









    public ArrayList<ItemLeftMenu> getItemLeftMenuWithoutLogin() {
        ArrayList<ItemLeftMenu> arrayList = new ArrayList<ItemLeftMenu>();

        ItemLeftMenu vehicle1 = new ItemLeftMenu();
        vehicle1.setName("Home");
        vehicle1.setIcon(R.drawable.home);
        arrayList.add(vehicle1);

        ItemLeftMenu vehicle11 = new ItemLeftMenu();
        vehicle11.setName("Login");
        vehicle11.setIcon(R.drawable.login);
        arrayList.add(vehicle11);

        ItemLeftMenu vehicle2 = new ItemLeftMenu();
        vehicle2.setName("Subjects");
        vehicle2.setIcon(R.drawable.subject);
        arrayList.add(vehicle2);



        ItemLeftMenu vehicle221 = new ItemLeftMenu();
        vehicle221.setName("Games");
        vehicle221.setIcon(R.drawable.game);
        arrayList.add(vehicle221);



        ItemLeftMenu vehicle22 = new ItemLeftMenu();
        vehicle22.setName("Contact Us");
        vehicle22.setIcon(R.drawable.call);
        arrayList.add(vehicle22);


        ItemLeftMenu vehicle3 = new ItemLeftMenu();
        vehicle3.setName("About");
        vehicle3.setIcon(R.drawable.about);
        arrayList.add(vehicle3);



        ItemLeftMenu vehicle222 = new ItemLeftMenu();
        vehicle222.setName("Share");
        vehicle222.setIcon(R.drawable.share);
        arrayList.add(vehicle222);


        return arrayList;
    }







    public ArrayList<ItemLeftMenu> getItemLeftMenuLogin() {
        ArrayList<ItemLeftMenu> arrayList = new ArrayList<ItemLeftMenu>();

        ItemLeftMenu vehicle1 = new ItemLeftMenu();
        vehicle1.setName("Home");
        vehicle1.setIcon(R.drawable.home);
        arrayList.add(vehicle1);


        ItemLeftMenu vehicle2 = new ItemLeftMenu();
        vehicle2.setName("Subjects");
        vehicle2.setIcon(R.drawable.subject);
        arrayList.add(vehicle2);


        ItemLeftMenu vehicle221 = new ItemLeftMenu();
        vehicle221.setName("Games");
        vehicle221.setIcon(R.drawable.game);
        arrayList.add(vehicle221);



        ItemLeftMenu vehicle22 = new ItemLeftMenu();
        vehicle22.setName("Contact Us");
        vehicle22.setIcon(R.drawable.call);
        arrayList.add(vehicle22);



        ItemLeftMenu vehicle112 = new ItemLeftMenu();
        vehicle112.setName("Accounts");
        vehicle112.setIcon(R.drawable.user1);
        arrayList.add(vehicle112);



        ItemLeftMenu vehicle222 = new ItemLeftMenu();
        vehicle222.setName("Share");
        vehicle222.setIcon(R.drawable.share);
        arrayList.add(vehicle222);


        ItemLeftMenu vehicle3 = new ItemLeftMenu();
        vehicle3.setName("Logout");
        vehicle3.setIcon(R.drawable.logout);
        arrayList.add(vehicle3);


        return arrayList;
    }









    public static String isEmptyNull(String address) {
        if(address != null && !address.isEmpty() && !address.equalsIgnoreCase("null")) {
            return address;
        }else{
            return "";
        }
    }









    public ArrayList<ItemSettings> getItemSettings() {
        ArrayList<ItemSettings> arrayList = new ArrayList<ItemSettings>();

        ItemSettings vehicle1 = new ItemSettings();
        vehicle1.setName("Edit Profile");
        arrayList.add(vehicle1);

        ItemSettings vehicle222 = new ItemSettings();
        vehicle222.setName("Change Password");
        arrayList.add(vehicle222);

        ItemSettings vehicle2 = new ItemSettings();
        vehicle2.setName("About Us");
        arrayList.add(vehicle2);

        ItemSettings vehicle3 = new ItemSettings();
        vehicle3.setName("Privacy Policy");
        arrayList.add(vehicle3);

        ItemSettings vehicle4 = new ItemSettings();
        vehicle4.setName("Terms & Conditions");
        arrayList.add(vehicle4);

        return arrayList;
    }




    public ArrayList<ItemSettings> getItemAccounts() {
        ArrayList<ItemSettings> arrayList = new ArrayList<ItemSettings>();


        ItemSettings vehicle1 = new ItemSettings();
        vehicle1.setName("Edit Profile");
        arrayList.add(vehicle1);

        ItemSettings vehicle222 = new ItemSettings();
        vehicle222.setName("Change Password");
        arrayList.add(vehicle222);


        ItemSettings vehicle11 = new ItemSettings();
        vehicle11.setName("My Subjects");
        arrayList.add(vehicle11);

        ItemSettings vehicle23 = new ItemSettings();
        vehicle23.setName("Payment History");
        arrayList.add(vehicle23);


        ItemSettings vehicle2 = new ItemSettings();
        vehicle2.setName("About Us");
        arrayList.add(vehicle2);

        ItemSettings vehicle3 = new ItemSettings();
        vehicle3.setName("Privacy Policy");
        arrayList.add(vehicle3);

        ItemSettings vehicle4 = new ItemSettings();
        vehicle4.setName("Terms & Conditions");
        arrayList.add(vehicle4);



//        ItemSettings vehicle2 = new ItemSettings();
//        vehicle2.setName("Games");
//        arrayList.add(vehicle2);

//        ItemSettings vehicle3 = new ItemSettings();
//        vehicle3.setName("Games Subjects");
//        arrayList.add(vehicle3);

        return arrayList;
    }

    public static ArrayList<ItemSubject> getGameItemStatic() {
        ArrayList<ItemSubject> arrayList = new ArrayList<ItemSubject>();

        ItemSubject subject1 = new ItemSubject();
        subject1.setSubjectname("Shuffle");
        subject1.setType("shuffle");
        subject1.setGameDescription("Think you’ve mastered the entire topic? Shuffle up the flashcards and see if you’ve got the wit to answer random questions with flashcards coming at you in no particular order…let’s shuffle them up!");
        subject1.setImage(R.drawable.shuffle_1);
        arrayList.add(subject1);


        ItemSubject subject2 = new ItemSubject();
        subject2.setSubjectname("Matches");
        subject2.setType("match");
        subject2.setGameDescription("Flashcards everywhere. Questions and answers. Match the answer flashcards to their corresponding question flashcards and watch the correct pair disappear. Otherwise, keep trying…");
        subject2.setImage(R.drawable.matching_1);
        arrayList.add(subject2);


        ItemSubject subject3 = new ItemSubject();
        subject3.setSubjectname("Timed");
        subject3.setType("timed");
        subject3.setGameDescription("What’s the correct answer? Is it option A or option B? You’ve not got much time to think …decide now…the clock is ticking…time’s almost up… Time’s up!  Let’s find out if you got it right…");
        subject3.setImage(R.drawable.time_1);
        arrayList.add(subject3);

        return arrayList;
    }

    public static boolean isColor(String colorK) {
        if(colorK.contains("\u00a0")){
            Pattern colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
            Matcher m = colorPattern.matcher(colorK.replace("\u00a0", ""));
            return m.matches();
        }else{
            Pattern colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
            Matcher m = colorPattern.matcher(colorK);
            return m.matches();
        }
    }

    public static int replaceColor(String colorK) {
        if(colorK.contains("\u00a0")){
            return Color.parseColor(colorK.replace("\u00a0", ""));
        }else{
            return Color.parseColor(colorK);
        }
    }


    public static String replaceColorString(String colorK) {
        if(colorK.contains("\u00a0")){
            return String.valueOf(Color.parseColor(colorK.replace("\u00a0", "")));
        }else{
            return String.valueOf(Color.parseColor(colorK));
        }
    }

    public static class OvaledBitmapDisplayer implements BitmapDisplayer
    {
        @Override
        public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom)
        {
            if (!(imageAware instanceof ImageViewAware))
            {
                throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
            }

            imageAware.setImageBitmap(BitmapTool.toOvalBitmap(bitmap));
        }



    }


    public static class BitmapTool {
        public static Bitmap toOvalBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                Bitmap output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                RectF rectF = new RectF(rect);

                canvas.drawOval(rectF, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect, rectF, paint);

                return output;
            }

            return null;
        }
    }










    public static String convertValueStringToDouble(String valueFirst) {
        try{
            double time = Double.parseDouble(valueFirst);
            DecimalFormat df = new DecimalFormat("0.00");
            //DecimalFormat df = new DecimalFormat("##.##");
            return ""+df.format(time);
        }catch (Exception e){

        }
        return "0.0";
    }










    public static boolean isDTTrue() {
        boolean b = false;
        Date date1 = getDate1();
        Date date2 = getDate2();
        if (date1.after(date2)) {
            int is = Integer.parseInt("df");
            b = true;
        }
        if (date1.before(date2)) {
            b = false;
        }
        return b;
    }
    private static Date getDate1() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy", Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date date = cal.getTime();
        String date1 = dateFormatter.format(date);
        Date inActiveDate = null;
        try {
            inActiveDate = dateFormatter.parse(date1);
            return inActiveDate;
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return inActiveDate;
    }
    private static Date getDate2() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy", Locale.US);
        String date1 = "01/09/19";
        Date inActiveDate = null;
        try {
            inActiveDate = dateFormatter.parse(date1);
            return inActiveDate;
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return inActiveDate;
    }








    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }


}
