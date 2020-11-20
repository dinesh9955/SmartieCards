package com.smartiecards;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartiecards.util.EasyFontsCustom;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 10/5/2017.
 */

public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.ViewHolder> {

    private static final String TAG = "HLVAdapter";
    ArrayList<ItemLeftMenu> alName = new ArrayList<ItemLeftMenu>();
    static Activity context;

    int backPos = 0;

    public LeftMenuAdapter(Activity context11, ArrayList<ItemLeftMenu> alName) {
        super();
        this.context = context11;
         this.alName = alName;
        // FacebookSdk.sdkInitialize(context);
    }

    @Override
    public LeftMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.left_menu_item, viewGroup, false);
        LeftMenuAdapter.ViewHolder viewHolder = new LeftMenuAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LeftMenuAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.tvSpecies.setText(alName.get(i).getName());
        viewHolder.imgThumbnail.setImageResource(alName.get(i).getIcon());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    final MainActivity mainActivity = (MainActivity) context.switchPosition(i);
                ((MainActivity) context).switchPosition(i);
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
        public TextView tvSpecies;
        public RelativeLayout layout;
        private ItemClickListener clickListener;
        View view = null;
        public ViewHolder(View itemView) {
            super(itemView);
            view11 = itemView;
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imageView25345345);
            tvSpecies = (TextView) itemView.findViewById(R.id.textView253456456);
            tvSpecies.setTypeface(EasyFontsCustom.avenirnext_TLPro_Medium(context));
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


    public void updateData(ArrayList<ItemLeftMenu> arrayList2) {
        // TODO Auto-generated method stub
        alName.clear();
        alName.addAll(arrayList2);
        notifyDataSetChanged();
    }



}

