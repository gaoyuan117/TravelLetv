package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.my.HistoryAdapter;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.db.HistoryVideo;
import com.jzbwlkj.travelletv.db.HistoryVideoDao;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HistoryActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.lv_history_video)
    ListView mListView;
    @BindView(R.id.activity_history)
    LinearLayout activityHistory;
    @BindView(R.id.bt_history_clear)
    Button clear;

    private HistoryAdapter mAdapter;
    private List<HistoryVideo> mList;
    private HistoryVideoDao historyVideoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getResources().getString(R.string.my_history));
        mList = new ArrayList<>();
        mAdapter = new HistoryAdapter(this, mList);
        historyVideoDao = App.getDaoInstant().getHistoryVideoDao();
    }

    @Override
    protected void initData() {
        getHistoryVideo();
    }

    @Override
    protected void setData() {
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        clear.setOnClickListener(this);
    }


    /**
     * 获取历史播放记录
     */

    private void getHistoryVideo()
    {
        Observable.from(this.historyVideoDao.queryBuilder().list())
                .filter(new Func1<HistoryVideo, Boolean>() {
                    @Override
                    public Boolean call(HistoryVideo historyVideo) {
                        return  historyVideo.getUser().equals(App.user);
                    }
                })
                .compose(RxUtils.<HistoryVideo>io_main())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Collections.reverse(HistoryActivity.this.mList);
                        if (HistoryActivity.this.mList.size() == 0) {
                            HistoryActivity.this.clear.setVisibility(View.GONE);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .subscribe(new Action1<HistoryVideo>() {
                    @Override
                    public void call(HistoryVideo historyVideo) {
                        HistoryActivity.this.mList.add(historyVideo);
                    }
                });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("id", mList.get(position).getVideoId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        historyVideoDao.deleteAll();
        mList.clear();
        mAdapter.notifyDataSetChanged();
        clear.setVisibility(View.GONE);
    }
}
