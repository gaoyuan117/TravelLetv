package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.adapter.my.HistoryAdapter;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.DownLoadBean;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.db.HistoryVideo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dn on 2017/3/2.
 */

public class DownLoadAdapter extends MyBaseAdapter {
    public DownLoadAdapter(Context context, List mList) {
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

        DownLoadBean historyVideo = (DownLoadBean) mList.get(position);
        vh.mTitle.setText(historyVideo.getTitle());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_lv_history_title)
        TextView mTitle;
        @BindView(R.id.img_lv_history_video)
        ImageView videoImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
