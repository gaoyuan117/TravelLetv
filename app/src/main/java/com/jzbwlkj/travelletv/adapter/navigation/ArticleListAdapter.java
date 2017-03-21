package com.jzbwlkj.travelletv.adapter.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.MyBaseAdapter;
import com.jzbwlkj.travelletv.bean.navigation.ArticleListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dn on 2017/2/25.
 */

public class ArticleListAdapter extends MyBaseAdapter {
    public ArticleListAdapter(Context context, List mList) {
        super(context, mList);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_article_list, null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        ArticleListBean.DataBean bean = (ArticleListBean.DataBean) mList.get(i);
        vh.mListTitle.setText(bean.getTitle());
        //判断是否购买
        if (bean.getIs_pur().equals("0")) {
            vh.mListPrice.setText("￥ " + bean.getPrice());
        } else {
            vh.mListPrice.setText("已购买");
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_article_list_title)
        TextView mListTitle;
        @BindView(R.id.tv_article_list_price)
        TextView mListPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
