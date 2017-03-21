package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.my.VideoBean;
import com.jzbwlkj.travelletv.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/3.
 */

public class OtherAdapter extends MyBaseAdapter {
    public OtherAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_recommend_gv, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        VideoBean.DataBean.ListBean bean = (VideoBean.DataBean.ListBean) mList.get(position);
        Util.glide(bean.getImgpath(),vh.mCover);
        vh.mTitle.setText(bean.getTitle());
        vh.mDesc.setText(bean.getDetails());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_item_recommend_cover)
        ImageView mCover;
        @BindView(R.id.tv_item_recommend_title)
        TextView mTitle;
        @BindView(R.id.tv_item_recommend_desc)
        TextView mDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
