package com.jzbwlkj.travelletv.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dn on 2017/2/10.
 */

public abstract class BaseFragment extends Fragment {

    public Activity mActivity;
    public Map<String,String> maps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        maps = new HashMap<>();
        /**
         * 加载布局
         */
        View view = loadLayout(inflater);
        /**
         * 获取所有的主件
         */
        init(view);
        /**
         * 业务逻辑过程
         */
        initData();

        set();

        return view;
    }

    protected abstract View loadLayout(LayoutInflater inflater);

    protected abstract void init(View view);

    protected abstract void initData();

    protected abstract void set();

}
