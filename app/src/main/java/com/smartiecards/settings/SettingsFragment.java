package com.smartiecards.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartiecards.R;
import com.smartiecards.storage.SavePref;
import com.smartiecards.util.Utility;

import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/25/2018.
 */

public class SettingsFragment extends Fragment {


    ArrayList<ItemSettings> contacts = new ArrayList<ItemSettings>();

    SavePref pref = new SavePref();

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SettingsAdapter mAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new SettingsAdapter(getActivity(), contacts);
        mRecyclerView.setAdapter(mAdapter);

        contacts = new Utility().getItemSettings();
        mAdapter.updateData(contacts);




//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // swipeRefreshLayout.setRefreshing(true);
////                callRefreshMethod();
////                mAdapter.updateData(contacts);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });



        return view;
    }
}
