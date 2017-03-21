package com.jzbwlkj.travelletv.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.adapter.PlayGvAdapter;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.PlayBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.db.DownDao;
import com.jzbwlkj.travelletv.db.DownDaoDao;
import com.jzbwlkj.travelletv.db.HistoryVideo;
import com.jzbwlkj.travelletv.db.HistoryVideoDao;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.service.DownLoadService;
import com.jzbwlkj.travelletv.service.MyService;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.Dialog;
import com.jzbwlkj.travelletv.view.MyGridView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.ResponseBody;

public class PlayActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.jiecao_play_video)
    JCVideoPlayerStandard mVideoView;
    @BindView(R.id.cb_play_buy)
    CheckBox mPlayBuy;
    @BindView(R.id.cb_play_down)
    CheckBox mPlayDown;
    @BindView(R.id.cb_play_collect)
    CheckBox mPlayCollect;
    @BindView(R.id.tv_play_name)
    TextView mPlayName;
    @BindView(R.id.tv_play_des)
    TextView mPlayDes;
    @BindView(R.id.gv_play_select)
    MyGridView mGridView;
    @BindView(R.id.sv_play)
    ScrollView scrollView;

    private HistoryVideoDao historyVideoDao;
    private PlayGvAdapter mAdapter;
    private List<PlayBean.DataBean.JishuBean> mList;
    private DownDaoDao dao;
    private List<DownDao> list;

    private String video_id;
    private Timer timer;
    private PlayBean.DataBean dataBean;
    private int isCollect, isPur, ji = 1, state;//是否购买  是否收藏

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mVideoView.startButton.performClick();
            showDialog();
            mVideoView.release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {

        video_id = getIntent().getStringExtra("id");
        timer = new Timer();
        historyVideoDao = App.getDaoInstant().getHistoryVideoDao();
        dao = App.getDaoInstant().getDownDaoDao();
        mList = new ArrayList<>();
        mAdapter = new PlayGvAdapter(this, mList);
    }

    @Override
    protected void initData() {
        this.list = this.dao.queryBuilder().list();
        loadData("1");
    }

    @Override
    protected void setData() {
        mVideoView.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        jcOnClick();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * JieCaoVideoPlayer的点击事件
     */
    private void jcOnClick() {
        mVideoView.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 设置播放页信息
     */
    private void setInfo(PlayBean bean) {
        //设置不缓存
        JCVideoPlayer.SAVE_PROGRESS = false;
        //设置播放视频
        mVideoView.setUp(bean.getData().getMoviepath()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, bean.getData().getTitle());
        //加载缩略图
        Glide.with(App.app).load(URL.BASE_URL + bean.getData().getImgpath()).into(mVideoView.thumbImageView);

        mVideoView.backButton.setVisibility(View.VISIBLE);
        mVideoView.startButton.performClick();

        mPlayName.setText(bean.getData().getTitle());
        mPlayDes.setText(bean.getData().getDescribe());

        isCollect = bean.getData().getIs_collect();
        isPur = bean.getData().getIs_pur();

        //设置收藏状态
        if (isCollect == 0) {
            mPlayCollect.setChecked(false);
        } else if (isCollect == 1) {
            mPlayCollect.setChecked(true);
        }

        //如果该视频还未购买 试看10s
        if (isPur == 0) {
            noVipBuy();
        }
        //设置ScrollView回到顶部
        scrollView.smoothScrollTo(0,20);
    }


    /**
     * 加载视频详情页
     */
    private void loadData(String ji) {
        if (TextUtils.isEmpty(video_id)) {
            return;
        }
        maps.put("video_id", video_id);
        maps.put("jishu", ji);
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.GET_PLAY, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    PlayBean bean = new Gson().fromJson(responseBody.string(), PlayBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Logger.d("播放地址：" + bean.getData().getMoviepath());
                        dataBean = bean.getData();
                        //设置播放信息
                        setInfo(bean);
                        //历史播放，保存到数据库
                        saveHistory(bean);
                        //设置集数
                        mList.clear();
                        mList.addAll(dataBean.getJishu());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 历史播放保存到本地
     */
    private void saveHistory(PlayBean bean) {
        HistoryVideo historyVideo = new HistoryVideo();
        historyVideo.setVideoId(bean.getData().getId());
        historyVideo.setTitle(bean.getData().getTitle());
        historyVideo.setVideoPath(bean.getData().getImgpath());
        historyVideo.setDes(bean.getData().getDescribe());
        historyVideo.setUser(App.user);
        historyVideoDao.insertOrReplace(historyVideo);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.cb_play_buy, R.id.cb_play_down, R.id.cb_play_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_play_buy://购买视频
                if (isPur == 1) {
                    Util.toast("您已购买过该视频");
                    return;
                }
                if (mVideoView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {//如果正在播放  暂停
                    mVideoView.startButton.performClick();
                }
                showBuy();
                break;
            case R.id.cb_play_down://开启服务下载
                if (isPur == 0) {
                    Util.toast("您还未购买本视频，请购买后再下载");
                    return;
                }

                if (this.list.size() > 0)
                {
                    int i = 0;
                    while (i < this.list.size())
                    {
                        if ((((DownDao)this.list.get(i)).getUser().equals(App.user)) && (this.dataBean.getId().equals(((DownDao)this.list.get(i)).getVideoId())))
                        {
                            Util.toast("您已经下载过该视频了");
                            return;
                        }
                        i += 1;
                    }
                }
                downLoad();
                break;
            case R.id.cb_play_collect://收藏
                collect();
                break;
        }
    }

    /**
     * 如果不是vip 试看10s
     */
    private void noVipBuy() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mVideoView.getCurrentPositionWhenPlaying() >= 10000) {
                    handler.sendEmptyMessage(110);
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    /**
     * 收藏
     */
    private void collect() {
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.ADD_COLLECT, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(), CommonBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        if (isCollect == 1) {
                            Toast.makeText(PlayActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                            isCollect = 0;
                        } else if (isCollect == 0) {
                            Toast.makeText(PlayActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            isCollect = 1;
                        }
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 试看结束显示购买对话框
     */
    private void showDialog() {
        new Dialog(this).setMessage("试看结束，购买之后才可继续观看，是否花费" + dataBean.getPrice() + "元购买本视频？如果您是会员或者充值金额达到一定数目，会有打折优惠，具体优惠政策请到用户指南中查看。")
                .setPositiveClick("立即购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyVideo();
                    }
                })
                .setNegativeClick2(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).onKeyDown(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    finish();
                }
                return false;
            }
        }).showDialog();
    }


    /**
     * 购买对话框
     */
    private void showBuy() {
        new Dialog(this).setMessage("是否花费" + dataBean.getPrice() + "元购买本视频？如果您是会员或者充值金额达到一定数目，会有打折优惠，具体优惠政策请到用户指南中查看。")
                .setPositiveClick("立即购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyVideo();
                    }
                }).showDialog();
    }

    /**
     * 购买视频
     */
    private void buyVideo() {
        maps.put("video_id", video_id);
        maps.put("jishu", ji + "");
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.BUY_PLAY, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(), CommonBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {//购买成功
                        Util.toast("购买成功");
                        timer.cancel();
                        loadData(ji+"");
                    } else {
                        if (bean.getCode() == -3) {//余额不足
                            Dialog.rechargeDialog(PlayActivity.this);
                        } else {
                            Util.toast(bean.getMessage());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 开启下载服务
     */
    private void downLoad() {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("data", dataBean);
        startService(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ji = position;//当前集数
        mVideoView.release();
        timer = new Timer();
        loadData(mList.get(position).getJishu());
        mAdapter.setPlay(position);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.getCurrentPositionWhenPlaying() == 0) {
            JCVideoPlayer.releaseAllVideos();
        } else if (mVideoView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING_BUFFERING_START) {//如果正在播放 加载，停止播放
            JCMediaManager.instance().mediaPlayer.pause();
            state = 3;
        } else if (mVideoView.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
            mVideoView.startButton.performClick();
            state = 2;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mVideoView.getCurrentPositionWhenPlaying() > 0) {
            if (state == 3) {
                JCMediaManager.instance().mediaPlayer.start();
            } else if (state == 2) {
                mVideoView.startButton.performClick();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
        timer.cancel();
    }
}
