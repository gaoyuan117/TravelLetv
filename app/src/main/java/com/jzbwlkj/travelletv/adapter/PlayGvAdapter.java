package com.jzbwlkj.travelletv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.PlayBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gaoyuan on 2017/3/7.
 */

public class PlayGvAdapter extends MyBaseAdapter {
    private int p;

    public PlayGvAdapter(Context context, List mList) {
        super(context, mList);
    }

    public void setPlay(int position){
        p = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_play_gv, parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (p == position) {
            vh.mJi.setVisibility(View.GONE);
            vh.img.setVisibility(View.VISIBLE);
        } else {
            vh.mJi.setVisibility(View.VISIBLE);
            vh.img.setVisibility(View.GONE);
        }
        PlayBean.DataBean.JishuBean jishuBean = (PlayBean.DataBean.JishuBean) mList.get(position);
        vh.mJi.setText(jishuBean.getJishu());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_play_gv_ji)
        TextView mJi;
        @BindView(R.id.img_play_gv)
        ImageView img;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
