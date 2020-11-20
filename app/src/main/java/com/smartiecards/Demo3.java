package com.smartiecards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.smartiecards.flipview.AnimationFactory;
import com.smartiecards.home.FlashCardFlip;

public class Demo3 extends AppCompatActivity {


    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);



        setContentView(R.layout.demo33);

        button = (Button) findViewById(R.id.button5);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialogLast2();
//            }
//        });




        final ViewAnimator viewAnimator = (ViewAnimator)findViewById(R.id.viewFlipper);
        final ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
                //viewFlipper.showNext();
            }
        }, 2000);



    }





    private void openDialogLast() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Demo3.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("You have no Subject Topic.")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();

                      //  onleave(event);
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();

         // alert.setCanceledOnTouchOutside(true);
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "Finishaaa", Toast.LENGTH_SHORT).show();
            }
        });

     //   alert.setOn

        alert.show();



    }


    private void openDialogLast2() {
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Demo3.this);
//        builder.setTitle(getString(R.string.app_name));
//        builder.setMessage("You have no Subject Topic.")
//                .setCancelable(true)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                        finish();
//
//                        //  onleave(event);
//                    }
//                });
//        android.support.v7.app.AlertDialog alert = builder.create();
//
//        // alert.setCanceledOnTouchOutside(true);
//        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                Toast.makeText(getApplicationContext(), "Finishaaa", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //   alert.setOn
//
//        alert.show();




//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Demo3.this);
//        SmartDialogBuilder smartDialogBuilder = new SmartDialogBuilder(Demo3.this);
//
//        smartDialogBuilder.setTitle("Smart Dialog");
//        smartDialogBuilder.setSubTitle("This is the alert dialog to showing alert to user");
//
//        final CharSequence[] items = { "Redo", "Next Topic", "Subject Topic", "Top Subjects" };
//
//       // smartDialogBuilder.set
//
//        new SmartDialogBuilder(Demo3.this)
//                .setTitle("Smart Dialog")
//                .setSubTitle("This is the alert dialog to showing alert to user")
//                .setCancalable(false)
//                //.setTitleFont(titleFont) //set title font
//                //.setSubTitleFont(subTitleFont) //set sub title font
//                .setNegativeButtonHide(true) //hide cancel button
//                .setPositiveButton("OK", new SmartDialogClickListener() {
//                    @Override
//                    public void onClick(SmartDialog smartDialog) {
//                        Toast.makeText(Demo3.this,"Ok button Click",Toast.LENGTH_SHORT).show();
//                        smartDialog.dismiss();
//                    }
//                }).setNegativeButton("Cancel", new SmartDialogClickListener() {
//            @Override
//            public void onClick(SmartDialog smartDialog) {
//                Toast.makeText(Demo3.this,"Cancel button Click",Toast.LENGTH_SHORT).show();
//                smartDialog.dismiss();
//
//            }
//        }).build().show();

    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Rect dialogBounds = new Rect();
//        getWindow().getDecorView().getHitRect(dialogBounds);
//
//        if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
//            // Tapped outside so we finish the activity
//            this.finish();
//        }
//        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//            Toast.makeText(getApplicationContext(), "Finishaaa"+event.getAction(), Toast.LENGTH_SHORT).show();
//
//            return true;
//        }

        return super.dispatchTouchEvent(event);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent ev)
//    {
//        if(MotionEvent.ACTION_DOWN == ev.getAction())
//        {
//            Rect dialogBounds = new Rect();
//            getWindow().getDecorView().getHitRect(dialogBounds);
//            if (!dialogBounds.contains((int) ev.getX(), (int) ev.getY())) {
//                // You have clicked the grey area
//                //displayYourDialog();
//                //Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
//                return false; // stop activity closing
//            }
//        }
//
//        // Touch events inside are fine.
//        return super.onTouchEvent(ev);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //  Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }

}
