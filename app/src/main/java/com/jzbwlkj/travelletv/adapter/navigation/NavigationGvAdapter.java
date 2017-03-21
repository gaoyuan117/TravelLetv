package com.jzbwlkj.travelletv.adapter.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.navigation.NavigationBean;
import com.jzbwlkj.travelletv.config.URL;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：admin on 2017/2/11 14:03
 */

public class NavigationGvAdapter extends MyBaseAdapter {
    public NavigationGvAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_gv_navigation, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        NavigationBean.DataBean bean = (NavigationBean.DataBean) mList.get(i);
        vh.mName.setText(((NavigationBean.DataBean) mList.get(i)).getName());
        Glide.with(context).load(URL.IMG + bean.getImgpath()).placeholder(R.mipmap.default_image).error(R.mipmap.default_image).into(vh.mImag);
        return view;

    }

    static class ViewHolder {
        @BindView(R.id.img_ng_gv_mark)
        ImageView mImag;
        @BindView(R.id.tv_ng_gv_name)
        TextView mName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
