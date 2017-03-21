package com.jzbwlkj.travelletv.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by dn on 2017/2/10.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> mList;
    protected LayoutInflater mInflater;

    public MyBaseAdapter(Context context, List<T> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



}
