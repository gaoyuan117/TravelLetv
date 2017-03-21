package com.jzbwlkj.travelletv.adapter.my;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.my.CollectBean;
import com.jzbwlkj.travelletv.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dn on 2017/2/14.
 */

public class CollectAdapter extends MyBaseAdapter{

    public CollectAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_gv_collect, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        //判断是否隐藏下面的横线
        if (mList.size() % 2 != 0) {
            if (i == mList.size() - 1) {
                vh.view.setVisibility(View.GONE);
            }
        }
        CollectBean.DataBean bean = (CollectBean.DataBean) mList.get(i);
        vh.mTitle.setText(bean.getVideo_name());
        Util.glide(bean.getVideo_img(),vh.mImg);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.img_gv_collect)
        ImageView mImg;
        @BindView(R.id.tv_gv_collect_title)
        TextView mTitle;
        @BindView(R.id.ll_gv_collect)
        LinearLayout mLayout;
        @BindView(R.id.view)
        View view;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
