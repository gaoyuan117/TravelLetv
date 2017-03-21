package com.jzbwlkj.travelletv.adapter.my;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.my.BuyVideoBean;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/16.
 */

public class BuyAdapter extends MyBaseAdapter {

    public BuyAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lv_history, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mImgLvHistoryBuy.setVisibility(View.VISIBLE);
        BuyVideoBean.DataBean bean = (BuyVideoBean.DataBean) mList.get(position);
        Glide.with(context).load(URL.BASE_URL + bean.getImgpath()).error(R.mipmap.shipin).placeholder(R.mipmap.shipin).into(vh.img);
        vh.mBuyTitle.setText(bean.getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_lv_history_buy)
        ImageView mImgLvHistoryBuy;
        @BindView(R.id.tv_lv_history_title)
        TextView mBuyTitle;
        @BindView(R.id.img_lv_history_video)
        ImageView img;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
