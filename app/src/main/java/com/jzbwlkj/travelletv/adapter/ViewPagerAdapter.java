package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dn on 2017/2/10.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> mList;
    private List<String> mTitles;

    public ViewPagerAdapter(FragmentManager fm, Context context, List<Fragment> mList, List<String> mTitles) {
        super(fm);
        this.context = context;
        this.mList = mList;
        this.mTitles = mTitles;
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
