package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.home.SearchBean;
import com.jzbwlkj.travelletv.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/3/9.
 */

public class SearchAdapter extends MyBaseAdapter {
    public SearchAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SearchBean.DataBean.ListBean bean = (SearchBean.DataBean.ListBean) mList.get(position);
        if (!TextUtils.isEmpty(bean.getImgpath())) {
            viewHolder.img.setVisibility(View.VISIBLE);
            Util.glide(bean.getImgpath(), viewHolder.img);
        } else {
            viewHolder.img.setVisibility(View.GONE);
        }
        viewHolder.title.setText(bean.getTitle());

        if (bean.getIs_pur() == 0) {
            viewHolder.isPur.setText("¥" + bean.getPrice());
        } else {
            viewHolder.isPur.setText("已购买");
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_search_video)
        ImageView img;
        @BindView(R.id.tv_search_title)
        TextView title;
        @BindView(R.id.tv_search_pur)
        TextView isPur;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
