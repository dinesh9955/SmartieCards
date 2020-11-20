package com.smartiecards;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Demo2 extends AppCompatActivity {
    private static final int TAKE_PHOTO_CODE = 23;
    private static final String TAG = "Demo2";
    Button button1, button2;

    private static final int IMAGE_CROP = 117;
    File file;
    Uri fileUri;
    final int RC_TAKE_PHOTO = 1;

    String photoFileName = "";

//    @Override
//    protected int getLayoutResource() {
//        return R.layout.demo2;
//    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.demo2);

        button1 = (Button) findViewById(R.id.button2) ;
        button2 = (Button) findViewById(R.id.button3) ;


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        registerForContextMenu(button1);
        registerForContextMenu(button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // startActivityForResult(i, fffffff);


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                capturarFoto();
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                file = new File(getExternalCacheDir(),
//                        String.valueOf(System.currentTimeMillis()) + ".jpg");
//                fileUri = Uri.fromFile(file);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                startActivityForResult(intent, TAKE_PHOTO_CODE);



                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PHOTO_CODE);

            }
        });



    }



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.setHeaderTitle("Choose a filter");
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Context Menu");
//        menu.add(0, v.getId(), 0, "Upload");
//        menu.add(0, v.getId(), 0, "Search");
//    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "AA");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            //Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void capturarFoto() {
        final String dir =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/Folder/";
        File newdir = new File(dir);
        newdir.mkdirs();
        String file = dir+DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+".jpg";


        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {}

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK && null != data) {
//            // Uri selectedImage = data.getData();
//
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//
//            Toast.makeText(Demo2.this,"Here "+ data.getData(), Toast.LENGTH_LONG).show();
//
//
//            String imagePath1 = getImagePathCamera(data);
//
//        Log.e(TAG, "imagePath1 "+imagePath1);
//
//        }
//    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");



            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            Toast.makeText(Demo2.this,"Here "+ getRealPathFromURI(tempUri),
                    Toast.LENGTH_LONG).show();

            try {
                Intent CropIntent = new Intent("com.android.camera.action.CROP");

                CropIntent.setDataAndType(tempUri, "image/*");
                CropIntent.putExtra("crop", true);
                CropIntent.putExtra("outputX", 200);
                CropIntent.putExtra("outputY", 200);
                CropIntent.putExtra("aspectX", 2);
                CropIntent.putExtra("aspectY", 2);
               // CropIntent.putExtra("scale", true);
                CropIntent.putExtra("return-data", true);

                startActivityForResult(CropIntent, IMAGE_CROP);


            } catch (ActivityNotFoundException ex){

            }

        }

        if (requestCode == IMAGE_CROP && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
//            Bitmap selectedBitmap = extras.getParcelable("data");

            Log.e(TAG , "pathpathpath "+requestCode);


//            Bundle extras = data.getExtras();
//            Parcelable xxd = data.getExtras().getParcelable("data");
//
            Bitmap selectedBitmap = extras.getParcelable("data");
            final Uri path = getImageUri(Demo2.this,selectedBitmap);

            String imagePath1 = getImagePath(Demo2.this , path);

            Log.e(TAG , "imagePath1QQQQQQq "+imagePath1);
        }

    }



    public static String getImagePath(Activity activity, Uri selectedImage) {
        // TODO Auto-generated method stub
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Log.d(TAG, "path is :: " + picturePath);
        return picturePath;
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }



    private String getImagePathCamera(Intent data) {
        // TODO Auto-generated method stub
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());

            String imagePath1 = destination.toString();

            fo.close();

            return imagePath1;

            //imageLoader.displayImage("file:///"+imagePath1, imageViewDriverProfile, options);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
