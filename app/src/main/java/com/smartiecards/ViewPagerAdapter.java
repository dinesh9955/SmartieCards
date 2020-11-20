package com.smartiecards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    public String mGeneralString;


    private static final String TAG = "ViewPagerAdapter";
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
//        Fragment fragment = new FirstFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("url",""+);
//        fragment.setArguments(bundle);
//        return fragment;
        return fragmentList.get(position);
    }




    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void addFragment(Fragment fragment, String name) {
        fragmentList.add(fragment);
        fragmentTitles.add(name);
    }



    //received from ManagerFragment
    public void update(String string) {
        mGeneralString = string;
        //updated
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        if (object instanceof UpdateableFragmentListener) {
            //sent to FirstFragment and SecondFragment
            ((UpdateableFragmentListener) object).update(mGeneralString);
        }
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout) object);
    }



//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        FragmentManager manager = ((Fragment)object).getFragmentManager();
//        FragmentTransaction trans = manager.beginTransaction();
//        trans.remove((Fragment)object);
//        trans.commit();
//    }



//    public synchronized void refreshEvents(ArrayList<ItemGiftCategory> events) {
//        this.itemGiftCategories.clear();
//        this.itemGiftCategories.addAll(events);
//        notifyDataSetChanged();
//    }



//    public void destroyItem(ViewGroup container, int position, Object object) {
//
//        FragmentManager manager = ((Fragment) object).getFragmentManager();
//        FragmentTransaction trans = manager.beginTransaction();
//        trans.remove((Fragment) object);
//        trans.commit();
//
//        super.destroyItem(container, position, object);
//    }


}
