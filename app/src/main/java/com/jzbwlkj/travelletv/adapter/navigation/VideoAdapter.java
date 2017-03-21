package com.jzbwlkj.travelletv.adapter.navigation;

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
 * Created by dn on 2017/2/26.
 */

public class VideoAdapter extends MyBaseAdapter {

    public VideoAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_video_gv, viewGroup, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }
        VideoBean.DataBean.ListBean bean = (VideoBean.DataBean.ListBean) mList.get(i);
        vh.mTitle.setText(bean.getTitle());
        Util.glide(bean.getImgpath(),vh.mCover);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.img_video_gv_cover)
        ImageView mCover;
        @BindView(R.id.tv_video_gv_title)
        TextView mTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
