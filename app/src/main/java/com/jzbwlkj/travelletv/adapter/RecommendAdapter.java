package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.home.RecommendBean;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dn on 2017/2/10.
 */

public class RecommendAdapter extends MyBaseAdapter {

    public RecommendAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_recommend_gv, viewGroup, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        RecommendBean.DataBean bean = (RecommendBean.DataBean) mList.get(i);

        vh.mTitle.setText(bean.getTitle());
        vh.mDesc.setText(bean.getDetails());
        Util.glide(bean.getImgpath(), vh.mCover);
        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_item_recommend_title)
        TextView mTitle;
        @BindView(R.id.tv_item_recommend_desc)
        TextView mDesc;
        @BindView(R.id.img_item_recommend_cover)
        ImageView mCover;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
