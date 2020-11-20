package com.smartiecards.util;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartiecards.MainActivity;
import com.smartiecards.R;
import com.smartiecards.home.TopSubjectFragment;


public class ShowMsg {

	public Dialog createCustomProgressbarDialog(Activity mainActivity) {
		final Dialog dialog = new Dialog(mainActivity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.progress_bar_dialog);

		ImageView imageViewProgress = (ImageView) dialog.findViewById(R.id.imageView12234) ;

		@SuppressLint("ResourceType") AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mainActivity , R.anim.rotate_indefinitely);
		set.setTarget(imageViewProgress);
		set.start();
		imageViewProgress.setVisibility(View.VISIBLE);
		dialog.show();
		return dialog;
	}




	public void createSnackbar(Activity mainActivity, String message) {
		InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = mainActivity.getCurrentFocus();
		if (view == null) {
			view = new View(mainActivity);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

		Snackbar.make(mainActivity.getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
		//Snackbar.make(mainActivity.getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
	}




	public void createSnackbarWithButton(final Activity mainActivity, String message) {
		InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = mainActivity.getCurrentFocus();
		if (view == null) {
			view = new View(mainActivity);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

		Snackbar.make(mainActivity.getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG)
				.setAction("CLOSE", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
//						MainActivity activity = (MainActivity)mainActivity.getApplication();
//						activity.hello();
						//(mainActivity.TopSubjectFragment()).hello();
						//new TopSubjectFragment().hello();

						//(mainActivity.(MainActivity.hello());

					}
				})
				.setActionTextColor(mainActivity.getResources().getColor(android.R.color.holo_red_light ))
				.show();
	}



	public void createToast(final Activity splash, final String string) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager) splash.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = splash.getCurrentFocus();
		if (view == null) {
			view = new View(splash);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(splash,string, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	

	public void createDialog(final Activity splash, final String string) {
		// TODO Auto-generated method stub

		InputMethodManager imm = (InputMethodManager) splash.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = splash.getCurrentFocus();
		if (view == null) {
			view = new View(splash);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				 Builder dialog = new Builder(splash, AlertDialog.THEME_HOLO_LIGHT);
//				 dialog.setTitle("Brain At Work");
//				 dialog.setMessage(string);
//				 dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						dialog.dismiss();
//					}
//				});
//				 dialog.show();


				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(splash);
				builder.setTitle(splash.getString(R.string.app_name));
				builder.setMessage(""+string)
						.setCancelable(false)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
				android.support.v7.app.AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}





	public void createDialogFinishClass(final Activity splash, final String string) {
		// TODO Auto-generated method stub

		InputMethodManager imm = (InputMethodManager) splash.getSystemService(Activity.INPUT_METHOD_SERVICE);
		View view = splash.getCurrentFocus();
		if (view == null) {
			view = new View(splash);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(splash);
				builder.setTitle(splash.getString(R.string.app_name));
				builder.setMessage(""+string)
						.setCancelable(false)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								splash.finish();
							}
						});
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
				android.support.v7.app.AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}


	public void createDialogNew(final Activity splash, final String string) {
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(splash);
		builder.setTitle(splash.getString(R.string.app_name));
		builder.setMessage(""+string)
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
		android.support.v7.app.AlertDialog alert = builder.create();
		alert.show();
	}



	public void createDialogNewTitleANDDescription(final Activity splash, final String title, final String msg) {
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(splash);
		builder.setTitle(title);
		builder.setMessage(Html.fromHtml(msg))
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
//				.setNegativeButton("No", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
		android.support.v7.app.AlertDialog alert = builder.create();
		alert.show();
	}



//	public void createShortDialog(final Activity splash, final String string) {
//		// TODO Auto-generated method stub
//		splash.runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				final Builder dialog = new Builder(splash, AlertDialog.THEME_HOLO_LIGHT);
//				final Dialog dialog = new Dialog(splash);
//				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//				dialog.setContentView(R.layout.custom_dialog_design);
//				TextView txt = (TextView) dialog.findViewById(R.id.textView3);
//				txt.setText(string);
//
//
//
//// 	dialog.setMessage(string);
//
////				dialog.setMessage(string);
////				dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
////
////					@Override
////					public void onClick(DialogInterface dialog, int which) {
////						// TODO Auto-generated method stub
////						dialog.dismiss();
////					}
////				});
//
//				dialog.show();
//
//			}
//		});
//
//	}





	public void createDialogCancelable(final Activity splash, final String string) {
		// TODO Auto-generated method stub
		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Builder dialog = new Builder(splash, AlertDialog.THEME_HOLO_LIGHT);
				 dialog.setTitle(splash.getString(R.string.app_name));
				 dialog.setMessage(string);
				 dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				
				 dialog.setCancelable(false);
				 dialog.show();
				
			}
		});
		
	}
	
	
	

	public void createDialogForgotPassword(final Activity splash, final String string) {
		// TODO Auto-generated method stub
		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Builder dialog = new Builder(splash, AlertDialog.THEME_HOLO_LIGHT);
				 dialog.setTitle(splash.getString(R.string.app_name));
				 dialog.setMessage(string);
				 dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						splash.finish();
					}
				});
				 dialog.show();
			}
		});
		
	}












	public void createDialogNewTitleColor(final Activity splash, final String string) {
		// TODO Auto-generated method stub
		splash.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(splash, R.style.MaterialNavyTheme);
				builder.setTitle(splash.getString(R.string.app_name));
				builder.setMessage(string);
//				final EditText input = new EditText(splash);
//				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//						LinearLayout.LayoutParams.MATCH_PARENT,
//						LinearLayout.LayoutParams.MATCH_PARENT);
//				input.setLayoutParams(lp);
//				builder.setView(input);
				builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
//						splash.finish();
					}
				});
				builder.show();
			}
		});
	}



//	public void showLoginCustomDialog(Dialog dialog) {
//		// TODO Auto-generated method stub
//		
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		dialog.setContentView(R.layout.custom_dialog_progressbar);
//		dialog.setTitle("Pakly Partner!");
//		
//		
//		//dialog.show();
//		
//		
//	}
	
	
//
//	public void showLoginCustomMessageDialog(final Activity splash, String string) {
//		// TODO Auto-generated method stub
//		final Dialog dialog = new Dialog(splash, R.style.translucentDialog);
//
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////		splash.getWindow().setBackgroundDrawable(
////	            new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		dialog.setContentView(R.layout.custom_message_dialog);
////		dialog.setTitle("Forgot Password!");
//
//		TextView textView = (TextView)dialog.findViewById(R.id.textView1654656);
////		textView.setText(string);
//		Button button = (Button)dialog.findViewById(R.id.button111);
//
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//
//
//		dialog.show();
//
//
//	}
//
//
//
	
	
//
//
//	public void showLoginCustomMessageDialog2(final Activity splash, String string) {
//		// TODO Auto-generated method stub
//		final Dialog dialog = new Dialog(splash, R.style.translucentDialog);
//
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////		splash.getWindow().setBackgroundDrawable(
////	            new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		dialog.setContentView(R.layout.custom_message_dialog_2);
////		dialog.setTitle("Forgot Password!");
//
//		TextView textView = (TextView)dialog.findViewById(R.id.textView1654656);
//		textView.setText(string);
//		Button buttonOk = (Button)dialog.findViewById(R.id.button111);
//		Button buttonCancel = (Button)dialog.findViewById(R.id.button222);
//		buttonOk.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//
//		buttonCancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//
//
//		dialog.show();
//
//
//	}
//
//
//
	
	
	public void turnGPSOn(Activity activity){
	    String provider = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(!provider.contains("gps")){ //if gps is disabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3")); 
	        activity.sendBroadcast(poke);
	    }
	}

	public void turnGPSOff(Activity activity){
	    String provider = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

	    if(provider.contains("gps")){ //if gps is enabled
	        final Intent poke = new Intent();
	        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
	        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
	        poke.setData(Uri.parse("3")); 
	        activity.sendBroadcast(poke);
	    }
	}


	


	public ProgressDialog createProgressDialog(Activity loginScreen) {
		// TODO Auto-generated method stub
		final ProgressDialog dialog = new ProgressDialog(loginScreen, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
//		dialog.setProgressStyle(dialog.THEME_HOLO_DARK);
		dialog.setMessage("Loading...");
//		dialog.show();
		
		return dialog;
	}


	
	public ProgressDialog createProgressDialogWithMsg(Activity loginScreen,String msg) {
		// TODO Auto-generated method stub
		final ProgressDialog dialog = new ProgressDialog(loginScreen, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
//		dialog.setProgressStyle(dialog.THEME_HOLO_DARK);
		dialog.setMessage(""+msg);
		dialog.show();
		
		return dialog;
	}





//	public Dialog createCustomProgressbarDialog(Activity mainActivity) {
//		final Dialog dialog = new Dialog(mainActivity);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		dialog.setContentView(R.layout.progress_bar_dialog);
//
//		ImageView imageView = (ImageView) dialog.findViewById(R.id.progressBar2);
//		GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
//		Glide.with(mainActivity).load(R.drawable.ajax_loader).into(imageViewTarget);
//
////		ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar2);
////		progressBar.setScaleX(1f);
////		progressBar.setScaleY(1f);
//		//progressBar.strokeWidth(8f);
//
//
//		dialog.show();
//		return dialog;
//	}
	
}
