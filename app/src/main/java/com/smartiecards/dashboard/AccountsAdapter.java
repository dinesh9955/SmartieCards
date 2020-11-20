package com.smartiecards.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartiecards.ItemClickListener;
import com.smartiecards.R;
import com.smartiecards.account.EditProfileActivity;
import com.smartiecards.network.WSContants;
import com.smartiecards.settings.ChangePassword;
import com.smartiecards.settings.ItemSettings;
import com.smartiecards.settings.SettingsAdapter;
import com.smartiecards.settings.WebActivity;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/30/2018.
 */

public class AccountsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private static final String TAG = "AccountsAdapter";
    public static final int EDIT_PROFILE_REFRESH_REQUEST_CODE = 543;

    ArrayList<ItemSettings> alName = new ArrayList<ItemSettings>();
    Activity context;

    int backPos = 0;

    public AccountsAdapter(Activity context11, ArrayList<ItemSettings> alName) {
        super();
        this.context = context11;
        this.alName = alName;
        // FacebookSdk.sdkInitialize(context);
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.settings_item, viewGroup, false);
        SettingsAdapter.ViewHolder viewHolder = new SettingsAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final SettingsAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.buttonText.setText(alName.get(i).getName());
        viewHolder.buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i == 0){
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    context.startActivityForResult(intent, EDIT_PROFILE_REFRESH_REQUEST_CODE);
                }else if(i == 1){
                    Intent intent = new Intent(context, ChangePassword.class);
                    context.startActivity(intent);
                }else if(i == 2){
                    Intent intent = new Intent(context, MySubject.class);
                    context.startActivityForResult(intent, EDIT_PROFILE_REFRESH_REQUEST_CODE);
                }else if(i == 3){
                    Intent intent = new Intent(context, PaymentHistory.class);
                    context.startActivity(intent);
                }else if(i == 4){
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("key", alName.get(i).getName());
                    intent.putExtra("url", WSContants.ABOUTUS);
                    context.startActivity(intent);
                }else if(i == 5){
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("key", alName.get(i).getName());
                    intent.putExtra("url", WSContants.PRIVACY);
                    context.startActivity(intent);
                }else if(i == 6){
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("key", alName.get(i).getName());
                    intent.putExtra("url", WSContants.TERMS);
                    context.startActivity(intent);
                }
            }
        });
    }







    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        View view11 = null;
        public ImageView imgThumbnail;
        public Button buttonText;
        public RelativeLayout layout;
        private ItemClickListener clickListener;
        View view = null;
        public ViewHolder(View itemView) {
            super(itemView);
            view11 = itemView;
//            imgThumbnail = (ImageView) itemView.findViewById(R.id.imageView25345345);
            buttonText = (Button) itemView.findViewById(R.id.button154235);
            layout = (RelativeLayout) itemView.findViewById(R.id.top_layout);

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




    public synchronized void refreshPos(int events) {
        backPos = events;
        notifyDataSetChanged();
    }


    public void updateData(ArrayList<ItemSettings> arrayList2) {
        // TODO Auto-generated method stub
        alName.clear();
        alName.addAll(arrayList2);
        notifyDataSetChanged();
    }



}

