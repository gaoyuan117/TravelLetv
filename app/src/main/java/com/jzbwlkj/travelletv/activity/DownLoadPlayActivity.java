package com.jzbwlkj.travelletv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.DownLoadBean;
import com.jzbwlkj.travelletv.db.HistoryVideo;
import com.jzbwlkj.travelletv.db.HistoryVideoDao;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class DownLoadPlayActivity extends BaseActivity {

    @BindView(R.id.jc_down_play)
    JCVideoPlayerStandard mVideoView;
    @BindView(R.id.tv_down_play_name)
    TextView mDownPlayName;
    @BindView(R.id.tv_down_play_des)
    TextView mDownPlayDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_play);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        DownLoadBean bean = (DownLoadBean) getIntent().getSerializableExtra("data");
        if(bean==null){
            return;
        }
        mVideoView.setUp(bean.getVideoPath(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, bean.getTitle());
        JCVideoPlayer.SAVE_PROGRESS = false;
        mVideoView.backButton.setVisibility(View.VISIBLE);
        mDownPlayName.setText(bean.getTitle());
        mDownPlayDes.setText(bean.getDes());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        mVideoView.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 查询本地数据库
     */
    private void queryData(String videoId) {
        HistoryVideoDao historyVideoDao = App.getDaoInstant().getHistoryVideoDao();
        List<HistoryVideo> list = historyVideoDao.queryBuilder().where(HistoryVideoDao.Properties.VideoId.eq(videoId)).list();
        if (list == null || list.size() == 0) {
            return;
        }
        mDownPlayName.setText(list.get(0).getTitle());
        mDownPlayDes.setText(list.get(0).getDes());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d("当前状态："+mVideoView.currentState);
        if (mVideoView.getCurrentPositionWhenPlaying() == 0) {
            JCVideoPlayer.releaseAllVideos();
        } else{//如果正在播放 加载，停止播放
            JCMediaManager.instance().mediaPlayer.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mVideoView.getCurrentPositionWhenPlaying()>0){
            JCMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
